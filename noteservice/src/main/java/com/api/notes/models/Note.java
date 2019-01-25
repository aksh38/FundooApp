package com.api.notes.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long noteId;
	
	private String title;
	
	private String description;
	
	private boolean archieve;
	
	private boolean trash;
	
	private boolean pin;
	
	private String image;
	
	private String color;
	
	private LocalDateTime reminder;

	private LocalDateTime createdate;

	private LocalDateTime updateddate;

	private String userid;
	
}
