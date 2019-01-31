package com.api.user.util;

import com.api.user.exception.UserException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtil {

	public String getUrl(String service, String username) throws UserException {
		
		return "http://localhost:8080/api/user/" + service 
				+ "/" + TokenUtil.generateToken(username);

	}

}
