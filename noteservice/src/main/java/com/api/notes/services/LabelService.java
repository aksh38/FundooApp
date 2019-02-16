package com.api.notes.services;


import java.util.List;

import com.api.notes.models.Label;

public interface LabelService {

	void addLabel(Long noteId, Long labelId, String token);
	
	void removeLabel(Long noteId, Long labelId, String token);
	
	List<Label> getNotesLabel(Long noteId, String token);
	
	List<Label> getAllLabel(String token);

	void updateLabel(Label label, String token);

	void createLabel(Label label, String token);

	void deleteLabel(Label label, String token);
	
	
}
