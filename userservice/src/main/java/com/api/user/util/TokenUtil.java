package com.api.user.util;

import com.api.user.exception.UserException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TokenUtil {

	private String TOKEN_SECRET = "abdt35dsfjds6";

	public String generateToken(String username) throws UserException {

		Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

		String token = JWT.create().withClaim("ID", username).sign(algorithm);

		return token;

	}

	public String verifyToken(String token) {
		
		String username;

		Verification verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));

		JWTVerifier jwtVerifier = verification.build();

		DecodedJWT decodedJWT = jwtVerifier.verify(token);

		Claim claim = decodedJWT.getClaim("ID");

		username = claim.asString();

		return username;

	}

}
