package com.ywq.ti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock.Block;

import com.ywq.ti.common.TxType;
import com.ywq.ti.common.Web3jConfig;
import com.ywq.ti.service.EthBcService;

/**
 * <b>版权信息 :</b> 2018，杨文清<br/>
 * <b>功能描述 :</b> 解析以太坊区块链的交易<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年5月22日 下午6:28:19 | 创建
 */
@Component
public class EtherumParser {
	
	private static final Logger log = LoggerFactory.getLogger(EtherumParser.class);
	
	@Value("${web3j.client-address}")
	private String WEB3_CLIENT_URL;
	
	@Autowired
	private EthBcService service;
	
	//@Scheduled(fixedDelay = 5 * 1000)
	public void parseBlock() throws Exception{
		
		Web3jConfig web3jConfig = new Web3jConfig();
		Web3j web3j = Web3j.build(web3jConfig.buildService(WEB3_CLIENT_URL));
		//Web3j web3j = Web3j.build(new WindowsIpcService("\\\\.\\pipe\\geth.ipc")); //IPC
		 
		Long maxBlockBumber = (web3j.ethBlockNumber().send().getBlockNumber()).longValue();//最新的区块高度
		log.info("最新区块高度：" + maxBlockBumber);
		long currentBlockNumber = service.currentBlockNumber(TxType.ETH).getBlockNumber(); // 读取待处理block编号
		log.info("待已处理区块：" + currentBlockNumber);
		
		while (currentBlockNumber <= maxBlockBumber) {
			//待处理block
			Long t_web3_0 = System.currentTimeMillis();
			DefaultBlockParameter blockParam = new DefaultBlockParameterNumber(currentBlockNumber);
			Block currentBlock = web3j.ethGetBlockByNumber(blockParam, true).send().getResult();
			Long t_web3_1 = System.currentTimeMillis();
			//处理当前区块
			Long t_db_0 = System.currentTimeMillis();
			service.handleBlock(web3j, currentBlock);
			Long t_db_1 = System.currentTimeMillis();
			log.info("已处理区块：" + currentBlockNumber + "/" + maxBlockBumber + " - Web3Read: " + (t_web3_1 - t_web3_0) + " - DBWrite: " + (t_db_1 - t_db_0));
			
			currentBlockNumber++;
			while (currentBlockNumber > maxBlockBumber) {
				Thread.sleep(2 * 1000);// 暂停2秒钟
				// 读取最新的区块高度 
				maxBlockBumber = (web3j.ethBlockNumber().send().getBlockNumber()).longValue();	
				log.info("最新区块高度：" + maxBlockBumber);
			}
		}
		
		web3j.shutdown();
		
	}

}
