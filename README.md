# clientServiceForFileUpDownService

:handshake: client service for [FileOperationsSprinboot](https://github.com/Justy-11/fileOperationsSpringboot) 

Using the Spring RestTemplate to communicate with RESTful web service [FileOperationsSprinboot](https://github.com/Justy-11/fileOperationsSpringboot) located at a specified baseUrl.

📂 The first endpoint, "/getAndSave/{fileName}", retrieves a file from the fileUploadDownload service located at the baseUrl + "/download/" + fileName, and saves it to a temporary directory in the client service using the Resource and Files classes from the Java NIO library.

🔄 The second endpoint, "/executeTask", retrieves files from a specified directory on the local file system of the client service, and uploads them to the fileUploadDownload service using the RestTemplate's postForObject method and a MultiValueMap containing the files to be uploaded.
