package com.example.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.EdECPublicKey;
import java.util.Set;

@ConfigurationProperties("jwt")
public final class JwtProperties {
	static final Set<String> REGISTERED_NAMES = Set.of("iss", "sub", "aud", "exp", "nbf", "iat", "jti");

	private final EdECPublicKey publicKey;
	private final EdECPrivateKey privateKey;
	private final long expiry;

	@ConstructorBinding
	public JwtProperties(Path publicKey, Path privateKey, @DefaultValue("3600") long expiry) throws IOException {
		var pub = Files.readString(publicKey);
		var priv = Files.readString(privateKey);
		this.publicKey = EdDSAKeyReader.publicKey(pub);
		this.privateKey = EdDSAKeyReader.privateKey(priv);
		this.expiry = expiry;
	}

	public EdECPublicKey publicKey() {
		return this.publicKey;
	}

	public EdECPrivateKey privateKey() {
		return this.privateKey;
	}

	public long expiry() {
		return this.expiry;
	}
}
