package com.trivago.casestudy;

import com.trivago.casestudy.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PriceController {
    private final AccommodationService accommodationService;

    @Autowired
    public PriceController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping("/prices/{accommodationId}")
    public List<?> getPrices(@PathVariable int accommodationId) {
        return accommodationService.getAccommodationPricesByAccommodationId((long) accommodationId);
    }
}
