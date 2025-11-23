package com.lld.stockexchange.controllers;


import com.lld.stockexchange.dto.CreateOrderDto;
import com.lld.stockexchange.services.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trade")
public class TradingController {

    @Autowired
    private TradingService service;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderDto dto) {
        return new ResponseEntity<>(service.createOrder(dto), HttpStatus.OK);
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> getOrders(@PathVariable("symbol") String symbol) {
        return new ResponseEntity<>(service.getOrdersBySymbol(symbol), HttpStatus.OK);
    }
}
