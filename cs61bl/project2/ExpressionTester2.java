//import org.junit.Assert.*;
import junit.framework.TestCase;

import java.util.*;

public class ExpressionTester2 extends TestCase {
	// changed tests to use constructor instead of exprTree
	// note that tests need a try/catch structure to compile
	// because constructor can throw an exception
	// (or just test with exprTree instead)
	// also note that testExpressionTreeInvalidOnlyNot should fail

	/*public void testConstrution1() {
		Expression x = new Expression();
		x.fillSampleTree4();// currently has fillSampleTree 1 through 4
		x.print();
	}*/

	/*public void testExpressionTreeAndOr() {
		System.out.println();
		try{
			Expression x = new Expression("((a&b)|y)");
			x.print();
		}catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
		
	}

	public void testExpressionTreeNot1() {
		System.out.println();
		try {
			Expression x = new Expression("~y");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}

	public void testExpressionTreeNot2() {
		System.out.println();
		try {
			Expression x = new Expression("(~y&w)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}

	public void testExpressionTreeNot2Reverse() {
		System.out.println();
		try {
			Expression x = new Expression("(w&~y)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}

	public void testExpressionTreeImplies() {
		System.out.println();
		try {
			Expression x = new Expression("(w=>y)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}

	public void testExpressionTreeImpliesNot() {
		System.out.println();
		try {
			Expression x = new Expression("(~a=>(~b=>~(a|b)))");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}*/

	/*public void testExpressionTreeInvalidOnlyNot() {
		System.out.println();
		try {
			Expression x = new Expression("~");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testInvalidUnmatched() {
		System.out.println();
		try {
			Expression x = new Expression("(y&a))");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testInvalidChar() {
		System.out.println();
		try {
			Expression x = new Expression("(2&r)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testInvalidChar2() {
		System.out.println();
		try {
			Expression x = new Expression("( &r)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testInvalidChar3() {
		System.out.println();
		try {
			Expression x = new Expression("(x|>=)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*public void testInvalidDoubleLetter() {
		System.out.println();
		try {
			Expression x = new Expression("(x|no)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testInvalidMissingArgOr() {
		System.out.println();
		try {
			Expression x = new Expression("(x|)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testInvalidMissingArgAnd() {
		System.out.println();
		try {
			Expression x = new Expression("(x&)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testInvalidMissingArgImplies() {
		System.out.println();
		try {
			Expression x = new Expression("(x=>)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testInvalidExtraArgNot() {
		System.out.println();
		try {
			Expression x = new Expression("(x~u)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testInvalidNeedsParentheses() {
		System.out.println();
		try {
			Expression x = new Expression("(x&~b|u)");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testInvalidOnlyNot() {
		System.out.println();
		try {
			Expression x = new Expression("&");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testInvalidOnlyOr() {
		System.out.println();
		try {
			Expression x = new Expression("|");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testInvalidOnlyImplies() {
		System.out.println();
		try {
			Expression x = new Expression("=>");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testInvalidAndSomething() {
		System.out.println();
		try {
			Expression x = new Expression("y&t");
			x.print();
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	*/
	
	/*public void testEqual1() {
		System.out.println();
		try{
			Expression E1 = new Expression("a");
			Expression E2 = new Expression("a");
			assertTrue(E1.equals(E2));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testEqual2() {
		System.out.println();
		try{
			Expression x = new Expression("((a&b)|~c)");
			Expression y = new Expression("((a&b)|~c)");
			assertTrue(x.equals(y));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testEqual3() {
		System.out.println();
		try{
			Expression x = new Expression("(~((a=>b)|~c)&d)");
			Expression y = new Expression("(~((a=>b)|~c)&d)");
			assertTrue(x.equals(y));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testEqual4() {
		System.out.println();
		Expression x = new Expression();
		Expression y = new Expression();
		assertTrue(x.equals(y));
	}
	*/
	
