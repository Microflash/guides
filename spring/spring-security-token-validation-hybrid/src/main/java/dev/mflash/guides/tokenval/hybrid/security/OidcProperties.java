package dev.mflash.guides.tokenval.hybrid.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("auth")
@ConstructorBinding
public class OidcProperties {

  private final String audience;

  public OidcProperties(String audience) {
    this.audience = audience;
  }

  public String getAudience() {
    return audience;
  }
}
