package com.ywq.ti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class EtherumParser implements ApplicationRunner{

	private static final Logger log = LoggerFactory.getLogger(EtherumParser.class);
	
	public static final String INFURA_URL = "https://mainnet.infura.io/gKGM2GtuAn4ubcw1pRBp";
	public static final String DEFAULT_URL = "http://localhost:8545/";
	
	@Override
	public void run(ApplicationArguments arg) throws Exception {
		Web3j web3j = Web3j.build(new HttpService(INFURA_URL));
		// 读取最新的区块编号
		Long maxBlockBumber = (web3j.ethBlockNumber().send().getBlockNumber()).longValue();
		long currentBlockNumber = 0; // 读取已更新block_number值
		while (currentBlockNumber <= maxBlockBumber) {
			// 事务:插入block，插入tx，更新已处理完block_number值
			log.info("当前处理完的Block = " + currentBlockNumber);
			currentBlockNumber++;
			
			while (currentBlockNumber > maxBlockBumber) {
				// 重新获取max的值 ,max = 10;
				System.out.printf("已处理完毕读取的Block %s\n",maxBlockBumber);
				maxBlockBumber ++;
				// 暂停2秒钟
				Thread.sleep(2 * 1000);
				System.out.printf("重新读取最新的的Block %s",maxBlockBumber);
			}
		}
		
	}

}
