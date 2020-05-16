
//import java.io.FileWriter;
//import java.io.IOException;
import java.math.BigDecimal;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;



@SuppressWarnings("deprecation")
public class Runner extends Applet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -245696510215218336L;

	public int NUMBEROFSIMULATIONS = 1000000;
	
	public String link = "https://1drv.ms/x/s!Ap8aKWXu4H4R5V9fpGcgSpYmp1X8?e=wjsd1M";
	
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
	Simulate[] simulations;
	public int PLAYERS = 5;
	public int NUMDICE = 2;
	public int LAPSAROUND = 10;
	public int movesTotal = 0;
	public int MOVESTOTALCALC = 0;
	
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();

	JTextArea output;
	
	JLabel gif = new JLabel();
	
	
	Font font1 = new Font("SansSerif", Font.BOLD, 30);
	
	Font font2 = new Font("SansSerif", Font.BOLD, 14);
	
	
	
	
	
	
	
public static void main(String[] args) {
	
	Runner r = new Runner();

	String playersInput = JOptionPane.showInputDialog("How many players are in your game: ");
	String lapsInput = JOptionPane.showInputDialog("How many laps do you predict to go around: ");
	String simsInput = JOptionPane.showInputDialog("How many simulations do you wish to run: ");
	
	r.PLAYERS = Integer.parseInt(playersInput);
	r.LAPSAROUND = Integer.parseInt(lapsInput);
	r.NUMBEROFSIMULATIONS = Integer.parseInt(simsInput);
	
	
	r.simulations = new Simulate[r.NUMBEROFSIMULATIONS];
	
	r.start();
	
	
	
	
	
	
	
	
	
	
	//initializes each space with its name, number of times landed (0 so far), and place on board (Mediterranean = 0, GO = 39)
	for (int i = 0; i < r.namesOfSpaces.length; i++) {
		r.squares[i] = new Square(r.namesOfSpaces[i], 0, i);
	}

//	int simulationsRun = 0;
	
	BigDecimal[] averagedPercents = new BigDecimal[r.squares.length];
	
	
	//RUNS ALL SIMULATIONS
	System.out.println("Running simulations...");
	for (int i = 0; i < r.simulations.length; i++) {
		//System.out.println("Running Simulation " + simulationsRun);
		r.output.setText("Simulation "+ i + " running...");
		r.simulations[i] = new Simulate(r.squares, r.PLAYERS, r.NUMDICE, r.numsPerDice, r.chanceCards, r.communityChestCards, r.movesTotal, r.LAPSAROUND);
		r.simulations[i].runSimulation();
		
		//
		
	
		
		//

		r.MOVESTOTALCALC += r.simulations[i].getMovesTotal();
		
	//	simulationsRun++;
	}
		r.gif.setVisible(false);
		r.output.setFont(r.font2);
	
	
	
	System.out.println("Simulations done.");
	r.output.setText("Simulations Done.");
	for (int i = 0; i < r.squares.length; i++) {
		//averagedPercents[i] = truncateDecimal((double)r.squares[i].getTimesLanded()/(double)r.MOVESTOTALCALC, 4).multiply(new BigDecimal(100));
		averagedPercents[i] = truncateDecimal((double)r.squares[i].getTimesLanded()/(double)r.MOVESTOTALCALC, 4);
	}
	
//	String endString = "DECIMAL PERCENTS: \n \n";

//	for (int i = 0; i < r.squares.length; i++) {
//		//endString+=r.squares[i].getName() + " : " + averagedPercents[i] + "%\n";
//		endString+=averagedPercents[i]+"\n";
//	}
		
	//endString+="--------------------------------------------------------------------------------------\n NAMES: \n \n";
	
	//prints out names
//	for (int i = 0; i < r.squares.length; i++) {
//		endString+=r.squares[i].getName() + "\n";
//	}

//	try {
//		FileWriter fw = new FileWriter("src/results.txt");
//		fw.write(endString);
//		
//		fw.close();
//	} 	catch (IOException e) {
//		e.printStackTrace();
//	}
	
	String excel = "";
	
	for (int i = 0; i < r.squares.length; i++) {
		excel+=averagedPercents[i] + "\n";
	}
	
	r.output.setText("Copy and paste these numbers into the excel spreadsheet: \n \n" + r.link + "\n \n"+ excel);
	
	
	
	
}

private static BigDecimal truncateDecimal(double x,int numberofDecimals)
{
    if ( x > 0) {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
    } else {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
    }
}


public void start() {
	frame.add(panel);

	frame.setVisible(true);
	panel.setVisible(true);
	
	output = new JTextArea("Simulations Running...");
	
	
	
	
	panel.add(output);
	panel.setBackground(Color.DARK_GRAY);
	
	output.setFont(font1);

	
	panel.add(gif);
	Icon imgIcon = new ImageIcon("src/gifmonopoly.gif");
	gif = new JLabel(imgIcon);
//	gif.setBounds(300, 300, 300, 300); // for example, you can use your own values
	gif.setVisible(true);
	panel.add(gif);
	
	
	
	
	frame.setSize(1000, 900);

	
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
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



