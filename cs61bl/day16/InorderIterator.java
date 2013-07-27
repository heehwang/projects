// This code gets put inside the BinaryTree class.

// Method for the BinaryTree class
	public Iterator iterator(){
		return new InorderIterator();
	}

// Inner class inside of the BinaryTree class.
// Also, it uses java.util.Iterator and java.util.Stack.
private class InorderIterator implements Iterator {
  private Stack<TreeNode> nodeStack;
  private TreeNode currentNode;
  
  public InorderIterator ( ) {
    nodeStack = new Stack<TreeNode> ( );
    currentNode = myRoot;
  } // end default constructor
  
  public boolean hasNext ( ) {
    return !nodeStack.isEmpty() || (currentNode != null);
  } // end hasNext
  
  public Object next ( ) {
    TreeNode nextNode = null;
    
    // find leftmost node with no left child
    while (currentNode != null) {
      nodeStack.push(currentNode);
      currentNode = currentNode.myLeft;
    } // end while
    
    // get leftmost node, then move to its right subtree
    if (!nodeStack.isEmpty ( )) {
      nextNode = nodeStack.pop();
      assert nextNode != null; // since nodeStack was not empty
                               // before the pop
      currentNode = nextNode.myRight;
    } else {
      throw new NoSuchElementException();
    }
    return nextNode.myItem; 
  } // end next
  
  public void remove ( ) {
    throw new UnsupportedOperationException();
  } // end remove
} // end InorderIterator
