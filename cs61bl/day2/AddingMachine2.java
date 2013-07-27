import java.util.*;

public class AddingMachine2{
    public static void main(String[] args){
	Scanner scanner = new Scanner (System.in);
	int k; 
	int previous = -99999; 
	int total = 0; 
	int subtotal = 0; 
	k = scanner.nextInt ( ); 
	while (true) { 
	    if (k == 0 && previous == 0) { 
		System.out.println("total "+total);
		return;
	    } 
	    else if (k == 0) { 
 		total = subtotal + total;
 		System.out.println("subtotal "+subtotal);
		subtotal = 0;
 		previous = 0;
	    } 
	    else { 
		subtotal = subtotal+k;
		previous = k;
	    } 
	    k = scanner.nextInt ( ); 
	} 
    }
}