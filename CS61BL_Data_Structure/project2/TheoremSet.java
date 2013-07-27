
import java.util.*;

public class TheoremSet {

	Hashtable<String, Expression> theorems;

	public TheoremSet ( ) {
		theorems = new Hashtable<String, Expression>();
	}

	public Expression put (String s, Expression e) {
		theorems.put(s, e);
		return e;
	}

	public Expression get(String s){
		return theorems.get(s);
	}

	public boolean contains(String s){
		return theorems.keySet().contains(s);
	}
}
