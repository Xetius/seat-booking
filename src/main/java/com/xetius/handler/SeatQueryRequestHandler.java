package com.xetius.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.xetius.storage.SeatService;

import java.io.IOException;
import java.io.OutputStream;

public class SeatQueryRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, 0);

        int count = SeatService.getInstance().getFreeSeatCount();

        OutputStream responseBody = httpExchange.getResponseBody();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ \"seats\" : ").append(count).append(" }");
        responseBody.write(stringBuilder.toString().getBytes());
        responseBody.close();

    }
}
