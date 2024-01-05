package com.redbus.user.Service.implementation;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.operator.repository.TicketCostRepository;
import com.redbus.user.Repository.BookingRepository;
import com.redbus.user.Service.BookingService;
import com.redbus.user.entity.Booking;
import com.redbus.user.payload.BookingDetailsDto;
import com.redbus.user.payload.PassengerDetails;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private BusOperatorRepository busOperatorRepository;

    private TicketCostRepository ticketCostRepository;

    private BookingRepository bookingRepository;

    public BookingServiceImpl(BusOperatorRepository busOperatorRepository,
                              TicketCostRepository ticketCostRepository,
                              BookingRepository bookingRepository) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingDetailsDto createBooking(String busId, String ticketId, PassengerDetails passengerDetails) {
//        BusOperator bus = busOperatorRepository.findById(busId).orElseThrow(
//                () -> new RuntimeException("Bus not found with ID: " + busId)
//        );

        BusOperator bus = busOperatorRepository.findById(busId).get();
        TicketCost ticketCost = ticketCostRepository.findById(ticketId).get();

        String paymentIntent = createPaymentIntent((int) ticketCost.getCost());

        if(paymentIntent != null){
            Booking booking = new Booking();
            String bookingId = UUID.randomUUID().toString();
            booking.setBookingId(bookingId);
            booking.setBusId(busId);
            booking.setTicketId(ticketId);
            booking.setToCity(bus.getArrivalCity());
            booking.setFromCity(bus.getDepartureCity());
            booking.setBusCompany(bus.getBusOperatorCompanyName());
            booking.setPrice(ticketCost.getCost());
            booking.setFirstName(passengerDetails.getFirstName());
            booking.setLastName(passengerDetails.getLastName());
            booking.setEmail(passengerDetails.getEmail());
            booking.setMobile(passengerDetails.getMobile());


            Booking ticketCreatedDetails = bookingRepository.save(booking);


            BookingDetailsDto dto = new BookingDetailsDto();
            dto.setBookingId(ticketCreatedDetails.getBookingId());
            dto.setFirstName(ticketCreatedDetails.getFirstName());
            dto.setLastName(ticketCreatedDetails.getLastName());
            dto.setPrice(ticketCreatedDetails.getPrice());
            dto.setEmail(ticketCreatedDetails.getEmail());
            dto.setMobile(ticketCreatedDetails.getMobile());
            dto.setBusCompany(ticketCreatedDetails.getBusCompany());
            dto.setTo(ticketCreatedDetails.getToCity());
            dto.setFrom(ticketCreatedDetails.getFromCity());
            dto.setMessage("Booking Confirmed");

            return dto;
        }
        else {
            System.out.println("Error!!");
        }
        return null;

    }


    public String createPaymentIntent(Integer amount) {

        Stripe.apiKey = stripeApiKey;

        try {
            PaymentIntent intent = PaymentIntent.create(
                    new PaymentIntentCreateParams.Builder()
                            .setCurrency("usd")
                            .setAmount((long) amount * 100) // amount in cents
                            .build()
            );
            return generateResponse(intent.getClientSecret());
        } catch (StripeException e) {
            return generateResponse("Error creating PaymentIntent: " + e.getMessage());
        }
    }

    private String generateResponse(String cilentSecret){
        return "{\"clientSecret\":\"" + cilentSecret + "\"}";
    }
}
