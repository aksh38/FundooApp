package com.api.notes.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.notes.models.Collaborator;


@Repository
public interface CollabaratorRepository extends JpaRepository<Collaborator, Long> {

	@Query(value="SELECT user_id FROM collaborator WHERE note_id= :note_id", nativeQuery=true)
	Optional<List<BigInteger>> findUserIdByNoteId(@Param("note_id") Long noteId);
	
	
	@Query(value="SELECT collaborator_id FROM collaborator WHERE user_id=:user_id and note_id=:note_id", nativeQuery=true)
	Optional<Long> findBy(@Param("note_id")Long noteId, @Param("user_id")Long userId);
	
	@Query(value="SELECT note_id FROM collaborator WHERE user_id= :user_id", nativeQuery=true)
	Optional<List<Long>> findNoteIdByUserId(@Param("user_id")Long userId);
}
