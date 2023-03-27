package com.jathursh.client_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@RestController
public class FileController {

    @Autowired
    RestTemplate restTemplate;  // to communicate to third party
    //simplifies the process of making HTTP requests and handling HTTP responses from a RESTful web service.

    String baseUrl = "http://localhost:9090";

    // check "http://localhost:8080/getAndSave/{fileName}"
    // {fileName} - in the fileStorage dir. of fileUploadDownload service
    // get the selected file from the fileUploadDownload service dir. and save to this client service dir. (temp)
    @GetMapping("/getAndSave/{fileName}")
    String getAndSaveFile(@PathVariable String fileName) throws IOException {

        Resource resource = restTemplate.getForObject(baseUrl+ "/download/" + fileName, Resource.class);

        Path path = Paths.get("temp");
        Files.createDirectories(Paths.get("temp"));

        Files.copy(resource.getInputStream(), Paths.get(path + "\\" + fileName), StandardCopyOption.REPLACE_EXISTING);

        return "data retrieved properly";
    }

    // http://localhost:9090//multiple/upload?files="1.jpg"&files="2.jpg"
    // check  http://localhost:8080/executeTask
    @GetMapping("/executeTask")
    String executeTask() throws IOException {

        // get the files from the location (hardcoded the location)
        Stream<Path> list = Files.list(Paths.get("C:\\Users\\JATHURSHAN\\Pictures\\toUpload"));

        MultiValueMap filesToUpload = new LinkedMultiValueMap<>();

        list.forEach(
                file ->{
                    try {
                        filesToUpload.add("files", new UrlResource(file.toAbsolutePath().toUri()));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
        );

        return restTemplate.postForObject(baseUrl + "/multiple/upload", filesToUpload, String.class);  // uploaded to the fileStorage dir. of fileUploadDownload service
    }

}
