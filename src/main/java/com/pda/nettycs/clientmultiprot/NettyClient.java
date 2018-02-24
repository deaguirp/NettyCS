package com.pda.nettycs.clientmultiprot;

import java.util.Collection;

import com.google.inject.Inject;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
	
	// Mejorar con: https://github.com/code4craft/netty-learning/blob/master/netty-3.7/src/main/java/org/jboss/netty/example/http/tunnel/HttpTunnelingClientExample.java
	@Inject
	private Collection<? extends ChannelHandler> handler;
	private String host="localhost";
	private int port;

	public void run() throws InterruptedException{
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(NettyClient.this.handler.toArray(new ChannelHandler[0]));
				}
			});
            
            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
	}

	public NettyClient withHost(String host) {
		this.host=host;
		return this;
	}

	public NettyClient withPort(int port) {
		this.port = port;
		return this;
	}

}
