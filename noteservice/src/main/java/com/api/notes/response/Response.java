package com.api.notes.response;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class Response {

	private String statusMessage;
	private int statusCode;
	
}
