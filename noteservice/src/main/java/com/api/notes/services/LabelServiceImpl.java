package com.api.notes.services;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.api.notes.dto.CollabUserInfo;
import com.api.notes.dto.TotalNotesDto;
import com.api.notes.exception.NoteException;
import com.api.notes.models.Label;
import com.api.notes.models.Note;
import com.api.notes.repository.CollabaratorRepository;
import com.api.notes.repository.LabelRepository;
import com.api.notes.repository.NotesRepository;
import com.api.notes.util.TokenUtil;

@Service
public class LabelServiceImpl implements LabelService {

	@Autowired
	private NotesRepository notesRepo;

	@Autowired
	private LabelRepository labelRepo;
	
	@Autowired
	private CollabaratorRepository collabRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Override
	public void createLabel(Label label, String token) {

		try {

			Long userId = TokenUtil.verifyToken(token);

			label.setUserId(userId);

			labelRepo.save(label);

		} catch (Exception e) {

			throw new NoteException(400, "Label Creation failed.......");
		}

	}

	
	@Override
	public void deleteLabel(Long labelId, String token) {

		try {

			TokenUtil.verifyToken(token);
			
			Label label= labelRepo.findById(labelId).orElseThrow(()->new NoteException(404,"Label not found...."));
			
			label.getNotes().forEach(note-> note.getLabels().remove(label));
			
			label.setNotes(null);
			
			labelRepo.delete(label);
			

		} catch (Exception e) {

			throw new NoteException(400, "Label Deleting failed.....");

		}
	}

	@Override
	public Set<Label> getAllLabel(String token) {

		try {
			Long userId = TokenUtil.verifyToken(token);

			Set<Label> labels = labelRepo.findAll()
										  .stream().filter(label-> label.getUserId()==userId)
										  .collect(Collectors.toSet());
			
			return labels;

		} catch (Exception e) {

			e.printStackTrace();
			throw new NoteException(404, "No labels found.....");
		}
	}

	@Override
	public void updateLabel(Label label, String token) {
		
		TokenUtil.verifyToken(token);
		Label label2=labelRepo.findById(label.getLabelId()).orElseThrow(()->new NoteException(404, "Label not found....."));
		
		label2.setLabelValue(label.getLabelValue());
		
		labelRepo.save(label2);
		
	}

	@Override
	public Label getLabel(String labelValue, String token) {

		long userId=TokenUtil.verifyToken(token);
		
		return labelRepo.findAll()
						.stream()
						.filter(label-> label.getLabelValue().equals(labelValue)
								&& label.getUserId().equals(userId))
						.findFirst()
						.orElseThrow(()-> new NoteException(404, "Label Not found...."));
		 
	}

	@Override
	public List<TotalNotesDto> getLabeledNotes(String labelValue, String token) {
		Long userId= TokenUtil.verifyToken(token);
		
		Label label= labelRepo.findAll()
						.stream()
						.filter(lbl-> lbl.getUserId().equals(userId)
								&& lbl.getLabelValue().equals(labelValue))
						.findFirst()
						.orElseThrow(()-> new NoteException(404, "Label Not found...."));
		Set<Note> notes=label.getNotes();
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


}
