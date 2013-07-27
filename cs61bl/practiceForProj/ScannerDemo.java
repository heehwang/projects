import java.util.Scanner;

public class ScannerDemo {
	public static void main(String[] args) {
		String s = "Hello World! 3 + 3.0 = 6 ";

		Scanner scanner = new Scanner(s);

		System.out.println("" + scanner.next());

		System.out.println(scanner.next());
		System.out.println(scanner.next());
		System.out.println(scanner.next());

		scanner.close();

		Scanner sc = new Scanner(System.in);
		String phrase = sc.nextLine();
		System.out.println(phrase);
		sc.close();
	}
}