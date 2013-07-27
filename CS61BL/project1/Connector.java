public class Connector {
	
	// Implement an immutable connector that connects two points on the game board.
	// Invariant: 1 <= myPoint1 < myPoint2 <= 6.
	
	private int myPoint1, myPoint2;
	
	public Connector (int p1, int p2) {
		if (p1 < p2) {
			myPoint1 = p1;
			myPoint2 = p2;
		} else {
			myPoint1 = p2;
			myPoint2 = p1;
		}
	}
	
	public int endPt1 ( ) {
		return myPoint1;
	}
	
	public int endPt2 ( ) {
		return myPoint2;
	}
	
	public boolean equals (Object obj) {
		Connector e = (Connector) obj;
		return (e.myPoint1 == myPoint1 && e.myPoint2 == myPoint2);
	}
	
	public String toString ( ) {
		return "" + myPoint1 + myPoint2;
	}
	
	// Format of a connector is endPoint1 + endPoint2 (+ means string concatenation),
	// possibly surrounded by white space. Each endpoint is a digit between
	// 1 and 6, inclusive; moreover, the endpoints aren't identical.
	// If the contents of the given string is correctly formatted,
	// return the corresponding connector.  Otherwise, throw IllegalFormatException.
	public static Connector toConnector (String s) throws IllegalFormatException {
		// You fill this in.
		try{
			s = s.trim();
		} catch(NullPointerException e){
			throw new IllegalFormatException("must give two digits");
		}
		int myPt1, myPt2;
		if(s.length() < 2){
			throw new IllegalFormatException("must give two digits");
		}
		try{
			myPt1 = Integer.parseInt(s.substring(0, 1));
			myPt2 = Integer.parseInt(s.substring(1, 2));
		} catch(NumberFormatException e){
			if(s.charAt(1) == ' ' || s.charAt(1) == '	'){
				throw new IllegalFormatException("cannot have any whitespace between digits");
			}
			if(s.charAt(0) == '-' || s.charAt(1) == '-'){
				throw new IllegalFormatException("cannot have negative signs");
			}
			throw new IllegalFormatException("must be numbers");
		}
		if(s.length() > 2){
			throw new IllegalFormatException("must give exactly two digits");
		}
		if(myPt1 == myPt2){
			throw new IllegalFormatException("must be two different digits");
		}
		if(myPt1 < 1 || myPt1 > 6){
			throw new IllegalFormatException("first digit must be between 1 and 6 inclusive");
		} else if(myPt2 < 1 || myPt2 > 6){
			throw new IllegalFormatException("second digit must be between 1 and 6 inclusive");
		}
		return new Connector(myPt1, myPt2);
	}
}
