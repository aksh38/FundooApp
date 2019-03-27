package com.api.notes.services;

import java.util.List;

import com.api.notes.models.Note;


public interface ElasticService {

	void save(Note object);
	void update(Note note);
	void delete(String noteId);
	List<Note> search(String search, String field);
 }
