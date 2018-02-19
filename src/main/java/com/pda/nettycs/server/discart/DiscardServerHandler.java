package com.pda.nettycs.server.discart;

/**
 * Hello world!
 *
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.log4j.Log4j2;

/**
 * Handles a server-side channel.
 */
@Log4j2
@Sharable 
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    	ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) { // (1)
                log.info((char) in.readByte());
            }
        } finally {
            ReferenceCountUtil.release(msg); // (2)
        }      
    }

    @Override	
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        log.error(cause);
        ctx.close();
    }
}