package com.pda.nettycs.echo;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pda.nettycs.NettyServer;

public class MainEchoServer {

	public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        Injector injector = Guice.createInjector(new EchoServerModule());
        injector.getInstance(NettyServer.class).withPort(port).run();
    }
}
