package com.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

interface Fibonacci {

	static List<Integer> fibonacciViaLoop(int limit) {
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

	record Pair(BigInteger previous, BigInteger current) {
	}

	static Stream<Pair> fibonacciViaStream(int limit) {
		return Stream.iterate(new Pair(BigInteger.ONE, BigInteger.ONE),
				e -> new Pair(e.current, e.previous.add(e.current))).limit(limit);
	}

	static int recursiveFibonacci(int num) {
		if (num < 3) {
			return 1;
		}
		return recursiveFibonacci(num - 2) + recursiveFibonacci(num - 1);
	}
}
