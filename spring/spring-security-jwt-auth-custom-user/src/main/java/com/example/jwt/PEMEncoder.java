package com.example.jwt;

import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;

public final class PEMEncoder {

	private PEMEncoder() {}

	public static PEMEncoder of() {
		return new PEMEncoder();
	}

	public String encodeToString(Key key) {
		var marker = key instanceof PublicKey ? "PUBLIC KEY" : "PRIVATE KEY";
		String base64 = Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(key.getEncoded());
		return "-----BEGIN " + marker + "-----\n" + base64 + "\n-----END " + marker + "-----";
	}
}
