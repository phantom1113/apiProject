package com.myclass.service;


import org.springframework.web.multipart.MultipartFile;

public interface AmazonClientService {	
	public String uploadFile(MultipartFile multipartFile);
	
	public void deleteFile(String keyName);
}
