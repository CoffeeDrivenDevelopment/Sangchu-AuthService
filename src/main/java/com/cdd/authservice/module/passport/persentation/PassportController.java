package com.cdd.authservice.module.passport.persentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.authservice.module.passport.application.PassportService;
import com.cdd.authservice.module.passport.dto.PassportIssueRequest;
import com.cdd.authservice.module.passport.dto.PassportRequest;

import lombok.RequiredArgsConstructor;

@RequestMapping("/auth-service")
@RequiredArgsConstructor
@RestController
public class PassportController {
	private final PassportService passportService;

	@PostMapping("/v1/passport")
	public ResponseEntity<PassportRequest> issue(@RequestBody PassportIssueRequest request) {
		PassportRequest passport = passportService.issue(request);
		return ResponseEntity.ok(passport);
	}
}
