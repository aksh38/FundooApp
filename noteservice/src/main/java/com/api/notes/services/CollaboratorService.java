package com.api.notes.services;


import java.util.List;

import com.api.notes.dto.CollaboratorDto;
import com.api.notes.models.Collaborator;
import com.api.notes.models.Note;

public interface CollaboratorService {

	void addCollaborator(CollaboratorDto collaboratorDto, String token);

	void removeCollaborator(Long collabId, String token);

	List<Note> getSharedNotes(String token);

	
}
