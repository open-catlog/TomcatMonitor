package main;
import java.util.Formatter;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.MBeanServerConnection;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import dao.DBManager;
import jmx.JMXManager;
import model.TomcatModel;
import model.TomcatSessionModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TomcatMonitor {

	public static void main(String[] args) {

		try {

			//获取JMX配置
			Properties props = Utils.getJMXConf();
			String[] ips = props.getProperty("ip").split(",");
			String[] ports = props.getProperty("port").split(",");
			String[] threadPools = props.getProperty("threadPool").split(",");
			String garbageCollector = props.getProperty("garbageCollector");
			String[] sessionsIgnore = props.getProperty("sessionsIgnore").split(",");
			String mongodb = props.getProperty("mongdb");
			int dbPort = Integer.parseInt(props.getProperty("dbPort"));
			//获取cpu的个数
			int cpuCount = Runtime.getRuntime().availableProcessors();
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(cpuCount + 1);
			
			MongoCollection<Document> tomcats = DBManager.getDBCollection(mongodb, dbPort, "tomcats");
			MongoCollection<Document> tomcatSessions = DBManager.getDBCollection(mongodb, dbPort, "tomcatsessions");

			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					for (int i = 0; i < ips.length; i++) {
						final int index = i;
						fixedThreadPool.execute(new Runnable() {
							public void run() {
								// 建立连接
								MBeanServerConnection mbsc = JMXManager.createMBeanServer(ips[index], ports[index]);
								// ThreadPool
								Map<String, Integer> threadPoolInfo = JMXManager.getThreadPoolInfo(mbsc, threadPools[index]);
								// GarbageCollector
								Map<String, Long> gcInfo = JMXManager.getGarbageCollectionInfo(mbsc, garbageCollector);
								// Runtime
								Map<String, Long> timeSpanInfo = JMXManager.getTimeSpanInfo(mbsc);
								// Session
								Map<String, Map<String, String>> sessionsInfo = JMXManager.getSessionInfo(mbsc, sessionsIgnore);

								TomcatModel tomcatModel = new TomcatModel(
										ips[index],
										threadPoolInfo.get("maxThreads"),
										threadPoolInfo.get("currentThreadCount"), 
										threadPoolInfo.get("currentThreadsBusy"),
										gcInfo.get("collectionCount"), 
										gcInfo.get("collectionTime"),
										timeSpanInfo.get("startTime"), 
										formatTimespan(timeSpanInfo.get("uptime")));
								DBManager.insert(tomcats, tomcatModel);
								
								if (sessionsInfo != null && !sessionsInfo.isEmpty()) {
									for(Map.Entry<String, Map<String, String>> sessionInfo: sessionsInfo.entrySet()) {
										Map<String, String> sessions = sessionInfo.getValue();
										TomcatSessionModel tomcatSessionModel = new TomcatSessionModel(
												ips[index], sessionInfo.getKey(), Integer.parseInt(sessions.get("maxActiveSessions")),
												Integer.parseInt(sessions.get("activeSessions")), Long.parseLong(sessions.get("sessionCounter")));
										DBManager.insert(tomcatSessions, tomcatSessionModel);
									}
								}
							}
						});
					}
				}
				
			}, 0, 3000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String formatTimespan(long span) {
		long minseconds = span % 1000;  
        span = span / 1000;  
        long seconds = span % 60;  
        span = span / 60;  
        long mins = span % 60;  
        span = span / 60;  
        long hours = span % 24;  
        span = span / 24;  
        long days = span;  
        return (new Formatter()).format("%1$d天 %2$02d:%3$02d:%4$02d.%5$03d",  
                days, hours, mins, seconds, minseconds).toString(); 
	}
}
