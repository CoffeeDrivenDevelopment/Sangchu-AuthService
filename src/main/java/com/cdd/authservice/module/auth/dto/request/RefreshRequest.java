package com.cdd.authservice.module.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshRequest(
	@JsonProperty("member_id")
	Long memberId
) {
}
