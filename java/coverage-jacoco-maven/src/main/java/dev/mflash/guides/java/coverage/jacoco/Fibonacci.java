package dev.mflash.guides.java.coverage.jacoco;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Fibonacci {

  public static List<Integer> fibonacciViaLoop(int limit) {
    var prev = 0;
    var curr = 1;
    int temp;
    var list = new ArrayList<Integer>();
    for (var i = 1; i <= limit; i++) {
      temp = curr;
      curr = prev + curr;
      prev = temp;
      list.add(prev);
    }
    return list;
  }

  static class Pair {

    BigInteger previous;
    BigInteger current;

    Pair(BigInteger previous, BigInteger current) {
      this.previous = previous;
      this.current = current;
    }
  }

  public static Stream<Pair> fibonacciViaStream(int limit) {
    return Stream.iterate(new Pair(BigInteger.ONE, BigInteger.ONE),
        e -> new Pair(e.current, e.previous.add(e.current))).limit(limit);
  }

  public static int recursiveFibonacci(int num) {
    if (num < 3) {
      return 1;
    }
    return recursiveFibonacci(num - 2) + recursiveFibonacci(num - 1);
  }
}
