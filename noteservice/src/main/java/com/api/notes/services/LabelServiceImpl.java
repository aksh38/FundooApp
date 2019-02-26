package com.api.notes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.api.notes.exception.NoteException;
import com.api.notes.models.Label;
import com.api.notes.models.Note;
import com.api.notes.repository.LabelRepository;
import com.api.notes.repository.NotesRepository;
import com.api.notes.util.TokenUtil;

@Service
public class LabelServiceImpl implements LabelService {

	@Autowired
	private NotesRepository notesRepo;

	@Autowired
	private LabelRepository labelRepo;
	
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
	public List<Label> getAllLabel(String token) {

		try {
			Long userId = TokenUtil.verifyToken(token);

			List<Label> labels = labelRepo.findAll()
										  .stream().filter(label-> label.getUserId()==userId)
										  .collect(Collectors.toList());
			
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
	public List<Note> getLabeledNotes(String labelValue, String token) {
		Long userId= TokenUtil.verifyToken(token);
		
		Label label= labelRepo.findAll()
						.stream()
						.filter(lbl-> lbl.getUserId().equals(userId)
								&& lbl.getLabelValue().equals(labelValue))
						.findFirst()
						.orElseThrow(()-> new NoteException(404, "Label Not found...."));
		
		return label.getNotes();
	}



}
