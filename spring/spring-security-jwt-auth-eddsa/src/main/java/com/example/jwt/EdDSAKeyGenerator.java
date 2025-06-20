package com.example.jwt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.Base64;

public class EdDSAKeyGenerator {

	public static final KeyPairGenerator KEY_PAIR_GENERATOR;

	static {
		try {
			KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance("Ed25519");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws IOException {
		var keyPair = KEY_PAIR_GENERATOR.generateKeyPair();
		writeKeyPair(keyPair, Paths.get("src/main/resources"));
	}

	static void writeKeyPair(KeyPair keyPair, Path parent) throws IOException {
		var publicKeyPath = Paths.get(parent.toString(), "public.pem");
		Files.writeString(publicKeyPath, toPem("PUBLIC KEY", keyPair.getPublic().getEncoded()));
		var privateKeyPath = Paths.get(parent.toString(), "private.pem");
		Files.writeString(privateKeyPath, toPem("PRIVATE KEY", keyPair.getPrivate().getEncoded()));
	}

	static String toPem(String marker, byte[] encoded) {
		String base64 = Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(encoded);
		return "-----BEGIN " + marker + "-----\n" + base64 + "\n-----END " + marker + "-----";
	}
}
