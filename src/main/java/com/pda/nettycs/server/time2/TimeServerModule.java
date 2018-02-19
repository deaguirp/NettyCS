package com.pda.nettycs.server.time2;

import com.google.inject.AbstractModule;

import io.netty.channel.ChannelHandler;

public class TimeServerModule extends AbstractModule {
	  public void configure() {
		  bind(ChannelHandler.class).to(TimeServerHandler.class);
	  }
	}