package com.api.notes.services;


import com.api.notes.dto.NotesDto;
import com.api.notes.dto.TotalNotesDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Note;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesService {

	void createNote(NotesDto notesDto, String token)throws NoteException;
	
	void updateNote(Note note, String token)throws NoteException;
	
	void deleteNotes(Long noteId, String token) throws NoteException;
	
	List<TotalNotesDto> getNoteList(String token, boolean archived, boolean trashed);
	
	void archiveNote(Note note,String token);
	
	void addLabel(Long noteId, Long labelId);
	
	void addReminder(Note note, LocalDateTime time);
	
	List<Note> sortByTitle();

	List<Note> sortByDate();

	void removeLabel(Long noteId, Long labelId, String token);
	
	List<TotalNotesDto> searchNotes(String token, String keyword, String field);
}
