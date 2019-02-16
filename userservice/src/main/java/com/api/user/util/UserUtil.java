package com.api.user.util;

import com.api.user.exception.UserException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserUtil {

	public String getUrl(String service, Long id) throws UserException {
		
		return "http://localhost:4200/" + service 
				+ "/" + TokenUtil.generateToken(id);

	}

}
