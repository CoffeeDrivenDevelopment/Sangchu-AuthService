package com.cdd.authservice.module.auth.dto.response;

import com.cdd.authservice.global.infra.member.dto.response.MemberRequest;
import com.cdd.authservice.global.utils.jwt.JwtToken;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record SignInResponse(
	@JsonProperty("member_id")
	Long memberId,
	@JsonProperty("nickname")
	String nickname,
	@JsonProperty("profile_image")
	String profileImage,
	@JsonProperty("jwt")
	JwtToken jwtToken
) {
	public static SignInResponse of(MemberRequest member, JwtToken token) {
		return SignInResponse.builder()
			.memberId(member.memberId())
			.nickname(member.nickname())
			.profileImage(member.profileImage())
			.jwtToken(token)
			.build();
	}
}
