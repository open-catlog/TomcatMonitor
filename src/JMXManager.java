import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Properties;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class JMXManager {

	/**
	 * 建立连接
	 * 
	 * @param ip
	 * @param port
	 * @return
	 */
	public static MBeanServerConnection createMBeanServer(String ip, String port) {
		try {
			String jmxURL = "service:jmx:rmi:///jndi/rmi://" + ip + ":" + port + "/jmxrmi";
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
			MBeanServerConnection mbsc = connector.getMBeanServerConnection();
			System.out.println("连接成功");
			return mbsc;
		} catch (Exception e) {
			System.err.println(ip + "的中间件不可以达");
		}
		return null;
	}

	public static Properties getJMXConf() {
		try {
			Properties properties = new Properties();
			InputStream in = TomcatMonitor.class.getClassLoader()
					.getResourceAsStream("JMXConf.properties");
			properties.load(in);
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void getThreadPoolInfo(MBeanServerConnection mbsc, String threadPool) {
		try {
			ObjectName threadPoolObjName = new ObjectName(
					"Catalina:type=ThreadPool,name=" + threadPool);
			System.out.println("最大线程数:"
						+ mbsc.getAttribute(threadPoolObjName, "maxThreads"));
			System.out.println("当前线程数:" 
						+ mbsc.getAttribute(threadPoolObjName, "currentThreadCount"));
			System.out.println("繁忙线程数:" 
						+ mbsc.getAttribute(threadPoolObjName, "currentThreadsBusy"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getGarbageCollectionInfo(MBeanServerConnection mbsc, String garbageCollector) {
		try {
			ObjectName garbageCollectorObjName = new ObjectName(
					"java.lang:type=GarbageCollector,name=" + garbageCollector);
			System.out.println("垃圾回收次数:"
					+ mbsc.getAttribute(garbageCollectorObjName, "CollectionCount"));
			System.out.println("垃圾回收时间:" 
					+ mbsc.getAttribute(garbageCollectorObjName, "CollectionTime"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getTimeSpanInfo(MBeanServerConnection mbsc) {
		try {
			ObjectName runtimeObjName = new ObjectName(
					"java.lang:type=Runtime");
			Date starttime = new Date((Long) mbsc.getAttribute(runtimeObjName, "StartTime"));
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Long timespan = (Long) mbsc.getAttribute(runtimeObjName, "Uptime");
			System.out.println("启动时间:" 
					+ df.format(starttime));
			System.out.println("连续工作时间:"
						+ formatTimeSpan(timespan));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getSessionInfo(MBeanServerConnection mbsc) {
		try {
			ObjectName managerObjName = new ObjectName(  
	                "Catalina:type=Manager,*");  
	        Set<ObjectName> s = mbsc.queryNames(managerObjName, null);  
	        for (ObjectName obj : s) {  
	            System.out.println("应用名:" + obj.getKeyProperty("context"));  
	            ObjectName objname = new ObjectName(obj.getCanonicalName());  
	            System.out.println("允许最大会话数:"  
	                    + mbsc.getAttribute(objname, "maxActiveSessions"));  
	            System.out.println("活动会话数:"  
	                    + mbsc.getAttribute(objname, "activeSessions"));  
	            System.out.println("创建会话数:"  
	                    + mbsc.getAttribute(objname, "sessionCounter"));  
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String formatTimeSpan(long span) {
		span = span / 1000;
		long seconds = span % 60;
		span = span / 60;
		long mins = span % 60;
		span = span / 60;
		long hours = span % 24;
		span = span / 24;
		long days = span;
		return (new Formatter()).format("%1$d天 %2$02d:%3$02d:%4$02d", 
				days, hours, mins, seconds).toString();
	}

}
