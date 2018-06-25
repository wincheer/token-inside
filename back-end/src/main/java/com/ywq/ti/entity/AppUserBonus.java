package com.ywq.ti.entity;

import java.util.Date;

public class AppUserBonus {

	private Long id;
    private Long userId;
    private String eventMsg;
    private Integer bonus;
    private Date create;
    
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
	public String getEventMsg() {
		return eventMsg;
	}
	public void setEventMsg(String eventMsg) {
		this.eventMsg = eventMsg;
	}
	public Integer getBonus() {
		return bonus;
	}
	public void setBonus(Integer bonus) {
		this.bonus = bonus;
	}
	public Date getCreate() {
		return create;
	}
	public void setCreate(Date create) {
		this.create = create;
	}


}