import junit.framework.TestCase;

public class AbstractListNodeTest extends TestCase {

	AbstractListNode l4 = new EmptyListNode();
	AbstractListNode l3 = new NonemptyListNode(new Integer(5), l4);
	AbstractListNode l2 = new NonemptyListNode(new Integer(3), l3);
	AbstractListNode l1 = new NonemptyListNode(new Integer(1), l2);

	AbstractListNode m4 = new EmptyListNode();
	AbstractListNode m3 = new NonemptyListNode(new Integer(6), m4);
	AbstractListNode m2 = new NonemptyListNode(new Integer(5), m3);
	AbstractListNode m1 = new NonemptyListNode(new Integer(4), m2);

	AbstractListNode n4 = new EmptyListNode();
	AbstractListNode n3 = new NonemptyListNode(new Integer(3), n4);
	AbstractListNode n2 = new NonemptyListNode(new Integer(2), n3);
	AbstractListNode n1 = new NonemptyListNode(new Integer(1), n2);

	AbstractListNode e0 = new EmptyListNode();

	public void testEfirst() {
		try {
			l4.first();
			fail("Should not reach this code");
		} catch (IllegalArgumentException ex1) {
			String m1 = ex1.getMessage();
			assertEquals(m1,
					"There is no 'first' value stored in an EmptyListNode.");
		}
	}

	public void testErest() {
		try {
			l4.rest();
		} catch (IllegalArgumentException ex2) {
			String m2 = ex2.getMessage();
			assertEquals(m2, "No elements follow an EmptyListNode.");
		}
	}

	public void testEisEmpty() {
		assertTrue(l4.isEmpty());
	}

	public void testNfirst() {
		assertEquals(l1.first(), 1);
	}

	/*
	 * public void testNrest(){ assertEquals(l1.rest(),null); }
	 */
	public void testNisEmpty() {
		assertFalse(l1.isEmpty());
	}

	public void testValues() {
		assertEquals(l1.first(), 1);
		assertEquals(l2.first(), 3);
		assertEquals(l3.first(), 5);
	}

	public void testSize() {
		assertEquals(l1.size(), 3);
	}

	public void testGet() {
		assertEquals(l1.get(1), 3);
		assertEquals(l1.get(0), 1);
	}

	public void testToString() {
		assertEquals(l1.toString(), "( 1 3 5 )");
	}

	public void testEquals() {
		assertTrue(l1.equals(l1));
		assertFalse(l1.equals(m1));

	}

	public void testSmallest() {
		assertEquals(l1.smallest(), 1);
	}

	public void testAdd() {
		AbstractListNode l1 = new EmptyListNode();
		AbstractListNode l2 = l1.add("a");
		assertEquals("( a )", l2.toString());
		AbstractListNode l3 = l2.add("b");
		assertEquals("( a b )", l3.toString());
	}

	public void testAppend() {
		assertEquals(e0.append(m1).toString(), "( 4 5 6 )");
		assertEquals(n1.append(e0).toString(), "( 1 2 3 )");
		assertEquals(n1.append(m1).toString(), "( 1 2 3 4 5 6 )");
		assertEquals(n1.append(n1).toString(), "( 1 2 3 1 2 3 )");
	}
}
