package com.pda.nettycs.tcpdump;

import com.google.inject.Inject;
import com.pda.nettycs.client.NettyClient;
import com.pda.nettycs.client.Target;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ClientTask extends NettyClient implements Runnable {
	
	public void run() {
		try {
			super.run();
		} catch (InterruptedException e) {
			log.error(e);
			Thread.currentThread().interrupt();
			throw new TcpDumpRuntimeException(e);
		}
	}

	public void shutDown() {
		Implementar
	}
	
	
}
