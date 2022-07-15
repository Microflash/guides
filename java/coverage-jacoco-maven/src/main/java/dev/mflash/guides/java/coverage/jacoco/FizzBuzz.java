package dev.mflash.guides.java.coverage.jacoco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FizzBuzz {

  public static Stream<String> fizzBuzzViaStream(int lim) {
    return IntStream.rangeClosed(1, Math.abs(lim))
        .mapToObj(e -> e % 15 == 0 ? "FizzBuzz" : e % 3 == 0 ? "Fizz" : e % 5 == 0 ? "Buzz" : String.valueOf(e));
  }

  public static List<String> fizzBuzzViaLoop(int lim) {
    var list = new ArrayList<String>();
    for (int i = 1; i <= Math.abs(lim); i++) {
      if (i % 15 == 0) {
        list.add("FizzBuzz");
      } else if (i % 3 == 0) {
        list.add("Fizz");
      } else if (i % 5 == 0) {
        list.add("Buzz");
      } else {
        list.add(String.valueOf(i));
      }
    }
    return list;
  }

  public static List<String> fizzBuzzViaRecursion(int lim, List<String> list) {
    if (lim < 1) {
      Collections.reverse(list);
      return list;
    }
    list.add(lim % 15 == 0 ? "FizzBuzz" : lim % 3 == 0 ? "Fizz" : lim % 5 == 0 ? "Buzz" : String.valueOf(lim));
    return fizzBuzzViaRecursion(lim - 1, list);
  }
}
