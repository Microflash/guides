package dev.mflash.guides.upload.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.mflash.guides.upload.service.StorageException;
import dev.mflash.guides.upload.service.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Paths;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public @SpringBootTest class FileUploadTests {

  private @Autowired MockMvc mvc;
  private @MockBean StorageService storageService;

  public @Test void shouldListAllFiles() throws Exception {
    final String firstFile = getFullPath("first.txt");
    final String secondFile = getFullPath("second.txt");
    given(this.storageService.loadAll())
        .willReturn(Stream.of(Paths.get(firstFile), Paths.get(secondFile)));

    this.mvc.perform(get("/file")).andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").value(attachPrefix(firstFile)))
        .andExpect(jsonPath("$[1]").value(attachPrefix(secondFile)));
  }

  public @Test void shouldSaveUploadedFile() throws Exception {
    final String testFile = getFullPath("test.txt");
    MockMultipartFile multipartFile = new MockMultipartFile("data", testFile,
        "text/plain", "Spring Framework".getBytes());

    this.mvc.perform(multipart("/file").file(multipartFile)).andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("Successfully uploaded"));

    then(this.storageService).should().store(multipartFile);
  }

  public @Test void should404WhenMissingFile() throws Exception {
    final String testFile = getFullPath("missing.txt");
    given(this.storageService.loadAsResource(testFile))
        .willThrow(StorageException.class);

    this.mvc.perform(get("/file/download").param("fileName", testFile))
        .andExpect(status().isNotFound());
  }

  private String getFullPath(String filename) {
    String rootDir = "src/test/resources/files/";
    return rootDir + filename;
  }

  private String attachPrefix(String filePath) {
    // Assign prefix the absolute path till the root Dir, e.g., file:///C:/guides/spring/uploading-files-with-spring-boot-and-angular/
    String prefix = "file:///C:/guides/spring/uploading-files-with-spring-boot-and-angular/";
    return prefix + filePath;
  }
}