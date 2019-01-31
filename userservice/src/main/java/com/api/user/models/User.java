package com.api.user.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="users")
public class User{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(unique=true)
	private String userName;

	private String emailId;

	private String name;
	
	private String password;

	private String mobileNumber;
	
	private boolean isVerified;
	
}
