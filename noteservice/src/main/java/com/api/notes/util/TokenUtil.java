package com.api.notes.util;

import org.springframework.beans.factory.annotation.Value;

import com.api.notes.exception.NoteException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author admin1
 *
 *Utility class for JWT token Generation and Validation
 *
 */
@UtilityClass
@Slf4j
public class TokenUtil {
	
	private String TOKEN_SECRET = "abdt35dsfjds6";

	/**
	 * @param userId
	 * @return 
	 */
	public String generateToken(String userId) {
		log.info("Get JWT token with userId: {}", userId);
		String token = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			token = JWT.create().withClaim("ID", userId).sign(algorithm);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage(), e);
		}
		return token;
	}

	/**
	 * @param token
	 * @return
	 */
	public Long verifyToken(String token) {
		long userId;
		log.info("Verify JWT token ");
		try {
			
			Verification verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
			JWTVerifier jwtVerifier = verification.build();
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
			Claim claim = decodedJWT.getClaim("ID");
			userId = claim.asLong();
			return userId;

		} catch (IllegalArgumentException exception) {
			log.error(exception.getMessage());
			throw new NoteException(400, exception.getMessage());
		}

	}

}
