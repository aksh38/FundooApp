package com.api.user.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	void addProfilePic(MultipartFile file, String token );
	
	Resource downloadFile(String fileName);
}
