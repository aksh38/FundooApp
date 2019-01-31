package com.api.user.response;

import java.util.List;

import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FieldErrorResponse {

	
	List<FieldError> fieldResponses;
	
}
