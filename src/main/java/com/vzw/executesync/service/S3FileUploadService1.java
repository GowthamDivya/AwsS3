//package com.vzw.executesync.service;
//
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.File;
//import java.io.IOException;
//import java.util.Objects;
//
//@Service
//public class S3FileUploadService {
//
//    @Value("${aws.s3.bucketName}")
//    private String bucketName;
//
//    private final AmazonS3 s3Client;
//
//    public S3FileUploadService() {
//        this.s3Client = AmazonS3Client.builder()
//                .withRegion("us-east-1") // Replace with your desired region
//                .build();
//    }
//
//    public void uploadFile(MultipartFile multipartFile) throws IOException {
//        File file = convertMultiPartToFile(multipartFile);
//        String fileName = generateFileName(multipartFile);
//
//        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
//    }
//
//    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
//        file.transferTo(convFile);
//        return convFile;
//    }
//
//    private String generateFileName(MultipartFile multiPart) {
//        return multiPart.getOriginalFilename(); // You can modify this method to generate unique file names
//    }
//
//
//}
