package com.redbus.operator.controller;

import com.redbus.operator.payload.BusOperatorDto;
import com.redbus.operator.service.BusOperatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bus-operator")
public class BusOperatorController {

    private BusOperatorService busOperatorService;

    public BusOperatorController(BusOperatorService busOperatorService){
        this.busOperatorService=busOperatorService;
    }

    //http://localhost:8080/api/bus-operator
    @PostMapping
    public ResponseEntity<BusOperatorDto> scheduleBus(@RequestBody BusOperatorDto busOperatorDto){
        BusOperatorDto dto = busOperatorService.scheduleBus(busOperatorDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
