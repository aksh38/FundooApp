package com.api.notes.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.notes.dto.NotesDto;
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
	public boolean createNote(NotesDto notesDTO, String token) {
		Note note = modelMapper.map(notesDTO, Note.class);
		try {
			
			String userid= verifyToken(token);
			note.setUserid(userid);
			note.setCreatedate(LocalDateTime.now());
			note.setUpdateddate(LocalDateTime.now());
			
			notesRepo.save(note);

			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateNote(Note note, String token) {

		String userid= verifyToken(token);
		
		if(userid.equals(note.getUserid()))
		{
			note.setUpdateddate(LocalDateTime.now());
			notesRepo.save(note);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean deleteNotes(Note note, String token) {
		
		String userid= verifyToken(token);
		
		if(userid.equals(note.getUserid()))
		{
			notesRepo.delete(note);
			return true;
		}
		return false;
	}
	
	@Override
	public List<Note> getNotes(String token) {
		
		List<Note> allNotes= notesRepo.findAll();
		String userid= verifyToken(token);
		
		List<Note> notes=new ArrayList<Note>();
		allNotes.stream().filter(note-> note.getUserid().equals(userid)).forEach(notes::add);
		return notes;
	}
	
	@Override
	public boolean archieveNotes(Long noteid) {

		return false;
	}

	@Override
	public boolean labelNotes(String label, Long noteid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean organizeNotes(Note note) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addReminder(String noteid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Note searchNote(Note note) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String verifyToken(String token) {
		
		String userid=NotesUtil.verifyToken(token);
		return userid;
	}

	
	

}
