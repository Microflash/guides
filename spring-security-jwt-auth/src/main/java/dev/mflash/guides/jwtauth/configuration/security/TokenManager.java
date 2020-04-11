package dev.mflash.guides.jwtauth.configuration.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.time.ZonedDateTime;
import java.util.Date;

public class TokenManager {

  private static final String SECRET = "U3ByaW5nQm9vdFN1cGVyU2VjcmV0";
  private static final long EXPIRATION_TIME = 10; // 10 days
  static final String TOKEN_PREFIX = "Bearer ";
  static final String HEADER_STRING = "Authorization";
  public static final String SIGN_UP_URL = "/users/register";

  static String generateToken(String subject) {
    return Jwts.builder()
        .setSubject(subject)
        .setExpiration(Date.from(ZonedDateTime.now().plusDays(EXPIRATION_TIME).toInstant()))
        .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
        .compact();
  }

  static String parseToken(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET.getBytes())
        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
        .getBody()
        .getSubject();
  }
}
