package com.cdd.authservice.global.utils.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record JwtToken(
	@JsonProperty("access_token")
	String accessToken,
	@JsonProperty("refresh_token")
	String refreshToken
) {
}
