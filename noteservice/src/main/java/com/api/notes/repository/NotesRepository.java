package com.api.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.notes.models.Note;


public interface NotesRepository extends JpaRepository<Note, Long> {
	
	@Query(value="select * from notes where note_id in :note_ids", nativeQuery=true)
	List<Note> findNoteByNoteIdIn(@Param("note_ids")List<Long> noteIds);

}
