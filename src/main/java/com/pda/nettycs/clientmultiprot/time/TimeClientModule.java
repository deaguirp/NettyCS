package com.pda.nettycs.clientmultiprot.time;

import java.util.Arrays;
import java.util.Collection;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import io.netty.channel.ChannelHandler;

public class TimeClientModule extends AbstractModule {

	public void configure() {
		  bind(new TypeLiteral<Collection<? extends ChannelHandler>>() {})
		  	.toInstance(Arrays.asList(
		  			new TimeClientTransportProtocolHandler(),
		  			new TimeClientHandler()
		  			));
	  }
	}