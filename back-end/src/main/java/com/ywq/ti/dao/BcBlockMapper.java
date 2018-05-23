package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.BcBlock;

public interface BcBlockMapper {

    BcBlock selectBlock(String hash);
    
    List<BcBlock> selectBlockList(BcBlock block);
    
    Long totalBlocks(Map<String,Object> param);
    
    List<BcBlock> selectBlockListPage(Map<String,Object> param);
    
    void insertBlock(BcBlock block);
    
}