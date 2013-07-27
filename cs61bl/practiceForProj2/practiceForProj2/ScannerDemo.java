import java.util.Scanner;

public class ScannerDemo {
	public static void main(String[] args) {
		String s = "Hello World! 3 + 3.0 = 6 ";

		Scanner scanner = new Scanner(s);

		System.out.println("" + scanner.next());

		System.out.println("" + scanner.next());

		scanner.close();
	}
}