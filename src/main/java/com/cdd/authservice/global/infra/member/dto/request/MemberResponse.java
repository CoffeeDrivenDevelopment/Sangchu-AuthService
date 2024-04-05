package com.cdd.authservice.global.infra.member.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record MemberResponse(
	@JsonProperty("oauth_id")
	String oauthId,
	@JsonProperty("nickname")
	String nickname
) {
}
