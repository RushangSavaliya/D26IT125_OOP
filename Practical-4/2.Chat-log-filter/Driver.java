import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {

		String[] logs = {"10:05 alice Hello there", "10:06 bob How are you?", "10:07 malformed",
				"10:08 charlie HELLO everyone", "10:09 david This is a normal message",
				"10:10 eve Did you say hello?"};

		Scanner sc = new Scanner(System.in);

		System.out.print("Enter keyword: ");
		String keyword = sc.nextLine();

		System.out.println();
		System.out.println(ChatFilter.filter(logs, keyword));

		sc.close();
	}
}
