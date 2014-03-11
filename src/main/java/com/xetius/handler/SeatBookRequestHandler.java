package com.xetius.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.xetius.storage.SeatService;

import java.io.IOException;
import java.io.OutputStream;

public class SeatBookRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String bookingId = SeatService.getInstance().reserveSeat();

        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/json");

        OutputStream responseBody = httpExchange.getResponseBody();
        StringBuilder stringBuilder = new StringBuilder();

        if (bookingId != null) {

            stringBuilder.append("{ \"booking\" : \"").append(bookingId).append("\" }");
            httpExchange.sendResponseHeaders(200, stringBuilder.length());
            responseBody.write(stringBuilder.toString().getBytes());
        } else {
            httpExchange.sendResponseHeaders(404, 0);
        }
        responseBody.close();
    }
}
