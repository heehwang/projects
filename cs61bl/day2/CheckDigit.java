public class CheckDigit {

    public static void main (String [ ] args) {
        int id = 0;
        try {
            id = Integer.parseInt (args[0]);
        } catch (NumberFormatException e) {
            System.out.println ("The argument has to be a sequence of digits.");
            System.exit (1);
        }
        boolean isLegal = true;
        // your missing code goes here
	int sum = 0;
	int remainder = 0;
	int dividend = id;
	while(dividend != 0){
	    remainder = dividend%10;
	    sum = sum + remainder;
	    dividend = dividend/10;
	}
	sum = sum-(id%10);

	if ((sum%10) != (id%10))
	    isLegal = false;

        if (isLegal) {
            System.out.println (id + " is legal");
        } else {
            System.out.println (id + " is not legal");
        }
    }
}
