
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;

// Custom NotBlank annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

// Custom MaxLength annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

// SignupForm class
class SignupForm {

    @NotBlank
    @MaxLength(20)
    String username;

    @NotBlank
    @MaxLength(30)
    String email;

    @NotBlank
    @MaxLength(10)
    String password;

    SignupForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

// FormValidator class
public class FormValidator {

    public static List<String> validate(Object obj) {

        List<String> errors = new ArrayList<>();

        Class<?> cls = obj.getClass();

        for (Field field : cls.getDeclaredFields()) {

            field.setAccessible(true);

            try {
                String value = (String) field.get(obj);

                // Validate NotBlank
                if (field.isAnnotationPresent(NotBlank.class)) {
                    if (value == null || value.trim().isEmpty()) {
                        errors.add(field.getName() + " must not be blank");
                    }
                }

                // Validate MaxLength
                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength max = field.getAnnotation(MaxLength.class);

                    if (value != null && value.length() > max.value()) {
                        errors.add(field.getName() +
                            " must not exceed " + max.value() + " characters");
                    }
                }

            } catch (IllegalAccessException e) {
                errors.add("Unable to access field: " + field.getName());
            }
        }

        return errors;
    }

    public static void main(String[] args) {

        SignupForm form = new SignupForm(
            "",
            "verylongemailaddress@example.com",
            "123456789012345"
        );

        List<String> errors = validate(form);

        if (errors.isEmpty()) {
            System.out.println("Signup successful!");
        } else {
            System.out.println("Validation Errors:");

            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}

