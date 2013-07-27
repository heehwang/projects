import junit.framework.TestCase;

public class BinaryTreeTest extends TestCase {

	public void testHeight() {
		BinaryTree t;
		t = new BinaryTree();
		t.fillSampleTree3();
		assertEquals(t.height(), 3);
	}
}
