package com.trivago.casestudy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.trivago.casestudy.entity.Accommodation;
import com.trivago.casestudy.entity.Advertiser;
import com.trivago.casestudy.entity.Price;
import com.trivago.casestudy.repository.AdvertiserRepository;
import com.trivago.casestudy.service.FileParsingService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FileParsingServiceImpl implements FileParsingService {
    private AdvertiserRepository advertiserRepository;
    private ResourceLoader resourceLoader;

    public FileParsingServiceImpl(AdvertiserRepository advertiserRepository, ResourceLoader resourceLoader) {
        this.advertiserRepository = advertiserRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void parseJsonAndSaveToDb(String jsonFilePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + jsonFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        Advertiser advertiser = objectMapper.readValue(resource.getInputStream(), Advertiser.class);
        cleanAndSaveAdvertiser(advertiser);
    }

    @Override
    public void parseYamlAndSaveToDb(String yamlFilePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + yamlFilePath);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Advertiser advertiser = objectMapper.readValue(resource.getInputStream(), Advertiser.class);
        cleanAndSaveAdvertiser(advertiser);
    }

    private void cleanAndSaveAdvertiser(Advertiser advertiser) {
        List<Accommodation> cleanedAccommodations = removeDuplicateAccommodations(advertiser.getAccommodations());
        advertiser.setAccommodations(cleanedAccommodations);
        advertiserRepository.save(advertiser);
    }

    private List<Accommodation> removeDuplicateAccommodations(List<Accommodation> accommodations) {
        Map<String, Integer> accommodationCounts = new HashMap<>();
        accommodations.forEach(accommodation -> {
            String uniqueKey = accommodation.getAdvertiserId() + "-" + accommodation.getId();
            accommodationCounts.put(uniqueKey, accommodationCounts.getOrDefault(uniqueKey, 0) + 1);
        });

        Set<String> duplicateKeys = accommodationCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return accommodations.stream()
                .filter(accommodation -> {
                    String uniqueKey = accommodation.getAdvertiserId() + "-" + accommodation.getId();
                    return !duplicateKeys.contains(uniqueKey);
                })
                .collect(Collectors.toList());
    }

    private Accommodation cleanAccommodation(Accommodation accommodation) {
        List<Price> cleanedPrices = accommodation.getPrices().stream()
                .filter(price -> price.getCurrency() != null && isValidDouble(price.getPrice()))
                .collect(Collectors.toList());
        accommodation.setPrices(cleanedPrices);
        return accommodation;
    }

    private boolean isValidDouble(Double price) {
        return price != null && price >= 0;
    }
}