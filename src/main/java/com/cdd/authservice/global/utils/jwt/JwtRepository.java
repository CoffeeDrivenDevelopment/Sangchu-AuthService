package com.cdd.authservice.global.utils.jwt;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface JwtRepository extends CrudRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
