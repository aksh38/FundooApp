package com.api.user.repository;

import java.math.BigInteger;
import java.util.List;
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
	
	Optional<User> findByEmailId(String emailId);
	
	Optional<List<User>> findByIdIn(List<BigInteger> userIds);

}


