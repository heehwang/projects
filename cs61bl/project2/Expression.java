import java.util.*;

public class Expression {

	private ExpressionNode myRoot;
	private boolean iAmDebugging = false;

	// constructors
	public Expression(){
		myRoot=null;
	}
	
	public Expression(ExpressionNode node){
		myRoot = node;
	}
	
	public Expression(String expr) throws IllegalLineException {
		// check for correct input
		int opens = 0;
		int closes = 0;
		if ((expr == null) || (expr.equals(""))) {
			throw new IllegalLineException("string must not be blank");
		}
		for (int i = 0; i < expr.length(); i++) {
			char x = expr.charAt(i);
			String X = expr.substring(i, i + 1);
			String okchars = "()&|=>~";
			if (!Character.isLetter(x) && (!okchars.contains(X))) {
				throw new IllegalLineException("character not allowed");
			}
			try {
				if ((expr.charAt(i) == '=') && (expr.charAt(i + 1) != '>')) {
					throw new IllegalLineException("character not allowed");
				}
				if ((expr.charAt(i) == '>') && (expr.charAt(i - 1) != '=')) {
					throw new IllegalLineException("character not allowed");
				}
			} catch (StringIndexOutOfBoundsException e) {
				throw new IllegalLineException("character not allowed");
			}
			if (x == '(') {
				opens++;
			}
			if (x == ')') {
				closes++;
			}
		}
		if (opens != closes) {
			throw new IllegalLineException("unmatched parentheses");
		}

		try {
			myRoot = exprTree(expr);
		} catch (Exception e) {
			throw new IllegalLineException(e.getMessage());
		}
	}

	// sample trees to test things
	public void fillSampleTree1() {
		if (iAmDebugging) {
			myRoot = new ExpressionNode("a", new ExpressionNode("b"),
					new ExpressionNode("c"));
		}
	}

	public void fillSampleTree2() {
		if (iAmDebugging) {
			myRoot = new ExpressionNode("a", new ExpressionNode("b",
					new ExpressionNode("d", new ExpressionNode("e"),
							new ExpressionNode("f")), null),
					new ExpressionNode("c"));
		}
	}

	public void fillSampleTree3() {
		if (iAmDebugging) {
			myRoot = new ExpressionNode("a", new ExpressionNode("b"),
					new ExpressionNode("c", new ExpressionNode("d",
							new ExpressionNode("e"), new ExpressionNode("f")),
							null));
		}
	}

	public void fillSampleTree4() {
		if (iAmDebugging) {
			myRoot = new ExpressionNode("a", new ExpressionNode("b",
					new ExpressionNode("d"), new ExpressionNode("e")),
					new ExpressionNode("c", new ExpressionNode("f"),
							new ExpressionNode("g")));
		}
	}

	// method to draw the tree
	public void print() {
		if (myRoot != null) {
			printHelper(myRoot, 0);
		}
	}

	private static final String indent1 = "    ";

	private static void printHelper(ExpressionNode root, int indent) {
		if (root.myRight != null) {
			printHelper(root.myRight, indent + 1);
		}

		println(root.myItem, indent);

		if (root.myLeft != null) {
			printHelper(root.myLeft, indent + 1);
		}
	}

	private static void println(Object obj, int indent) {
		for (int k = 0; k < indent; k++) {
			System.out.print(indent1);
		}
		System.out.println(obj);
	}

	// the node object
	private static class ExpressionNode {
		public Object myItem;
		public ExpressionNode myLeft;
		public ExpressionNode myRight;

		public ExpressionNode(Object obj) {
			myItem = obj;
			myLeft = myRight = null;
		}

		public ExpressionNode(Object obj, ExpressionNode left,
				ExpressionNode right) {
			myItem = obj;
			myLeft = left;
			myRight = right;
		}

		public String toString() {
			return myItem.toString();
		}
	
		//this equals method takes two arguments
		public static boolean Equals(ExpressionNode e1,ExpressionNode e2){
			if ((e1==null)&&(e2==null)){
				return true;
			}
			if ((e1==null)&&(e2!=null)){
				return false;
			}
			if ((e1!=null)&&(e2==null)){
				return false;
			}
			if (!e1.myItem.equals(e2.myItem)){
				return false;
			}
			return (Equals(e1.myLeft,e2.myLeft)&&Equals(e1.myRight,e2.myRight));
		}
		
