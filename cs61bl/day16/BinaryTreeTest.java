
import java.util.*;
import junit.framework.TestCase;

public class BinaryTreeTest extends TestCase{
	
	private ArrayList makeArrayList(String s){
		ArrayList a = new ArrayList();
		String[] content = s.split(" ");
		for(int i = 0; i < content.length; i++){
			a.add(content[i]);
		}
		return a;
	}
	
	public void testConstructor() {
		ArrayList preorder = makeArrayList("A B C D E F");		
		ArrayList inorder = makeArrayList("B A E D F C");
		BinaryTree t = new BinaryTree(preorder, inorder);
		Iterator iter = t.iterator();
		for(int i = 0; iter.hasNext(); i++){
			assertEquals(inorder.get(i), iter.next());
		}
		assertFalse(iter.hasNext());
		
		preorder = makeArrayList("A B C D");
		inorder = makeArrayList("A B C D");
		t = new BinaryTree(preorder, inorder);
		iter = t.iterator();
		for(int i = 0; iter.hasNext(); i++){
			assertEquals(inorder.get(i), iter.next());
		}
		assertFalse(iter.hasNext());
		
		preorder = makeArrayList("A B C D");
		inorder = makeArrayList("D C B A");
		t = new BinaryTree(preorder, inorder);
		iter = t.iterator();
		for(int i = 0; iter.hasNext(); i++){
			assertEquals(inorder.get(i), iter.next());
		}
		assertFalse(iter.hasNext());
		
		preorder = makeArrayList("A");
		inorder = makeArrayList("A");
		t = new BinaryTree(preorder, inorder);
		iter = t.iterator();
		for(int i = 0; iter.hasNext(); i++){
			assertEquals(inorder.get(i), iter.next());
		}
		assertFalse(iter.hasNext());
	}

}
