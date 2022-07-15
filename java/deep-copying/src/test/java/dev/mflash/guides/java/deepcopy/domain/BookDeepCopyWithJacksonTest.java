package dev.mflash.guides.java.deepcopy.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BookDeepCopyWithJacksonTest {

  @Test
  @DisplayName("Should copy data but not reference")
  void shouldCopyDataButNotReference() throws JsonProcessingException {
    final var andyWeir = new Author();
    andyWeir.setName("Andy Weir");

    final var martian = new Book();
    martian.setTitle("The Martian");
    martian.setAuthor(andyWeir);

    final var objectMapper = new ObjectMapper();
    final String json = objectMapper.writeValueAsString(martian);
    final Book copyOfMartian = objectMapper.readValue(json, Book.class);

    assertThat(copyOfMartian).isNotEqualTo(martian);
    assertThat(copyOfMartian.getAuthor()).isNotEqualTo(martian.getAuthor());
    assertThat(copyOfMartian.getTitle()).isEqualTo(martian.getTitle());
    assertThat(copyOfMartian.getAuthor().getName()).isEqualTo(martian.getAuthor().getName());

    copyOfMartian.getAuthor().setName("Marie Lu");
    assertThat(copyOfMartian.getAuthor().getName()).isNotEqualTo(martian.getAuthor().getName());
  }
}
