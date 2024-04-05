package com.cdd.authservice.global.utils.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cdd.authservice.module.auth.exception.AuthErrorCode;
import com.cdd.authservice.module.auth.exception.AuthException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	private final long ACCESS_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;
	private final long REFRESH_TOKEN_EXPIRE_TIME = 30 * 24 * 60 * 60 * 1000L;

	private final Key key;

	public JwtProvider(@Value("${jwt.secret-key}") String secret_key) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret_key));
	}

	public String createAccessToken(Long memberId, Date now) {
		Date expiredTime = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
		return Jwts.builder()
			.setExpiration(expiredTime)
			.claim("member_id", memberId)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String createRefreshToken(Long memberId, Date now) {
		Date expiredTime = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME);
		return Jwts.builder()
			.setExpiration(expiredTime)
			.claim("member_id", memberId)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public JwtToken generateToken(Long memberId) {
		Date now = new Date();
		String accessToken = createAccessToken(memberId, now);
		String refreshToken = createRefreshToken(memberId, now);
		return JwtToken.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new AuthException(AuthErrorCode.UNAUTHORIZED);
		} catch (JwtException e) {
			throw new AuthException(AuthErrorCode.BAD_REQUEST);
		}
	}

	private Claims parseClaims(String accessToken) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(accessToken)
			.getBody();
	}

	public Long parseMemberId(String accessToken) {
		return parseClaims(accessToken).get("member_id", Long.class);
	}
}
