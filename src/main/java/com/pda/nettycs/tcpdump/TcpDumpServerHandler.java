package com.pda.nettycs.tcpdump;

import java.util.concurrent.ExecutionException;
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

	private TcpDumpMainClient client;


	private ChannelHandlerContext context;
    
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		context = ctx;
	}

	@Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
		TcpDumpMainClient ct = injector.getInstance(TcpDumpMainClient.class);
		ct.setServerHandler(this);
		setClient(ct);
		EXECUTOR_SERVICE.submit(()->{
			try{
				ct.run();
			}finally {
				clientEnded(ctx);
			}
		});
    }
    
    private void setClient(TcpDumpMainClient ct) {
		this.client = ct;
	}
    
    public TcpDumpMainClient getMainClient() {
		return client;
	}

	private void clientEnded(ChannelHandlerContext ctx) {
		setClient(null);
		ctx.close();
	}

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	TcpDumpMainClient ct = getMainClient();
    	if (ct != null)
    		ct.shutDown();
    }
    
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		TcpDump.dump(">>>>", msg);
		ChannelHandlerContext c;
		try {
			c = getMainClient().getClientHandler().getContext().get();
			c.write(msg);
			c.flush();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override	
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        log.error(cause);
        clientEnded(ctx);
    }

	public ChannelHandlerContext getContext() {
		return this.context;
	}
}
