package com.api.notes.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name="notes")
public class Note implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long noteId;
	
	private Long userId;

	private String title;
	
	private String description;
	
	private boolean archieve;
	
	private boolean trash;
	
	private boolean pin;
	
	private String imageUrl;
	
	@ManyToMany(cascade=CascadeType.ALL)
	public Set<Label> labels;
	 
	private String color;
	
	private LocalDateTime reminder;

	private LocalDateTime createDate = LocalDateTime.now();

	private LocalDateTime updatedDate = LocalDateTime.now();
	
}
