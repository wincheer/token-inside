package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.AppUser;

public interface AppUserMapper {

	AppUser selectUser(Long id);
	
	List<AppUser> selectUserList(AppUser record);
	
	Long totalUsers(Map<String,Object> param);
	List<AppUser> selectUserListPage(Map<String, Object> map);
	
    void deleteUser(AppUser record);
    
    Long insertUser(AppUser record);
    
    void updateUser(AppUser record);
}