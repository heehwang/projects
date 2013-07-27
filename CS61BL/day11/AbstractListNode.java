import java.util.NoSuchElementException;

///////////////////////////////////////////////////////////////////////////////
//
// ABSTRACT LIST NODE
//
///////////////////////////////////////////////////////////////////////////////

abstract public class AbstractListNode {
	abstract public Comparable first();

	abstract public AbstractListNode rest();

	abstract public boolean isEmpty();

	abstract public int size();

	abstract public Comparable get(int index);

	abstract public String toString();

	abstract public String toString(String currentString);

	abstract public boolean equals(AbstractListNode n);

	public Comparable smallest() {
		if (isEmpty()) {
			throw new NoSuchElementException(
					"can't find smallest in empty list");
		}
		return this.rest().smallestHelper(this.first());
	}

	public Comparable smallestHelper(Comparable smallestSoFar) {
		AbstractListNode n = this;
		if (isEmpty()) {
			return smallestSoFar;
		}
		return this.rest().smallestHelper(min(this.first(), smallestSoFar));
	}

	public static Comparable min(Comparable c1, Comparable c2) {
		if (c1.compareTo(c2) < 0) {
			return c1;
		} else {
			return c2;
		}
	}

	abstract public NonemptyListNode add(Comparable x);

	abstract public AbstractListNode append(AbstractListNode l);

	/*
	 * public AbstractListNode doubled_version1() { if (isEmpty()) { return
	 * this; } else { NonemptyListNode temp = new NonemptyListNode(first(),
	 * rest() .doubled_version1()); return new NonemptyListNode(first(), temp);
	 * }
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * public AbstractListNode doubled_version2() { if (isEmpty()) { return
	 * this; } else { NonemptyListNode temp = new NonemptyListNode(first(),
	 * rest() .doubled_version2()); setRest(temp); } }
	 */

}

// /////////////////////////////////////////////////////////////////////////////
//
// EMPTY LIST NODE
//
// /////////////////////////////////////////////////////////////////////////////

class EmptyListNode extends AbstractListNode {
	public EmptyListNode() {
	}

	public Comparable first() {
		throw new IllegalArgumentException(
				"There is no 'first' value stored in an EmptyListNode.");
	}

	public AbstractListNode rest() {
		throw new IllegalArgumentException(
				"No elements follow an EmptyListNode.");
	}

	public boolean isEmpty() {
		return true;
	}

	public int size() {
		return 0;
	}

	public Comparable get(int index) {
		throw new IllegalArgumentException("Out of Range");
	}

	public String toString() {
		return this.toString("");
	}

	public String toString(String currentString) {
		return "(" + currentString + " )";
	}

	public boolean equals(AbstractListNode n) {
		if (this.isEmpty() == n.isEmpty())
			return true;
		else
			return false;
	}

	/* OK */
	public NonemptyListNode add(Comparable x) {
		NonemptyListNode n = new NonemptyListNode(x);
		return n;
	}

	public AbstractListNode append(AbstractListNode l) {
		return l;
	};

}

// /////////////////////////////////////////////////////////////////////////////
//
// NONEMPTY LIST NODE
//
// /////////////////////////////////////////////////////////////////////////////

class NonemptyListNode extends AbstractListNode {

	private Comparable myFirst;
	private AbstractListNode myRest;

	// cons in Scheme.
	public NonemptyListNode(Comparable first, AbstractListNode rest) {
		myFirst = first;
		if (rest == null) {
			myRest = new EmptyListNode();
		} else {
			myRest = rest;
		}
	}

	public NonemptyListNode(Comparable first) {
		this(first, new EmptyListNode());
	}

	// car in Scheme.
	public Comparable first() {
		return myFirst;
	}

	// cdr in Scheme.
	public AbstractListNode rest() {
		return myRest;
	}

	public boolean isEmpty() {
		return false;
	}

	public int size() {
		return 1 + myRest.size();
	}

	public Comparable get(int index) {
		if (index + 1 > this.size())
			throw new IllegalArgumentException("Out of Range");
		else if (index == 0) {
			return myFirst;
		} else {
			index = index - 1;
			AbstractListNode l = this.myRest;
			return l.get(index);
		}
	}

	public String toString() {
		return this.toString("");
	}

	public String toString(String currentString) {
		return myRest.toString(currentString + " " + myFirst.toString());
	}

	public boolean equals(AbstractListNode n) {
		if (this.myFirst.equals(n.first()))
			return myRest.equals(n.rest());
		else
			return false;
	}

	public NonemptyListNode add(Comparable x) {
		NonemptyListNode n = new NonemptyListNode(myFirst, myRest.add(x));
		return n;

	}

	public AbstractListNode append(AbstractListNode l) {
		AbstractListNode n = new NonemptyListNode(myFirst, myRest.append(l));
		return n;
	};

}
