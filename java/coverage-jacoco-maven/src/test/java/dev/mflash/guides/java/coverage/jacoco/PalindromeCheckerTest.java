package dev.mflash.guides.java.coverage.jacoco;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class PalindromeCheckerTest {

  @ParameterizedTest
  @MethodSource
  @DisplayName("Should verify if text is palindrome")
  void shouldVerifyIfTextIsPalindrome(String text, boolean isPalindrome) {
    assertThat(PalindromeChecker.isPalindrome(text)).isEqualTo(isPalindrome);
  }

  @ParameterizedTest
  @MethodSource
  @DisplayName("Should verify if number is palindrome")
  void shouldVerifyIfNumberIsPalindrome(int number, boolean isPalindrome) {
    assertThat(PalindromeChecker.isPalindrome(number)).isEqualTo(isPalindrome);
  }

  private static Stream<Arguments> shouldVerifyIfTextIsPalindrome() {
    return Stream.of(
      Arguments.of("BOB", true),
      Arguments.of("hannah", true),
      Arguments.of("Viv", true),
      Arguments.of("A", true),
      Arguments.of("@##@", true),
      Arguments.of("Holly", false)
    );
  }

  private static Stream<Arguments> shouldVerifyIfNumberIsPalindrome() {
    return Stream.of(
        Arguments.of(1, true),
        Arguments.of(22, true),
        Arguments.of(434, true),
        Arguments.of(123454321, true),
        Arguments.of(-9, true),
        Arguments.of(-98089, true),
        Arguments.of(123, false),
        Arguments.of(-567, false)
    );
  }
}
