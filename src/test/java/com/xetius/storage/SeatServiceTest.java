package com.xetius.storage;

import com.xetius.model.BookingInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

public class SeatServiceTest {
    private SeatService service;

    @Before
    public void setUp() throws Exception {
        service = SeatService.getInstance();
        service.reset();
    }

    @Test
    public void testSingletonIsSingleton() {
        SeatService service1 = SeatService.getInstance();
        SeatService service2 = SeatService.getInstance();

        Assert.assertEquals(service1, service2);
    }

    @Test
    public void testReserveSeatAddsItemToMap() {
        ConcurrentHashMap<String,BookingInfo> bookingInfo = service.getBookingInfo();
        Assert.assertEquals(0, bookingInfo.size());
        String bookingId = service.reserveSeat();
        Assert.assertNotNull(bookingId);
        Assert.assertFalse(bookingId.isEmpty());
        Assert.assertEquals(1, bookingInfo.size());
        Assert.assertTrue(bookingInfo.containsKey(bookingId));
    }

    @Test
    public void testReserving51stSeatFails() {
        ConcurrentHashMap<String,BookingInfo> bookingInfo = service.getBookingInfo();
        while(bookingInfo.size() < 50) {
            service.reserveSeat();
        }

        String bookingId = service.reserveSeat();
        Assert.assertNull(bookingId);
    }

    @Test
    public void testRemovingReservationByBookingIdRemovesItFromMap() {
        ConcurrentHashMap<String,BookingInfo> bookingInfo = service.getBookingInfo();
        String bookingId = service.reserveSeat();
        Assert.assertNotNull(bookingId);
        Assert.assertEquals(1, bookingInfo.size());
        BookingInfo reservedBookingInfo = service.cancelReservation(bookingId);
        Assert.assertNotNull(reservedBookingInfo);
        Assert.assertEquals(0, bookingInfo.size());
    }

    @Test
    public void testAttemptingToRemoveItemWithInvalidBookingIdReturnsNull() {
        ConcurrentHashMap<String,BookingInfo> bookingInfo = service.getBookingInfo();
        String bookingId = service.reserveSeat();
        Assert.assertNotNull(bookingId);
        Assert.assertEquals(1, bookingInfo.size());
        BookingInfo reservedBookingInfo = service.cancelReservation("ABC");
        Assert.assertNull(reservedBookingInfo);
        Assert.assertEquals(1, bookingInfo.size());
    }

    @Test
    public void testRemovingFromEmptyListReturnsNull() {
        ConcurrentHashMap<String,BookingInfo> bookingInfo = service.getBookingInfo();
        BookingInfo reservedBookingInfo = service.cancelReservation("ABC");
        Assert.assertNull(reservedBookingInfo);
        Assert.assertEquals(0, bookingInfo.size());
    }

    @Test
    public void testGetFreeSeatCountReturns50WhenEmpty() {
        Assert.assertEquals(50, service.getFreeSeatCount());
    }


}
