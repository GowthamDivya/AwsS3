//package com.vzw.executesync.controller;
//
//import com.vzw.executesync.service.S3FileUploadService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/upload")
//public class S3FileUploadController {
//
//    private final S3FileUploadService s3FileUploadService;
//
//    @Autowired
//    public S3FileUploadController(S3FileUploadService s3FileUploadService) {
//        this.s3FileUploadService = s3FileUploadService;
//    }
//
//
//    @PostMapping("/directory")
//    public ResponseEntity<String> uploadDirectory(@RequestParam String directoryPath) {
//        try {
//            String bucketName = "gurralabucket";
//            s3FileUploadService.uploadFilesInDirectory(bucketName, directoryPath);
//            return ResponseEntity.ok("Files uploaded successfully to S3 from directory: " + directoryPath);
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle or log the exception as needed
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files: " + e.getMessage());
//        }
//    }
//
//    @PostMapping("/directory-async")
//    public ResponseEntity<String> uploadDirectoryAsync(@RequestParam String directoryPath) {
//        try {
//            String bucketName = "gurralabucket";
//            s3FileUploadService.uploadFilesInDirectoryAsync(bucketName, directoryPath);
//            return ResponseEntity.ok("Files uploading asynchronously to S3 from directory: " + directoryPath);
//        } catch (Exception e) {
//            e.printStackTrace(); // Handle or log the exception as needed
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files asynchronously: " + e.getMessage());
//        }
//    }
//
//
//}
