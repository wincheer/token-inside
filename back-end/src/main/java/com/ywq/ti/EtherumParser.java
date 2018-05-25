package com.ywq.ti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.http.HttpService;

import com.ywq.ti.common.TxType;
import com.ywq.ti.servie.EthBcSrvice;

/**
 * <b>版权信息 :</b> 2018，杨文清<br/>
 * <b>功能描述 :</b> 解析以太坊区块链的交易<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年5月22日 下午6:28:19 | 创建
 */
public class EtherumParser implements ApplicationRunner{

	private static final Logger log = LoggerFactory.getLogger(EtherumParser.class);
	
	@Value("${web3j.client-address}")
	private String WEB3_CLIENT_URL;
	
	@Autowired
	private EthBcSrvice service;
	
	@Override
	public void run(ApplicationArguments arg) throws Exception {
		
		Web3j web3j = Web3j.build(new HttpService(WEB3_CLIENT_URL));
		 
		Long maxBlockBumber = (web3j.ethBlockNumber().send().getBlockNumber()).longValue();//最新的区块高度
		log.info("最新区块高度：" + maxBlockBumber);
		long currentBlockNumber = service.currentBlockNumber(TxType.ETH).getBlockNumber(); // 读取待处理block编号
		log.info("待已处理区块：" + currentBlockNumber);
		
		while (currentBlockNumber <= maxBlockBumber) {
			//待处理block
			DefaultBlockParameter blockParam = new DefaultBlockParameterNumber(currentBlockNumber);
			Block currentBlock = web3j.ethGetBlockByNumber(blockParam, true).send().getResult();
			//处理当前区块
			service.handleBlock(web3j,currentBlock);
			log.info("已处理区块：" + currentBlockNumber);
			
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
