package com.pda.nettycs.client.time;

import com.google.inject.AbstractModule;
import com.pda.nettycs.client.ClientHandler;
import com.pda.nettycs.client.Target;

import io.netty.channel.ChannelHandler;

public class TimeClientModule extends AbstractModule {
	private Target target;
	
	public TimeClientModule(Target t) {
		this.target = t;
	}

	public void configure() {
		bind(ChannelHandler.class).annotatedWith(ClientHandler.class).to(TimeClientHandler.class);
		bind(Target.class).toInstance(this.target);
	}
}