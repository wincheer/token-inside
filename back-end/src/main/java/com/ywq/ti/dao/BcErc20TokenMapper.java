package com.ywq.ti.dao;

import java.util.List;
import java.util.Map;

import com.ywq.ti.entity.BcErc20Token;

public interface BcErc20TokenMapper {

	BcErc20Token selectToken(String tokenAddress);
	
	Long totalTokens(Map<String,Object> param);
	
	List<BcErc20Token> selectTokenListPage(Map<String,Object> param);
	
	Long selectMaxToken();
    
	int insertToken(BcErc20Token token);
	
	int insertTokenBatch(List<BcErc20Token> tokenList);
    
	int updateToken(BcErc20Token token);
	
}