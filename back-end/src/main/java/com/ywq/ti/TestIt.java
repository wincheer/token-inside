package com.ywq.ti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.ens.EnsResolver;
import org.web3j.ens.contracts.generated.PublicResolver;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class TestIt {
	private static final Logger log = LoggerFactory.getLogger(TestIt.class);

	public static void main(String[] args) throws Exception {
		
		Web3j web3j = Web3j.build(new HttpService("https://mainnet.infura.io/gKGM2GtuAn4ubcw1pRBp"));
		
		EnsResolver resolver = new EnsResolver(web3j);
		String ens = "facebook";
		String addr = "0xf3da1ec67a5ef61d55a5d9992d0807fe58b986b670ddce29e4c6e4762a4a1b21";
		//PublicResolver pr = resolver.obtainPublicResolver("facebook.eth");
		
		
		System.out.println(resolver.resolve(ens));
		
	}
}
