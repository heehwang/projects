/*  Name: Hee Hwang
 UID: 804212513

 Others With Whom I Discussed Things: 
 Hesam Samimi, Jerry Wang

 Other Resources I Consulted: 
 piazza.com   */



import java.util.*;

// a type for arithmetic expressions
interface AExp {
    double eval(); 	                       // Problem 1a
    List<Sopn> toRPN(); 	               // Problem 1c
    Pair<List<Sopn>,Integer> toRPNopt();    // Problem 1d


}

enum Op {
    PLUS { public double calculate(double a1, double a2) { return a1 + a2; } },
    MINUS { public double calculate(double a1, double a2) { return a1 - a2; } },
    TIMES { public double calculate(double a1, double a2) { return a1 * a2; } },
    DIVIDE { public double calculate(double a1, double a2) { return a1 / a2; } };

    abstract double calculate(double a1, double a2);
}


/////////////////////////////////////////////////////////////////////////
//
// Problem 1a, 1c, and 1d
//
/////////////////////////////////////////////////////////////////////////

// create a Push class with new command, add that to the list
class Num implements AExp{
    private double d;
    public Num(double d1) { d = d1; }


    public double eval(){
	return d;
    }

    // Problem 1c: Implementation for toRPN() in Num class.
    public List<Sopn> toRPN(){
	List<Sopn> basicList = new LinkedList<Sopn>();
	basicList.add(new Push(d));
	    return basicList;
	} 

    // Problem 1d: Implementation for toRPNopt() in Num class.
    public Pair<List<Sopn>,Integer> toRPNopt(){
    	List<Sopn> optList = new LinkedList<Sopn>();
    	optList.add(new Push(d));
    	Pair<List<Sopn>,Integer> optPair = new Pair<List<Sopn>,Integer> (optList,1);
    	return optPair;
    }

}




class BinOp implements AExp{
    private AExp ex1, ex2;
    private Op o;
    private double val;
    private int stackCount = 0;
    public BinOp(AExp e1, Op o1, AExp e2){
	ex1 = e1; ex2 = e2; o=o1;
    }
    public double eval(){
	val = o.calculate(ex1.eval(),ex2.eval());
	return val;
    }
    // Problem 1c: Implementation for toRPN() in BinOp class.
    public List<Sopn> toRPN(){
	List<Sopn> basicList = new LinkedList<Sopn>();
	basicList.addAll(ex1.toRPN());
	basicList.addAll(ex2.toRPN()); 
	basicList.add(new Calculate(o));
	return basicList;
    }    

    //    Problem 1d: Implementation for toRPNopt() in BinOp class.
    public Pair<List<Sopn>,Integer> toRPNopt(){	
    	List<Sopn> optList = new LinkedList<Sopn>();
	Pair<List<Sopn>,Integer> leftPair = ex1.toRPNopt();
    	Pair<List<Sopn>,Integer> rightPair = ex2.toRPNopt();

    	if(leftPair.snd < rightPair.snd){
    	    rightPair.fst.addAll(leftPair.fst);
	    if ((o == Op.MINUS) || (o == Op.DIVIDE)){
		rightPair.fst.add(new Swap());
		rightPair.fst.add(new Calculate(o));
	    }
	    else rightPair.fst.add(new Calculate(o));
	    rightPair.snd++;
	    return rightPair;
    	}
    	else{
    	    leftPair.fst.addAll(rightPair.fst);
	    if ((o == Op.MINUS) || (o == Op.DIVIDE)){
		leftPair.fst.add(new Swap());
		leftPair.fst.add(new Calculate(o));
	    }
	    else leftPair.fst.add(new Calculate(o));
	    leftPair.snd++;
	    return leftPair;
	}
    }
}







/////////////////////////////////////////////////////////////////////////
//
// Problem 1b
//
/////////////////////////////////////////////////////////////////////////


// Sopn: a type for stack operations
interface Sopn {
    void eval(Stack<Double> s);
}

// Push: a method that put an element into the stack.
class Push implements Sopn{
    private double d; 
    public Push(double d1) { d = d1; }
    public void eval(Stack<Double> s){
	s.push(d);
    }	
    public String toString(){
	return "Push"+" "+d; // Overriding toString()
    }
}
// Swap: a method that changed the order of the elements in the stack.
class Swap implements Sopn{
    private double d1,d2;
    public void eval(Stack<Double> s){
	d1 = s.pop();
	d2 = s.pop();
	s.push(d1);
	s.push(d2);
    }
    public String toString(){
	return "Swap"; // Overriding toString()
    }
}
/* Calculate: a method that pops two elements from the stack, 
   apply an arithmetic operation, and push the result to the stack. */
class Calculate implements Sopn{
    private Op o; 
    private double d1,d2,val;
    public Calculate(Op o1) { o = o1; }
    public void eval(Stack<Double> s){
	d1 = s.pop();
	d2 = s.pop();
	val = o.calculate(d1,d2);
	s.push(val);
    }
    public String toString(){
	return "Calculate" + " " + o; // Overriding toString()
    }
}

