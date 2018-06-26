package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.AppUserSubscribe;

public interface AppUserSubscribeMapper {

	AppUserSubscribe selectSubscribe(Long id);
	
	List<AppUserSubscribe> selectSubscribeList(AppUserSubscribe record);
	
	Long totalSubscribes(Map<String,Object> param);
	List<AppUserSubscribe> selectSubscribeListPage(Map<String, Object> map);
	
	void deleteSubscribe(Long id);

    void insertSubscribe(AppUserSubscribe record);

    void updateSubscribe(AppUserSubscribe record); //根据主键跟新内容
    
    void updateSubscribeDate(AppUserSubscribe record); //按条件跟新订阅时间
    
}