		public void replaceNodes3(ExpressionNode toReplace, ExpressionNode toAdd){
			
			if (ExpressionNode.Equals(this,toReplace)){
				myItem=toAdd.myItem;
				myLeft=toAdd.myLeft; 
				myRight=toAdd.myRight;
			}
			
			//if we did the replacement, don't check on left and right
			else{
				if (myLeft!=null){
					myLeft.replaceNodes3(toReplace,toAdd);
				}
				if (myRight!=null){
					myRight.replaceNodes3(toReplace,toAdd);
				}
			}
		}
		
	}//end ExpressionNode class
	
	
	public boolean equals(Object E){//the object should be an Expression
		return ExpressionNode.Equals(myRoot,((Expression) E).myRoot);
	}
	
	public static Expression replace(Expression oldExpr, String oldString, String newString){
		if (oldExpr!=null){
			return (new Expression(replace(oldExpr.myRoot,oldString,newString))); 
		}
		return null;
		
	}
	
	public static ExpressionNode replace(ExpressionNode oldNode,String oldString, String newString){
		if (oldNode==null){
			return null;
		}
		
		if (oldNode.myItem.equals(oldString)){
			return new ExpressionNode(newString,replace(oldNode.myLeft,oldString,newString),
					replace(oldNode.myRight,oldString,newString));
		}
		else{
			return new ExpressionNode(oldNode.myItem,replace(oldNode.myLeft,oldString,newString),
					replace(oldNode.myRight,oldString,newString));
		}
		
	}
	
	//replaceNodes1 makes a copy with some nodes replaced- original should not change
	public static Expression replaceNodes1(Expression oldExpr, ExpressionNode toReplace, ExpressionNode toAdd){
		if (oldExpr!=null){
			return (new Expression(replaceNodes1(oldExpr.myRoot,toReplace,toAdd))); 
		}
		return null;
		
	}
	
	public static ExpressionNode replaceNodes1(ExpressionNode toCopy,
			ExpressionNode toReplace, ExpressionNode toAdd){
		if (toCopy==null){
			return null;
		}
		
		if (ExpressionNode.Equals(toCopy,toReplace)){
			return toAdd;
		}
		else{
			return new ExpressionNode(toCopy.myItem,replaceNodes1(toCopy.myLeft,toReplace,toAdd),
				replaceNodes1(toCopy.myRight,toReplace,toAdd));
		}
		
	}
	
	//replaceNodes2 changes the original object
	public static void replaceNodes2(Expression oldExpr, ExpressionNode toReplace, ExpressionNode toAdd){
		if (oldExpr!=null){
			replaceNodes2(oldExpr.myRoot,toReplace,toAdd); 
		}
	}
	
	public static void replaceNodes2(ExpressionNode toChange,
			ExpressionNode toReplace, ExpressionNode toAdd){
		if (toChange==null){
			return;
		}
		
		if (ExpressionNode.Equals(toChange,toReplace)){
			toChange.myItem=toAdd.myItem;
			toChange.myLeft=toAdd.myLeft; 
			toChange.myRight=toAdd.myRight;
		}
		
		//if we did the replacement, don't check on left and right
		else{
			if (toChange.myLeft!=null){
				replaceNodes2(toChange.myLeft,toReplace,toAdd);
			}
			if (toChange.myRight!=null){
				replaceNodes2(toChange.myRight,toReplace,toAdd);
			}
		}
	}
	
	//but to use in another function, it's better to have a non-static method
	public void replaceNodes3(ExpressionNode toReplace, ExpressionNode toAdd){
		if (myRoot!=null){
			myRoot.replaceNodes3(toReplace,toAdd); 
		}
	}
	
	
	
	
	
	public ExpressionNode myRoot(){
		return myRoot;
	}
	
	public Expression myLeft(){
		Expression e = new Expression();
		e.myRoot = myRoot.myLeft;
		return e;
	}

	public Expression myRight(){
		Expression e = new Expression();
		e.myRoot = myRoot.myRight;
		return e;

	}
	
	public boolean isNegation(Expression e1)throws IllegalLineException{
			Expression e2=new Expression("~"+e1.toString());
			return equals(e2);
	}

