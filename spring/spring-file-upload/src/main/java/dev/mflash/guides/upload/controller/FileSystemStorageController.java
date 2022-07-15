package dev.mflash.guides.upload.controller;

import dev.mflash.guides.upload.service.StorageException;
import dev.mflash.guides.upload.service.StorageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequestMapping("/file")
public @RestController class FileSystemStorageController {

  private final StorageService storageService;

  public FileSystemStorageController(StorageService storageService) {
    this.storageService = storageService;
  }

  public @GetMapping List<Path> listAllFiles() {
    return storageService.loadAll().collect(Collectors.toList());
  }

  @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<?> getFile(@RequestParam("fileName") String fileName) {
    try {
      return ResponseEntity.ok(storageService.loadAsResource(Objects.requireNonNull(fileName)));
    } catch (StorageException e) {
      return ResponseEntity.notFound().build();
    }
  }

  public @PostMapping Map<String, String> uploadFile(@RequestParam("data") MultipartFile... file) {
    try {
      storageService.store(Objects.requireNonNull(file));
      return Collections.singletonMap("status", "Successfully uploaded");
    } catch (Exception e) {
      return Collections.singletonMap("status", e.getLocalizedMessage());
    }
  }
}
