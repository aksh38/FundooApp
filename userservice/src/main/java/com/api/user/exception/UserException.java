package com.api.user.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@Getter
@Setter
public class UserException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	private int errcode;
	
	public UserException(String message)
	{
		super(message);
	}
	
	public UserException(String message, Throwable throwable)
	{
		super(message, throwable);
	}
	
	public UserException(int errcode, String message)
	{
		super(message);
		this.errcode=errcode;
		
	}
	
	public UserException(int errcode, String message, Throwable throwable)
	{
	
		super(message, throwable);
		this.errcode= errcode;
	}
	
}
	