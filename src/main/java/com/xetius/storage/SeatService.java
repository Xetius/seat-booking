package com.xetius.storage;

import com.xetius.model.BookingIdGenerator;
import com.xetius.model.BookingInfo;

import java.util.concurrent.ConcurrentHashMap;

public class SeatService {
    public final static SeatService INSTANCE = new SeatService();
    public final static int MAX_SEAT_COUNT = 50;

    private ConcurrentHashMap<String, BookingInfo> bookingInfo;

    private SeatService() {
        bookingInfo = new ConcurrentHashMap<String, BookingInfo>();
    }

    public static SeatService getInstance() {
        return INSTANCE;
    }

    public int getFreeSeatCount() {
        return MAX_SEAT_COUNT - bookingInfo.size();

    }

    public String reserveSeat() {
        if (bookingInfo.size() < MAX_SEAT_COUNT) {
            String bookingId = BookingIdGenerator.getId();
            bookingInfo.put(bookingId, new BookingInfo());
            return bookingId;
        }
        return null;
    }

    public BookingInfo cancelReservation(String bookingId) {
        return bookingInfo.remove(bookingId);
    }

    public void reset() {
        bookingInfo.clear();
    }

    ConcurrentHashMap<String, BookingInfo> getBookingInfo() {
        return bookingInfo;
    }
}
