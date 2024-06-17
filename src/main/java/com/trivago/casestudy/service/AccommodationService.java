package com.trivago.casestudy.service;

import com.trivago.casestudy.dto.AccommodationPriceDTO;

import java.util.List;

public interface AccommodationService {
    List<AccommodationPriceDTO> getAccommodationPricesByAccommodationId(Long accommodationId);
}
