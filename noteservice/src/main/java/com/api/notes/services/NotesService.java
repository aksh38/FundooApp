package com.api.notes.services;


import com.api.notes.dto.NotesDto;
import com.api.notes.models.Note;
import java.util.List;

public interface NotesService {

	boolean createNote(NotesDto notesDto, String token);
	boolean updateNote(Note note, String token);
	boolean deleteNotes(Note note, String token);
	List<Note> getNotes(String token);
	
	boolean archieveNotes(Long noteid);
	boolean labelNotes(String label, Long noteid);
	boolean organizeNotes(Note note);
	boolean addReminder(String noteid);
	Note searchNote(Note note);
	String verifyToken(String token);
	
	
}
