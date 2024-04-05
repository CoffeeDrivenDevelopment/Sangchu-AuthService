package com.cdd.authservice.global.infra.oauth.application;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.cdd.authservice.global.infra.oauth.vo.OAuthInfo;
import com.cdd.authservice.global.infra.oauth.vo.OAuthToken;
import com.cdd.authservice.module.auth.exception.AuthErrorCode;
import com.cdd.authservice.module.auth.exception.AuthException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuthClient {
	@Value("${oauth.naver.client-id}")
	private String NAVER_CLIENTID;
	@Value("${oauth.naver.secret}")
	private String NAVER_SECRET;
	@Value("${oauth.naver.state}")
	private String NAVER_STATE;
	@Value("${oauth.naver.url.api}")
	private String NAVER_APIURL;
	@Value("${oauth.naver.url.auth}")
	private String NAVER_AUTHURL;

	public OAuthToken getOAuthToken(String code) {
		return Optional.ofNullable(WebClient.create(NAVER_AUTHURL)
			.get()
			.uri(uriBuilder -> uriBuilder
				.queryParam("grant_type", "authorization_code")
				.queryParam("client_id", NAVER_CLIENTID)
				.queryParam("client_secret", NAVER_SECRET)
				.queryParam("code", code)
				.queryParam("state", NAVER_STATE)
				.build())
			.headers(header -> {
				header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
			})
			.retrieve()
			.bodyToMono(OAuthToken.class)
			.block()).orElseThrow(() -> new AuthException(AuthErrorCode.UNAUTHORIZED));
	}

	public String getOAuthId(OAuthToken oauthToken) {
		log.info("GetOAuthId Start!");
		OAuthInfo request = Optional.ofNullable(WebClient.create(NAVER_APIURL)
			.get()
			.headers(header -> {
				header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
				header.setBearerAuth(oauthToken.getAccessToken());
			})
			.retrieve()
			.bodyToMono(OAuthInfo.class)
			.block()).orElseThrow(() -> new AuthException(AuthErrorCode.UNAUTHORIZED));
		log.info("GetOAuthId End!");
		return request.getOAuthId();
	}
}
