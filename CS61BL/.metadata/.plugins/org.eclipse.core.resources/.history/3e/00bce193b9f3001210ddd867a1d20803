public class Person implements Comparable {
	private String myName;

	public Person(String name) {
		this.myName = name;
	}

	// return a String representation of the Person object
	public String toString() {
		return myName;
	}

	// Change the name of the person
	public void changeName(String newName) {
		this.myName = newName;
	}
	
	/*
	 * Returns a negative number if this is less than obj2. 
	 * Returns a positive number if this is greater than obj2. 
	 * Returns 0 if the two objects are equal
	 */
	public int compareTo(Object obj2) {
		Person otherPerson = (Person) obj2;
		return this.myName.compareTo(otherPerson.myName);
	}
	/*
	 * Returns a negative number if this is less than person2. 
	 * Returns a positive number if this is greater than person2. 
	 * Returns 0 if the two objects are equal
	 */
	public int compareTo(Person person2){
		return this.myName.compareTo(person2.myName);		
	}
	/*
	 * Returns true if the two objects are equal
	 */
	public boolean equals(Object obj2){
		Person otherPerson = (Person) obj2;
		return this.myName.equals(otherPerson.myName);
	}
	/*
	 * Returns true if the two objects are equal
	 */
	public boolean equals(Person person2){
		return this.myName.equals(person2.myName);
	}


}
