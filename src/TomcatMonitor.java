import java.util.Properties;

import javax.management.MBeanServerConnection;

public class TomcatMonitor {

	public static void main(String[] args) {
		
		try {
			
			//获取JMX配置
			Properties props = JMXManager.getJMXConf();
			String ip = props.getProperty("ip");
			String port = props.getProperty("port");
			String threadPool = props.getProperty("threadPool");
			String garbageCollector = props.getProperty("garbageCollector");
			
			// 建立连接
			MBeanServerConnection mbsc = JMXManager.createMBeanServer(ip, port);
			
			//ThreadPool
			JMXManager.getThreadPoolInfo(mbsc, threadPool);
			
			//GarbageCollector
			JMXManager.getGarbageCollectionInfo(mbsc, garbageCollector);
			
			//Runtime
			JMXManager.getTimeSpanInfo(mbsc);
			
			//Session
			JMXManager.getSessionInfo(mbsc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}
