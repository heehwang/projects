
import java.util.*;
import junit.framework.TestCase;

public class ExpressionTest extends TestCase{

	private void checkNodes(String expr, String preorder) throws IllegalLineException{
		Expression e = new Expression(expr);
		Iterator iter = e.allExpr();
		if(preorder != null){
			String[] content = preorder.split(" ");
			for(int i = 0; i < content.length; i++){
				assertTrue(iter.hasNext());
				assertEquals(iter.next().toString(), content[i]);
			}
		}
		assertFalse(iter.hasNext());
	}

	public void testCorrectExprTree(){
		try{
			checkNodes(null, null);
			checkNodes("", null);
			checkNodes("p", "p");
			checkNodes("~p", "~ p");
			checkNodes("(p&q)", "& p q");
			checkNodes("(p|q)", "| p q");
			checkNodes("(p=>q)", "=> p q");
			checkNodes("((a&b)=>a)", "=> & a b a");
			checkNodes("(~~x=>x)", "=> ~ ~ x x");
			checkNodes("(((a|b)&~c)=>(a|b))", "=> & | a b ~ c | a b");
			checkNodes("(p=>(~p=>q))", "=> p => ~ p q");
			checkNodes("(((p=>q)=>q)=>((q=>p)=>p))", "=> => => p q q => => q p p");
		} catch(IllegalLineException e){
			System.err.println("this line should not be printed");
		}
	}

	public void testIncorrectExprTree(){
		String[] exprArgs = {"1", "&", "|", "!", "ab", "p&q"};
		for(int i = 0; i < exprArgs.length; i++){
			try{
				System.out.println(exprArgs[i]);
				checkNodes(exprArgs[i], null);
			} catch(IllegalLineException e){
				System.err.println(e.getMessage());
			}
		}
	}
}
