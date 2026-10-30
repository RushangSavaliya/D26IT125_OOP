package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import model.Account;

public class StatePersister {

	private StatePersister() {
	}

	public static void save(Account[] accounts, Path file)
	    throws IOException {
		createParent(file);

		try (ObjectOutputStream out = new ObjectOutputStream(
		    Files.newOutputStream(file))) {
			out.writeObject(accounts);
		}
	}

	public static Account[] load(Path file) throws IOException {
		try (ObjectInputStream in = new ObjectInputStream(
		    Files.newInputStream(file))) {
			return (Account[]) in.readObject();
		} catch (ClassNotFoundException e) {
			throw new IOException(
			    "Unknown class in " + file, e);
		}
	}

	private static void createParent(Path file) throws IOException {
		Path parent = file.getParent();

		if (parent != null) {
			Files.createDirectories(parent);
		}
	}
}
