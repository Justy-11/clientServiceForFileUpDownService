package com.jathursh.client_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
public class FileController {

    @Autowired
    RestTemplate restTemplate;  // to communicate to third party
    //simplifies the process of making HTTP requests and handling HTTP responses from a RESTful web service.

    String baseUrl = "http://localhost:9090";

    @GetMapping("getAndSave/{fileName}")
    String getAndSaveFile(@PathVariable String fileName) throws IOException {

        Resource resource = restTemplate.getForObject(baseUrl+ "/download" + fileName, Resource.class);

        Path path = Paths.get("temp");
        Files.createDirectories(Paths.get("temp"));

        Files.copy(resource.getInputStream(), Paths.get(path + "\\" + fileName), StandardCopyOption.REPLACE_EXISTING);

        return "data retrieved properly";
    }

}
