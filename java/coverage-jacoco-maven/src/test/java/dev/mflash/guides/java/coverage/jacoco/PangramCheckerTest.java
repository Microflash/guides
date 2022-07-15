package dev.mflash.guides.java.coverage.jacoco;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class PangramCheckerTest {

  @ParameterizedTest
  @MethodSource
  @DisplayName("Should verify if text is pangram")
  void shouldVerifyIfTextIsPangram(String text, boolean isPangram) {
    assertThat(PangramChecker.isPangram(text)).isEqualTo(isPangram);
  }

  private static Stream<Arguments> shouldVerifyIfTextIsPangram() {
    return Stream.of(
        Arguments.of("abcdefghijklmnopqrstuvwxyz", true),
        Arguments.of("the quick brown fox jumps over the lazy dog", true),
        Arguments.of("the_quick_brown_fox_jumps_over_the_lazy_dog", true),
        Arguments.of("the 1 quick brown fox jumps over the 2 lazy dogs", true),
        Arguments.of("\"Five quacking Zephyrs jolt my wax bed.\"", true),
        Arguments.of("", false),
        Arguments.of("a quick movement of the enemy will jeopardize five gunboats", false),
        Arguments.of("five boxing wizards jump quickly at it", false),
        Arguments.of("7h3 qu1ck brown fox jumps ov3r 7h3 lazy dog", false),
        Arguments.of("the quick brown fox jumps over with lazy FX", false)
    );
  }
}
