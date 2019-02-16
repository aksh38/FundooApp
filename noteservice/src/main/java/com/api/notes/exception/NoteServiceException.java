package com.api.notes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.api.notes.response.Response;

@ControllerAdvice
public class NoteServiceException {


	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> exceptionHandler(Exception exception)
	{
		Response response=new Response();
		response.setStatusCode(500);
		response.setStatusMessage(exception.getMessage());
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<?> noteExceptionHandler(NoteException exception)
	{
		Response response=new Response();
		
		response.setStatusCode(exception.getErrorCode());
		response.setStatusMessage(exception.getStatusMessage());
		
		return new ResponseEntity<Response>(response, HttpStatus.OK);
		
	}
}
