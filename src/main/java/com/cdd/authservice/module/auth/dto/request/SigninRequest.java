package com.cdd.authservice.module.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SigninRequest(
	@JsonProperty("code")
	String code
) {
}
