package com.cdd.authservice.module.auth.exception;

import com.cdd.common.exception.ErrorCode;

import lombok.Getter;

@Getter
public enum AuthErrorCode implements ErrorCode {
	BAD_REQUEST(400, "AUTH_001", "잘못된 요청입니다."),
	UNAUTHORIZED(401, "AUTH_002", "허가되지 않은 요청입니다."),
	NO_SUCH_ELEMENT(404, "AUTH_003", "요청한 정보가 존재하지 않습니다."),
	INTERNAL_SERVER_ERROR(500, "AUTH_004", "내부 서버 오류입니다.");

	private final int statusCode;
	private final String errorCode;
	private final String message;

	private AuthErrorCode(int statusCode, String errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}
}
