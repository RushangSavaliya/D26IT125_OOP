abstract class Employee {

	String name;
	int id;

	// Parent constructor for shared name and id
	Employee(String name, int id) {
		this.name = name;
		this.id = id;
	}

	// Abstract method
	abstract double monthlySalary();
}

// FullTime subclass
class FullTime extends Employee {

	double fixedSalary;

	FullTime(String name, int id, double fixedSalary) {
		super(name, id);
		this.fixedSalary = fixedSalary;
	}

	@Override
	double monthlySalary() {
		return fixedSalary;
	}
}

// PartTime subclass
class PartTime extends Employee {

	double hours;
	double rate;

	PartTime(String name, int id, double hours, double rate) {
		super(name, id);
		this.hours = hours;
		this.rate = rate;
	}

	@Override
	double monthlySalary() {
		return hours * rate;
	}
}

// Intern subclass
class Intern extends Employee {

	double stipend;

	Intern(String name, int id, double stipend) {
		super(name, id);
		this.stipend = stipend;
	}

	@Override
	double monthlySalary() {
		return stipend;
	}
}

// Main class
public class Payroll {

	public static void main(String[] args) {

		// Mixed array of different Employee objects
		Employee[] employees = {new FullTime("Rahul", 101, 50000), new PartTime("Priya", 102, 120, 200),
				new Intern("Amit", 103, 10000), new FullTime("Neha", 104, 60000),
				new Intern("Riya", 105, 8000)};

		double totalSalary = 0;

		// One loop handles all employees using polymorphism
		for (Employee employee : employees) {

			// Calls the correct monthlySalary() method
			double salary = employee.monthlySalary();

			// Print employee details and salary
			System.out.printf("Name: %s, ID: %d, Salary: %.2f%n", employee.name, employee.id, salary);

			// Add salary to total
			totalSalary += salary;

			// Add note only for interns
			if (employee instanceof Intern) {
				System.out.println("Note: This employee is an Intern.");
			}
		}

		// Print total salary
		System.out.printf("%nTotal Monthly Payroll = %.2f%n", totalSalary);
	}
}
