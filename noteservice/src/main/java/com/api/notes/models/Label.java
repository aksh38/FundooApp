package com.api.notes.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

@Table(name="labels")
public class Label {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long labelId;
	
	private Long userId;
	
	@ManyToMany(mappedBy="labels",cascade=CascadeType.ALL)
	@JsonIgnore
	private Set<Note> notes;
	
	private String labelValue;
	
}
