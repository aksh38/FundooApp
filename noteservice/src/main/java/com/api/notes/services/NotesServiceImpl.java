package com.api.notes.services;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ConfigurationException;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.api.notes.dto.CollabUserInfo;
import com.api.notes.dto.NotesDto;
import com.api.notes.dto.TotalNotesDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Label;
import com.api.notes.models.Note;
import com.api.notes.repository.CollabaratorRepository;
import com.api.notes.repository.LabelRepository;
import com.api.notes.repository.NotesRepository;
import com.api.notes.util.TokenUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotesServiceImpl implements NotesService {

	@Autowired
	private NotesRepository notesRepo;
	
	@Autowired
	private LabelRepository labelRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CollabaratorRepository collabRepo;

	@Autowired
	private RabbitMqSender sender;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ElasticService elasticService;
	
	@Override
	public void createNote(NotesDto notesDTO, String token) throws NoteException {
		try {
		Note note = modelMapper.map(notesDTO, Note.class);
		long userId = TokenUtil.verifyToken(token);
		note.setUserId(userId);
		note=notesRepo.save(note);
		sender.send(note);
		}
		catch (IllegalArgumentException|ConfigurationException|MappingException e) {
			log.info(e.getMessage());
			throw new NoteException(e.getMessage());
			
		}
	}

	@Override
	public void updateNote(Note note, String token) throws NoteException {
		TokenUtil.verifyToken(token);
		note.setUpdatedDate(LocalDateTime.now());
		note=notesRepo.save(note);
		elasticService.update(note);
	}

	@Override
	public void deleteNotes(Long noteId, String token) throws NoteException {
		TokenUtil.verifyToken(token);
		notesRepo.findById(noteId).ifPresentOrElse(note->{
					note.setLabels(new HashSet<>());
					notesRepo.delete(note);}
				,()->new NoteException(404, "Note not foound....."));
		elasticService.delete(""+noteId);
	}
	
	
	@Override
	public void addLabel(Long noteId, Long labelId) {
		notesRepo.findById(noteId).map(note->{
			Label label = labelRepo.findById(labelId).get();
			note.getLabels().add(label);
			note.setUpdatedDate(LocalDateTime.now());
			elasticService.update(note);
			return note;
		}).ifPresent(notesRepo::save);
		
	}
	
	
	@Override
	public List<TotalNotesDto> getNoteList(String token, boolean archived, boolean trashed)
	{
		Long userId=TokenUtil.verifyToken(token);
		
		List<Note> notes=notesRepo.findAll()
								  .stream()
								  .filter(note-> note.getUserId().equals(userId)
										  	&& note.isArchive()==archived
										  	&& note.isTrash()==trashed)
								  .collect(Collectors.toList());
		
		List<Long> noteIds=collabRepo.findNoteIdByUserId(userId).orElse(new ArrayList<Long>());
		
		if(noteIds.size()>0) {
			notes.addAll(notesRepo.findNoteByNoteIdIn(noteIds).orElse(new ArrayList<Note>()));
		}
		List<TotalNotesDto> allNotes=new ArrayList<TotalNotesDto>();
		for(Note note:notes)
		{
			List<BigInteger> userIds=collabRepo.findUserIdByNoteId(note.getNoteId()).orElse(new ArrayList<BigInteger>());
			List<CollabUserInfo> collabUserInfos=this.getCollaborator(userIds);
			allNotes.add(new TotalNotesDto(note, collabUserInfos));
		}
		return allNotes;
	}
	
	private List<CollabUserInfo> getCollaborator(List<BigInteger> userIds)
	{
		try {
		return Arrays.asList(restTemplate.postForObject("http://localhost:8084/api/user/details", userIds, CollabUserInfo[].class));
		}
		catch(RestClientException exception)
		{
			throw new NoteException(400, exception.getMessage());
		}
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
		notesRepo.save(note);
		elasticService.update(note);
	}

	@Override
	public void archiveNote(Note note, String token) {
		try {
			TokenUtil.verifyToken(token);
			note.setArchive(!note.isArchive());
			
			this.updateNote(note, token);
			elasticService.update(note);
			
		} catch (Exception e) {
			throw new NoteException(e.getMessage());
		}
	}

	@Override
	public void removeLabel(Long noteId, Long labelId, String token) {

		try {

			TokenUtil.verifyToken(token);

			Note note = notesRepo.findById(noteId)
					.orElseThrow(() -> new NoteException(404, "Notes not found......"));

			Label label = labelRepo.findById(labelId)
					.orElseThrow(() -> new NoteException(404, "Notes not found......"));

			note.getLabels().remove(label);

			notesRepo.save(note);
			elasticService.update(note);
			
		} catch (Exception e) {
			throw new NoteException(e.getMessage());
		}
	}

	@Override
	public List<TotalNotesDto> searchNotes(String token, String keyword, String field) {
		Long userId= TokenUtil.verifyToken(token);
		List<Note> notes= elasticService.search(keyword, field);
		List<Long> noteIds=collabRepo.findNoteIdByUserId(userId).orElse(new ArrayList<Long>());
		
		if(noteIds.size()>0) {
			notes.addAll(notesRepo.findNoteByNoteIdIn(noteIds).orElse(new ArrayList<Note>()));
		}
		List<TotalNotesDto> allNotes=new ArrayList<TotalNotesDto>();
		for(Note note:notes)
		{
			List<BigInteger> userIds=collabRepo.findUserIdByNoteId(note.getNoteId()).orElse(new ArrayList<BigInteger>());
			List<CollabUserInfo> collabUserInfos=this.getCollaborator(userIds);
			allNotes.add(new TotalNotesDto(note, collabUserInfos));
		}
		return allNotes;
	}
}
