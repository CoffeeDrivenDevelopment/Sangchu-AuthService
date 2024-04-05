package com.cdd.authservice.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "client")
public class ClientProperties {
	private String signInUrl;
}
