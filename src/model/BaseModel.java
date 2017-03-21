package model;

public class BaseModel {

	//服务器
	private String  server;
	//创建时间
	private long	createAt;
	
	public BaseModel(String server, long createAt) {
		super();
		this.server = server;
		this.createAt = createAt;
	}
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public long getCreateAt() {
		return createAt;
	}
	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}
	
}