	/*
	 * public static Expression exprTree(String s) { Expression result = new
	 * Expression(); result.myRoot = result.exprTreeHelper(s); // also need to
	 * check for illegal input return result; }
	 */

	private ExpressionNode exprTree(String expr) throws IllegalLineException {
		String opnd1, opnd2, op;

		if ((expr == null) || (expr.equals(""))) {
			return null;
		}

		if (expr.charAt(0) != '(') {
			//should be a single variable or a ~ statement
			if (expr.charAt(0) == '~') {
				if (expr.length() < 2) {
					throw new IllegalLineException(
							"Invalid input: an expression must follow ~");
				}
				opnd1 = expr.substring(1, expr.length());
				return new ExpressionNode("~", exprTree(opnd1), null);
			} 
			else if (expr.contains("&")){
				throw new IllegalLineException( 
						"& statements take two arguments and must be enclosed in parentheses");					
			}
			else if (expr.contains("|")){
				throw new IllegalLineException( 
						"| statements take two arguments and must be enclosed in parentheses");					
			}
			else if (expr.contains("=>")){
				throw new IllegalLineException( 
						"=> statements take two arguments and must be enclosed in parentheses");					
			}
			else if (expr.length() > 1) {
				throw new IllegalLineException(
						"variables may only be one letter");
			}
			return new ExpressionNode(expr);
			// assume every sub-expression is in parentheses
			// so the only one without parentheses is a single variable
		} else {
			// expr is a parenthesized expression.
			int nesting = 0; // probably should be 0
			int opPos = 0;
			boolean isImplication = false; // If it is '=>' symbol, this boolean
											// value is set to true.

			for (int k = 1; k < expr.length() - 1; k++) {
				// this skips the enclosing parentheses, so it sees main
				// operation at nesting=0
				// find the main operator (an occurrence of + or * not nested in
				// parentheses
				if (expr.charAt(k) == '(') {
					nesting++;
				}
				if (expr.charAt(k) == ')') {
					nesting--;
				}
				if ((nesting == 0)
						&& ((expr.charAt(k) == '&') || (expr.charAt(k) == '|'))) {
					opPos = k;
					break;
				} else if ((nesting == 0) && (expr.charAt(k) == '=')
						&& (expr.charAt(k + 1) == '>')) {
					opPos = k;
					isImplication = true;
					break;
				}
			}

			try {
				if (isImplication) {
					op = expr.substring(opPos, opPos + 2);
					opnd1 = expr.substring(1, opPos);
					opnd2 = expr.substring(opPos + 2, expr.length() - 1);
				} else {
					op = expr.substring(opPos, opPos + 1);
					opnd1 = expr.substring(1, opPos);
					opnd2 = expr.substring(opPos + 1, expr.length() - 1);
				}
			} catch (StringIndexOutOfBoundsException e) {
				throw new IllegalArgumentException("Invalid input: check the parentheses");
			}
			if (iAmDebugging) {
				System.out.println("expression = " + expr);
				System.out.println("operand 1  = " + opnd1);
				System.out.println("operator   = " + op);
				System.out.println("operand 2  = " + opnd2);
				System.out.println();
			}

			if (op != "~") {
				if (opnd1.isEmpty() || opnd2.isEmpty()) {
					throw new IllegalArgumentException(
							"Invalid input: &, |, and => take two arguments");
				}
			}
			// construct the two subtrees.
			return new ExpressionNode(op, exprTree(opnd1), exprTree(opnd2));
		}
	}
	public String toString(){
		if (myRoot!=null){
			return toString(myRoot);
		}
		return "";
	}

	public String toString(ExpressionNode node){
	
		if((node.myLeft==null)&&(node.myRight==null)){//variable
			return (String) node.myItem;
		}
		if (node.myItem.equals("~")){
			return "~"+toString(node.myLeft);
		}
		if ((node.myLeft!=null)&&(node.myRight!=null)){//&, | , =>
			return "("+toString(node.myLeft)+node.myItem+toString(node.myRight)+")";
		}
		return "";
	}
	
	public static boolean matchesTheorem(Expression expr, Expression theorem) throws IllegalLineException{
		//do this one with hashSet
		//make a copy since replaceNodes2 modifies the argument
		Hashtable<String,String> substitutions = new Hashtable<String,String>();
		return matchesTheorem(expr,expr.myRoot,theorem.myRoot,substitutions);			
	}
	
	
	
