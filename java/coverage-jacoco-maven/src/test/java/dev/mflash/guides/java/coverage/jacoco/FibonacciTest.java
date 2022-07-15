package dev.mflash.guides.java.coverage.jacoco;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;
import java.util.stream.Collectors;

class FibonacciTest {

  private final List<Integer> fibonacciViaLoop = Fibonacci.fibonacciViaLoop(15);
  private final List<Fibonacci.Pair> fibonacciViaStream = Fibonacci.fibonacciViaStream(15).collect(Collectors.toList());

  @ParameterizedTest
  @DisplayName("Should get fibonacci at number via loop")
  @CsvFileSource(resources = "fibonacciDataSet.csv", numLinesToSkip = 1)
  void shouldGetFibonacciAtNumberViaLoop(int input, int fibonacci) {
    assertThat(fibonacciViaLoop.get(input - 1)).isEqualTo(fibonacci);
  }

  @ParameterizedTest
  @DisplayName("Should get fibonacci at number via stream")
  @CsvFileSource(resources = "fibonacciDataSet.csv", numLinesToSkip = 1)
  void shouldGetFibonacciAtNumberViaStream(int input, int fibonacci) {
    assertThat(fibonacciViaStream.get(input - 1).previous).isEqualTo(fibonacci);
  }

  @ParameterizedTest
  @DisplayName("Should get fibonacci at number recursively")
  @CsvFileSource(resources = "fibonacciDataSet.csv", numLinesToSkip = 1)
  void shouldGetFibonacciAtNumberRecursively(int input, int fibonacci) {
    assertThat(Fibonacci.recursiveFibonacci(input)).isEqualTo(fibonacci);
  }
}
