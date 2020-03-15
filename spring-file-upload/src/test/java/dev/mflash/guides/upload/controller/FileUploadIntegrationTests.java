package dev.mflash.guides.upload.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

import dev.mflash.guides.upload.service.StorageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadIntegrationTests {

  private @Autowired TestRestTemplate restTemplate;
  private @MockBean StorageService storageService;
  private @LocalServerPort int port;

  public @Test void shouldUploadFile() {
    FileSystemResource resource = new FileSystemResource(Paths.get(getFullPath("testupload.txt")));

    MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
    map.add("data", resource);
    ResponseEntity<String> response = this.restTemplate.postForEntity("/file", map, String.class);

    assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    then(storageService).should().store(any(MultipartFile.class));
  }

  public @Test void shouldDownloadFile() {
    final String testFile = getFullPath("testupload.txt");
    FileSystemResource resource = new FileSystemResource(Paths.get(testFile));
    given(this.storageService.loadAsResource(testFile)).willReturn(resource);

    ResponseEntity<String> response = this.restTemplate
        .getForEntity("/file/download?fileName={filename}", String.class, testFile);

    assertThat(response.getStatusCodeValue()).isEqualTo(200);
    assertThat(response.getBody()).isEqualTo("Spring Framework");
  }

  private String getFullPath(String filename) {
    String rootDir = "src/test/resources/files/";
    return rootDir + filename;
  }
}