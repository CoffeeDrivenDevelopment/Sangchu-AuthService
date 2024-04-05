package com.cdd.authservice.module.passport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PassportIssueRequest(
	@JsonProperty("token")
	String accessToken
) {
}
