package dev.mflash.guides.java.coverage.jacoco;

public class PangramChecker {

  public static boolean isPangram(final String text) {
    return text.toLowerCase()
        .replaceAll("[^a-z]+", "")
        .chars()
        .distinct()
        .count() == 26;
  }
}
