package com.cdd.authservice.global.infra.member.application;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cdd.authservice.global.infra.member.dto.request.MemberResponse;
import com.cdd.authservice.global.infra.member.dto.response.MemberRequest;
import com.cdd.authservice.global.properties.ClientProperties;
import com.cdd.authservice.module.auth.exception.AuthErrorCode;
import com.cdd.authservice.module.auth.exception.AuthException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MemberClient {

	private final String signInUrl;

	@Autowired
	public MemberClient(ClientProperties clientProperties) {
		signInUrl = clientProperties.getSignInUrl();
	}

	public MemberRequest getMemberByOAuthId(String oauthId) {
		log.info("Fetch Member Info");
		return Optional.ofNullable(WebClient.create(signInUrl)
				.post()
				.bodyValue(MemberResponse.builder().oauthId(oauthId).build())
				.retrieve()
				.bodyToMono(MemberRequest.class)
				.block())
			.orElseThrow(() -> new AuthException(AuthErrorCode.NO_SUCH_ELEMENT));
	}
}
