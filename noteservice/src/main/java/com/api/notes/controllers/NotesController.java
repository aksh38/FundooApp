package com.api.notes.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.notes.dto.NotesDto;
import com.api.notes.models.Note;
import com.api.notes.response.Response;
import com.api.notes.services.NotesService;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;
	
	@Autowired
	private Response response;
	
	@PostMapping
	public ResponseEntity<?> createNote(@Valid @RequestBody NotesDto notesDto, HttpServletRequest request)
	{
		String token= request.getHeader("Authorization");
		
		notesService.createNote(notesDto, token);
		
		response.setStatusCode(200);
		response.setStatusMessage("New Note Created....");
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	
	@PutMapping("/updatenote")
	public ResponseEntity<?> updateNotes(@Valid @RequestBody Note note, HttpServletRequest request)
	{
		String token= request.getHeader("Authorization");
		
		notesService.updateNote(note, token);
		
		response.setStatusCode(200);
		response.setStatusMessage("Notes Updated....");
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/removenote")
	public ResponseEntity<?> deleteNotes(@RequestBody Note note, HttpServletRequest request)
	{
		String token= request.getHeader("Authorization");
		
		notesService.deleteNotes(note, token);
		
		response.setStatusCode(200);
		response.setStatusMessage("Note deleted....");
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/getnote")
	public ResponseEntity<?> getNotes(HttpServletRequest request)
	{
		String token= request.getHeader("Authorization");
		System.out.println("hiiii"+token);
		List<Note> notes= notesService.getNoteList(token);
		
		return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
		
	}
	
}
