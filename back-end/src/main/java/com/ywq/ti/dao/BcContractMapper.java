package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.BcContract;

public interface BcContractMapper {

	BcContract selectToken(String contractAddress);
	
	Long totalContracts(Map<String,Object> param);
	
	List<BcContract> selectContractListPage(Map<String,Object> param);
    
	int insertContract(BcContract contract);
	
	int insertContractBatch(List<BcContract> contractList);
	
}