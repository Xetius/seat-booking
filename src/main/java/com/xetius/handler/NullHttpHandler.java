package com.xetius.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Set;

public class NullHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "application/json");
        httpExchange.sendResponseHeaders(200, 0);

        OutputStream responseBody = httpExchange.getResponseBody();
        Headers requestHeaders = httpExchange.getRequestHeaders();
        Set<String> keySet = requestHeaders.keySet();
        for (String key : keySet) {
            List<String> values = requestHeaders.get(key);
            String s = key + " = " + values.toString() + "\n";
            responseBody.write(s.getBytes());
        }

        URI uri = httpExchange.getRequestURI();
        responseBody.write(("path = " + uri.getPath() + "\n").getBytes());
        responseBody.write(("query = " + uri.getQuery() + "\n").getBytes());
        responseBody.close();
    }
}
