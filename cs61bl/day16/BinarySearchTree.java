public class BinarySearchTree extends BinaryTree {

	public boolean contains(Comparable key) {
		return containsHelper(key, myRoot);
	}

	public boolean containsHelper(Comparable key, TreeNode t) {
		if (myRoot == null) {
			return false;
		} else if (key.compareTo(t.myItem) == 0) {
			return true;
		} else if (key.compareTo(t.myItem) < 0) {
			return containsHelper(key, t.myLeft);
		} else {
			return containsHelper(key, t.myRight);
		}
	}

	public void add(Comparable key) {
		myRoot = add(myRoot, key);
	}

	private static TreeNode add(TreeNode t, Comparable key) {
		if (t == null) {
			return new TreeNode(key); // return the new leaf
		} else if (key.compareTo(t.myItem) < 0) {
			t.mySize++;
			t.myLeft = add(t.myLeft, key);
			return t;
		} else {
			t.mySize++;
			t.myRight = add(t.myRight, key);
			return t;
		}
	}

	{

	}

	public static void main(String[] args) {
		BinarySearchTree t = new BinarySearchTree();
		t.add("C");
		t.add("A");
		t.add("E");
		t.add("B");
		t.add("D");
		t.print();
	}

}