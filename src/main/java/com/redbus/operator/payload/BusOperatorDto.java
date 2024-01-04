package com.redbus.operator.payload;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.redbus.operator.entity.TicketCost;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperatorDto {

    private String busId;
    private String busNumber;
    private String busOperatorCompanyName;
    private String driverName;
    private String supportStaff;
    private int numberSeats;
    private String departureCity;
    private String arrivalCity;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date departureDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date arrivalDate;
    private double totalTravelTime;
    private String busType;
    private String amenities;

    private TicketCost ticketCost;

}
