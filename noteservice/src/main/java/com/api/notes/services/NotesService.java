package com.api.notes.services;


import com.api.notes.dto.NotesDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Note;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesService {

	void createNote(NotesDto notesDto, String token)throws NoteException;
	
	void updateNote(Note note, String token)throws NoteException;
	
	void deleteNotes(Note note, String token) throws NoteException;
	
	List<Note> getNoteList(String token);
	
	/* public Note getNoteById(Note note); */
	
	void archieveNotes(Note note);
	
	void labelNotes(String label, Note note);
	
	void addReminder(Note note, LocalDateTime time);
	
	Note searchNote(Note note);
	
}
