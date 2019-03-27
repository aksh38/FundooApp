package com.api.notes.services;

import java.util.List;
import java.util.Set;

import com.api.notes.dto.TotalNotesDto;
import com.api.notes.models.Label;

public interface LabelService {
	
	Set<Label> getAllLabel(String token);

	void updateLabel(Label label, String token);

	void createLabel(Label label, String token);

	void deleteLabel(Long labelId, String token);
	
	Label getLabel(String labelValue, String token);
	
	List<TotalNotesDto> getLabeledNotes(String labelValue, String token);
	
	
}
