package com.nova.madrid.everisdarmytasksms.controller;

public class AddResponse {
	private long id;
	private String msg;
	
	public AddResponse(long id,String mgs) {
		this.id = id;
		this.msg = msg;
	}
	
	public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
