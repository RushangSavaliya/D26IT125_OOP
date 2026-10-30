
import java.util.*;

public class UniqueVisitors {

    public static void main(String[] args) {

        // Login names with repeated users
        String[] logins = {
            "Rahul", "Amit", "Priya", "Rahul",
            "Karan", "Amit", "Neha", "Priya"
        };

        // HashSet to count unique users
        HashSet<String> uniqueUsers = new HashSet<>();

        // LinkedHashSet to maintain insertion order
        LinkedHashSet<String> orderedUsers = new LinkedHashSet<>();

        for (String name : logins) {
            uniqueUsers.add(name);
            orderedUsers.add(name);
        }

        // Display unique visitor count
        System.out.println("Total Unique Visitors: "
                + uniqueUsers.size());

        // Display first three distinct users
        System.out.println("First Three Unique Visitors:");

        int count = 0;

        for (String name : orderedUsers) {

            if (count == 3) {
                break;
            }

            System.out.println(name);
            count++;
        }
    }
}

