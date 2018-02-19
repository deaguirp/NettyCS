package com.pda.nettycs.server.time;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pda.nettycs.server.NettyServer;

public class MainTimeServer {

	/*
	 * To test if our time server works as expected, you can use the UNIX rdate command:
     *
     * $ rdate -o <port> -p <host>
     * 
	 */

	public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        Injector injector = Guice.createInjector(new TimeServerModule());
        injector.getInstance(NettyServer.class).withPort(port).run();
    }
}
