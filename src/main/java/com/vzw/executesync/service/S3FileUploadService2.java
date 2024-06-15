//package com.vzw.executesync.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import software.amazon.awssdk.core.sync.RequestBody;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.PutObjectRequest;
//import software.amazon.awssdk.services.s3.model.PutObjectResponse;
//
//import java.io.IOException;
//import java.net.URI;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Service
//public class S3FileUploadService2 {
//
//    private final S3Client s3Client;
//
//    @Autowired
//    public S3FileUploadService2(S3Client s3Client) {
//        this.s3Client = s3Client;
//    }
//
//    public String uploadFile(String bucketName, String key, URI filePathURI) throws IOException {
//        Path filePath = Paths.get(filePathURI);
//        byte[] fileContent = Files.readAllBytes(filePath);
//
//        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(key)
//                .build();
//
//        PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileContent));
//        return response.eTag();
//    }
//}
