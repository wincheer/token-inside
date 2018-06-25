package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.AppMessage;

public interface AppMessageMapper {

	AppMessage selectMessage(Long id);
	
	List<AppMessage> selectMessageList(AppMessage msg);
	
	Long totalMessages(Map<String,Object> param);
	List<AppMessage> selectMessageListPage(Map<String, Object> map);
	
	void insertMessageBatch(List<AppMessage> msgList);
	
	void updateMessage(AppMessage msg);

}