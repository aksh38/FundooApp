package com.api.notes.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.notes.dto.NotesDto;
import com.api.notes.models.Note;
import com.api.notes.response.Response;
import com.api.notes.services.NotesService;

/**
 * @author admin1
 *
 */
@RestController
@CrossOrigin(origins="http://localhost:4200",exposedHeaders= {"jwt_token"})
@RequestMapping("/api/notes")
public class NotesController {

	@Autowired	
	private NotesService notesService;

	@PostMapping
	public ResponseEntity<?> createNote(@RequestBody NotesDto notesDto, HttpServletRequest request) {
		String token = request.getHeader("jwt_token");

		notesService.createNote(notesDto, token);

		Response response = new Response();
		response.setStatusCode(200);
		response.setStatusMessage("New Note Created....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> updateNotes(@RequestBody Note note, HttpServletRequest request) {
		String token = request.getHeader("jwt_token");

		notesService.updateNote(note, token);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Notes Updated....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteNotes(@RequestParam Long noteId, HttpServletRequest request) {
		String token = request.getHeader("jwt_token");

		notesService.deleteNotes(noteId, token);

		Response response = new Response();

		response.setStatusCode(200);
		response.setStatusMessage("Note deleted....");

		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> getNotes(@RequestHeader("jwt_token") String token, @RequestParam Boolean archived, @RequestParam Boolean trashed, @RequestParam Boolean pinned) {
		
		List<Note> notes = notesService.getNoteList(token, archived, trashed, pinned);

		return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);

	}
	
	@GetMapping("/sortByTitle")
	public List<?> sortByTitle()
	{
		return notesService.sortByTitle();
	}
	
	@GetMapping("/sortByDate")
	public List<?> sortByDate()
	{
		return notesService.sortByDate();
	}

}
