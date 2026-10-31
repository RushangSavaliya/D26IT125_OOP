package service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import model.annotation.Id;

public class Repository<T> {

	private final HashMap<String, T> store = new HashMap<>();

	public T save(T entity) {
		String id = extractId(entity);
		store.put(id, entity);
		return entity;
	}

	public T findById(String id) {
		return store.get(id);
	}

	public ArrayList<T> findAll() {
		return new ArrayList<>(store.values());
	}

	private String extractId(T entity) {
		if (entity == null) {
			throw new IllegalArgumentException(
			    "Cannot save a null entity");
		}

		for (Class<?> type = entity.getClass();
		    type != null && type != Object.class;
		    type = type.getSuperclass()) {

			for (Field field : type.getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					return readId(field, entity);
				}
			}
		}

		throw new IllegalArgumentException("No @Id field on " +
		    entity.getClass().getSimpleName());
	}

	private String readId(Field field, T entity) {
		field.setAccessible(true);

		try {
			Object value = field.get(entity);

			if (value == null) {
				throw new IllegalArgumentException("@Id field " +
				    field.getName() + " is null");
			}

			return value.toString();
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(
			    "Cannot read @Id field " + field.getName(), e);
		}
	}
}
