package dev.mflash.guides.tokenval.introspection.security;

import java.util.Map;
import java.util.StringJoiner;

public final class WWWAuthenticateHeaderBuilder {

  public static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
    StringJoiner wwwAuthenticate = new StringJoiner(", ", "Bearer ", "");

    if (!parameters.isEmpty()) {
      parameters.forEach((k, v) -> wwwAuthenticate.add(k + "=\"" + v + "\""));
    }

    return wwwAuthenticate.toString();
  }
}
