package com.pda.nettycs.server.time;

import com.google.inject.AbstractModule;
import com.pda.nettycs.server.ServerHandler;

import io.netty.channel.ChannelHandler;

public class TimeServerModule extends AbstractModule {
	  public void configure() {
		  bind(ChannelHandler.class).annotatedWith(ServerHandler.class).to(TimeServerHandler.class);
	  }
	}