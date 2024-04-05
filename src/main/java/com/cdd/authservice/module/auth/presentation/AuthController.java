package com.cdd.authservice.module.auth.presentation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.authservice.global.utils.header.HeaderUtils;
import com.cdd.authservice.module.auth.application.AuthService;
import com.cdd.authservice.module.auth.dto.request.RefreshRequest;
import com.cdd.authservice.module.auth.dto.request.SigninRequest;
import com.cdd.authservice.module.auth.dto.request.SignoutRequest;
import com.cdd.authservice.module.auth.dto.response.RefreshResponse;
import com.cdd.authservice.module.auth.dto.response.SignInResponse;
import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*")
@RequestMapping("/auth-service")
@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController {
	private final AuthService authService;

	@PostMapping("/v1/signin")
	public ResponseEntity<MessageBody<SignInResponse>> signIn(
		@RequestBody SigninRequest request
	) {
		return ResponseEntityFactory.ok("로그인 성공", authService.signIn(request));
	}

	@DeleteMapping("/v1/signout")
	public ResponseEntity<MessageBody<Void>> signOut(
		@RequestBody SignoutRequest request
	) {
		authService.signOut(request.refreshToken());
		return ResponseEntityFactory.ok("로그아웃 성공");
	}

	@PostMapping("/v1/refresh")
	public ResponseEntity<MessageBody<RefreshResponse>> refresh(
		@RequestHeader HttpHeaders headers,
		@RequestBody RefreshRequest request
	) {
		return ResponseEntityFactory.ok("토큰 갱신 성공",
			RefreshResponse.from(authService.refresh(request,
				HeaderUtils.extractAuthorization(headers))));
	}

	@GetMapping("/v1/validate")
	public ResponseEntity<MessageBody<Void>> test(
		@RequestHeader HttpHeaders headers
	) {
		authService.validate(HeaderUtils.extractAuthorization(headers));
		return ResponseEntityFactory.ok("토큰 인증 성공");
	}
}
