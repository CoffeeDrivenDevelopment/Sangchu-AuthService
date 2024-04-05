package com.cdd.authservice.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdd.common.response.MessageBody;
import com.cdd.common.response.ResponseEntityFactory;

@RestController
public class HealthController {
	@GetMapping("/v1/health-check")
	public ResponseEntity<MessageBody<Void>> healthCheck() {
		return ResponseEntityFactory.ok("UP!");
	}
}
