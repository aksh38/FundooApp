package com.api.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.notes.models.Label;

public interface LabelRepository extends JpaRepository<Label, Long>{
	 
	

}
