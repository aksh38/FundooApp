package com.api.notes.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.notes.dto.CollaboratorDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Collaborator;
import com.api.notes.models.Note;
import com.api.notes.repository.CollabaratorRepository;
import com.api.notes.repository.NotesRepository;
import com.api.notes.util.TokenUtil;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

	@Autowired
	private CollabaratorRepository collabRepo;
	
	@Autowired
	private NotesRepository noteRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@Override
	public void addCollaborator(CollaboratorDto collaboratorDto, String token) {

		long userId=TokenUtil.verifyToken(token);
								 
		if(collaboratorDto.getUserId() != userId)
		{	
			Collaborator collaborator= modelMapper.map(collaboratorDto, Collaborator.class);
			collaborator.setUserId(collaboratorDto.getUserId());		
			collabRepo.save(collaborator);
		}
		else {
			throw new NoteException(403, "Collab already added......");
		}
	}
	
	@Override
	public void removeCollaborator(Long userId, Long noteId, String token)
	{
		try {
		TokenUtil.verifyToken(token);
		Long collabId=collabRepo.findBy(noteId, userId)
											.orElseThrow(()-> new NoteException(404,"Collaborator not found"));
		collabRepo.deleteById(collabId);
		}catch (IllegalArgumentException exception ) {
			throw new NoteException(exception.getMessage());
		}
	}
	
	public List<Note> getSharedNotes(String token)
	{
		long userId=TokenUtil.verifyToken(token);
		List<Note> notes=noteRepo.findNoteByNoteIdIn(collabRepo.findNoteIdByUserId(userId)
															   .orElse(new ArrayList<Long>()))
								 .orElse(new ArrayList<Note>());
		notes.forEach(System.out::println);
		return notes;
	}
}
