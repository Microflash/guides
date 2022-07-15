package dev.mflash.guides.java.coverage.jacoco;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class FizzBuzzTest {

  @Test
  @DisplayName("Should get correct text via stream")
  void shouldGetCorrectTextViaStream() {
    final List<String> texts = FizzBuzz.fizzBuzzViaStream(15).collect(Collectors.toList());

    assertThat(texts.get(2)).isEqualTo("Fizz");
    assertThat(texts.get(4)).isEqualTo("Buzz");
    assertThat(texts.get(14)).isEqualTo("FizzBuzz");
  }

  @Test
  @DisplayName("Should get correct text via loop")
  void shouldGetCorrectTextViaLoop() {
    final List<String> texts = FizzBuzz.fizzBuzzViaLoop(15);

    assertThat(texts.get(2)).isEqualTo("Fizz");
    assertThat(texts.get(4)).isEqualTo("Buzz");
    assertThat(texts.get(14)).isEqualTo("FizzBuzz");
  }

  @Test
  @DisplayName("Should get correct text recursively")
  void shouldGetCorrectTextRecursively() {
    final List<String> texts = FizzBuzz.fizzBuzzViaRecursion(15, new ArrayList<>());

    assertThat(texts.get(2)).isEqualTo("Fizz");
    assertThat(texts.get(4)).isEqualTo("Buzz");
    assertThat(texts.get(14)).isEqualTo("FizzBuzz");
  }
}
