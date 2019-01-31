package com.api.notes.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

@Table(name="notes")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long noteId;
	
	@Column(name="username")
	private String username;

	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="archieve")
	private boolean archieve;
	
	@Column(name="trash")
	private boolean trash;
	
	@Column(name="pin")
	private boolean pin;
	
	@Column(name="imageUrl")
	private String imageUrl;
	
	@Column(name="color")
	private String color;
	
	@Column(name="reminder")
	private LocalDateTime reminder;

	@Column(name="createdate")
	private LocalDateTime createdate;

	@Column(name="updateddate")
	private LocalDateTime updateddate;
	
}
