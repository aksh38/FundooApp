package com.api.notes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LabelDto {
	
	private Long noteId;
	
	private String labelValue;
	
}
