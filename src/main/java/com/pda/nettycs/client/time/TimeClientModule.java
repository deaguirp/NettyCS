package com.pda.nettycs.client.time;

import com.google.inject.AbstractModule;

import io.netty.channel.ChannelHandler;

public class TimeClientModule extends AbstractModule {
	  public void configure() {
		  bind(ChannelHandler.class).to(TimeClientHandler.class);
	  }
	}