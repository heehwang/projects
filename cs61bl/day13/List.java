import java.util.Iterator;

public class List {

	private int myCount;
	private ListNode myHead;
	private ListNode myTail;

	public List() {
		myCount = 0;
		myHead = null;
		myTail = null;
	}

	public boolean isEmpty() {
		return myHead == null;
	}

	private static class ListNode {

		private Object myFirst;
		private ListNode myRest;

		private ListNode(Object first, ListNode rest) {
			myFirst = first;
			myRest = rest;
		}

		private ListNode(Object first) {
			myFirst = first;
			myRest = null;
		}
	}

	public String toString() {
		String rtn = "( ";
		for (ListNode p = myHead; p != null; p = p.myRest) {
			rtn = rtn + p.myFirst + " ";
		}
		return rtn + ")";
	}

	// Return the number of items in this list ("length" in Scheme).
	public int size() {
		return myCount;
	}

	// Return true if the list contains the object
	public boolean contains(Object obj) {
		for (ListNode p = myHead; p != null; p = p.myRest) {
			if (obj.equals(p.myFirst)) {
				return true;
			}
		}
		return false;
	}

	// Returns the element at the given position in this list.
	public Object get(int pos) {
		if (pos < 0) {
			throw new IllegalArgumentException(
					"Argument to get must be at least 0.");
		}
		if (pos >= size()) {
			throw new IllegalArgumentException("Argument to get is too large.");
		}
		int k = 0;
		for (ListNode p = myHead; p != null; p = p.myRest) {
			if (k == pos) {
				return p.myFirst;
			}
			k++;
		}
		return null;
	}

	public void addToFront(Object obj) {
		myHead = new ListNode(obj, myHead);
		myCount++;
	}

	public boolean equals(Object obj) {
		if (!obj.getClass().equals(List.class)) {
			return false;
		}

		if (size() != ((List) obj).size()) {
			return false;
		}
		ListNode p = myHead;
		ListNode q = ((List) obj).myHead;
		while (p != null) {
			if (!p.myFirst.equals(q.myFirst)) {
				return false;
			}
			p = p.myRest;
			q = q.myRest;
		}
		return true;
	}

	public void add(Object x) {
		if (this.isEmpty())
			myHead = new ListNode(x);
		else {
			ListNode p = myHead;
			while (p.myRest != null) {
				p = p.myRest;
			}
			ListNode last = new ListNode(x);
			p.myRest = last;
			this.myTail = p.myRest;
		}
		myCount++;
	}

	public void appendInPlace(List l) {
		if (!l.isEmpty()) {
			if (this.isEmpty()) {
				myHead = l.myHead;
			} else {
				ListNode p = myHead;
				while (p.myRest != null) {
					p = p.myRest;
				}
				p.myRest = l.myHead;
			}
		}
		this.myCount = this.myCount + l.myCount;
		this.myTail = l.myTail;
	}

	public Iterator elements() {
		return new ElementIterator();
	}

	public class ElementIterator implements Iterator {

		// State variable(s) to be provided.

		private ListNode p;

		public ElementIterator() {
			// to be provided
			p = myHead;
		}

		public boolean hasNext() {
			if (p == null)
				return false;
			else if (p.myRest == null)
				return false;
			else
				return true;
		}

		public Object next() {
			Object n = p.myFirst;
			p = p.myRest;
			return n;
		}

		public void remove() {
			// not used; do not implement
		}
	}

}
