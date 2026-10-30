package util;

public class AuditTrail implements AutoCloseable {

	private final String name;

	public AuditTrail(String name) {
		this.name = name;
		System.out.println("AuditTrail opened: " + name);
	}

	public void record(String message) {
		System.out.println("  audit: " + message);
	}

	@Override
	public void close() {
		System.out.println("AuditTrail closed: " + name);
	}
}
