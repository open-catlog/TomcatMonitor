package model;

import java.util.Date;

public class TomcatModel extends BaseModel {

	// 最大线程数
	private int maxThreads;
	// 当前线程数
	private int currentThreadCount;
	// 繁忙线程数
	private int currentThreadsBusy;
	// 垃圾回收次数
	private long collectionCount;
	// 垃圾回收时间(ms)
	private long collectionTime;
	// 启动时间
	private long startTime;
	// 连续工作时间
	private String uptime;

	public TomcatModel(String server, int maxThreads, int currentThreadCount, int currentThreadsBusy,
			long collectionCount, long collectionTime, long startTime, String uptime) {
		super(server, new Date());
		this.maxThreads = maxThreads;
		this.currentThreadCount = currentThreadCount;
		this.currentThreadsBusy = currentThreadsBusy;
		this.collectionCount = collectionCount;
		this.collectionTime = collectionTime;
		this.startTime = startTime;
		this.uptime = uptime;
	}

	public int getMaxThreads() {
		return maxThreads;
	}

	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public int getCurrentThreadCount() {
		return currentThreadCount;
	}

	public void setCurrentThreadCount(int currentThreadCount) {
		this.currentThreadCount = currentThreadCount;
	}

	public int getCurrentThreadsBusy() {
		return currentThreadsBusy;
	}

	public void setCurrentThreadsBusy(int currentThreadsBusy) {
		this.currentThreadsBusy = currentThreadsBusy;
	}

	public long getCollectionCount() {
		return collectionCount;
	}

	public void setCollectionCount(long collectionCount) {
		this.collectionCount = collectionCount;
	}

	public long getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(long collectionTime) {
		this.collectionTime = collectionTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

}