// RPNExp: a list of Sopn objects
class RPNExp {
    private double val;
    public Sopn sopn1;
    protected List<Sopn> instrs;
    public RPNExp(List<Sopn> instrs) { this.instrs = instrs; }
    public Stack<Double> s = new Stack<Double>();
    public double eval() {
	for( int i = 0 ; i < instrs.size(); i++){
	    sopn1 = instrs.get(i);
	    sopn1.eval(s);
	}
	val = s.pop();
	return val;
    }  
}






class Pair<A,B> {
    protected A fst;
    protected B snd;

    Pair(A fst, B snd) { this.fst = fst; this.snd = snd; }

    A fst() { return fst; }
    B snd() { return snd; }
}






/////////////////////////////////////////////////////////////////////////
//
// CalcTest
//
/////////////////////////////////////////////////////////////////////////

 class CalcTest {
     public static void main(String[] args) {
 	    // a test for Problem 1a
 	 AExp aexp =
	     new BinOp(new BinOp(new Num(1.0), Op.PLUS, new Num(2.0)),
 		      Op.TIMES,
 	 	      new Num(3.0));
 	 System.out.println("aexp evaluates to " + aexp.eval()); 
	 // aexp evaluates to 9.0

	 //	a test for Problem 1b
	List<Sopn> instrs = new LinkedList<Sopn>();
	instrs.add(new Push(1.0));
	instrs.add(new Push(2.0));
	instrs.add(new Calculate(Op.PLUS));
	instrs.add(new Push(3.0));
	instrs.add(new Swap());
	instrs.add(new Calculate(Op.TIMES));
	RPNExp rpnexp = new RPNExp(instrs);

	System.out.println("rpnexp evaluates to " + rpnexp.eval());  
 	// rpnexp evaluates to 9.0

	// a test for Problem 1c
	System.out.println("aexp converts to " + aexp.toRPN());

	// a test for Problem 1d
	AExp aexp2 =
	     new BinOp(new Num(1.0), Op.MINUS, new BinOp(new Num(2.0), Op.PLUS, new Num(3.0)));
	 System.out.println("aexp2 optimally converts to " + aexp2.toRPNopt().fst());
	 System.out.println("aexp2 has stack size: " + aexp2.toRPNopt().snd());
  }
 }



/////////////////////////////////////////////////////////////////////////
//
// Problem 2a
//
/////////////////////////////////////////////////////////////////////////



// interface Dict<K,V> {
//     void put(K k, V v);
//     V get(K k);
// }

// class NotFoundException extends Exception {}


// class DictImpl2<K,V> implements Dict<K,V> {
//     protected Node<K,V> root;
//     DictImpl2() { root = new Empty<K,V>(); }

//     public void put(K k, V v) { 
// 	Entry l1 = new Entry();
// 	l1.add(k,v);
//     }

//     public V get(K k){
	
//     }
// }



// interface Node<K,V> {

// }

// class Empty<K,V> implements Node<K,V> {
//     Empty() {}

// }

// // Entry which is a linked list.
// class Entry<K,V> implements Node<K,V> {
//     protected K k;
//     protected V v;
//     protected Node<K,V> next;

//     Entry(K k, V v, Node<K,V> next) {
// 	this.k = k;
// 	this.v = v;
// 	this.next = next;
//     }
// }



// ////////////////////////////////////////////////////////////////////////////////
// //
// // Problem 2b
// //
// ////////////////////////////////////////////////////////////////////////////////




// interface DictFun<A,R> {
//     R invoke(A a);
// }


// class DictImpl3<K,V> implements Dict<K,V> {
//     protected DictFun<K,V> dFun; // old function object

//     DictImpl3() { }

//     public void put(K k, V v) {}

//     public V get(K k) {}
// }


// ////////////////////////////////////////////////////////////////////////////////
// //
// // DictTest
// //
// ////////////////////////////////////////////////////////////////////////////////


// class DictTest {
//     public static void main(String[] args) {

// 	    // a test for Problem 2a
// 	Dict<String,Integer> dict1 = new DictImpl2<String,Integer>();
// 	dict1.put("hello", 23);
// 	dict1.put("bye", 45);
// 	try {
// 	    System.out.println("bye maps to " + dict1.get("bye")); // prints 45
// 	    System.out.println("hi maps to " + dict1.get("hi"));  // throws an exception
// 	} catch(NotFoundException e) {
// 	    System.out.println("not found!");  // prints "not found!"
// 	}

// 	//	a test for Problem 2b
// 	Dict<String,Integer> dict2 = new DictImpl3<String,Integer>();
// 	dict2.put("hello", 23);
// 	dict2.put("bye", 45);
// 	try {
// 	    System.out.println("bye maps to " + dict2.get("bye"));  // prints 45
// 	    System.out.println("hi maps to " + dict2.get("hi"));   // throws an exception
// 	} catch(NotFoundException e) {
// 	    System.out.println("not found!");  // prints "not found!"
// 	}
//     }
// }


