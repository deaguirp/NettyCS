package com.pda.nettycs.discart;

import com.google.inject.AbstractModule;

import io.netty.channel.ChannelHandler;

public class DiscardServerModule extends AbstractModule {
	  public void configure() {
		  bind(ChannelHandler.class).to(DiscardServerHandler.class);
	  }
	}