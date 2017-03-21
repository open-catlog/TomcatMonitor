# TomcatMonitor

- 项目结构

	- src/：存储项目源码
	- src/jmx/：获取 Tomcat 相关信息
	- src/model/：数据模型层
	- src/dao：用于定义数据库操作
	
- 用到的技术

	- JMX：进行 Tomcat 监控
	- 多线程：为每一台监控服务器开启一个线程
	- mongodb：使用 mongodb 作为数据库
	
- 能够获取到的信息

	- ThreadPool：最大线程数，当前线程数，繁忙线程数
	- GarbageCollector：垃圾回收总次数，近似累积回收时间
	- Runtime：工作时间
	- Session：获取会话信息
	- Heap：最大堆内存



