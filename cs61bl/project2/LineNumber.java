public class LineNumber {
	
	private String lineNumber;
	private boolean firstIncrement;
	private boolean invalidInput;
	
	public LineNumber(){
		lineNumber = "1";
		firstIncrement = true;
		invalidInput = false;
	}
	
	public LineNumber(String s) throws IllegalLineException{
		numsAndPeriods(s);
		lineNumber = s;
		firstIncrement = false;
		invalidInput = false;
	}

	private static void numsAndPeriods(String s) throws IllegalLineException{
		String numsAndPeriods = "0123456789.";
		for(int i = 0; i < s.length(); i++){
			String c = s.substring(i, i+1);
			if(!numsAndPeriods.contains(c)){
				throw new IllegalLineException("not a valid line reference");
			}
		}
	}
	
	public LineNumber nesting(boolean done) throws IllegalLineException{
		if(!done){
			return new LineNumber(lineNumber + ".0");
		} else{
			int periodPos = lineNumber.lastIndexOf('.');
			return new LineNumber(lineNumber.substring(0, periodPos));
		}
		
	}
	
	public LineNumber increment() throws IllegalLineException{
		if(firstIncrement || invalidInput){
			firstIncrement = false;
			invalidInput = false;
			return this;
		}
		int periodPos = lineNumber.lastIndexOf('.');
		int num = 0;
		try{
			num = Integer.parseInt(lineNumber.substring(periodPos+1));
		} catch(Exception e){
			System.err.println("this line should never be printed");
		}
		num++;
		return new LineNumber(lineNumber.substring(0, periodPos+1) + num);
	}
	
	public void stayOnThisLineNumber(){
		invalidInput = true;
	}
	
	public String toString(){
		return lineNumber;
	}
	
	public boolean equals(Object obj){
		LineNumber otherLineNumber = (LineNumber) obj;
		return lineNumber.equals(otherLineNumber.lineNumber);
	}
}
