package com.example.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.security.PublicKey;
import java.util.*;

public record CustomJwtDecoder(JwtParser parser) implements JwtDecoder {

	public CustomJwtDecoder(PublicKey publicKey) {
		this(Jwts.parser().verifyWith(publicKey).build());
	}

	@Override
	public Jwt decode(String token) throws JwtException {
		try {
			var jws = parser.parseSignedClaims(token);
			return convert(token, jws);
		} catch (Exception e) {
			throw new BadJwtException("Failed to decode token", e);
		}
	}

	private static Jwt convert(String token, Jws<Claims> jws) {
		var builder = Jwt.withTokenValue(token)
				.headers((h) -> h.putAll(jws.getHeader()));

		var claims = jws.getPayload();

		String issuer = claims.getIssuer();
		if (StringUtils.hasText(issuer)) {
			builder.issuer(issuer);
		}

		String subject = claims.getSubject();
		if (StringUtils.hasText(subject)) {
			builder.subject(subject);
		}

		Set<String> audience = claims.getAudience();
		if (!CollectionUtils.isEmpty(audience)) {
			builder.audience(audience);
		}

		Date expiresAt = claims.getExpiration();
		if (expiresAt != null) {
			builder.expiresAt(expiresAt.toInstant());
		}

		Date notBefore = claims.getNotBefore();
		if (notBefore != null) {
			builder.notBefore(notBefore.toInstant());
		}

		Date issuedAt = claims.getIssuedAt();
		if (issuedAt != null) {
			builder.issuedAt(issuedAt.toInstant());
		}

		String jwtId = claims.getId();
		if (StringUtils.hasText(jwtId)) {
			builder.jti(jwtId);
		}

		Map<String, Object> customClaims = new HashMap<>();
		claims.forEach((name, value) -> {
			if (!JwtProperties.REGISTERED_NAMES.contains(name)) {
				customClaims.put(name, value);
			}
		});

		if (!customClaims.isEmpty()) {
			Objects.requireNonNull(builder);
			customClaims.forEach(builder::claim);
		}

		return builder.build();
	}
}
