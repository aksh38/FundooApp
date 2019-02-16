package com.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmailDto {

	private String toEmail;
	
	private String subject;
	
	private String body;
	
}
