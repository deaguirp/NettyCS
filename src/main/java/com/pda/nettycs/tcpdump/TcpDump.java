package com.pda.nettycs.tcpdump;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.pda.nettycs.client.Target;
import com.pda.nettycs.server.NettyServer;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TcpDump {
    
	@Inject
    Injector injector;
    
    public void run() throws InterruptedException {
    	
    }
    
    public static void main(String[] args) throws InterruptedException {
    	int p = getPort(args);
		Target t = getTarget(args);
        Injector injector = Guice.createInjector(new TcpDumpModule(t));
        
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
        		host = "localhost";
        		port = Integer.parseInt(args[1]);
        	}
        } else {
        	host = "localhost";
            port = 8080;
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
            p = 8080;
        }
		return p;
	}
}
