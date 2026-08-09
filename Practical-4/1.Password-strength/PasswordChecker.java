public class PasswordChecker {

	public static String strength(String pw) {
		int count = 0;

		if (pw.length() >= 8) {
			System.out.println("Length >= 8: passed");
			count++;
		}

		if (pw.matches(".*[A-Z].*")) {
			System.out.println("Uppercase letter: passed");
			count++;
		}

		if (pw.matches(".*[0-9].*")) {
			System.out.println("Digit: passed");
			count++;
		}

		if (pw.matches(".*[^a-zA-Z0-9].*")) {
			System.out.println("Special character: passed");
			count++;
		}

		if (count <= 1) {
			return "Weak";
		} else if (count <= 3) {
			return "Medium";
		} else {
			return "Strong";
		}
	}
}
