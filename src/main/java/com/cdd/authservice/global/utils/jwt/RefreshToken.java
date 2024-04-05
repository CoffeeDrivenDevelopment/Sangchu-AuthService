package com.cdd.authservice.global.utils.jwt;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@RedisHash(value = "JwtToken", timeToLive = 30 * 24 * 60 * 60 * 1000L)
public class RefreshToken {
	@Id
	private Long id;
	private Long memberId;
	@Indexed
	private String refreshToken;
}
