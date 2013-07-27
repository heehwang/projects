
import junit.framework.TestCase;

public class BinaryTreeTest extends TestCase{

	public void testHeight(){
		BinaryTree t = new BinaryTree();
		assertTrue(t.height() == 0);
		
		t.fillSampleTree1();
		assertTrue(t.height() == 2);
		
		t.fillSampleTree2();
		assertTrue(t.height() == 4);
		
		t.fillSampleTree3();
		assertTrue(t.height() == 4);
		
		t.fillSampleTree4();
		assertTrue(t.height() == 1);
		
		t.fillSampleTree5();
		assertTrue(t.height() == 5);
		
		t.fillSampleTree6();
		assertTrue(t.height() == 3);
		
		t.fillSampleTree7();
		assertTrue(t.height() == 3);
	}
	
	public void testIsCompletelyBalanced() {
		BinaryTree t = new BinaryTree();
		assertTrue(t.isCompletelyBalance());
		
		t.fillSampleTree1();
		assertTrue(t.isCompletelyBalance());
		
		t.fillSampleTree2();
		assertFalse(t.isCompletelyBalance());
		
		t.fillSampleTree3();
		assertFalse(t.isCompletelyBalance());
		
		t.fillSampleTree4();
		assertTrue(t.isCompletelyBalance());
		
		t.fillSampleTree5();
		assertFalse(t.isCompletelyBalance());
		
		t.fillSampleTree6();
		assertFalse(t.isCompletelyBalance());
		
		t.fillSampleTree7();
		assertTrue(t.isCompletelyBalance());
	}
	
	public void testIsOK(){
		BinaryTree t = new BinaryTree();
		assertTrue(t.isOK());
		t.fillSampleTree1();
		assertTrue(t.isOK());
		t.fillSampleTree2();
		assertTrue(t.isOK());
		t.fillSampleTree3();
		assertTrue(t.isOK());
		t.fillSampleTree4();
		assertTrue(t.isOK());
		t.fillSampleTree5();
		assertTrue(t.isOK());
		t.fillSampleTree6();
		assertTrue(t.isOK());
		t.fillSampleTree7();
		assertTrue(t.isOK());
		t.fillSampleTree8();
		assertFalse(t.isOK());
		t.fillSampleTree9();
		assertFalse(t.isOK());
	}
	
	public void testFibTree(){
		BinaryTree t = BinaryTree.fibTree(0);
		t.print();
		System.out.println();
		
		t = BinaryTree.fibTree(1);
		t.print();
		System.out.println();
		
		t = BinaryTree.fibTree(2);
		t.print();
		System.out.println();
		
		t = BinaryTree.fibTree(3);
		t.print();
		System.out.println();
		
		t = BinaryTree.fibTree(4);
		t.print();
		System.out.println();
		
		t = BinaryTree.fibTree(5);
		t.print();
		System.out.println();
	}
	
	public void testExpTreeHelper(){
		BinaryTree t = BinaryTree.exprTree("((a+(5*(a+b)))+(6*5))");
		t.print();
		
		t = BinaryTree.exprTree("(a+b)");
		t.print();
		
		t = BinaryTree.exprTree("(12*(4+20))");
		t.print();
	}
	
	public void testOptimize(){
		BinaryTree t = BinaryTree.exprTree("((a+(5*(9+1)))+(6*5))");
		System.out.println("original");
		t.print();
		System.out.println();
		System.out.println("optimize");
		t.optimize();
		t.print();
		System.out.println();

		
		t = BinaryTree.exprTree("(1+2)");
		System.out.println("original");
		t.print();
		System.out.println();
		System.out.println("optimize");
		t.optimize();
		t.print();
		System.out.println();

		t = BinaryTree.exprTree("(a+124)");
		System.out.println("original");
		t.print();
		System.out.println();
		System.out.println("optimize");
		t.optimize();
		t.print();
		System.out.println();

		t = BinaryTree.exprTree("((1+(394*b))+(5*((32+1)*(2+3))))");
		System.out.println("original");
		t.print();
		System.out.println();
		System.out.println("optimize");
		t.optimize();
		t.print();
		System.out.println();
		
		t = BinaryTree.exprTree("((1+(394*b))+(5*((32a+1)*(2+3))))");
		System.out.println("original");
		t.print();
		System.out.println();
		System.out.println("optimize");
		t.optimize();
		t.print();
		System.out.println();
	}
}
