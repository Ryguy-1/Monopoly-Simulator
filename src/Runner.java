import java.math.BigDecimal;

public class Runner {
	 public final int numsPerDice = 6;
	 String[] namesOfSpaces = {"Mediterranean Avenue", "Community Chest", "Baltic Avenue", "Income Tax", "Reading Railroad", 
			"Oriental Avenue", "Chance", "Vermont Avenue", "Connecticut Avenue", "Jail", "St. Charles Place", 
			"Electric Company", "States Avenue", "Virginia Avenue", "Pennsylvania Railroad", "St.James Place", "Community Chest", 
			"Tennessee Avenue", "New York Avenue", "Free Parking", "Kentucky Avenue", "Chance", "Indiana Avenue", "Illinois Avenue", 
			"B.& O. Railroad", "Atlantic Avenue", "Ventnor Avenue", "Water Works", "Marvin Gardens", "Go To Prison", "Pacific Avenue", 
			"North Carolina Avenue", "Community Chest", "Pennsylvania Avenue", "Short Line Railroad", "Chance", "Park Place", 
			"Luxary Tax", "Boardwalk", "GO" };
	 String[] chanceCards = {"Advance To GO", "Advance To Illinois Avenue", "Advance To St. Charles Place", "Advance Token To Nearest Utility",
			 "Advance Token To The Nearest Railroad", "Null", "Null", "Go Back 3 Spaces", "Go To Jail", "Null", "Null", 
			 "Go To Reading Railroad", "Go To Boardwalk", "Null", "Null", "Null"};
	 String[] communityChestCards = {"Null", "Null", "Null", "Null", "Null", "Advance To GO", "Null", "Null", "Null", "Null", "Go To Jail", "Null", "Null", "Null", "Null", "Null"};
	Square[] squares = new Square[namesOfSpaces.length];
	public final int PLAYERS = 5;
	public final int NUMDICE = 2;
	public final int LAPSAROUND = 30;
	public int movesTotal = 0;
public static void main(String[] args) {
	
	Runner r = new Runner();
	System.out.println(r.namesOfSpaces.length);
	//initializes each space with its name, number of times landed (0 so far), and place on board (Mediterranean = 0, GO = 39)
	for (int i = 0; i < r.namesOfSpaces.length; i++) {
		r.squares[i] = new Square(r.namesOfSpaces[i], 0, i);
	}
	System.out.println(r.squares[39].getName());
	System.out.println(r.squares[39].getPlaceOnBoard());
	System.out.println("tried to run");
	Simulate simulate1 = new Simulate(r.squares, r.PLAYERS, r.NUMDICE, r.numsPerDice, r.chanceCards, r.communityChestCards, r.movesTotal, r.LAPSAROUND);
	
	simulate1.runSimulation();
	
	Square[] returnedArray = simulate1.getSquareArray();
	int returnedMovesTotal = simulate1.getMovesTotal();
	
	BigDecimal percent = truncateDecimal((double)returnedArray[37].getTimesLanded()/(double)returnedMovesTotal, 4);
	
	  BigDecimal realPercent = percent.multiply(new BigDecimal(100));
	
	
	System.out.println("The spot "+ returnedArray[37].getName() + " was landed on " + returnedArray[37].getTimesLanded() + " times for a probability of " + realPercent + "% of the time");
	
	
}
private static BigDecimal truncateDecimal(double x,int numberofDecimals)
{
    if ( x > 0) {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
    } else {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
    }
}


}