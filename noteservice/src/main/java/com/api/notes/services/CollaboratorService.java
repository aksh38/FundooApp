package com.api.notes.services;


import com.api.notes.dto.CollaboratorDto;

public interface CollaboratorService {

	void addCollaborator(CollaboratorDto collaboratorDto, String token);

	
}
