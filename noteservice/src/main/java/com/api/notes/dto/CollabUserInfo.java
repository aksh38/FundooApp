package com.api.notes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CollabUserInfo {

	private String userName;
	private String emailId;
	private String profileImg;
}
