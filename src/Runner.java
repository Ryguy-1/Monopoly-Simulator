import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

public class Runner {
	
	public final int NUMBEROFSIMULATIONS = 100000;
	
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
	Simulate[] simulations = new Simulate[NUMBEROFSIMULATIONS];
	public final int PLAYERS = 5;
	public final int NUMDICE = 2;
	public final int LAPSAROUND = 10;
	public int movesTotal = 0;
	public int MOVESTOTALCALC = 0;
public static void main(String[] args) {
	
	Runner r = new Runner();
	//initializes each space with its name, number of times landed (0 so far), and place on board (Mediterranean = 0, GO = 39)
	for (int i = 0; i < r.namesOfSpaces.length; i++) {
		r.squares[i] = new Square(r.namesOfSpaces[i], 0, i);
	}

	int simulationsRun = 0;
	
	BigDecimal[] averagedPercents = new BigDecimal[r.squares.length];
	
	
	//RUNS ALL SIMULATIONS
	System.out.println("Running simulations...");
	for (int i = 0; i < r.simulations.length; i++) {
		System.out.println("Running Simulation " + simulationsRun);
		r.simulations[i] = new Simulate(r.squares, r.PLAYERS, r.NUMDICE, r.numsPerDice, r.chanceCards, r.communityChestCards, r.movesTotal, r.LAPSAROUND);
		r.simulations[i].runSimulation();
		
		r.MOVESTOTALCALC += r.simulations[i].getMovesTotal();
		
		simulationsRun++;
	}
	System.out.println("Simulations done.");

	for (int i = 0; i < r.squares.length; i++) {
		averagedPercents[i] = truncateDecimal((double)r.squares[i].getTimesLanded()/(double)r.MOVESTOTALCALC, 4).multiply(new BigDecimal(100));
	}
	
	String endString = "";

	for (int i = 0; i < r.squares.length; i++) {
		endString+=r.squares[i].getName() + " : " + averagedPercents[i] + "%\n";
	}

	try {
		FileWriter fw = new FileWriter("src/results.txt");
		fw.write(endString);
		
		fw.close();
	} 	catch (IOException e) {
		e.printStackTrace();
	}
	
	
	
	
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
































//FORBIDDEN CODE



//System.out.println(arrayOfSquareArrays[0].length);
//System.out.println(arrayOfSquareArrays.length);

//sets timesPerSquareTotal each value to how many times landed on throughout all simulations.
//for (int i = 0; i < arrayOfSquareArrays.length; i++) {
//	for (int j = 0; j < arrayOfSquareArrays[i].length; j++) {
//		timesPerSquareTotal[i] = timesPerSquareTotal[i] + arrayOfSquareArrays[i][j].getTimesLanded();
//		System.out.println(arrayOfSquareArrays[i][j].getTimesLanded());
//	}
//}
//
//System.out.println(timesPerSquareTotal[4]);
//
//changes all to percents



	//may work still in progress
//	Square[] squaresReturned = r.simulations[i].getSquareArray();
//	int[] numbersLandedInOrder = new int[r.squares.length];
//	BigDecimal[] percents = new BigDecimal[r.squares.length];
//	for (int j = 0; j < squaresReturned.length; j++) {
//		numbersLandedInOrder[j] = squaresReturned[j].getTimesLanded();
//	}
//	
//	//1 round of percents
//	for (int j = 0; j < percents.length; j++) {
//		percents[j] = truncateDecimal((double)squaresReturned[j].getTimesLanded()/(double)r.simulations[i].getMovesTotal(), 4).multiply(new BigDecimal(100));
//	}
//	
//	
//}

//FILE WRITING

//try {
//	FileWriter fw = new FileWriter("src/results.txt", true);
//	fw.write(r.squares[j].getName() + " : " + percents[j]);
//		
//	fw.close();
//} catch (IOException e) {
//	e.printStackTrace();
//}
//


//TEST SIMULATION BELOW TO CHECK A SINGLE ONE

//Simulate simulate1 = new Simulate(r.squares, r.PLAYERS, r.NUMDICE, r.numsPerDice, r.chanceCards, r.communityChestCards, r.movesTotal, r.LAPSAROUND);
//
//simulate1.runSimulation();
//
//Square[] returnedArray = simulate1.getSquareArray();
//int returnedMovesTotal = simulate1.getMovesTotal();
//
//BigDecimal percent = truncateDecimal((double)returnedArray[37].getTimesLanded()/(double)returnedMovesTotal, 4);
//
//  BigDecimal realPercent = percent.multiply(new BigDecimal(100));
//
//
//System.out.println("The spot "+ returnedArray[37].getName() + " was landed on " + returnedArray[37].getTimesLanded() + " times for a probability of " + realPercent + "% of the time");
//



