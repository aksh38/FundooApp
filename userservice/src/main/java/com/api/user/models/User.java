package com.api.user.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

public class User{

	@Id
	private String username;
	private String emailid;
	
	private String firstname;
	
	private String lastname;
	
	private String password;
	
	private String mobilenumber;
	
	private String role;
	
}
