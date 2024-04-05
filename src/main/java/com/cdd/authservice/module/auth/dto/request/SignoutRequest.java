package com.cdd.authservice.module.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SignoutRequest(
	@JsonProperty("refresh_token")
	String refreshToken
) {
}
