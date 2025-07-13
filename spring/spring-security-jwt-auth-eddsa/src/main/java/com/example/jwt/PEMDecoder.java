package com.example.jwt;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class PEMDecoder {

	private final KeyFactory keyFactory;

	private PEMDecoder(KeyFactory keyFactory) {
		this.keyFactory = keyFactory;
	}

	public static PEMDecoder withFactory(KeyFactory keyFactory) {
		return new PEMDecoder(keyFactory);
	}

	public <K extends Key> K decode(String str, Class<K> keyClazz) throws InvalidKeySpecException {
		var isPublicKey = PublicKey.class.isAssignableFrom(keyClazz);
		var marker = isPublicKey ? "PUBLIC KEY" : "PRIVATE KEY";
		String base64 = str
				.replaceAll("-----BEGIN " + marker + "-----", "")
				.replaceAll("-----END " + marker + "-----", "")
				.replaceAll("\\s", "");
		var key = Base64.getDecoder().decode(base64);
		return (K) (
				isPublicKey ?
					keyFactory.generatePublic(new X509EncodedKeySpec(key)) :
					keyFactory.generatePrivate(new PKCS8EncodedKeySpec(key))
		);
	}
}
