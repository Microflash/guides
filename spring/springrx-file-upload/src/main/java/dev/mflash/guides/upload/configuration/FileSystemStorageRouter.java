package dev.mflash.guides.upload.configuration;

import dev.mflash.guides.upload.handler.FileSystemStorageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

public @Configuration class FileSystemStorageRouter {

  public @Bean RouterFunction<ServerResponse> storageRouter(FileSystemStorageHandler storageHandler) {
    return RouterFunctions
        .route(RequestPredicates.GET("/file"), storageHandler::listAllFiles)
        .andRoute(RequestPredicates.GET("/file/download"), storageHandler::getFile)
        .andRoute(RequestPredicates.POST("/file").and(RequestPredicates.accept(MediaType.MULTIPART_FORM_DATA)),
            storageHandler::uploadFile);
  }
}
