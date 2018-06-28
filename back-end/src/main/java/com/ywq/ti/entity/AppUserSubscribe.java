package com.ywq.ti.entity;

import java.util.Date;

public class AppUserSubscribe {

    private Long id;
    private Long userId;
    private String address;
    private String topic;
    private Date update;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Date getUpdate() {
		return update;
	}
	public void setUpdate(Date update) {
		this.update = update;
	}
	
	public static void main(String[] args) {
		String s = "0x2a65aca4d5fc5b5c859090a6c34d164135398226";
		System.out.println(s.length());
	}

}