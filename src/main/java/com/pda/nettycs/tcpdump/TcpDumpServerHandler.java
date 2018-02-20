package com.pda.nettycs.tcpdump;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.inject.Inject;
import com.google.inject.Injector;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TcpDumpServerHandler extends ChannelInboundHandlerAdapter{

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    @Inject
    Injector injector;
    
    Map<ChannelHandlerContext, ClientTask> cCT = new HashMap<>();
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ClientTask ct = injector.getInstance(ClientTask.class);
		cCT.put(ctx, ct);
		EXECUTOR_SERVICE.submit(()->{
			try{
				ct.run();
			}finally {
				clientEnded(ctx);
			}
		});
    }

	private void clientEnded(ChannelHandlerContext ctx) {
		cCT.remove(ctx);
		ctx.close();
	}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	ClientTask ct = cCT.remove(ctx);
    	if (ct != null)
    		ct.shutDown();
    }
    
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
    }

    @Override	
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        log.error(cause);
        clientEnded(ctx);
    }
}
