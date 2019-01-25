package com.api.notes.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.notes.dto.NotesDto;
import com.api.notes.models.Note;
import com.api.notes.services.NotesService;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;
	
	
	@PostMapping("/createNote/{token}")
	public void createNote(@Valid @RequestBody NotesDto notesDto, @PathVariable String token, HttpServletRequest request)
	{
		
		notesService.createNote(notesDto, token);
	}
	
	
	@PutMapping("/updateNote/{token}")
	public ResponseEntity<?> updateNotes(@Valid @RequestBody Note note, @PathVariable String token, HttpServletRequest request)
	{
		
		if(notesService.updateNote(note, token))
		{
			return new ResponseEntity<String>("Notes Updated Successfully.....", HttpStatus.OK);
		}
		return new ResponseEntity<String>("You don't authorize to update notes.....", HttpStatus.OK);
	}
	
	
	@PostMapping("/deleteNotes/{token}")
	public ResponseEntity<?> deleteNotes(@RequestBody Note note, @PathVariable String token)
	{
		if(notesService.deleteNotes(note, token))
		{
			return new ResponseEntity<String>("Notes Removed Successfully.....", HttpStatus.OK);
		}
		return new ResponseEntity<String>("You don't authorize to remove notes.....", HttpStatus.OK);
	}
	
	@GetMapping("/getNotes/{token}")
	public ResponseEntity<?> getNotes(@PathVariable String token)
	{
		
		List<Note> notes= notesService.getNotes(token);
		return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
		
	}
	
}
