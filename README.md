# TomcatMonitor

- 项目结构

	- src/：存储项目源码
  - src/main/：主程序入口和工具类
	- src/jmx/：获取 Tomcat 相关信息
	- src/model/：数据模型层
	- src/dao：用于定义数据库操作

- 用到的技术

	- JMX：进行 Tomcat 监控
	- 定时器：定时采集 Tomcat 信息
	- mongodb：使用 mongodb 作为数据库
  - 线程池：通过线程池调度来分配更好利用CPU资源

- 能够获取到的信息

	- ThreadPool：最大线程数，当前线程数，繁忙线程数
	- GarbageCollector：垃圾回收总次数，近似累积回收时间
	- Runtime：工作时间
	- Session：获取会话信息

- 运行步骤

	- 环境准备：首先确保你的本机拥有 Java 环境，其次是由于在该项目中有涉及 Mongo 数据库的操作，所以在本地还有打开 Mongo 数据库才可以成功运行
	- 运行命令：由于已经封装好了运行脚本，可以直接在该根目录下运行 ./start.sh 来进行数据获取
	- 日志信息：运行日志可以在 nohup.out 中进行查看
