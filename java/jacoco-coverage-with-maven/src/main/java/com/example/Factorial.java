package com.example;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.LongStream;

interface Factorial {

	static long getFactorialViaLoop(final int num) {
		int factorial = num == 0 ? 1 : num;
		for (int i = num - 1; i > 1; i--) {
			factorial *= i;
		}
		return factorial;
	}

	static long getFactorialViaRecursion(final int num) {
		if (num == 1 || num == 0) {
			return 1;
		}
		return num * getFactorialViaRecursion(num - 1);
	}

	static long getFactorialFunctionally(final int num) {
		return LongStream.rangeClosed(2, num).reduce(1, Math::multiplyExact);
	}

	static Optional<BigDecimal> getFactorial(final int num) {
		return LongStream.rangeClosed(2, num).parallel().mapToObj(BigDecimal::new)
				.reduce(BigDecimal::multiply);
	}
}
