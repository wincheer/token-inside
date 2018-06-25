package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.AppUserBonus;

public interface AppUserBonusMapper {

	AppUserBonus selectBonus(Long id);
	
	List<AppUserBonus> selectBonusList(AppUserBonus bonus);
	
	Long totalBonus(Map<String,Object> param);
	List<AppUserBonus> selectBonusListPage(Map<String, Object> map);
	
	void deleteBonus(AppUserBonus bonus);
	
	void insertBonus(AppUserBonus bonus);
	
	void updateBonus(AppUserBonus bonus);
}