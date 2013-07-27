import java.awt.Color;
import java.util.*;

public class Board {

	// We are not sure how to use this so we did not implement it.
	// We think we can catch our bugs using JUnit.
	public static boolean iAmDebugging = true;
	
	private Hashtable<String, Connector> myConnectors;
	private Hashtable<Connector, Color> myColors;
	private ArrayList<Connector> white;
	private ArrayList<Connector> red;
	private ArrayList<Connector> blue;
	
	private ArrayList<Connector> getColorArrayList(Color c){
		if(c == Color.RED){
			return red;
		} else if( c == Color.BLUE){
			return blue;
		} else{
			return white;
		}
	}
	
	// Initialize an empty board with no colored edges.
	public Board ( ) {
		// You fill this in.
		myConnectors = new Hashtable<String, Connector>();
		myColors = new Hashtable<Connector, Color>();
		white = new ArrayList<Connector>();
		red = new ArrayList<Connector>();
		blue = new ArrayList<Connector>();
		for(int k = 1; k < 6; k++){
			for(int l = k+1; l <= 6; l++){
				Connector cnctr = new Connector(k, l);
				myConnectors.put(""+k+l, cnctr);
				myColors.put(cnctr, Color.WHITE);
				white.add(cnctr);
			}
		}
	}
	
	// Add the given connector with the given color to the board.
	// Unchecked precondition: the given connector is not already chosen 
	// as RED or BLUE.
	public void add (Connector cnctr, Color c) {
		// You fill this in.
		cnctr = myConnectors.get(cnctr.toString());
		white.remove(cnctr);
		getColorArrayList(c).add(cnctr);
		myColors.put(cnctr, c);
	}
	
	// Set up an iterator through the connectors of the given color, 
	// which is either RED, BLUE, or WHITE. 
	// If the color is WHITE, iterate through the uncolored connectors.
	// No connector should appear twice in the iteration.  
	public Iterator<Connector> connectors (Color c) {
		// You fill this in.
		return getColorArrayList(c).iterator();
	}
	
	// Set up an iterator through all the 15 connectors.
	// No connector should appear twice in the iteration.  
	public Iterator<Connector> connectors ( ) {
		// You fill this in.
		return myConnectors.values().iterator();
	}
	
	// Return the color of the given connector.
	// If the connector is colored, its color will be RED or BLUE;
	// otherwise, its color is WHITE.
	public Color colorOf (Connector e) {
		// You fill this in.
		e = myConnectors.get(e.toString());
		return myColors.get(e);
	}
	
	// Unchecked prerequisite: cnctr is an initialized uncolored connector.
	// Let its endpoints be p1 and p2.
	// Return true exactly when there is a point p3 such that p1 is adjacent
	// to p3 and p2 is adjacent to p3 and those connectors have color c.
	public boolean formsTriangle (Connector cnctr, Color c) {
		// You fill this in.
		int pt1 = cnctr.endPt1();
		int pt2 = cnctr.endPt2();
		for(int i = 1; i <= 6; i++){
			if(i == pt1 || i == pt2){
				continue;
			}
			ArrayList<Connector> color = getColorArrayList(c);
			if(color.contains(new Connector(pt1, i)) && color.contains(new Connector(pt2, i))){
				return true;
			}
		}
		return false;
	}
	
	// The computer (playing BLUE) wants a move to make.
	// The board is assumed to contain an uncolored connector, with no 
	// monochromatic triangles.
	// There must be an uncolored connector, since if all 15 connectors are colored,
	// there must be a monochromatic triangle.
	// Pick the first uncolored connector that doesn't form a BLUE triangle.
	// If each uncolored connector, colored BLUE, would form a BLUE triangle,
	// return any uncolored connector.
	

	public Connector choice(){
		//make an ArrayList of possible moves
		ArrayList<Connector> Moves = new ArrayList<Connector>();
		for (int i = 0; i < white.size(); i++){
			Moves.add(white.get(i));	
		}
   	 
		//if possible, remove losing moves
		int j = 0;
		while(Moves.size() > 1 && j < Moves.size()){
			if (this.formsTriangle(Moves.get(j), Color.BLUE)){
				Moves.remove(j);
			} else{
				j++;
			}
		}
   	 
		//if possible, remove losing moves for opponent
		j = 0;
		while (Moves.size() > 1 && j < Moves.size()){
			if (this.formsTriangle(Moves.get(j), Color.RED)){
				Moves.remove(j);
			} else{
				j++;
			}
		}
   	 
		// if still possible, pick the outer edges
		// or the ones going through the center.
		j=0;
		while (Moves.size() > 1 && j < Moves.size()){
			if (Moves.get(j).endPt2() - Moves.get(j).endPt1() == 2 || Moves.get(j).endPt2() - Moves.get(j).endPt1() == 4){
				Moves.remove(j);
			} else{
				j++;
			}
		}
		// add any other strategies here
   	 
		//make a remaining move
		return Moves.get(0);
	}

	// Return true if the instance variables have correct and internally
	// consistent values.  Return false otherwise.
	// Unchecked prerequisites:
	//	Each connector in the board is properly initialized so that 
	// 	1 <= myPoint1 < myPoint2 <= 6.
	public boolean isOK ( ) {
		// You fill this in.
		
		// Check size of variables
		// Note: this if statement catches duplicates because
		// duplicates would push connector count over 15
		if(white.size()+red.size()+blue.size() != 15 || myConnectors.size() != 15 || myColors.size() != 15){
			return false;
		}
		
		// Check red and blue counts
		if(red.size() > 7 || blue.size() > 7 || red.size() < blue.size() || red.size() > blue.size()+1){
			return false;
		}
		
		Color[] colors = {Color.WHITE, Color.RED, Color.BLUE};
		for(int i = 0; i < colors.length; i++){
			Iterator<Connector> colorIter = this.connectors(colors[i]);
			while(colorIter.hasNext()){
				Connector next = colorIter.next();
				
				// Check consistency with hashtables
				if(!myConnectors.values().contains(next) || !myColors.keySet().contains(next)){
					return false;
				}
				
				// Check color consistency
				if(myColors.get(next) != colors[i]){
					return false;
				}
				
				// Check for triangles
				if(i > 0 && this.formsTriangle(next, colors[i])){
					return false;
				}
			}
		}
		
		// Check for duplicates when Board is initialized
		Iterator<Connector> Iter1 = this.connectors();
		while(Iter1.hasNext()){
			int count = 0;
			Iterator<Connector> Iter2 = this.connectors();
			Connector check = Iter1.next();
			while(Iter2.hasNext()){
				if(check.equals(Iter2.next())){
					count++;
				}
				if(count > 1){
					return false;
				}
			}
		}
		
		return true;
	}
}