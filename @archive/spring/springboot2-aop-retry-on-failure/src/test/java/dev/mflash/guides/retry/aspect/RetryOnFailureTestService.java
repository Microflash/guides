package dev.mflash.guides.retry.aspect;

import dev.mflash.guides.retry.annotation.RetryOnFailure;
import org.springframework.stereotype.Service;

@Service
public class RetryOnFailureTestService {

  @RetryOnFailure
  public double attempt(double value) {
    if (value <= 0) {
      throw new ArithmeticException("Value <= 0");
    }

    return value;
  }
}
