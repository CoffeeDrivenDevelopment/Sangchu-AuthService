package com.cdd.authservice.module.auth.dto.response;

import com.cdd.authservice.global.utils.jwt.JwtToken;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record RefreshResponse(
	@JsonProperty("access_token")
	String accessToken,
	@JsonProperty("refresh_token")
	String refreshToken
) {
	public static RefreshResponse from(JwtToken token) {
		return RefreshResponse.builder()
			.accessToken(token.accessToken())
			.refreshToken(token.refreshToken())
			.build();
	}
}
