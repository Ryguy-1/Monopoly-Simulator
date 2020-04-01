
public class Square {
	String name;
	int timesLanded;
	int placeOnBoard;
	
	Square(String name, int timesLanded, int placeOnBoard){
		this.name = name;
		this.timesLanded = timesLanded;
		this.placeOnBoard = placeOnBoard;

	}
	
	public int getTimesLanded() {
		return timesLanded;
	}
	
	public void addLanded() {
		timesLanded+=1;
	}
	
	public int getPlaceOnBoard() {
		return placeOnBoard;
	}
	
	public String getName() {
		return name;
	}

}
