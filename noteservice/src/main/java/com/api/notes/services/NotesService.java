package com.api.notes.services;


import com.api.notes.dto.NotesDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Note;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesService {

	void createNote(NotesDto notesDto, String token)throws NoteException;
	
	void updateNote(Note note, String token)throws NoteException;
	
	void deleteNotes(Long noteId, String token) throws NoteException;
	
	List<Note> getNoteList(String token, Boolean archived, Boolean trashed, Boolean pinned);
	
	List<Note> getArchivedNotes(String token);
	
	void addReminder(Note note, LocalDateTime time);
	
	Note searchNote(Long noteId);

	List<Note> sortByTitle();

	List<Note> sortByDate();

	
}
