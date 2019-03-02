package com.api.notes.controllers;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.notes.dto.TotalNotesDto;
import com.api.notes.models.Label;
import com.api.notes.models.Note;
import com.api.notes.response.Response;
import com.api.notes.services.LabelService;

@RestController
@CrossOrigin(origins="http://localhost:4200",exposedHeaders= {"jwt_token"})
@RequestMapping("/api/labels")
public class LabelController {

	@Autowired
	private LabelService labelService;

	@PostMapping
	public ResponseEntity<?> createLabel(@RequestBody Label label, @RequestHeader("jwt_token")String token) {

		labelService.createLabel(label, token);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label added successfully......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	

	@GetMapping
	public Set<Label> getLabels(@RequestHeader("jwt_token")String token) {

		return labelService.getAllLabel(token);

	}

	

	@DeleteMapping
	public ResponseEntity<?> deleteLabel(@RequestParam Long labelId, @RequestHeader("jwt_token")String token)
	{
		
		labelService.deleteLabel(labelId, token);
		
		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label removed successfully.......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	
	@PutMapping
	public ResponseEntity<?> updateLabel(@RequestBody Label label,@RequestHeader("jwt_token")String token) {
				
		labelService.updateLabel( label, token);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label updated successfully.......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getLabel")
	public Label getLabel(@RequestParam String labelValue, @RequestHeader("jwt_token") String token)
	{
		return labelService.getLabel(labelValue, token);
	}
	
	@GetMapping("/labeledNotes/{labelValue}")
	public List<TotalNotesDto> getlLabeledNotes(@PathVariable String labelValue, @RequestHeader("jwt_token") String token)
	{
		return labelService.getLabeledNotes(labelValue, token);
	}

}
