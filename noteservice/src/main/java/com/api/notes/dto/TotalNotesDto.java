package com.api.notes.dto;

import java.math.BigInteger;
import java.util.List;

import com.api.notes.models.Note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TotalNotesDto {

	private Note note;
	private List<CollabUserInfo> collabUserInfos;
}
