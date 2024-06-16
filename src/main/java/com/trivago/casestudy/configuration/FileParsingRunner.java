package com.trivago.casestudy.configuration;

import com.trivago.casestudy.service.FileParsingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1) // Ensure this runner executes early in the startup process
public class FileParsingRunner implements CommandLineRunner {

    @Autowired
    private FileParsingService fileParsingService;

    @Override
    public void run(String... args) throws Exception {
        String jsonFilePath = "/prices/advertiser_200.json";
        String yamlFilePath = "/prices/advertiser_100.yaml";

        try {
            fileParsingService.parseJsonAndSaveToDb(jsonFilePath);
            System.out.println("JSON file parsed and saved successfully!");
        } catch (IOException e) {
            System.err.println("Error parsing JSON file: " + e.getMessage());
        }

        try {
            fileParsingService.parseYamlAndSaveToDb(yamlFilePath);
            System.out.println("YAML file parsed and saved successfully!");
        } catch (IOException e) {
            System.err.println("Error parsing YAML file: " + e.getMessage());
        }
    }
}
