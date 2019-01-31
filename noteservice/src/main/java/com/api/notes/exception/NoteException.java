package com.api.notes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NoteException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String statusMessage;
	
	NoteException(String message)
	{
		this.statusMessage=message;
	}
	
	NoteException(int errorCode, String statusMessage, Throwable throwable)
	{
		super(statusMessage, throwable);
		this.errorCode=errorCode;
	}
}
