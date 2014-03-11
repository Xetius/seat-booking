package com.xetius;

import com.xetius.server.Server;

public class Main {
    private static final int PORT = 8080;
    private static final String ROOT_CONTEXT = "/";

    public static void main (String[] args) {
        Server server = new Server(ROOT_CONTEXT, PORT);
        server.run();
    }
}
