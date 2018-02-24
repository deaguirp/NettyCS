package com.pda.nettycs.tcpdump;

import com.pda.nettycs.client.NettyClient;

import io.netty.bootstrap.Bootstrap;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TcpDumpMainClient extends NettyClient implements Runnable {
	
	private TcpDumpServerHandler serverHandler;

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
		getWorkerGroup().shutdownGracefully();
	}

	public TcpDumpClientHandler getClientHandler() {
		return (TcpDumpClientHandler) getHandler();
	}

	public void setServerHandler(TcpDumpServerHandler sh) {
		this.serverHandler = sh;
	}
	
	public TcpDumpServerHandler getServerHandler() {
		return serverHandler;
	}
	
	@Override
	protected Bootstrap initBootstrap() {
		Bootstrap b = super.initBootstrap();
		getClientHandler().setMainClient(this);
		return b;
	}

}