	//matchesTheorem and matchesTheorem2 are the same except when thm and expr share letters
	public static boolean matchesTheorem(Expression expr, ExpressionNode exprNode,
			ExpressionNode thmNode, Hashtable<String,String> subs){
		
		if ("~=>&|".contains((String) thmNode.myItem)){ //thmNode has operator- expr must have same operator
			if(!exprNode.myItem.equals(thmNode.myItem)){
				return false;
			}
			else{ //did have the same thing- keep looking
				//both sides null- return true
				//either side not null- check that side
				boolean leftOk=true;
				boolean rightOk=true;
				if (thmNode.myLeft!=null){
					leftOk = matchesTheorem(expr,exprNode.myLeft,thmNode.myLeft,subs);
				}
				if (thmNode.myRight!=null){
					rightOk = matchesTheorem(expr,exprNode.myRight,thmNode.myRight,subs);
				}
				return (leftOk&&rightOk);
			}
		}
				
		else{ //thmNode has variable- then no left and right
			
			if (subs.containsKey(thmNode.myItem)){
				return exprNode.toString().equals(subs.get(thmNode.myItem));
			}
			else{
				subs.put((String) thmNode.myItem,(String) exprNode.toString());
				return true;
			}
		}
	}
		
		

	//can probably take out matchesTheorem2
	public static boolean matchesTheorem2(Expression expr, Expression theorem) throws IllegalLineException{
		//do this one with hashSet
		//make a copy since replaceNodes2 modifies the argument
		Hashtable<String,String> substitutions = new Hashtable<String,String>();
		boolean reachesEnd= matchesTheorem2(expr,expr.myRoot,theorem.myRoot,substitutions);	
		return reachesEnd;
		
	}
	
	
	
	
	public static boolean matchesTheorem2(Expression expr, ExpressionNode exprNode,
			ExpressionNode thmNode, Hashtable<String,String> subs){
		
		if(!exprNode.myItem.equals(thmNode.myItem)){//different item- only ok if thmNode has a letter
			if ("~=>&|".contains((String) thmNode.myItem)){ //thmNode has operator- expr must have same operator
				return false;
			}
			else{ //thmNode has variable
				
				if (subs.containsKey(thmNode.myItem)){
					return exprNode.toString().equals(subs.get(thmNode.myItem));
				}
				else{
					subs.put((String) thmNode.myItem,(String) exprNode.toString());
					return true;
				}
			}
		}
		
		else{ //did have the same thing- keep looking
			//both sides null- return true
			//either side not null- check that side
			boolean leftOk=true;
			boolean rightOk=true;
			if (thmNode.myLeft!=null){
				leftOk = matchesTheorem2(expr,exprNode.myLeft,thmNode.myLeft,subs);
			}
			if (thmNode.myRight!=null){
				rightOk = matchesTheorem2(expr,exprNode.myRight,thmNode.myRight,subs);
			}
			return (leftOk&&rightOk);
		}
	}
	
	
	
	
	
	public Iterator<ExpressionNode> allExpr() {
		return new ExprIterator();
	}

	private class ExprIterator implements Iterator<ExpressionNode> {
		// ExpressionNodes in the family are enumerated in preorder,
		// with children enumerated oldest first.
		// This exprIterator is Depth-first.

		private Stack<ExpressionNode> fringe = new Stack<ExpressionNode>();

		public ExprIterator() {
			if (myRoot != null) {
				fringe.push(myRoot);
			}
		}

		public boolean hasNext() {
			return !fringe.empty(); // check if there is something in fringe.
		}

		public ExpressionNode next() {
			if (!hasNext()) {
				throw new NoSuchElementException("tree ran out of elements");
			}

			// Be careful! index is one less than the size.
			ExpressionNode curExpr = fringe.pop();
			if (curExpr.myRight != null) {
				fringe.push(curExpr.myRight);
			}
			if (curExpr.myLeft != null) {
				fringe.push(curExpr.myLeft);
			}
			return curExpr;
		}

		public void remove() {
			// not used
		}
	} // end of depth first ExprIterator nested class
}
