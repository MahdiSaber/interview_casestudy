package com.trivago.casestudy.service;

import org.springframework.stereotype.Service;

import java.io.IOException;


public interface FileParsingService {
    void parseJsonAndSaveToDb(String jsonFilePath) throws IOException;

    void parseYamlAndSaveToDb(String yamlFilePath) throws IOException;
}
