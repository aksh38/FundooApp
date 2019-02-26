package com.api.notes.services;


import java.util.List;

import com.api.notes.models.Label;
import com.api.notes.models.Note;

public interface LabelService {
	
	List<Label> getAllLabel(String token);

	void updateLabel(Label label, String token);

	void createLabel(Label label, String token);

	void deleteLabel(Long labelId, String token);
	
	Label getLabel(String labelValue, String token);
	
	List<Note> getLabeledNotes(String labelValue, String token);
	
	
}
