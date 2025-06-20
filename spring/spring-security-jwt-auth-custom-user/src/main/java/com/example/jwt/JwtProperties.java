package com.example.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties("jwt")
public record JwtProperties(
		RSAPublicKey publicKey,
		RSAPrivateKey privateKey,
		@DefaultValue("3600") long expiry) {
}
