package com.redbus.user.Service;

import com.redbus.user.payload.BookingDetailsDto;
import com.redbus.user.payload.PassengerDetails;

public interface BookingService {
    public BookingDetailsDto createBooking(String busId, String ticketId, PassengerDetails passengerDetails);
}
