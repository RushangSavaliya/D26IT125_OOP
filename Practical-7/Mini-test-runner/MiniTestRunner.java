
import java.lang.annotation.*;
import java.lang.reflect.*;

// Custom Run annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

// Class containing test methods
class MyTests {

    @Run
    public void testAddition() {
        System.out.println("Test Addition: Passed");
    }

    @Run
    public void testSubtraction() {
        System.out.println("Test Subtraction: Passed");
    }

    @Run
    public void testMultiplication() {
        System.out.println("Test Multiplication: Passed");
    }

    // This method will not run
    public void normalMethod() {
        System.out.println("Normal method");
    }
}

// Mini Test Runner
public class MiniTestRunner {

    public static void main(String[] args) {

        MyTests obj = new MyTests();

        Class<?> cls = obj.getClass();

        int count = 0;

        // Get all declared methods
        for (Method method : cls.getDeclaredMethods()) {

            // Check for @Run annotation
            if (method.isAnnotationPresent(Run.class)) {

                try {
                    method.invoke(obj);
                    count++;
                } catch (Exception e) {
                    System.out.println("Test failed: " + method.getName());
                }
            }
        }

        System.out.println("Total tests run: " + count);
    }
}

