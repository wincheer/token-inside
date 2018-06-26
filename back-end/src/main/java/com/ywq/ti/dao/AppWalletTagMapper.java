package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.AppWalletTag;

public interface AppWalletTagMapper {

	AppWalletTag selectTag(Long id);
	
	List<AppWalletTag> selectTagList(AppWalletTag record);
	
	Long totalTags(Map<String,Object> param);
	List<AppWalletTag> selectTagListPage(Map<String, Object> map);
	
	void deleteTag(Long id);

    void insertTag(AppWalletTag record);

    void updateTag(AppWalletTag record);
}