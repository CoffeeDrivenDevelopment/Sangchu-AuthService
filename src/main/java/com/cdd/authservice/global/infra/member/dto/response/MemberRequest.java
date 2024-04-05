package com.cdd.authservice.global.infra.member.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberRequest(
	@JsonProperty("member_id")
	Long memberId,
	@JsonProperty("nickname")
	String nickname,
	@JsonProperty("profile_image")
	String profileImage
) {
}
