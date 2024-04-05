package com.cdd.authservice.module.passport.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cdd.authservice.global.utils.jwt.JwtProvider;
import com.cdd.authservice.module.passport.dto.PassportIssueRequest;
import com.cdd.authservice.module.passport.dto.PassportRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PassportService {
	private final JwtProvider jwtProvider;

	public PassportRequest issue(PassportIssueRequest request) {
		jwtProvider.validateToken(request.accessToken());

		Long memberId = jwtProvider.parseMemberId(request.accessToken());
		long now = System.currentTimeMillis();

		return PassportRequest.builder()
			.id(UUID.randomUUID().toString())
			.memberId(memberId)
			.expiredTime(now + 5_000L)
			.build();
	}
}
