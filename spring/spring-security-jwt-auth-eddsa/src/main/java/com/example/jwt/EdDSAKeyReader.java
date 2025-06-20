package com.example.jwt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.EdECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EdDSAKeyReader {

	static final KeyFactory KEY_FACTORY;

	static {
		try {
			KEY_FACTORY = KeyFactory.getInstance("Ed25519");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static EdECPublicKey publicKey(String pem) {
		var spec = new X509EncodedKeySpec(fromPem("PUBLIC KEY", pem));
		try {
			return (EdECPublicKey) KEY_FACTORY.generatePublic(spec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	public static EdECPrivateKey privateKey(String pem) {
		var spec = new PKCS8EncodedKeySpec(fromPem("PRIVATE KEY", pem));
		try {
			return (EdECPrivateKey) KEY_FACTORY.generatePrivate(spec);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	static byte[] fromPem(String marker, String pem) {
		String base64 = pem
				.replaceAll("-----BEGIN " + marker + "-----", "")
				.replaceAll("-----END " + marker + "-----", "")
				.replaceAll("\\s", "");
		return Base64.getDecoder().decode(base64);
	}
}
