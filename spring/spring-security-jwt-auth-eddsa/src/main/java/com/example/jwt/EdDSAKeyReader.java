package com.example.jwt;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.EdECPrivateKey;
import java.security.interfaces.EdECPublicKey;
import java.security.spec.InvalidKeySpecException;

public class EdDSAKeyReader {

	static final PEMDecoder PEM_DECODER;

	static {
		try {
			PEM_DECODER = PEMDecoder.withFactory(KeyFactory.getInstance("Ed25519"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static EdECPublicKey publicKey(String pem) {
		try {
			return PEM_DECODER.decode(pem, EdECPublicKey.class);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	public static EdECPrivateKey privateKey(String pem) {
		try {
			return PEM_DECODER.decode(pem, EdECPrivateKey.class);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
}
