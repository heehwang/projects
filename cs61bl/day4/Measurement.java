public class Measurement {
	// instance variables
	private int currentLength;

	/*
	 * Constructor: initialize this object to be a measurement of 0 feet, 0
	 * inches.
	 */
	public Measurement() {
		currentLength = 0;
		
	}

	/*
	 * Constructor: takes a number of feet as its single argument, using 0 as
	 * the number of inches
	 */
	public Measurement(int feet) {
		currentLength = feet*12;
	}

	/*
	 * Constructor: takes the number of feet in the measurement and the number
	 * of inches as arguments (in that order), and does the appropriate
	 * initialization
	 */
	public Measurement(int feet, int inches) {
		currentLength = feet*12 + inches;
	}

	/*
	 * Adds the argument m2 to the current measurement
	 */
	// Peter, I'm assuming that m2 is in inches. perhaps clarify with our TA later (Piazza)?
	public Measurement plus(Measurement m2) {
		int originalLength = this.getMeasurement();
		int addLength = m2.getMeasurement(); 
		return new Measurement(originalLength + addLength); 
	}

	/*
	 * Subtracts the argument m2 from the current measurement. You may assume
	 * that m2 will always be smaller than the current measurement.
	 */
	public Measurement minus(Measurement m2) {
		int originalLength = this.getMeasurement();
		int minusLength = m2.getMeasurement();
		return new Measurement(originalLength + minusLength); 
	}

	/*
	 * Takes a nonnegative integer argument n, and returns a new object that
	 * represents the result of multiplying this object's measurement by n. For
	 * example, if this object represents a measurement of 7 inches, multiple
	 * (3) should return an object that represents 1 foot, 9 inches.
	 */
	public Measurement multiple(int multipleAmount) {
		return new Measurement(); // provided to allow the file to compile
	}

	/*
	 * toString should return the String representation of this object in the
	 * form f'i" that is, a number of feet followed by a single quote followed
	 * by a number of inches less than 12 followed by a double quote (with no
	 * blanks).
	 */
	public String toString() {
		return new String(); // provided to allow the file to compile
	}
	public int getMeasurement(){
		return this.currentLength;
	}
	public void setMeasurement(int m){
		this.currentLength = m;
	}

}
