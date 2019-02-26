package com.api.notes.dto;

import java.util.List;

import com.api.notes.models.Note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TotalNotesDto {

	private Note note;
	private List<CollabUserInfo> collabrators;
}
