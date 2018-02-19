package com.pda.nettycs.server.discart;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pda.nettycs.server.NettyServer;

public class MainDiscardServer {

	public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        Injector injector = Guice.createInjector(new DiscardServerModule());
        injector.getInstance(NettyServer.class).withPort(port).run();
    }
}
