package com.api.notes.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.notes.dto.CollaboratorDto;
import com.api.notes.dto.TotalNotesDto;
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
	
	@Autowired
	private ElasticService elasticService;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public void addCollaborator(CollaboratorDto collaboratorDto, String token) {

		long userId = TokenUtil.verifyToken(token);

		if (collaboratorDto.getUserId() != userId) {
			Collaborator collaborator = modelMapper.map(collaboratorDto, Collaborator.class);
			collaborator.setUserId(collaboratorDto.getUserId());
			collabRepo.save(collaborator);
		} else {
			throw new NoteException(403, "Collab already added......");
		}
	}

	@Override
	public void removeCollaborator(TotalNotesDto notesDto, String token) {
		try {

			TokenUtil.verifyToken(token);
			List<Long> userIds = notesDto.getCollabUserInfos().stream()
					.map(userInfo -> findUserByEmail(userInfo.getEmailId())).collect(Collectors.toList());

			for (Long userId : userIds) {
				Long collabId = collabRepo.findBy(notesDto.getNote().getNoteId(), userId)
						.orElseThrow(() -> new NoteException(404, "Collaborator not found"));
				collabRepo.deleteById(collabId);
			}
		} catch (IllegalArgumentException exception) {
			throw new NoteException(exception.getMessage());
		}
	}

	
	private Long findUserByEmail(String emailId) {
		return restTemplate.getForObject("http://localhost:8084/api/user/email/" + emailId, Long.class);
	}

	public List<Note> getSharedNotes(String token) {
		long userId = TokenUtil.verifyToken(token);
		List<Note> notes = noteRepo
				.findNoteByNoteIdIn(collabRepo.findNoteIdByUserId(userId).orElse(new ArrayList<Long>()))
				.orElse(new ArrayList<Note>());
		notes.forEach(System.out::println);
		return notes;
	}
}
