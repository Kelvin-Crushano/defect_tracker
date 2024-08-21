package com.sgic.semita.utils;

import java.sql.Date;
import java.util.regex.Pattern;

public class Utills {

	public static boolean stringValidation(String name) {
		if (name != null && !name.trim().isEmpty()) {
			String word[] = name.split("[;:-]");
			int size = word.length;
			if (size > 0) {
				return false;
			}

		}
		return true;
	}

	public static boolean stringValidationUsingPattern(String name) {
		if (Pattern.matches("^[a-zA-Z]{4,20}$", name.trim())) {
			return true;
		}
		return false;
	}

	public static boolean patternMatches(String emailAddress) {
		if (Pattern.matches("^(.+)@(\\S+)$", emailAddress.trim())) {
			return true;
		}
		return false;

	}

	public static boolean notNullValidation(String name) {
		if (name != null && !name.trim().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean idValidation(Long id) {
		if (id != null) {
			return true;
		}
		return false;
	}

	public static boolean dateValidation(Date startDate, Date endDate) {
		if (endDate.after(startDate)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isValidPassword(String password) {
		return password.length() >= 8 && password.matches(".*\\d.*") && password.matches(".*[a-z].*")
				&& password.matches(".*[A-Z].*") && password.matches(".*[@#$%^&+=].*");
	}

	// Validate if the string is a valid number
	public static boolean isValidNumber(String value) {
		if (value == null || value.trim().isEmpty()) {
			return false;
		}
		try {
			Integer.parseInt(value.trim());
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// Parse the string to an integer
	public static Integer parseNumber(String value) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException("Value cannot be null or empty");
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid number format: " + value);
		}
	}

	// Validate the length of the number
	public static boolean isValidNumberLength(Integer number, int minLength) {
		if (number == null) {
			return false;
		}
		return number.toString().length() >= minLength;
	}

	public static boolean isValidBoolean(String value) {
		if (value == null) {
			return false;
		}
		String trimmedValue = value.trim().toLowerCase();
		return "true".equals(trimmedValue) || "false".equals(trimmedValue) ||
				"0".equals(trimmedValue) || "1".equals(trimmedValue);
	}

	public static Boolean parseBoolean(String value) {
		if (isValidBoolean(value)) {
			String trimmedValue = value.trim().toLowerCase();
			if ("true".equals(trimmedValue) || "1".equals(trimmedValue)) {
				return true;
			} else if ("false".equals(trimmedValue) || "0".equals(trimmedValue)) {
				return false;
			}
		}
		throw new IllegalArgumentException("Invalid boolean format: " + value);
	}
}
