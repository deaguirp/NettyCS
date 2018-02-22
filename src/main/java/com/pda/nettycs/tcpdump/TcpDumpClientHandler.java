package com.pda.nettycs.tcpdump;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TcpDumpClientHandler extends ChannelInboundHandlerAdapter{
	private ByteBuf buf;
	private ChannelHandlerContext context;
	private TcpDumpMainClient mainClient;

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		buf = ctx.alloc().buffer(4); // (1)

		context = ctx;
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
		buf.release(); // (1)
		buf = null;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		getServerHandler().getContext().write(msg);
	}

	private TcpDumpServerHandler getServerHandler() {
		return getMainClient().getServerHandler();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		log.error(cause);
		ctx.close();
	}

	public ChannelHandlerContext getContext() {
		return context;
	}

	void setMainClient(TcpDumpMainClient mc) {
		this.mainClient=mc;
	}
	
	public TcpDumpMainClient getMainClient() {
		return mainClient;
	}
}
