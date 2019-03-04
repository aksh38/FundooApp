package com.api.notes.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.notes.dto.CollaboratorDto;
import com.api.notes.dto.TotalNotesDto;
import com.api.notes.response.Response;
import com.api.notes.services.CollaboratorService;

@RestController
@CrossOrigin(origins="http://localhost:4200", exposedHeaders = {"jwt_token" })
@RequestMapping("/api/collab/")
public class CollaboratorController {

	@Autowired
	private CollaboratorService collaboratorService;
	
	@PostMapping
	public ResponseEntity<?> addCollaborator(@RequestBody CollaboratorDto collaboratorDto, HttpServletRequest request) {
		
		String token = request.getHeader("jwt_token");

		collaboratorService.addCollaborator(collaboratorDto, token);

		Response response=new Response();
		response.setStatusCode(200);
		response.setStatusMessage("Collaborator added.......");
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PostMapping("/remove")
	public ResponseEntity<?> removeCollaborator(@RequestBody TotalNotesDto dto,@RequestHeader("jwt_token") String token) {
		
		System.out.println("hello");
		collaboratorService.removeCollaborator(dto, token);

		Response response=new Response();
		response.setStatusCode(200);
		response.setStatusMessage("Collaborator Removed.......");
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	
}
