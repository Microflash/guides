package dev.mflash.guides.tokenval.hybrid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(GenericController.CONTEXT)
public class GenericController {

  public static final String CONTEXT = "/spring-security-oidc";
  public static final String PUBLIC_ENDPOINT = "/public";
  public static final String PRIVATE_ENDPOINT_LOCAL = "/private-local";
  public static final String PRIVATE_SCOPED_ENDPOINT_REMOTE = "/private-scoped-remote";

  private static final String MSG_TEMPLATE = "Hello, world! This is a %s endpoint";

  @GetMapping(PUBLIC_ENDPOINT)
  public Map<String, String> publicEndpoint() {
    return response("public");
  }

  @GetMapping(PRIVATE_ENDPOINT_LOCAL)
  public Map<String, String> privateEndpoint() {
    return response("private locally-validated");
  }

  @GetMapping(PRIVATE_SCOPED_ENDPOINT_REMOTE)
  public Map<String, String> privateScopedEndpoint() {
    return response("private scoped remotely-validated");
  }

  private Map<String, String> response(String type) {
    return Map.of("message", String.format(MSG_TEMPLATE, type));
  }
}
