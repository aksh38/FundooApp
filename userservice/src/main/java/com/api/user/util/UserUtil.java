package com.api.user.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;

public class UserUtil {

	private static String TOKEN_SECRET = "abdt35dsfjds6";

	public static String generateToken(String id) {
		try {

			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);

			String token = JWT.create().withClaim("ID", id).sign(algorithm);

			return token;
		
		} catch (Exception e) {
		
			e.printStackTrace();
		
		}
		return null;
	}

	
	public static String verifyToken(String token)
	{
		String username;
		
		try {

			Verification verification= JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
			
			JWTVerifier jwtVerifier= verification.build();
			
			
			DecodedJWT decodedJWT=jwtVerifier.verify(token);
			
			Claim claim=decodedJWT.getClaim("ID");
			
			username= claim.asString();
					
			return username;
		}catch (Exception e) {
		
			e.printStackTrace();
			return null;
		}
		
	}
	
}
