package com.api.notes.services;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.notes.dto.CollaboratorDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Collaborator;
import com.api.notes.models.Note;
import com.api.notes.repository.NotesRepository;
import com.api.notes.util.TokenUtil;

@Service
public class CollaboratorServiceImpl implements CollaboratorService {

	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public void addCollaborator(CollaboratorDto collaboratorDto, String token) {

		TokenUtil.verifyToken(token);
		
		Note note = notesRepo.findById(collaboratorDto.getNoteId())
							 .orElseThrow(() -> new NoteException(404, "Notes not found....."));


		Collaborator collaborator=modelMapper.map(collaboratorDto, Collaborator.class);

		List<Collaborator> collaborators = note.getCollaborators();

		collaborators.add(collaborator);

		note.setCollaborators(collaborators);
		
		note.setUpdatedDate(LocalDateTime.now());

		notesRepo.save(note);

	}

}
