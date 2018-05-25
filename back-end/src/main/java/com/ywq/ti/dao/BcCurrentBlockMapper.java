package com.ywq.ti.dao;

import com.ywq.ti.entity.BcCurrentBlock;

public interface BcCurrentBlockMapper {

	BcCurrentBlock selectCurrentBlock(String bcType);

    int updateCurrentBlock(BcCurrentBlock record);
    
}