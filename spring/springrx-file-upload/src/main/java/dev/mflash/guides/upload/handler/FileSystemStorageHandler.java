package dev.mflash.guides.upload.handler;

import dev.mflash.guides.upload.service.StorageException;
import dev.mflash.guides.upload.service.StorageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

public @Controller class FileSystemStorageHandler {

  private final StorageService storageService;

  public FileSystemStorageHandler(StorageService storageService) {
    this.storageService = storageService;
  }

  public Mono<ServerResponse> listAllFiles(ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(storageService.loadAll()));
  }

  public Mono<ServerResponse> getFile(ServerRequest request) {
    String fileName = request.queryParam("fileName").get();
    try {
      return ServerResponse.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
          .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName)).body(
              BodyInserters.fromValue(storageService.loadAsResource(fileName))
          );
    } catch (StorageException e) {
      return ServerResponse.notFound().build();
    }
  }

  public Mono<ServerResponse> uploadFile(ServerRequest request) {
    return request.multipartData().flatMap(parts -> {
      try {
        storageService
            .store(parts.get("data").parallelStream().map(part -> (FilePart) part).collect(Collectors.toList()));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(
            BodyInserters.fromValue(Map.of("status", "Successfully uploaded"))
        );
      } catch (Exception e) {
        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(
            BodyInserters.fromValue(Map.of("status", e.getLocalizedMessage()))
        );
      }
    });
  }
}
