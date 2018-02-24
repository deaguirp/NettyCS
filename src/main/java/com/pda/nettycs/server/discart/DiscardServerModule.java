package com.pda.nettycs.server.discart;

import com.google.inject.AbstractModule;
import com.pda.nettycs.server.ServerHandler;

import io.netty.channel.ChannelHandler;

public class DiscardServerModule extends AbstractModule {
	  public void configure() {
		  bind(ChannelHandler.class).annotatedWith(ServerHandler.class).to(DiscardServerHandler.class);
	  }
	}