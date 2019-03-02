package com.api.notes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CollaboratorDto {

	private Long noteId;
	
	private Long userId;

}
