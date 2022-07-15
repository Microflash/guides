package dev.mflash.guides.java.coverage.jacoco;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;

class FactorialTest {

  @ParameterizedTest
  @DisplayName("Should get factorial of number via loop")
  @CsvFileSource(resources = "factorialDataSetSmall.csv", numLinesToSkip = 1)
  void shouldGetFactorialOfNumberViaLoop(int number, long factorial) {
    assertThat(Factorial.getFactorialViaLoop(number)).isEqualTo(factorial);
  }

  @ParameterizedTest
  @DisplayName("Should get factorial of number via recursion")
  @CsvFileSource(resources = "factorialDataSetSmall.csv", numLinesToSkip = 1)
  void shouldGetFactorialOfNumberViaRecursion(int number, long factorial) {
    assertThat(Factorial.getFactorialViaRecursion(number)).isEqualTo(factorial);
  }

  @ParameterizedTest
  @DisplayName("Should get factorial of number functionally")
  @CsvFileSource(resources = "factorialDataSetSmall.csv", numLinesToSkip = 1)
  void shouldGetFactorialOfNumberFunctionally(int number, long factorial) {
    assertThat(Factorial.getFactorialFunctionally(number)).isEqualTo(factorial);
  }

  @ParameterizedTest
  @DisplayName("Should get factorial of number")
  @CsvFileSource(resources = "factorialDataSetLarge.csv", numLinesToSkip = 1)
  void shouldGetFactorialOfNumber(int number, BigDecimal factorial) {
    assertThat(Factorial.getFactorial(number))
        .hasValueSatisfying(result -> assertThat(result).isEqualTo(factorial));
  }
}
