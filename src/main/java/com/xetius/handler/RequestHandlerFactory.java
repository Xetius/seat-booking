package com.xetius.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URI;

public class RequestHandlerFactory implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();

        HttpHandler httpHandler = getHandler(path, httpExchange.getRequestMethod());
        httpHandler.handle(httpExchange);
    }

    public HttpHandler getHandler(String path, String method) {
        if (!method.equalsIgnoreCase("GET")) {
            return new NullHttpHandler();
        }

        if (path.compareTo("/seat") == 0) {
            return new SeatQueryRequestHandler();
        } else if (path.compareTo("/seat/book") == 0) {
            return new SeatBookRequestHandler();
        } else if (path.startsWith("/seat/cancel")) {
            return new SeatCancelRequestHandler();
        }
        return new NullHttpHandler();
    }
}
