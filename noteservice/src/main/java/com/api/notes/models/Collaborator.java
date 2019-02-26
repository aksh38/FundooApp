package com.api.notes.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

public class Collaborator {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long collaboratorId;
	
	private Long userId;
	
	private Long noteId;
}
