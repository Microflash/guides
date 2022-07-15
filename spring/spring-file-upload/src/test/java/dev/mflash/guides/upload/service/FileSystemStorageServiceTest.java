package dev.mflash.guides.upload.service;

import static org.assertj.core.api.Assertions.*;

import dev.mflash.guides.upload.configuration.StorageProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Random;

class FileSystemStorageServiceTest {

  private StorageProperties properties = new StorageProperties();
  private FileSystemStorageService service;
  private final String rootDir = "src/test/resources/files/" + Math.abs(new Random().nextLong());

  public @BeforeEach void init() {
    properties.setLocation(rootDir);
    service = new FileSystemStorageService(properties);
    service.init();
  }

  public @AfterEach void cleanUp() {
    service.deleteAll();
  }

  public @Test void loadNonExistent() {
    assertThat(service.load("foo.txt")).doesNotExist();
  }

  public @Test void saveAndLoad() {
    service.store(new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
        "Hello World".getBytes()));
    assertThat(service.load("foo.txt")).exists();
  }

  @Test
  public void saveNotPermitted() {
    assertThatExceptionOfType(StorageException.class)
        .isThrownBy(() -> service.store(new MockMultipartFile("foo", "../foo.txt",
            MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes())));
  }

  public @Test void savePermitted() {
    service.store(new MockMultipartFile("foo", "bar/../foo.txt",
        MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()));
  }
}