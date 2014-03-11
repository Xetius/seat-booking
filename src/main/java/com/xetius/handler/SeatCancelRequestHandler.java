package com.xetius.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.xetius.storage.SeatService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeatCancelRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        URI uri = httpExchange.getRequestURI();
        String path = uri.getPath();

        String bookingId = getBookingId(path);
        if (bookingId != null) {
            SeatService.getInstance().cancelReservation(bookingId);
        }

        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/json");

        OutputStream responseBody = httpExchange.getResponseBody();
        responseBody.close();
    }

    private String getBookingId(String path) {
        Pattern pattern = Pattern.compile("/seat/cancel/(?<bookingId>.*)");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group("bookingId");
        }
        return null;
    }
}