	public void testNotEqual() {//note that x and y are different
		System.out.println();
		try{
			Expression x = new Expression("x");
			Expression y = new Expression("y");
			assertFalse(x.equals(y));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testNotEqual2() {
		System.out.println();
		try{
			Expression x = new Expression("(~((a=>b)|~c)&d)");
			Expression y = new Expression("(~((a=>r)|~c)&d)");
			assertFalse(x.equals(y));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testNotEqual3() {
		System.out.println();
		try{
			Expression x = new Expression("x");
			Expression y = new Expression();
			assertFalse(x.equals(y));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testNotEqual4() {
		System.out.println();
		try{
			Expression x = new Expression("x");
			Expression y = new Expression("(y&x)");
			assertFalse(x.equals(y));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceChar() {
		System.out.println();
		try{
			Expression E1 = new Expression("x");
			Expression E2 = new Expression("y");
			Expression E3 = Expression.replace(E1,"x","y");
			//E3.print();
			assertTrue(E2.equals(E3));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceChar2() {
		System.out.println();
		try{
			Expression E1 = new Expression("(a|b)");
			Expression E2 = new Expression("(a|d)");
			Expression E3 = Expression.replace(E1,"b","d");
			//E3.print();
			assertTrue(E2.equals(E3));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceChar3() {
		System.out.println();
		try{
			Expression E1 = new Expression("((a&b)|~c)");
			Expression E2 = new Expression("((a&d)|~c)");
			Expression E3 = Expression.replace(E1,"b","d");
			//E3.print();
			assertTrue(E2.equals(E3));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceChar4() {
		System.out.println();
		try{
			Expression E1 = new Expression("((((a&a)&a)&a)&a)");
			Expression E2 = new Expression("((((b&b)&b)&b)&b)");
			Expression E3 = Expression.replace(E1,"a","b");
			//E3.print();
			assertTrue(E2.equals(E3));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testReplaceNodes11() {
		System.out.println();
		try{
			Expression E1 = new Expression("a");
			Expression E2 = new Expression("b");
			Expression E3 = Expression.replaceNodes1(E1,
					E1.myRoot(),E2.myRoot() );
			//E3.print();
			assertTrue(E2.equals(E3));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceNodes12() {
		System.out.println();
		try{
			Expression E1 = new Expression("((((a&a)&a)&a)&a)");
			Expression E2 = new Expression("((((b&b)&b)&b)&b)");
			Expression A = new Expression("a");
			Expression B = new Expression("b");
			Expression E3 = Expression.replaceNodes1(E1,
					A.myRoot(),B.myRoot() );
			//E3.print();
			assertTrue(E2.equals(E3));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceNodes13() {
		System.out.println();
		try{
			Expression E1 = new Expression("((a&b)|~a)");
			Expression E2 = new Expression("(((x|y)&b)|~(x|y))");
			Expression A = new Expression("a");
			Expression B = new Expression("(x|y)");
			Expression E3 = Expression.replaceNodes1(E1,
					A.myRoot(),B.myRoot() );
			//E3.print();
			assertTrue(E2.equals(E3));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testReplaceNodes14() {
		System.out.println();
		try{
			Expression E2 = new Expression("((a&b)|~a)");
			Expression E1 = new Expression("(((x|y)&b)|~(x|y))");
			Expression B = new Expression("a");
			Expression A = new Expression("(x|y)");
			Expression E3 = Expression.replaceNodes1(E1,
					A.myRoot(),B.myRoot() );
			//E3.print();
			assertTrue(E2.equals(E3));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceNodes21() {
		System.out.println();
		try{
			Expression E1 = new Expression("a");
			Expression E2 = new Expression("b");
			Expression.replaceNodes2(E1,E1.myRoot(),E2.myRoot() );
			//E1.print();
			assertTrue(E2.equals(E1));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceNodes22() {
		System.out.println();
		try{
			Expression E1 = new Expression("((((a&a)&a)&a)&a)");
			Expression E2 = new Expression("((((b&b)&b)&b)&b)");
			Expression A = new Expression("a");
			Expression B = new Expression("b");
			Expression.replaceNodes2(E1,
					A.myRoot(),B.myRoot() );
			//E1.print();
			assertTrue(E2.equals(E1));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceNodes23() {
		System.out.println();
		try{
			Expression E1 = new Expression("((a&b)|~a)");
			Expression E2 = new Expression("(((x|y)&b)|~(x|y))");
			Expression A = new Expression("a");
			Expression B = new Expression("(x|y)");
			Expression.replaceNodes2(E1,
					A.myRoot(),B.myRoot() );
			//E1.print();
			assertTrue(E2.equals(E1));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testReplaceNodes24() {
		System.out.println();
		try{
			Expression E2 = new Expression("((a&b)|~a)");
			Expression E1 = new Expression("(((x|y)&b)|~(x|y))");
			Expression B = new Expression("a");
			Expression A = new Expression("(x|y)");
			Expression.replaceNodes2(E1,
					A.myRoot(),B.myRoot() );
			//E1.print();
			assertTrue(E2.equals(E1));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testReplaceNodes31() {
		System.out.println();
		try{
			Expression E1 = new Expression("a");
			Expression E2 = new Expression("b");
			E1.replaceNodes3(E1.myRoot(),E2.myRoot() );
			//E1.print();
			assertTrue(E2.equals(E1));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceNodes32() {
		System.out.println();
		try{
			Expression E1 = new Expression("((((a&a)&a)&a)&a)");
			Expression E2 = new Expression("((((b&b)&b)&b)&b)");
			Expression A = new Expression("a");
			Expression B = new Expression("b");
			E1.replaceNodes3(A.myRoot(),B.myRoot() );
			//E1.print();
			assertTrue(E2.equals(E1));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testReplaceNodes33() {
		System.out.println();
		try{
			Expression E1 = new Expression("((a&b)|~a)");
			Expression E2 = new Expression("(((x|y)&b)|~(x|y))");
			Expression A = new Expression("a");
			Expression B = new Expression("(x|y)");
			E1.replaceNodes3(A.myRoot(),B.myRoot() );
			//E1.print();
			assertTrue(E2.equals(E1));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testReplaceNodes34() {
		System.out.println();
		try{
			Expression E2 = new Expression("((a&b)|~a)");
			Expression E1 = new Expression("(((x|y)&b)|~(x|y))");
			Expression B = new Expression("a");
			Expression A = new Expression("(x|y)");
			E1.replaceNodes3(A.myRoot(),B.myRoot() );
			//E1.print();
			assertTrue(E2.equals(E1));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testToString() {
		System.out.println();
		try{
			Expression E = new Expression("((a&b)|~a)");
			//E.print();
			//System.out.println(E.toString());
			assertEquals(E.toString(),"((a&b)|~a)");
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testToString2() {
		System.out.println();
		String s="(((~(a&~a)=>a)&~a)|a)";
		try{
			Expression E = new Expression(s);
			//E.print();
			//System.out.println(E.toString());
			assertEquals(E.toString(),s);
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testmatchesTheoremTrueSame(){
		try{
			Expression expr = new Expression("(~~a=>a)");
			Expression thm = new Expression("(~~b=>b)");
			assertTrue(Expression.matchesTheorem(expr,thm));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testmatchesTheoremTrueSimple(){
		try{
			Expression expr = new Expression("(~~(p|q)=>(p|q))");
			Expression thm = new Expression("(~~b=>b)");
			assertTrue(Expression.matchesTheorem(expr,thm));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testmatchesTheoremTrueOverlap(){
		try{
			Expression expr = new Expression("(((c&m)|c)=>(c&m))");
			Expression thm = new Expression("((a|b)=>a)");
			assertTrue(Expression.matchesTheorem(expr,thm));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testmatchesTheoremTrueSimple2(){
		try{
			Expression expr= new Expression("((~f|(x&y))=>((x&y)|~f))");
			Expression thm = new Expression("((a|b)=>(b|a))");
			assertTrue(Expression.matchesTheorem(expr,thm));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testmatchesTheoremDoesntGoBackward(){
		try{
			Expression expr = new Expression("((a|b)=>(b|a))");
			Expression thm = new Expression("((~f|(x&y))=>((x&y)|~f))");
			assertFalse(Expression.matchesTheorem(expr,thm));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testmatchesTheoremFalseDifferentOperator(){
		try{
			Expression expr = new Expression("(a=>a)");
			Expression thm = new Expression("(b&b)");
			assertFalse(Expression.matchesTheorem(expr,thm));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testmatchesTheoremTrueDoubleLetters(){
		try{
			Expression expr = new Expression("((c|d)=>(d|c))");
			Expression thm = new Expression("((a|b)=>(b|a))");
			assertTrue(Expression.matchesTheorem(expr,thm));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void testmatchesTheoremFalseDoubleLetters(){
		try{
			Expression expr = new Expression("((c|d)=>(c|c))");
			Expression thm = new Expression("((a|b)=>(b|a))");
			assertFalse(Expression.matchesTheorem(expr,thm));
		} catch (IllegalLineException e) {
			System.out.println(e.getMessage());
		}
	}
	
}
