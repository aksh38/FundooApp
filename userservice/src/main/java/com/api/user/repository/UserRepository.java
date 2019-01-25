package com.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.user.models.User;

public interface UserRepository extends JpaRepository<User, String>{

	
}
