package com.api.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.user.models.User;

/**
 * @author admin1
 *
 */
public interface UserRepository extends JpaRepository<User, Long>{

	/**
	 * @param username
	 * @return Optional<User> 
	 */
	Optional<User> findByUserName(String username);

	
}
