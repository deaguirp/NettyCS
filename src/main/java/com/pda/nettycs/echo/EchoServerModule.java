package com.pda.nettycs.echo;

import com.google.inject.AbstractModule;

import io.netty.channel.ChannelHandler;

public class EchoServerModule extends AbstractModule {
	  public void configure() {
		  bind(ChannelHandler.class).to(EchoServerHandler.class);
	  }
	}