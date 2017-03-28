package model;

import java.util.Date;

public class BaseModel {

	//服务器
	private String  server;
	//创建时间
	private Date	create_at;
	
	public BaseModel(String server, Date create_at) {
		super();
		this.server = server;
		this.create_at = create_at;
	}
	
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}
}
