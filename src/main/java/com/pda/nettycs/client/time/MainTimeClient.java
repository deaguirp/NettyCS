package com.pda.nettycs.client.time;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pda.nettycs.client.NettyClient;

public class MainTimeClient {

	public static void main(String[] args) throws Exception {
		String host;
        int port;
        if (args.length > 0) {
        	if (args.length >1){
        		host = args[0];
        		port = Integer.parseInt(args[1]);
        	}else{ 
        		host = "localhost";
        		port = Integer.parseInt(args[0]);
        	}
        } else {
        	host = "localhost";
            port = 8080;
        }
        Injector injector = Guice.createInjector(new TimeClientModule());
        injector.getInstance(NettyClient.class).withHost(host).withPort(port).run();
    }
}
