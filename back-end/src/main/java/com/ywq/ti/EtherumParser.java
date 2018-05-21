package com.ywq.ti;

import java.util.Date;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class EtherumParser implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments arg) throws Exception {
		
		long maxBlockBumber = 10; // 读取最新的区块编号
		long currentBlockNumber = 0; // 读取已更新block_number值
		while (currentBlockNumber <= maxBlockBumber) {
			// 事务:插入block，插入tx，更新已处理完block_number值
			System.out.println("当前处理完的Block = " + currentBlockNumber);
			currentBlockNumber++;
			
			while (currentBlockNumber > maxBlockBumber) {
				// 重新获取max的值 ,max = 10;
				System.out.printf("已处理完毕读取的Block %s\n",maxBlockBumber);
				maxBlockBumber ++;
				// 暂停2秒钟
				Thread.sleep(2 * 1000);
				System.out.printf("重新读取最新的的Block %s",maxBlockBumber);
				System.out.println(" " + new Date(System.currentTimeMillis()));
			}
		}
		
	}

}
