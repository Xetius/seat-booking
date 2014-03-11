package com.xetius.server;

import com.sun.net.httpserver.HttpServer;
import com.xetius.handler.RequestHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);
    private HttpServer httpServer;

    public Server(String rootContext, int port) {
        try {
            InetSocketAddress address = createAddress(port);
            createHttpServer(address);
            configureServerHandler(rootContext);
            configureServer();
        } catch (IOException e) {
            logger.error("Unable to create new HttpServer : {}", e);
        }
    }

    public void run() {
        httpServer.start();
    }

    private void configureServer() {
        httpServer.setExecutor(Executors.newFixedThreadPool(100));
    }

    private void configureServerHandler(String rootContext) {
        httpServer.createContext(rootContext, new RequestHandlerFactory());
    }

    private void createHttpServer(InetSocketAddress address) throws IOException {
        httpServer = HttpServer.create(address, 0);
    }

    private InetSocketAddress createAddress(int port) {
        return new InetSocketAddress(port);
    }

}
