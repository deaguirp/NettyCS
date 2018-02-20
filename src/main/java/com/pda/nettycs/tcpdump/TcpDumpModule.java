package com.pda.nettycs.tcpdump;

import com.google.inject.AbstractModule;
import com.pda.nettycs.client.ClientHandler;
import com.pda.nettycs.client.Target;
import com.pda.nettycs.server.ServerHandler;

import io.netty.channel.ChannelHandler;

public class TcpDumpModule extends AbstractModule {
	private Target target;

	public TcpDumpModule(Target t) {
		this.target = t;
	}

	public void configure() {
		bind(ChannelHandler.class).annotatedWith(ServerHandler.class).to(TcpDumpServerHandler.class);
		bind(ChannelHandler.class).annotatedWith(ClientHandler.class).to(TcpDumpClientHandler.class);
		bind(Target.class).toInstance(this.target);
	}
}