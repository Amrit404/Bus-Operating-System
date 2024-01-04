package com.redbus.user.controller;

import com.redbus.user.Service.BookingService;
import com.redbus.user.payload.BookingDetailsDto;
import com.redbus.user.payload.PassengerDetails;
import com.redbus.user.util.EmailService;
import com.redbus.user.util.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;
    private EmailService emailService;
    private PdfService pdfService;

    public BookingController(BookingService bookingService, EmailService emailService, PdfService pdfService){
        this.bookingService = bookingService;
        this.emailService = emailService;
        this.pdfService = pdfService;
    }

    //http://localhost:8080/api/bookings?busId=&ticketId=
    @PostMapping
    public ResponseEntity<BookingDetailsDto> bookBus(
            @RequestParam("busId") String busId,
            @RequestParam("ticketId") String ticketId,
            @RequestBody PassengerDetails passengerDetails){

        BookingDetailsDto booking = bookingService.createBooking(busId, ticketId, passengerDetails);

        if(booking != null){
            //generate pdf
            byte[] pdfBytes = pdfService.generatePdf(booking);

            //send confirmation mail with PDF attachment
            sendBookingConfirmationEmailWithAttachment(passengerDetails, booking, pdfBytes);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    private void sendBookingConfirmationEmailWithAttachment(PassengerDetails passengerDetails, BookingDetailsDto booking, byte[] pdfBytes) {
        String emailSubject = "Booking Confirmed. Booking Id: " + booking.getBookingId();
        String emailBody = String.format("Your booking is Confirmed \n Name: %s %s",
                passengerDetails.getFirstName(),passengerDetails.getLastName());

        //attach pdf to the email
        emailService.sendEmailWithAttachment(
                passengerDetails.getEmail(),
                emailSubject,
                emailBody,
                pdfBytes,
                "Booking confirmation.pdf");
    }
}
