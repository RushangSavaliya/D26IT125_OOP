interface Switchable {

	// Abstract methods
	void on();

	void off();

	// Default method
	default void toggle() {
		System.out.println("Toggling device...");
	}
}

// Fan implements Switchable
class Fan implements Switchable {

	boolean isOn = false;

	@Override
	public void on() {
		isOn = true;
		System.out.println("Fan is ON");
	}

	@Override
	public void off() {
		isOn = false;
		System.out.println("Fan is OFF");
	}

	@Override
	public void toggle() {
		if (isOn) {
			off();
		} else {
			on();
		}
	}
}

// Light implements Switchable
class Light implements Switchable {

	boolean isOn = false;

	@Override
	public void on() {
		isOn = true;
		System.out.println("Light is ON");
	}

	@Override
	public void off() {
		isOn = false;
		System.out.println("Light is OFF");
	}

	@Override
	public void toggle() {
		if (isOn) {
			off();
		} else {
			on();
		}
	}
}

// Functional interface
interface SwitchPermission {

	boolean maySwitchOn(Switchable device, int hour);
}

// Main class
public class RemoteControl {

	public static void main(String[] args) {

		// Store different devices in one Switchable array
		Switchable[] devices = {new Fan(), new Light()};

		// Toggle every device using one loop
		System.out.println("Toggling Devices:");

		for (Switchable device : devices) {
			device.toggle();
		}

		// Anonymous class
		SwitchPermission permission1 = new SwitchPermission() {

			@Override
			public boolean maySwitchOn(Switchable device, int hour) {
				return hour >= 8 && hour <= 22;
			}
		};

		// Lambda expression
		SwitchPermission permission2 = (device, hour) -> hour >= 8 && hour <= 22;

		// Test anonymous class
		System.out.println("\nAnonymous Class:");

		System.out.println("Can switch on at 10: " + permission1.maySwitchOn(devices[0], 10));

		System.out.println("Can switch on at 23: " + permission1.maySwitchOn(devices[0], 23));

		// Test lambda
		System.out.println("\nLambda:");

		System.out.println("Can switch on at 10: " + permission2.maySwitchOn(devices[1], 10));

		System.out.println("Can switch on at 23: " + permission2.maySwitchOn(devices[1], 23));
	}
}
