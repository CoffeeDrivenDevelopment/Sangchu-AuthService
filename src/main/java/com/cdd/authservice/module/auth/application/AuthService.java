package com.cdd.authservice.module.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdd.authservice.global.infra.member.application.MemberClient;
import com.cdd.authservice.global.infra.member.dto.response.MemberRequest;
import com.cdd.authservice.global.infra.oauth.application.OAuthClient;
import com.cdd.authservice.global.utils.jwt.JwtProvider;
import com.cdd.authservice.global.utils.jwt.JwtRepository;
import com.cdd.authservice.global.utils.jwt.JwtToken;
import com.cdd.authservice.global.utils.jwt.RefreshToken;
import com.cdd.authservice.module.auth.dto.request.RefreshRequest;
import com.cdd.authservice.module.auth.dto.request.SigninRequest;
import com.cdd.authservice.module.auth.dto.response.SignInResponse;
import com.cdd.authservice.module.auth.exception.AuthErrorCode;
import com.cdd.authservice.module.auth.exception.AuthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
	private final JwtProvider jwtProvider;
	private final OAuthClient oauthClient;
	private final MemberClient memberClient;
	private final JwtRepository jwtRepository;

	@Transactional
	public SignInResponse signIn(SigninRequest request) {
		log.info("SignIn Start!");
		String oauthId = oauthClient.getOAuthId(oauthClient.getOAuthToken(request.code()));
		MemberRequest member = memberClient.getMemberByOAuthId(oauthId);
		JwtToken token = jwtProvider.generateToken(member.memberId());
		jwtRepository.save(RefreshToken.builder()
			.memberId(member.memberId())
			.refreshToken(token.refreshToken())
			.build());
		log.info("SignIn End!");
		return SignInResponse.of(member, token);
	}

	@Transactional
	public void signOut(String refreshToken) {
		jwtRepository.delete(jwtRepository.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new AuthException(AuthErrorCode.NO_SUCH_ELEMENT)));
	}

	@Transactional
	public JwtToken refresh(RefreshRequest request, String refreshToken) {
		jwtRepository.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new AuthException(AuthErrorCode.NO_SUCH_ELEMENT));
		jwtProvider.validateToken(refreshToken);
		jwtRepository.delete(jwtRepository.findByRefreshToken(refreshToken)
			.orElseThrow(() -> new AuthException(AuthErrorCode.NO_SUCH_ELEMENT)));
		JwtToken token = jwtProvider.generateToken(request.memberId());
		jwtRepository.save(RefreshToken.builder()
			.memberId(request.memberId())
			.refreshToken(token.refreshToken())
			.build()
		);
		return token;
	}

	public Long validate(String accessToken) {
		jwtProvider.validateToken(accessToken);
		return jwtProvider.parseMemberId(accessToken);
	}
}
