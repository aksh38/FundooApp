package com.api.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.notes.models.Note;


public interface NotesRepository extends JpaRepository<Note, Long> {

	
	
}
