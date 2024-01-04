package com.redbus.user.Service;

import com.redbus.user.payload.BusListDto;

import java.util.Date;
import java.util.List;

public interface SearchBusesService {
    public List<BusListDto> searchBusBy(String departureCity, String arrivalCity, Date departureDate);
}
