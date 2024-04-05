package com.cdd.authservice.global.infra.oauth.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OAuthInfo {
	@JsonProperty("resultcode")
	private String resultCode;
	@JsonProperty("message")
	private String message;
	@JsonProperty("response")
	private UserInfo userInfo;

	public String getOAuthId() {
		return userInfo.oauthId;
	}

	@Setter
	@Getter
	@NoArgsConstructor
	static class UserInfo {
		@JsonProperty("id")
		private String oauthId;
		@JsonProperty("nickname")
		private String nickname;
		@JsonProperty("profile_image")
		private String profileImage;
		@JsonProperty("age")
		private String age;
		@JsonProperty("gender")
		private String gender;
		@JsonProperty("email")
		private String email;
		@JsonProperty("mobile")
		private String mobile;
		@JsonProperty("mobile_e164")
		private String mobileE164;
		@JsonProperty("name")
		private String name;
		@JsonProperty("birthday")
		private String birthday;
		@JsonProperty("birthyear")
		private String birthyear;
	}
}