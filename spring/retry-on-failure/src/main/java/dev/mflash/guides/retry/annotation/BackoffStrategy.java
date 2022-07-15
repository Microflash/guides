package dev.mflash.guides.retry.annotation;

public enum BackoffStrategy {
  RANDOM, LINEAR, LINEAR_RANDOM, GEOMETRIC, GEOMETRIC_RANDOM, EXPONENTIAL, EXPONENTIAL_RANDOM
}
