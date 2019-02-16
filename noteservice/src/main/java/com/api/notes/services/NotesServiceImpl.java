package com.api.notes.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.notes.dto.NotesDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Note;
import com.api.notes.repository.NotesRepository;
import com.api.notes.util.TokenUtil;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void createNote(NotesDto notesDTO, String token) throws NoteException {
		try {
			Note note = modelMapper.map(notesDTO, Note.class);
			long userId = TokenUtil.verifyToken(token);
			note.setUserId(userId);
			note.setCreateDate(LocalDateTime.now());
			note.setUpdatedDate(LocalDateTime.now());
			notesRepo.save(note);
			
		} catch (Exception e) {

			throw new NoteException(400, e.getMessage(), e);
		}
	}

	@Override
	public void updateNote(Note note, String token) throws NoteException {

		long userId = TokenUtil.verifyToken(token);

		note.setUserId(userId);

		note.setUpdatedDate(LocalDateTime.now());

		notesRepo.save(note);
	}

	@Override
	public void deleteNotes(Long noteId, String token) throws NoteException {
		TokenUtil.verifyToken(token);
		notesRepo.findById(noteId).ifPresentOrElse(notesRepo::delete, ()->{
			throw new NoteException(404, "Note not foound.....");
		});
		/*
		 * Note note = notesRepo.findById(noteId).orElse
		 * 
		 * Throw(() -> new
		 * NoteException(404, "Note not foound....."));
		 * 
		 * notesRepo.delete(note);
		 */
	}

	@Override
	public List<Note> getNoteList(String token, Boolean archived, Boolean trashed, Boolean pinned) throws NoteException{

			Long userId = TokenUtil.verifyToken(token);
			

			List<Note> allNotes = notesRepo.findAll();

			List<Note> notes = allNotes.stream()
					.filter(note -> note.getUserId().equals(userId) 
								  && note.isArchieve()==archived
								  && note.isTrash()==trashed
								  && note.isPin()==pinned
								  )
					.collect(Collectors.toList());

			return notes;
	}

	@Override
	public List<Note> sortByTitle() {
		List<Note> notes = notesRepo.findAll();

		notes.sort(Comparator.comparing(Note::getTitle));

		return notes;
	}

	@Override
	public List<Note> sortByDate() {
		List<Note> notes = notesRepo.findAll();

		notes.sort(Comparator.comparing(Note::getCreateDate));

		return notes;
	}   

	
	@Override
	public void addReminder(Note note, LocalDateTime time) {

		note.setReminder(time);
	}

	@Override
	public List<Note> getArchivedNotes(String token) {
		try {
			Long userId = TokenUtil.verifyToken(token);

			List<Note> allNotes = notesRepo.findAll();

			List<Note> notes = allNotes.stream()
									   .filter(note -> note.getUserId()
											   .equals(userId) && note.isArchieve())
									   .collect(Collectors.toList());

			return notes;
		} catch (Exception e) {
			throw new NoteException(e.getMessage());
		}
	}

	@Override
	public Note searchNote(Long noteId) {

		Note note = notesRepo.findById(noteId).orElseThrow(() -> new NoteException(404, "Notes not found....!!"));

		return note;
	}
}
