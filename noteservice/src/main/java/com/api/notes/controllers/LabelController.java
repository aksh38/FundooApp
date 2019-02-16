package com.api.notes.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.notes.models.Label;
import com.api.notes.response.Response;
import com.api.notes.services.LabelService;

@RestController
@RequestMapping("/api/labels")
public class LabelController {

	@Autowired
	private LabelService labelService;

	@PostMapping
	public ResponseEntity<?> createLabel(@RequestBody Label label, HttpServletRequest request) {
		String token = request.getHeader("Authorization");

		labelService.createLabel(label, token);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label added successfully......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	@PostMapping("/addLabel")
	public ResponseEntity<?> addLabel(@RequestParam Long labelId, @RequestParam Long noteId,
			HttpServletRequest request) {

		String token = request.getHeader("Authorization");

		labelService.addLabel(noteId, labelId, token);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label added successfully......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping
	public List<Label> getLabels(HttpServletRequest request) {

		String token = request.getHeader("Authorization");

		return labelService.getAllLabel(token);

	}

	@GetMapping("/noteLabels")
	public List<Label> getNotesLabels(@RequestParam Long noteId, HttpServletRequest request) {

		String token = request.getHeader("Authorization");

		return labelService.getNotesLabel(noteId, token);

	}

	@DeleteMapping
	public ResponseEntity<?> deleteLabel(@RequestBody Label label, HttpServletRequest request)
	{
		String token = request.getHeader("Authorization");
		
		labelService.deleteLabel(label, token);
		
		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label removed successfully.......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/noteLabels")
	public ResponseEntity<?> removeNoteLabel(@RequestParam Long noteId, @RequestParam Long labelId,
			HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");

		labelService.removeLabel(noteId, labelId, token);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label removed successfully.......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	
	}

	@PutMapping
	public ResponseEntity<?> updateLabel(@RequestBody Label label, HttpServletRequest request) {
		
		String token = request.getHeader("Authorization");
		
		labelService.updateLabel( label, token);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label updated successfully.......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

}
