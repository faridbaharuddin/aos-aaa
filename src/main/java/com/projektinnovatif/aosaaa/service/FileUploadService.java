package com.projektinnovatif.aosaaa.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Service("fileUploadService")
public class FileUploadService {
	
	@Value("${amazons3repository}")
	private String amazonS3Repository;
		
	@Value("${amazons3imagefileformat}")
	private String amazonFileFormat;
	
	@Value("${amazons3repositoryuri}")
	private String amazonS3UriContext;
	
	@Value("${amazons3accesskey}")
	private String amazonS3AccessKey;
	
	@Value("${amazons3secret}")
	private String amazonS3Secret;
	
	private AmazonS3 amazonS3;
	
	
	
	@Transactional
	public String uploadImage(MultipartFile multipartfile, String targetObjectType, Long targetObjectId) {
		File file = new File(multipartfile.getOriginalFilename());
		try {
			file.createNewFile();
			FileOutputStream fos;
			fos = new FileOutputStream(file);
			fos.write(multipartfile.getBytes());
			fos.close();
			System.out.println(amazonS3AccessKey);
			String fileName = String.format(amazonFileFormat, new Date().getTime(), targetObjectType, targetObjectId);
			PutObjectRequest putObjectRequest =
	                new PutObjectRequest(amazonS3Repository, fileName , file);
			
	        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
	        AWSCredentials awsCredentials = new BasicAWSCredentials (amazonS3AccessKey, amazonS3Secret);
			amazonS3 = new AmazonS3Client(awsCredentials);
			
	        amazonS3.putObject(putObjectRequest);
	        System.out.println(amazonS3UriContext);
	        System.out.println(fileName);
	        return amazonS3UriContext + fileName;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
    }


}