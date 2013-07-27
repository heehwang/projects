public class BinaryTree {

	private TreeNode myRoot;

	public BinaryTree() {
		myRoot = null;
	}

	public BinaryTree(TreeNode t) {
		myRoot = t;
	}

	private static class TreeNode {

		public Object myItem;
		public TreeNode myLeft;
		public TreeNode myRight;

		public TreeNode(Object obj) {
			myItem = obj;
			myLeft = myRight = null;
		}

		public TreeNode(Object obj, TreeNode left, TreeNode right) {
			myItem = obj;
			myLeft = left;
			myRight = right;
		}
	}

	// Print the values in the tree in preorder: root value first,
	// then values in the left subtree (in preorder), then values
	// in the right subtree (in preorder).
	public void printPreorder() {
		printPreorder(myRoot);
		System.out.println();
	}

	private static void printPreorder(TreeNode t) {
		if (t != null) {
			System.out.print(t.myItem + " ");
			printPreorder(t.myLeft);
			printPreorder(t.myRight);
		}
	}

	// Print the values in the tree in inorder: values in the left
	// subtree first (in inorder), then the root value, then values
	// in the right subtree (in inorder).
	public void printInorder() {
		printInorder(myRoot);
		System.out.println();
	}

	private static void printInorder(TreeNode t) {
		if (t != null) {
			printInorder(t.myLeft);
			System.out.print(t.myItem + " ");
			printInorder(t.myRight);
		}
	}

	public void fillSampleTree1() {
		myRoot = new TreeNode("a", new TreeNode("b"), new TreeNode("c"));
	}

	public void fillSampleTree2() {
		myRoot = new TreeNode("a", new TreeNode("b", new TreeNode("d",
				new TreeNode("e"), new TreeNode("f")), null), new TreeNode("c"));
	}

	public void fillSampleTree3() {
		myRoot = new TreeNode("a", new TreeNode("b"), new TreeNode("c",
				new TreeNode("d", new TreeNode("e"), new TreeNode("f")), null));
	}

	private static void print(BinaryTree t, String description) {
		System.out.println(description + " in preorder");
		t.printPreorder();
		System.out.println(description + " in inorder");
		t.printInorder();
	}

	public static void main(String[] args) {
		BinaryTree t;
		t = new BinaryTree();
		// print(t, "the empty tree");
		// t.fillSampleTree1();
		// print(t, "sample tree 1");
		// t.fillSampleTree2();
		// print(t, "sample tree 2");
		t.fillSampleTree3();
		// print(t, "sample tree 3");
		t.print();
		// int n = t.height();
		// System.out.println(n);
	}

	public int height() {
		if (myRoot != null) {
			return heightHelper(myRoot);
		}
		return 0;
	}

	private static int heightHelper(TreeNode x) {
		if (x.myLeft == null && x.myRight == null) {
			return 1;
		} else {
			int bestSoFar = 1;
			if (x.myLeft != null) {
				TreeNode leftChild = x.myLeft;
				bestSoFar = Math.max(heightHelper(leftChild), bestSoFar);
			} else if (x.myRight != null) {
				TreeNode rightChild = x.myRight;
				bestSoFar = Math.max(heightHelper(rightChild), bestSoFar);
			}
			bestSoFar++;
			return bestSoFar;
		}
	}

	public void print() {
		if (myRoot != null) {
			printHelper(myRoot, 0);
		}
	}

	private static final String indent1 = "    ";

	private static void printHelper(TreeNode root, int indent) {
		if (root.myRight != null)
			printHelper(root.myRight, indent + 1);
		println(root.myItem, indent);
		if (root.myLeft != null)
			printHelper(root.myLeft, indent + 1);
	}

	private static void println(Object obj, int indent) {
		for (int k = 0; k < indent; k++) {
			System.out.print(indent1);
		}
		System.out.println(obj);
	}

	public static BinaryTree exprTree(String s) {
		BinaryTree result = new BinaryTree();
		result.myRoot = result.exprTreeHelper(s);
		return result;
	}

	// Return the tree corresponding to the given arithmetic expression.
	// The expression is legal, fully parenthesized, contains no blanks,
	// and involves only the operations + and *.
	private TreeNode exprTreeHelper(String expr) {
		if (expr.charAt(0) != '(') {
			return new TreeNode(expr); // you fill this in
		} else {
			// expr is a parenthesized expression.
			// Strip off the beginning and ending parentheses,
			// find the main operator (an occurrence of + or * not nested
			// in parentheses, and construct the two subtrees.
			int nesting = 0;
			int opPos = 0;
			for (int k = 1; k < expr.length() - 1; k++) {
				if (expr.charAt(k) == '(') {
					nesting++;
				}
				if (expr.charAt(k) == ')') {
					nesting--;
				}
				if (nesting == 0
						&& (expr.charAt(k) == '*' || expr.charAt(k) == '+')) {
					opPos = k;
					break;
				}
				// you fill this in
			}
			String opnd1 = expr.substring(1, opPos);
			String opnd2 = expr.substring(opPos + 1, expr.length() - 1);
			String op = expr.substring(opPos, opPos + 1);
			System.out.println("expression = " + expr);
			System.out.println("operand 1  = " + opnd1);
			System.out.println("operator   = " + op);
			System.out.println("operand 2  = " + opnd2);
			System.out.println();
			TreeNode left = exprTreeHelper(opnd1);
			TreeNode right = exprTreeHelper(opnd2);
			return new TreeNode(op, left, right);
			// you fill this in
		}
	}

}