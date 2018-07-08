package com.ywq.ti.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import com.ywq.ti.entity.BcBlock;
import com.ywq.ti.entity.BcErc20Token;
import com.ywq.ti.entity.BcErc20Transaction;
import com.ywq.ti.entity.BcTransaction;
import com.ywq.ti.service.BlockService;
import com.ywq.ti.socket.SpringWebSocketHandler;

@RestController
public class BlockAction {
	
	private static final Logger log = LoggerFactory.getLogger(BlockAction.class);
	
	@Autowired
	private BlockService service;
	@Autowired
	private SpringWebSocketHandler myHandler;
	
	@RequestMapping(value = "/addFoo1", method = RequestMethod.GET)
	public void addFoo1() {
		myHandler.sendMessageToUser(18602010502L,new TextMessage("这是来自遥远的第三方发送的消息"));
	}
	
	@RequestMapping(value = "/bcOverview", method = RequestMethod.POST)
	public Map<String,Object> bcOverview() {
		Map<String,Object> overview = service.bcOverview();
		log.debug(overview.toString());
		return overview;
	}
	
	@RequestMapping(value = "/selectTokenListPage", method = RequestMethod.POST)
	public Map<String,Object> selectTokenListPage(@RequestBody Map<String, Object> queryParam) {
		
		log.debug("分页查询消息");	
		
		long total = service.totalTokens(queryParam);
		List<BcErc20Token> rows = service.selectTokenListPage(queryParam);
		
		Map<String, Object> result = new HashMap<String, Object>();
		long pageSize = Long.valueOf(queryParam.get("pageSize").toString()) ;
		long totalPages = (total + pageSize -1)/pageSize;
		result.put("totalPages", totalPages);
		result.put("rows", rows);

		return result;
	}
	
	@RequestMapping(value = "/selectBlockListPage", method = RequestMethod.POST)
	public Map<String,Object> selectBlockListPage(@RequestBody Map<String, Object> queryParam) {
		
		long total = service.totalBlocks(queryParam);
		List<BcBlock> rows = service.selectBlockListPage(queryParam);
		
		Map<String, Object> result = new HashMap<String, Object>();
		long pageSize = Long.valueOf(queryParam.get("pageSize").toString()) ;
		long totalPages = (total + pageSize -1)/pageSize;
		result.put("totalPages", totalPages);
		result.put("rows", rows);

		return result;
	}
	
	@RequestMapping(value = "/selectTxListPage", method = RequestMethod.POST)
	public Map<String,Object> selectTxListPage(@RequestBody Map<String, Object> queryParam) {
		
		long total = service.totalTxs(queryParam);
		List<BcTransaction> rows = service.selectTxListPage(queryParam);
		
		Map<String, Object> result = new HashMap<String, Object>();
		long pageSize = Long.valueOf(queryParam.get("pageSize").toString()) ;
		long totalPages = (total + pageSize -1)/pageSize;
		result.put("totalPages", totalPages);
		result.put("rows", rows);

		return result;
	}
	
	@RequestMapping(value = "/selectBlockTxListPage", method = RequestMethod.POST)
	public Map<String,Object> selectBlockTxListPage(@RequestBody Map<String, Object> queryParam) {
		
		long total = service.totalBlockTxs(queryParam);
		List<BcErc20Transaction> rows = service.selectBlockTxListPage(queryParam);
		
		Map<String, Object> result = new HashMap<String, Object>();
		long pageSize = Long.valueOf(queryParam.get("pageSize").toString()) ;
		long totalPages = (total + pageSize -1)/pageSize;
		result.put("totalPages", totalPages);
		result.put("rows", rows);

		return result;
	}
	

}
