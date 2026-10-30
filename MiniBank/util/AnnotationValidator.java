package util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import model.annotation.MaxLength;
import model.annotation.Positive;

public class AnnotationValidator {

	private AnnotationValidator() {
	}

	public static String[] validate(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException(
			    "Cannot validate a null object");
		}

		List<String> errors = new ArrayList<>();

		for (Class<?> type = obj.getClass();
		    type != null && type != Object.class;
		    type = type.getSuperclass()) {

			for (Field field : type.getDeclaredFields()) {
				field.setAccessible(true);
				Object value = read(field, obj);

				if (field.isAnnotationPresent(Positive.class)) {
					checkPositive(field, value, errors);
				}

				if (field.isAnnotationPresent(MaxLength.class)) {
					checkMaxLength(field, value, errors);
				}
			}
		}

		return errors.toArray(new String[0]);
	}

	private static void checkPositive(Field field, Object value,
	    List<String> errors) {
		if (value instanceof Number number && number.longValue() <= 0) {
			errors.add(field.getAnnotation(Positive.class).message());
		}
	}

	private static void checkMaxLength(Field field, Object value,
	    List<String> errors) {
		if (value instanceof String text) {
			int max = field.getAnnotation(MaxLength.class).value();

			if (text.length() > max) {
				errors.add(field.getName() +
				    " must be at most " + max +
				    " characters");
			}
		}
	}

	private static Object read(Field field, Object obj) {
		try {
			return field.get(obj);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(
			    "Cannot read field " + field.getName(), e);
		}
	}
}
