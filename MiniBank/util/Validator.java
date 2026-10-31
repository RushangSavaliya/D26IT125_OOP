package util;

import java.util.regex.Pattern;

public class Validator {

	private static final Pattern PAN =
	    Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

	private Validator() {
	}

	public static boolean isValidPan(String pan) {
		return matches(PAN, pan);
	}

	private static boolean matches(Pattern pattern, String value) {
		return value != null && pattern.matcher(value).matches();
	}
}
