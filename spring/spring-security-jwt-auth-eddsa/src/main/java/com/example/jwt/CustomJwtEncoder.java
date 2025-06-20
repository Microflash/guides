package com.example.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkSet;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.security.PublicKey;
import java.time.Instant;
import java.util.*;

public record CustomJwtEncoder(JwkSet jwkSet, JwsAlgorithm defaultAlgorithm) implements JwtEncoder {

	public CustomJwtEncoder(JwkSet jwkSet) {
		this(jwkSet, CustomJwsAlgorithm.EdDSA);
	}

	enum CustomJwsAlgorithm implements JwsAlgorithm {
		RSA("RSA"),
		EdDSA("EdDSA");

		private final String name;

		CustomJwsAlgorithm(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}

	@Override
	public Jwt encode(JwtEncoderParameters parameters) throws JwtEncodingException {
		try {
			JwsHeader headers = Objects.requireNonNullElse(parameters.getJwsHeader(), JwsHeader.with(defaultAlgorithm).build());
			JwtClaimsSet claims = parameters.getClaims();
			Key signingKey = this.jwkSet.getKeys().stream()
					.filter(jwk -> jwk.getAlgorithm().equals(headers.getAlgorithm().getName()))
					.findAny()
					.map(Jwk::toKey)
					.orElseThrow();
			var jws = convert(claims, signingKey);
			return new Jwt(jws, claims.getIssuedAt(), claims.getExpiresAt(), headers.getHeaders(), claims.getClaims());
		} catch (Exception e) {
			throw new JwtEncodingException("Failed to encode JWT", e);
		}
	}

	private static String convert(JwtClaimsSet claims, Key signingKey) {
		if (signingKey instanceof PublicKey) {
			throw new IllegalArgumentException("Signing token with public key is not supported");
		}

		JwtBuilder builder = Jwts.builder();
		Object issuer = claims.getClaim("iss");
		if (issuer != null) {
			builder.issuer(issuer.toString());
		}

		String subject = claims.getSubject();
		if (StringUtils.hasText(subject)) {
			builder.subject(subject);
		}

		List<String> audience = claims.getAudience();
		if (!CollectionUtils.isEmpty(audience)) {
			builder.audience().add(audience);
		}

		Instant expiresAt = claims.getExpiresAt();
		if (expiresAt != null) {
			builder.expiration(Date.from(expiresAt));
		}

		Instant notBefore = claims.getNotBefore();
		if (notBefore != null) {
			builder.notBefore(Date.from(notBefore));
		}

		Instant issuedAt = claims.getIssuedAt();
		if (issuedAt != null) {
			builder.issuedAt(Date.from(issuedAt));
		}

		String jwtId = claims.getId();
		if (StringUtils.hasText(jwtId)) {
			builder.id(jwtId);
		}

		Map<String, Object> customClaims = new HashMap<>();
		claims.getClaims().forEach((name, value) -> {
			if (!JwtProperties.REGISTERED_NAMES.contains(name)) {
				customClaims.put(name, value);
			}
		});

		if (!customClaims.isEmpty()) {
			Objects.requireNonNull(builder);
			customClaims.forEach(builder::claim);
		}

		return builder.signWith(signingKey).compact();
	}
}
