package com.xetius.handler;

import com.sun.net.httpserver.HttpHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequestHandlerFactoryTest {
    private RequestHandlerFactory factory;

    @Before
    public void setUp() {
        factory = new RequestHandlerFactory();
    }

    @Test
    public void testRequestForSeatCountGeneratesSeatQueryRequestHandler() {
        HttpHandler handler = factory.getHandler("/seat", "GET");
        Assert.assertTrue(handler instanceof SeatQueryRequestHandler);
    }

    @Test
    public void testRequestForSeatReservationGeneratesSeatBookRequestHandler() {
        HttpHandler handler = factory.getHandler("/seat/book", "GET");
        Assert.assertTrue(handler instanceof SeatBookRequestHandler);
    }

    @Test
    public void testReservationCancellationGeneratesSeatCancelRequestHandler() {
        HttpHandler handler = factory.getHandler("/seat/cancel/ABCDEFGHIJ", "GET");
        Assert.assertTrue(handler instanceof SeatCancelRequestHandler);
    }

    @Test
    public void testUnknownGetURIGeneratesNullHttpHandler() {
        HttpHandler handler = factory.getHandler("/somethingElse", "GET");
        Assert.assertTrue(handler instanceof NullHttpHandler);
    }

    @Test
    public void testNonGetRequestsReturnNullHttpHandler() {
        HttpHandler handler = factory.getHandler("/seat", "POST");
        Assert.assertTrue(handler instanceof NullHttpHandler);
    }
}
