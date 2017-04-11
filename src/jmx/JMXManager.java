package jmx;
import java.util.HashMap;
import java.util.Map;
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
	public static JMXConnector createConnection(String ip, String port) {
		try {
			String jmxURL = "service:jmx:rmi:///jndi/rmi://" + ip + ":" + port + "/jmxrmi";
			JMXServiceURL serviceURL = new JMXServiceURL(jmxURL);
			JMXConnector connector = JMXConnectorFactory.connect(serviceURL);
			return connector;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取线程池信息
	 * @param mbsc
	 * @param threadPool
	 */
	public static Map<String, Integer> getThreadPoolInfo(MBeanServerConnection mbsc, String threadPool) {
		try {
			ObjectName threadPoolObjName = new ObjectName(
					"Catalina:type=ThreadPool,name=" + threadPool);
			Map<String, Integer> threadPoolInfo = new HashMap<String, Integer>(); 
			threadPoolInfo.put("maxThreads", (Integer) mbsc.getAttribute(threadPoolObjName, "maxThreads"));
			threadPoolInfo.put("currentThreadCount", (Integer) mbsc.getAttribute(threadPoolObjName, "currentThreadCount"));
			threadPoolInfo.put("currentThreadsBusy", (Integer) mbsc.getAttribute(threadPoolObjName, "currentThreadsBusy"));
			return threadPoolInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取垃圾回收信息
	 * @param mbsc
	 * @param garbageCollector
	 */
	public static Map<String, Long> getGarbageCollectionInfo(MBeanServerConnection mbsc, String garbageCollector) {
		try {
			ObjectName garbageCollectorObjName = new ObjectName(
					"java.lang:type=GarbageCollector,name=" + garbageCollector);
			Map<String, Long> gcInfo = new HashMap<String, Long>(); 
			gcInfo.put("collectionCount", (Long) mbsc.getAttribute(garbageCollectorObjName, "CollectionCount"));
			gcInfo.put("collectionTime", (Long) mbsc.getAttribute(garbageCollectorObjName, "CollectionTime"));
			return gcInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取Tomcat工作时间
	 * @param mbsc
	 * @return
	 */
	public static Map<String, Long> getTimeSpanInfo(MBeanServerConnection mbsc) {
		try {
			ObjectName runtimeObjName = new ObjectName(
					"java.lang:type=Runtime");
			Map<String, Long> timeSpanInfo = new HashMap<String, Long>(); 
			timeSpanInfo.put("startTime", (Long) mbsc.getAttribute(runtimeObjName, "StartTime"));
			timeSpanInfo.put("uptime", (Long) mbsc.getAttribute(runtimeObjName, "Uptime"));
			return timeSpanInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取会话信息
	 * @param mbsc
	 * @param sessionsIgnore
	 */
	public static Map<String, Map<String, String>> getSessionInfo(MBeanServerConnection mbsc, String[] sessionsIgnore) {
		try {
			ObjectName managerObjName = new ObjectName(  
	                "Catalina:type=Manager,*");  
			Map<String, Map<String, String>> sessionInfo = new HashMap<String, Map<String, String>>(); 
	        Set<ObjectName> s = mbsc.queryNames(managerObjName, null);
	        boolean isIgnore = false;
	        for (ObjectName obj : s) {
	        	isIgnore = false;
	        	for (int i = 0; i < sessionsIgnore.length; i++) {
	        		if (obj.getKeyProperty("context").equals(sessionsIgnore[i])) {
	        			isIgnore = true;
	        			break;
	        		}
	        	}
	        	if (!isIgnore) {
	        		Map<String, String> sessions = new HashMap<String, String>();
	        		ObjectName objname = new ObjectName(obj.getCanonicalName());
	        		sessions.put("maxActiveSessions", mbsc.getAttribute(objname, "maxActiveSessions").toString());
	        		sessions.put("activeSessions", mbsc.getAttribute(objname, "activeSessions").toString());
	        		sessions.put("sessionCounter", mbsc.getAttribute(objname, "sessionCounter").toString());
	        		sessionInfo.put(obj.getKeyProperty("context"), sessions);
		            
	        	}
	        }
	        return sessionInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
