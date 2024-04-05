package com.cdd.authservice.module.auth.exception;

import com.cdd.common.exception.ErrorCode;
import com.cdd.common.exception.SangChuException;

public class AuthException extends SangChuException {
	public AuthException(ErrorCode errorCode) {
		super(errorCode);
	}
}
