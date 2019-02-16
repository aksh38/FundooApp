package com.api.notes.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class NoteException extends RuntimeException {


	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String statusMessage;
	
	public NoteException(String statusMessage)
	{
		this.statusMessage=statusMessage;
	}
	public NoteException(int errorCode, String statusMessage)
	{
		this.statusMessage=statusMessage;
		this.errorCode=errorCode;
	}
	public NoteException(int errorCode, String statusMessage, Throwable throwable)
	{
		super(statusMessage, throwable);
		this.errorCode=errorCode;
	}
}
