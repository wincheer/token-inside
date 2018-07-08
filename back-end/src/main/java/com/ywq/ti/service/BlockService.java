package com.ywq.ti.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ywq.ti.common.TxType;
import com.ywq.ti.dao.BcBlockMapper;
import com.ywq.ti.dao.BcCurrentBlockMapper;
import com.ywq.ti.dao.BcErc20TokenMapper;
import com.ywq.ti.dao.BcErc20TransactionMapper;
import com.ywq.ti.dao.BcTransactionMapper;
import com.ywq.ti.entity.BcBlock;
import com.ywq.ti.entity.BcCurrentBlock;
import com.ywq.ti.entity.BcErc20Token;
import com.ywq.ti.entity.BcErc20Transaction;
import com.ywq.ti.entity.BcTransaction;

@Service
@Transactional
public class BlockService {
	
	private static final Logger log = LoggerFactory.getLogger(BlockService.class);
	
	@Autowired
	private BcCurrentBlockMapper currentBlockdao;
	@Autowired
	private BcBlockMapper blockDao;
	@Autowired
	private BcTransactionMapper txDao;
	@Autowired
	private BcErc20TokenMapper tokenDao;
	@Autowired
	private BcErc20TransactionMapper tokenTxDao;

	public Map<String, Object> bcOverview() {
		
		Map<String, Object> overview = new HashMap<String, Object>();
		
		BcCurrentBlock  currentBlock  = currentBlockdao.selectCurrentBlock(TxType.ETH);
		long maxTx = txDao.selectMaxTransaction();
		long maxToken = tokenDao.selectMaxToken();
		long maxTokenTx = tokenTxDao.selectMaxErc20Transaction();
		log.debug("Token tx count :" + maxTokenTx);
		
		overview.put("bestBlock", currentBlock.getBlockNumber());
		overview.put("maxTransaction", maxTx);
		overview.put("maxToken", maxToken);
		overview.put("maxTokenTx", maxTokenTx);
		
		return overview;
	}

	public long totalTokens(Map<String, Object> queryParam) {
		return tokenDao.totalTokens(queryParam);
	}

	public List<BcErc20Token> selectTokenListPage(Map<String, Object> queryParam) {
		
		int pageNo = (Integer) queryParam.get("pageNo");
		int pageSize = (Integer) queryParam.get("pageSize");
		int start = (pageNo - 1) * pageSize;

		queryParam.put("start", start);

		return tokenDao.selectTokenListPage(queryParam);
	}

	public long totalBlocks(Map<String, Object> queryParam) {
		return blockDao.totalBlocks(queryParam);
	}

	public List<BcBlock> selectBlockListPage(Map<String, Object> queryParam) {
		int pageNo = (Integer) queryParam.get("pageNo");
		int pageSize = (Integer) queryParam.get("pageSize");
		int start = (pageNo - 1) * pageSize;

		queryParam.put("start", start);

		return blockDao.selectBlockListPage(queryParam);
	}

	public long totalTxs(Map<String, Object> queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<BcTransaction> selectTxListPage(Map<String, Object> queryParam) {
		// TODO Auto-generated method stub
		return null;
	}

	public long totalBlockTxs(Map<String, Object> queryParam) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<BcErc20Transaction> selectBlockTxListPage(Map<String, Object> queryParam) {
		// TODO Auto-generated method stub
		return null;
	}


}
