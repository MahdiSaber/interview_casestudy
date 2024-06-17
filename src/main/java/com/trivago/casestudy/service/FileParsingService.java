package com.trivago.casestudy.service;

import java.io.IOException;


public interface FileParsingService {
    void parseJsonAndSaveToDb(String jsonFilePath) throws IOException;

    void parseYamlAndSaveToDb(String yamlFilePath) throws IOException;
}
