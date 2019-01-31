package com.api.user.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

	@NotNull(message="Username can't be null")
	private String username;
	
	@NotNull(message="Password can't be null")
	@Pattern(regexp="[A-za-z0-9]+[@_]*", message="Password must contain Capital letter, Small letter, One number")
	private String password;
	
}
