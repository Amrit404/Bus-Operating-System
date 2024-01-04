package com.redbus.user.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsDto {

    private String bookingId;

    private String busCompany;

    private String to;

    private String from;

    private String firstName;

    private String lastName;

    private String email;

    private String mobile;

    private double price;

    private String message;
}
