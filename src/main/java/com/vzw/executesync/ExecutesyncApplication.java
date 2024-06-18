package com.vzw.executesync;

import com.vzw.executesync.service.S3FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExecutesyncApplication  implements CommandLineRunner {

	@Autowired
	S3FileUploadService s3FileUploadService;
	public static void main(String[] args) {
		SpringApplication.run(ExecutesyncApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		s3FileUploadService.uploadFilesInDirectoryAsync("gurralabucket","C:\\Users\\145627\\Desktop\\Images");
	}
}
