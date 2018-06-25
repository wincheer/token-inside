package com.ywq.ti.dao;

import com.ywq.ti.entity.AppWalletTag;

public interface AppWalletTagMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AppWalletTag record);

    int insertSelective(AppWalletTag record);

    AppWalletTag selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppWalletTag record);

    int updateByPrimaryKey(AppWalletTag record);
}