package com.api.notes.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.notes.dto.CollaboratorDto;
import com.api.notes.response.Response;
import com.api.notes.services.CollaboratorService;

@RestController
@RequestMapping("/api/collaborator/")
public class CollaboratorController {

	@Autowired
	private CollaboratorService collaboratorService;
	
	@PostMapping
	public ResponseEntity<?> addCollaborator(@RequestBody CollaboratorDto collaboratorDto, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");

		collaboratorService.addCollaborator(collaboratorDto, token);

		Response response=new Response();
		response.setStatusCode(200);
		response.setStatusMessage("Collaborator added.......");
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	
}
