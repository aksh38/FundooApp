package com.api.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailDto {

	private String toEmail;
	
	private String subject;
	
	private String body;
	
}
