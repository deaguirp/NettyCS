package com.pda.nettycs.clientmultiprot.time;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TimeClientHandler extends ChannelInboundHandlerAdapter{

  @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        try {
        	long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
        	log.info(new Date(currentTimeMillis).toString());
        	ctx.close();
        } finally {
            m.release();
        }
        
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause);
        ctx.close();
    }
    
}