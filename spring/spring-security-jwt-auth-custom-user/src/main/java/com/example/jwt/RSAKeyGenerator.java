package com.example.jwt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class RSAKeyGenerator {

	public static final KeyPairGenerator KEY_PAIR_GENERATOR;

	static {
		try {
			KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance("RSA");
			KEY_PAIR_GENERATOR.initialize(2048);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws IOException {
		var keyPair = generateKeyPair();
		writeKeyPair(keyPair, Paths.get("src/main/resources"));
	}

	static void writeKeyPair(KeyPair keyPair, Path parent) throws IOException {
		var publicKeyPath = Paths.get(parent.toString(), "public.pem");
		Files.writeString(publicKeyPath, toPem("PUBLIC KEY", keyPair.getPublic().getEncoded()));
		var privateKeyPath = Paths.get(parent.toString(), "private.pem");
		Files.writeString(privateKeyPath, toPem("PRIVATE KEY", keyPair.getPrivate().getEncoded()));
	}

	static KeyPair generateKeyPair() {
		KeyPair kp = KEY_PAIR_GENERATOR.generateKeyPair();
		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();
		return new KeyPair(publicKey, privateKey);
	}

	static String toPem(String marker, byte[] encoded) {
		String base64 = Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(encoded);
		return "-----BEGIN " + marker + "-----\n" + base64 + "\n-----END " + marker + "-----";
	}
}
