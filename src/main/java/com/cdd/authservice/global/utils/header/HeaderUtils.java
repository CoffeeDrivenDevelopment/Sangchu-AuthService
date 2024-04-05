package com.cdd.authservice.global.utils.header;

import org.springframework.http.HttpHeaders;

import com.cdd.common.exception.CallConstructorException;

public class HeaderUtils {

	private HeaderUtils() {
		throw new CallConstructorException();
	}

	public static String extractAuthorization(HttpHeaders headers) {
		String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
		return authorizationHeader != null && authorizationHeader.startsWith("Bearer ")
			? authorizationHeader.substring(7)
			: null;
	}
}
