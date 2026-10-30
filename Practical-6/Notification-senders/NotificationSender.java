// Functional interface
interface Notifier {

	void send(String message);
}

// Marker interface
interface Urgent {
}

// Main class
public class NotificationSender {

	public static void main(String[] args) {

		// Email sender using lambda
		Notifier email = message -> System.out.println("Email: " + message);

		// SMS sender using lambda
		Notifier sms = message -> System.out.println("SMS: " + message);

		// Urgent email sender
		class UrgentEmail implements Notifier, Urgent {

			@Override
			public void send(String message) {
				System.out.println("Urgent Email: " + message);
			}
		}

		// Create urgent email sender
		Notifier urgentEmail = new UrgentEmail();

		// Store all senders in an array
		Notifier[] senders = {email, sms, urgentEmail};

		String message = "Meeting at 10 AM";

		// Broadcast message to all senders
		for (Notifier sender : senders) {

			sender.send(message);

			// Send twice for urgent senders
			if (sender instanceof Urgent) {
				sender.send(message);
			}
		}
	}
}
