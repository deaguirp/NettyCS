package com.pda.nettycs.server.echo;

import com.google.inject.AbstractModule;
import com.pda.nettycs.server.ServerHandler;

import io.netty.channel.ChannelHandler;

public class EchoServerModule extends AbstractModule {
	  public void configure() {
		  bind(ChannelHandler.class).annotatedWith(ServerHandler.class).to(EchoServerHandler.class);
	  }
	}