package com.api.notes.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.notes.dto.NotesDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Note;
import com.api.notes.repository.NotesRepository;
import com.api.notes.util.NotesUtil;

@Service
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void createNote(NotesDto notesDTO, String token) throws NoteException {
	
		Note note = modelMapper.map(notesDTO, Note.class);
		
		String userid= NotesUtil.verifyToken(token);
		
		note.setUsername(userid);
		
		note.setCreatedate(LocalDateTime.now());
		
		note.setUpdateddate(LocalDateTime.now());
			
		notesRepo.save(note);

	}

	@Override
	public void updateNote(Note note, String token) throws NoteException{

		String userid= NotesUtil.verifyToken(token);
		
		note.setUsername(userid);
		
		note.setUpdateddate(LocalDateTime.now());
		
		notesRepo.save(note);
	}
	
	
	@Override
	public void deleteNotes(Note note, String token) throws NoteException{
		
		NotesUtil.verifyToken(token);
		notesRepo.delete(note);
	}	
	
	/*
	 * @Override public Note getNoteById(Note note) { Optional<Note>
	 * note=notesRepo.findById(note);
	 * 
	 * if(note.isPresent()) return note.get(); return null; }
	 */
	
	@Override
	public List<Note> getNoteList(String token) {
		
		String username= NotesUtil.verifyToken(token);
		
		List<Note> allNotes= notesRepo.findAll();
		
		List<Note> notes=new ArrayList<Note>();
		
		
		allNotes.stream().filter(note-> note.getUsername().equals(username)).forEach(notes::add);
		
		return notes;
	}
	
	public List<String> getLabelList(String token){
		
		List<Note> userNotes= getNoteList(token);
		
		List<String> lables= new ArrayList<String>();
		
		userNotes.parallelStream().forEach(note-> lables.add(note.getTitle()));
		
		return lables;
		
	}
	
	@Override
	public void archieveNotes(Note note) {
		
		note.setArchieve(true);
	}

	@Override
	public void labelNotes(String label, Note note) {
		
		note.setTitle(label);
		notesRepo.save(note);
	}

	
	@Override
	public void addReminder(Note note, LocalDateTime time) {
		
		note.setReminder(time);
	}

	@Override
	public Note searchNote(Note note) {
		
		return null;
	}

	

	
	

}
