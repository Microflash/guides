package dev.mflash.guides.tokenval.introspection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(GenericController.CONTEXT)
public class GenericController {

  public static final String CONTEXT = "/spring-security-oidc";
  public static final String PUBLIC_ENDPOINT = "/public";
  public static final String PRIVATE_ENDPOINT = "/private";
  public static final String PRIVATE_SCOPED_ENDPOINT = "/private-scoped";

  private static final String MSG_TEMPLATE = "Hello, world! This is a %s endpoint";

  @GetMapping(PUBLIC_ENDPOINT)
  public Map<String, String> publicEndpoint() {
    return response("public");
  }

  @GetMapping(PRIVATE_ENDPOINT)
  public Map<String, String> privateEndpoint() {
    return response("private");
  }

  @GetMapping(PRIVATE_SCOPED_ENDPOINT)
  public Map<String, String> privateScopedEndpoint() {
    return response("private scoped");
  }

  private Map<String, String> response(String type) {
    return Map.of("message", String.format(MSG_TEMPLATE, type));
  }
}
