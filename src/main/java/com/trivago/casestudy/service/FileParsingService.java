package com.trivago.casestudy.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.trivago.casestudy.entity.Accommodation;
import com.trivago.casestudy.entity.Advertiser;
import com.trivago.casestudy.entity.Price;
import com.trivago.casestudy.repository.AdvertiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileParsingService {

    @Autowired
    private AdvertiserRepository advertiserRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public void parseJsonAndSaveToDb(String jsonFilePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + jsonFilePath);
        ObjectMapper objectMapper = new ObjectMapper();
        Advertiser advertiser = objectMapper.readValue(resource.getInputStream(), Advertiser.class);
        cleanAndSaveAdvertiser(advertiser);
    }

    public void parseYamlAndSaveToDb(String yamlFilePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + yamlFilePath);
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        Advertiser advertiser = objectMapper.readValue(resource.getInputStream(), Advertiser.class);
        cleanAndSaveAdvertiser(advertiser);
    }

    private void cleanAndSaveAdvertiser(Advertiser advertiser) {
        List<Accommodation> cleanedAccommodations = advertiser.getAccommodations().stream()
                .map(this::cleanAccommodation)
                .collect(Collectors.toList());
        advertiser.setAccommodations(cleanedAccommodations);
        advertiserRepository.save(advertiser);
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