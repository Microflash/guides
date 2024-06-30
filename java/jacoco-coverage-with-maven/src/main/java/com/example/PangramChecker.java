package com.example;

interface PangramChecker {

	static boolean isPangram(final String text) {
		return text.toLowerCase()
				.replaceAll("[^a-z]+", "")
				.chars()
				.distinct()
				.count() == 26;
	}
}
