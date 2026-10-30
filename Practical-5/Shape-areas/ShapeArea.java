abstract class Shape {

	// Abstract method to calculate area
	abstract double area();
}

// Circle subclass
class Circle extends Shape {

	double radius;

	Circle(double radius) {
		this.radius = radius;
	}

	@Override
	double area() {
		return Math.PI * radius * radius;
	}
}

// Rectangle subclass
class Rectangle extends Shape {

	double length;
	double width;

	Rectangle(double length, double width) {
		this.length = length;
		this.width = width;
	}

	@Override
	double area() {
		return length * width;
	}
}

// Triangle subclass
class Triangle extends Shape {

	double base;
	double height;

	Triangle(double base, double height) {
		this.base = base;
		this.height = height;
	}

	@Override
	double area() {
		return 0.5 * base * height;
	}
}

// Main class
public class ShapeArea {

	public static void main(String[] args) {

		// Store different Shape objects in one Shape array
		Shape[] shapes = {new Circle(5), new Rectangle(10, 4), new Triangle(6, 8), new Circle(3),
				new Rectangle(7, 5)};

		double totalArea = 0;
		double largestArea = 0;
		Shape largestShape = null;

		// Single loop handles every shape using polymorphism
		for (Shape shape : shapes) {

			// Calls the correct area() method based on object type
			double currentArea = shape.area();

			// Print area of the current shape
			System.out.printf("%s Area = %.2f%n", shape.getClass().getSimpleName(), currentArea);

			// Add to total area
			totalArea += currentArea;

			// Check for the largest area
			if (currentArea > largestArea) {
				largestArea = currentArea;
				largestShape = shape;
			}
		}

		// Print total area
		System.out.printf("%nTotal Area = %.2f%n", totalArea);

		// Print the shape with the largest area
		System.out.println("Largest Shape = " + largestShape.getClass().getSimpleName());

		// Print largest area
		System.out.printf("Largest Area = %.2f%n", largestArea);
	}
}
