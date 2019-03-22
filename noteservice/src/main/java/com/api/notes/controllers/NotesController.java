package com.api.notes.controllers;

import java.util.List;

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

import com.api.notes.dto.NotesDto;
import com.api.notes.dto.TotalNotesDto;
import com.api.notes.models.Note;
import com.api.notes.response.Response;
import com.api.notes.services.NotesService;
import com.api.notes.services.RabbitMqSender;

import lombok.extern.slf4j.Slf4j;

/**
 * @author admin1
 *
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = { "jwt_token" })
@RequestMapping("/api/notes")
@Slf4j
public class NotesController {

	@Autowired
	private NotesService notesService;
	

	@PostMapping
	public ResponseEntity<?> createNote(@RequestBody NotesDto notesDto, HttpServletRequest request) {
		String token = request.getHeader("jwt_token");
		
		log.info("i am here");
		
		notesService.createNote(notesDto, token);
		Response response = new Response();
		response.setStatusCode(200);
		response.setStatusMessage("New Note Created....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateNotes(@RequestHeader("jwt_token") String token, @RequestBody Note note) {
		notesService.updateNote(note, token);
		Response response = new Response();
		response.setStatusCode(200);
		response.setStatusMessage("Notes Updated....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteNotes(@RequestParam Long noteId, @RequestHeader("jwt_token")String token) {
		notesService.deleteNotes(noteId, token);

		Response response = new Response();
		response.setStatusCode(200);
		response.setStatusMessage("Note deleted....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping
	public List<TotalNotesDto> getNotes(@RequestHeader("jwt_token") String token, @RequestParam boolean archived,
			@RequestParam boolean trashed) {
		
		return notesService.getNoteList(token, archived, trashed);
	}

	@PostMapping("/addLabel")
	public ResponseEntity<?> addLabel(@RequestParam Long labelId, @RequestParam Long noteId) {

		notesService.addLabel(noteId, labelId);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Label added successfully......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/sortByTitle")
	public List<?> sortByTitle() {
		return notesService.sortByTitle();
	}

	@GetMapping("/sortByDate")
	public List<?> sortByDate() {
		return notesService.sortByDate();
	}

	@DeleteMapping("/removeLabels/{noteId}/{labelId}")
	public ResponseEntity<?> removeLabels(@PathVariable Long noteId, @PathVariable Long labelId,
			@RequestHeader("jwt_token") String token) {
		notesService.removeLabel(noteId, labelId, token);

		Response response = new Response();
		response.setStatusCode(200);
		response.setStatusMessage("Label removed successfully.......");

		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}
	
	@PutMapping("/archive")
	public ResponseEntity<?> archive(@RequestHeader("jwt_token") String token, @RequestBody Note note) {
		notesService.archiveNote(note, token);
		Response response = new Response();
		response.setStatusCode(200);
		response.setStatusMessage("Notes Updated....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
