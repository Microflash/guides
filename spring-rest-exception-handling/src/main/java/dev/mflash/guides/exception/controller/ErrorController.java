package dev.mflash.guides.exception.controller;

import dev.mflash.guides.exception.service.ErrorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/exception")
public @RestController class ErrorController {

  private final ErrorService service;

  public ErrorController(ErrorService service) {
    this.service = service;
  }

  @GetMapping
  public Map<?, ?> get() {
    return service.get();
  }

  @PostMapping
  public Map<?, ?> post() {
    return service.post();
  }
}
