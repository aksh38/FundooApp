package com.api.notes.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void addLabel(Long noteId, Long labelId, String token) {

		try {

			TokenUtil.verifyToken(token);

			Note note = notesRepo.findById(noteId).orElseThrow(() -> new NoteException(404, "Notes not found....."));

			Label label = labelRepo.findById(labelId).orElseThrow(() -> new NoteException(404, "Label not found....."));

			note.getLabels().add(label);
			
			note.setUpdatedDate(LocalDateTime.now());

			//label.getNotes().add(note);

			//labelRepo.save(label);
			
			notesRepo.save(note);

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
			
		} catch (Exception e) {
			throw new NoteException(e.getMessage());
		}
	}

	@Override
	public void deleteLabel(Label label, String token) {

		try {

			TokenUtil.verifyToken(token);
			//List<Note> notes= labelRepo.findById(labelId).get().getNotes();
			
			labelRepo.delete(label);
			
			//notes.parallelStream().forEach(note);

		} catch (Exception e) {

			throw new NoteException(400, "Label Deleting failed.....");

		}
	}

	@Override
	public List<Label> getNotesLabel(Long noteId, String token) {

		TokenUtil.verifyToken(token);

		Note note = notesRepo.findById(noteId).orElseThrow(() -> new NoteException(404, "Notes Not Found....."));

		List<Label> labels = note.getLabels();

		return labels;
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
		
		Long userId=TokenUtil.verifyToken(token);
		
		label.setUserId(userId);
		
		labelRepo.save(label);
		
	}

}
