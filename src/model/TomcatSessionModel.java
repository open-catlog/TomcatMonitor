package model;

public class TomcatSessionModel extends BaseModel {

	// sessions
	private String   context;
	// 最大会话数
	private long	 maxActiveSessions;
	// 活动会话数
	private long 	 activeSessions;
	// 创建会话数
	private long 	 sessionCounter;
	
	public TomcatSessionModel(String server, String context, long maxActiveSessions, long activeSessions, long sessionCounter) {
		super(server, System.currentTimeMillis());
		this.context = context;
		this.maxActiveSessions = maxActiveSessions;
		this.activeSessions = activeSessions;
		this.sessionCounter = sessionCounter;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public long getMaxActiveSessions() {
		return maxActiveSessions;
	}

	public void setMaxActiveSessions(long maxActiveSessions) {
		this.maxActiveSessions = maxActiveSessions;
	}

	public long getActiveSessions() {
		return activeSessions;
	}

	public void setActiveSessions(long activeSessions) {
		this.activeSessions = activeSessions;
	}

	public long getSessionCounter() {
		return sessionCounter;
	}

	public void setSessionCounter(long sessionCounter) {
		this.sessionCounter = sessionCounter;
	}

}
