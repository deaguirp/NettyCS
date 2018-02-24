package com.pda.nettycs.tcpdump;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TcpDumpClientHandler extends ChannelInboundHandlerAdapter{
	private ByteBuf buf;
	private CompletableFuture<ChannelHandlerContext> context = new CompletableFuture<>();
	private TcpDumpMainClient mainClient;

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		super.handlerAdded(ctx);
		buf = ctx.alloc().buffer(4); // (1)
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		buf.release(); // (1)
		buf = null;
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		context.complete(ctx);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		TcpDump.dump("<<<<", msg);
		ChannelHandlerContext c = getServerHandler().getContext();
		c.write(msg);
		c.flush();
	}

	private TcpDumpServerHandler getServerHandler() {
		return getMainClient().getServerHandler();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error(cause);
		ctx.close();
	}

	public Future<ChannelHandlerContext> getContext() {
		return context;
	}

	void setMainClient(TcpDumpMainClient mc) {
		this.mainClient=mc;
	}
	
	public TcpDumpMainClient getMainClient() {
		return mainClient;
	}
}
