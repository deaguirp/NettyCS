package com.pda.nettycs.tcpdump;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.pda.nettycs.client.Target;
import com.pda.nettycs.server.NettyServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TcpDump {
    
	private static final int DEFAULT_PORT = 8080;
	private static final int DEFAULT_TARGET_PORT = 80;
	private static final String DEFAULT_TARGET_HOST = "www.google.com";
	@Inject
    Injector injector;
    
    public void run() throws InterruptedException {
    	
    }
    
    public static void main(String[] args) throws InterruptedException {
    	int p = getPort(args);
		Target t = getTarget(args);
        Injector injector = Guice.createInjector(new TcpDumpModule(t));
        // TODO: mejorar con uso de multichanel> https://netty.io/4.0/api/io/netty/channel/group/ChannelGroup.html
        injector.getInstance(NettyServer.class)
        	.withPort(p)
        	.run();	
    }

	private static Target getTarget(String[] args) {
		String host;
        int port;
        if (args.length > 1) {
        	if (args.length >0){
        		host = args[1];
        		port = Integer.parseInt(args[2]);
        	}else{ 
        		host = DEFAULT_TARGET_HOST;
        		port = Integer.parseInt(args[1]);
        	}
        } else {
        	host = DEFAULT_TARGET_HOST;
            port = DEFAULT_TARGET_PORT;
        }
		return new Target(){

			@Override
			public String getHost() {
				return host;
			}

			@Override
			public int getPort() {
				return port;
			}};
	}

	private static int getPort(String[] args) {
		int p;
        if (args.length > 0) {
            p = Integer.parseInt(args[0]);
        } else {
            p = DEFAULT_PORT;
        }
		return p;
	}

	public static void dump(String string, Object msg) {
		System.out.println(string);
		System.out.print(new String(ByteBufUtil.getBytes((ByteBuf) msg)));
	}
}
