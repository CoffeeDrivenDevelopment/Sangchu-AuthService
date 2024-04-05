package com.cdd.authservice.module.passport.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record PassportRequest(
	@JsonProperty("passport_id")
	String id,

	@JsonProperty("member_id")
	long memberId,

	@JsonProperty("expired_time")
	long expiredTime
) {
}
