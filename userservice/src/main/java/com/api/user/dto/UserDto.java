package com.api.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

	
	@NotNull(message = " Username can't be null")
	private String userName;

	@NotNull(message = " Email can't be null")
	@Email(regexp = "^[A-Za-z0-9]+@*(\\.[_A-Za-z0-9-]+)*@*[A-Za-z0-9-]+(\\.(?:[A-Z]{2,}|com|org))+$", message = "Email Not valid")
	private String emailId;

	@NotNull(message = " Name can't be null")
	private String name;

	
	@Pattern(regexp = "[A-za-z0-9]+[@_]*", message = "Password must contain Capital letter, Small letter, One number")
	@NotNull(message = "Password can't be null")
	private String password;

	@Pattern(regexp = "[0-9]{10}", message = "Mobile number is only in digits")
	private String mobileNumber;

}
