package com.api.user.service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.user.exception.UserException;

@Service
public class ImageServiceImpl implements ImageService {

	private final Path rootLocation = Paths.get("src/main/resources/profileImages");

	@Override
	public void addProfilePic(MultipartFile file, String token) {

		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Resource downloadFile(String fileName) {

		try {
			Path filePath = this.rootLocation.resolve(fileName).normalize();

			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) {

				return resource;

			} else 
			{
				
				throw new UserException(404,"File not found " + fileName);
			
			}
		} catch (MalformedURLException e) {
			throw new UserException(400, e.getMessage());
		}

	}

}
