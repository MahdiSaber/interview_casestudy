package com.trivago.casestudy.service.impl;

import com.trivago.casestudy.dto.AccommodationPriceDTO;
import com.trivago.casestudy.entity.Accommodation;
import com.trivago.casestudy.repository.AccommodationRepository;
import com.trivago.casestudy.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<AccommodationPriceDTO> getAccommodationPricesByAccommodationId(Long accommodationId) {
        List<Accommodation> allByAccommodationId = accommodationRepository.findAllByAccommodationId(accommodationId);
        List<AccommodationPriceDTO> accommodationPriceDTOList = allByAccommodationId.stream().map(accommodation -> {
            AccommodationPriceDTO accommodationPriceDTO = new AccommodationPriceDTO();
            accommodationPriceDTO.setPartnerId(accommodation.getAdvertiserId());
            accommodationPriceDTO.setPrices(accommodation.getPrices());
            return accommodationPriceDTO;
        }).collect(Collectors.toList());
        return accommodationPriceDTOList;
    }
}
