package com.api.notes.services;

import com.api.notes.dto.CollaboratorDto;
import com.api.notes.dto.TotalNotesDto;


public interface CollaboratorService {

	void addCollaborator(CollaboratorDto collaboratorDto, String token);

	void removeCollaborator(TotalNotesDto dto,String token);

	

	
}
