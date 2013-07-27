/* Sequence.java */

import java.util.*;

/** 
 * OVERVIEW:
 * This Sequence class implements a circular sequence in which
 * the item after the last is the first. 
 */

public class Sequence {

  // Initialize an empty sequence.
  public Sequence ( ) {
    mySize = 0;
    myHead = null;
  }

  // Return true if this list is empty, false otherwise.                                 
  public boolean isEmpty ( ) {
    return (mySize == 0);
  }
    
  // Return the number of elements in this list. 
  public int size ( ) {
    return mySize;
  }

    
  // Return a String representation of this list. 
  public String toString ( ) {
    if (isEmpty ( )) {
      return "[  ]";
    }
    String result = "[  " + myHead.myItem.toString ( ) + "  ";
    for (DListNode current = myHead.myNext;
         current != myHead;
         current = current.myNext) {
      result = result + current.myItem.toString ( ) + "  ";
    }
    return result + "]";
  }
  
  // Add the given object to the end of this list.
  public void addToSequence (Object toAdd) {
    if (isEmpty ( )) {
      myHead = new DListNode (toAdd);
    } else {
      // Insert toAdd between myHead and myHead.myPrev.
      DListNode newNode = new DListNode (toAdd, myHead.myPrev, myHead);
      // Link the new node into the list.
      myHead.myPrev.myNext = newNode;
      myHead.myPrev = newNode;
    }
    mySize++;
  }
  
  // Delete the object at the given position in the list.
  // Precondition: the given position is at least 0 and less than mySize.
  public void delete (int deletePos) {
    // *** You fill this in.
  }
  
  /* *** You substitute this for the existing version after supplying
     code for the Enumeration methods.
  public String toString ( ) {
    Enumeration enum = elements ( );
    String result = "[  ";
    while (enum.hasMoreElements ( )) {
      result = result + enum.nextElement ( ) + "  ";
    }
    result = result + "]";
    return result;
  } */
  
  // Return an initialized enumeration of the ring elements.  
  // This list must not be modified while the enumeration is in use.
  public Enumeration elements ( ) {
    return new SequenceEnumeration ( );
  }
  
  private class SequenceEnumeration implements Enumeration {
  
    /*
       OVERVIEW: This class provides an enumeration of the elements
       of the sequence.
     */
  
    // Initialize an enumeration object.
    public SequenceEnumeration ( ) {
      // *** You fill this in.
    }
    
    // Return true when there are more ring elements to enumerate;
    // return false otherwise.
    public boolean hasMoreElements ( ) {
      // *** You fill this in.
      return false;
    }
    
    // Return the next list element. 
    // Precondition: hasMoreElements.
    public Object nextElement ( ) {
      // *** You fill this in.
      return this; // You'll probably want to return something else.
    }
    
    // *** Your private variables go here.
  }
      
  private class DListNode {

    /* 
       OVERVIEW:
       DListNode is a class used internally by the Sequence class.  Each
       node in a Sequence is represented as a DListNode (Doubly linked List), 
       with an item and references to the previous and next node in the list. 
       The list is circular, so the last and first nodes contain references
       to each other.                   
    */
    
    public Object myItem;
    public DListNode myPrev;
    public DListNode myNext;

    // Initialize a DListNode with myItem obj and
    // myPrev and myNext both pointing to the new node.
    // (This produces a 1-element circular list.)  */
    public DListNode (Object obj) {
      myItem = obj;
      myPrev = this;
      myNext = this;
    }

    // Initialize a DListNode with myItem obj
    // and the given values for myPrev and myNext.
    public DListNode (Object obj, DListNode prev, DListNode next) {
      myItem = obj;
      myPrev = prev;
      myNext = next;
    }
  }

  // Invariant:
  // (myHead == null && mySize == 0)
  // || (myHead != null 
  //     && the number of nodes in the list pointed to by myHead == mySize
  //     && all the nodes are properly linked)

  private int mySize;	// number of elements in the sequence
  private DListNode myHead;	// pointer to the first element of the sequence
  
  public static void main (String [ ] args) {
	Sequence seq = new Sequence ( );
	seq.addToSequence ("a");
	System.out.println (seq);
	seq.addToSequence ("b");
	System.out.println (seq);
	seq.addToSequence ("c");
	System.out.println (seq);
	seq.addToSequence ("d");
	System.out.println (seq);
	seq.delete (3);
	System.out.println (seq + "; should be [a b c]");
	seq.delete (0);
	System.out.println (seq + "; should be [b c]");
	seq.delete (1);
	System.out.println (seq + "; should be [b]");
	seq.delete (0);
	System.out.println (seq + "; should be empty");
  }
}

