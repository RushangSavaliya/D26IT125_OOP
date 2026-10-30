import java.util.Scanner;

public class GuardedCalculator {

	public static double calculate(double num1, double num2, char operator) throws DivideByZeroException {

		switch (operator) {

		case '+':
			return num1 + num2;

		case '-':
			return num1 - num2;

		case '*':
			return num1 * num2;

		case '/':
			if (num2 == 0) {
				throw new DivideByZeroException("Cannot divide by zero.");
			}
			return num1 / num2;

		default:
			throw new IllegalArgumentException("Invalid operator. Use +, -, * or /.");
		}
	}

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		boolean success = false;

		while (!success) {

			try {
				System.out.print("Enter first number: ");
				double num1 = Double.parseDouble(scanner.nextLine());

				System.out.print("Enter second number: ");
				double num2 = Double.parseDouble(scanner.nextLine());

				System.out.print("Enter operator (+, -, *, /): ");
				char operator = scanner.nextLine().charAt(0);

				double result = calculate(num1, num2, operator);

				System.out.println("Result: " + result);

				success = true;

			} catch (NumberFormatException e) {

				System.out.println("Invalid number input. Please enter valid numbers.");

			} catch (DivideByZeroException e) {

				System.out.println("Divide by zero error: " + e.getMessage());

			} catch (IllegalArgumentException e) {

				System.out.println("Invalid input: " + e.getMessage());

			} finally {

				System.out.println("Attempt logged.");
				System.out.println("--------------------");
			}
		}

		scanner.close();
	}
}
