import java.util.ArrayList;
import java.util.Random;

public class Simulate {
	int movesTotal;
	Square[] squares;
	boolean hasCycled = false;
	int players;
	int numDice;
	int numsPerDice;
	Player[] playersArray;
	String[] chanceCards;
	String[] communityChestCards;
	int lapsAround;
	ArrayList<String> cycledChance;
	ArrayList<String> cycledCommunityChest;
	int doubleCounter = 0;
	int numRolled = 0;
	Random r = new Random();
	int FPC;
	
	
	Simulate(Square[] squares, int players, int numDice, int numsPerDice, String[] chanceCards, String[] communityChestCards, int movesTotal, int lapsAround){
		this.squares=squares;
		this.players=players;
		this.numDice = numDice;
		this.numsPerDice = numsPerDice;
		this.chanceCards = chanceCards;
		this.communityChestCards = communityChestCards;
		this.movesTotal = movesTotal;
		this.lapsAround = lapsAround;
		
		playersArray = new Player[players];
		cycledChance = new ArrayList<String>(chanceCards.length);
		cycledCommunityChest = new ArrayList<String>(communityChestCards.length);
		
	}
	
	public void runSimulation() {
		
		for (int i = 0; i < chanceCards.length; i++) {
			cycledChance.add(chanceCards[i]);
		}
		for (int i = 0; i < communityChestCards.length; i++) {
			cycledCommunityChest.add(communityChestCards[i]);
		}
		//System.out.println(cycledChance.size());
		//System.out.println(cycledCommunityChest.size());
		
		for (int i = 0; i < players; i++) {
			playersArray[i] = new Player();
			//System.out.println("array of players initialized");
		}
	//	System.out.println("started running");
		while(hasCycled == false) {
			
			
			//goes through each player and lets them roll and calculates consequences
			for (FPC = 0; FPC < playersArray.length; FPC++) {
				
				//rolls dice and sets default space /// CHECKS CONSEQUENCES
				
				roll();
				
				
				if(playersArray[FPC].getCycles()>lapsAround) {
					hasCycled = true;
					break;
				}
			}
		}
	}
	public void roll() {
		boolean noJail = true;
		for (int j = 0; j < numDice; j++) {
			int temp = 1 + r.nextInt(numsPerDice);
			if(doubleCounter == 3) {
				playersArray[FPC].setSpaceOn(9);
				squares[9].addLanded();
				//trial
				movesTotal++;
				noJail = false;
				doubleCounter = 0;
				numRolled = 0;

			}
			else if(numRolled == temp && j == numDice-1) {
				checkConsequences();
				doubleCounter++;
				numRolled=0;
				roll();
				
			}
			numRolled = numRolled + temp;
			
		}
		if(noJail == true) {
			checkConsequences();
			doubleCounter=0;
		}else {
			noJail = true;
		}
		
		
	}
	
	public void checkConsequences() {
		
		if(playersArray[FPC].getSpaceOn()+numRolled>39) {
			int temp = 39-playersArray[FPC].spaceOn;
			playersArray[FPC].setSpaceOn(numRolled-temp-1);
			squares[playersArray[FPC].getSpaceOn()].addLanded();
			//trial
			movesTotal++;
			playersArray[FPC].addCycle();
			numRolled = 0;
		}else {
			playersArray[FPC].addSpaces(numRolled);
			squares[playersArray[FPC].getSpaceOn()].addLanded();
			//trial
			movesTotal++;
			numRolled = 0;
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		if(squares[playersArray[FPC].getSpaceOn()].getName().contains("Go To Prison")) {
			playersArray[FPC].setSpaceOn(9);
			squares[9].addLanded();
			//trial
			movesTotal++;
		}
		//checking chance consequences
		else if(squares[playersArray[FPC].getSpaceOn()].getName().contains("Chance")) {
			int chanceInt = r.nextInt(cycledChance.size());
			String chanceChoice = cycledChance.get(chanceInt);
			cycledChance.remove(chanceInt);
			//System.out.println("Removed "+ chanceChoice + " now cycledChance is " + cycledChance.size() + " large");
			if(cycledChance.size() == 0) {
				for (int z = 0; z < chanceCards.length; z++) {
					cycledChance.add(chanceCards[z]);
				}
			}
			if(chanceChoice.contains("Nearest Utility")) {
				for (int j2 = playersArray[FPC].getSpaceOn(); j2 < squares.length; j2++) {
					if(squares[j2].getName()=="Electric Company" || squares[j2].getName()=="Water Works") {
						playersArray[FPC].setSpaceOn(j2);
						squares[j2].addLanded();
						//trial
						movesTotal++;
						//System.out.println("sent to nearest nearest utility by chance");
						break;
					}else if(j2==squares.length-1) {
						//space 11 is the electric company
						playersArray[FPC].setSpaceOn(11);
						squares[11].addLanded();
						//trial
						movesTotal++;
						//System.out.println("sent to nearest nearest utility by chance");
					}
				}
			}else if (chanceChoice.contains("Railroad")&& !chanceChoice.contains("Reading Railroad")) {
				for (int j2 = playersArray[FPC].getSpaceOn(); j2 < squares.length; j2++) {
					if(squares[j2].getName()=="Reading Railroad" || squares[j2].getName()=="Pennsylvania Railroad" || squares[j2].getName()=="B.& O. Railroad" || squares[j2].getName()=="Short Line Railroad") {
						playersArray[FPC].setSpaceOn(j2);
						squares[j2].addLanded();
						//trial
						movesTotal++;
						//System.out.println("Sent to "+ squares[j2].getName() + " with chance");
						break;
					}else if(j2==squares.length-1) {
						playersArray[FPC].setSpaceOn(4);
						squares[4].addLanded();
						//trial
						movesTotal++;
						//System.out.println("Sent to Reading Railroad with Chance");
					}
				}
			}else if(chanceChoice.contains("Reading Railroad")&&!chanceChoice.contains("Advance Token To The Nearest Railroad")) {
				playersArray[FPC].setSpaceOn(4);
				squares[4].addLanded();
				//trial
				movesTotal++;
			}
			else if (chanceChoice.contains("Go Back 3 Spaces")) {
				playersArray[FPC].setSpaceOn(playersArray[FPC].getSpaceOn()-3);
				squares[playersArray[FPC].getSpaceOn()-3].addLanded();
				//trial
				movesTotal++;
			}else {
			for (int j = 0; j < squares.length; j++) {	
				if(chanceChoice.contains(squares[j].getName())) {
					playersArray[FPC].setSpaceOn(j);
					squares[j].addLanded();
					//trial
					movesTotal++;
					//System.out.println("sent to "+ squares[j].getName() + " with chance");
				}
			}
			}
		}
		//checking community chest consequences
		else if(squares[playersArray[FPC].getSpaceOn()].getName().contains("Community Chest")){
			int chestInt = r.nextInt(cycledCommunityChest.size());
			String chestChoice = cycledCommunityChest.get(chestInt);
			cycledCommunityChest.remove(chestInt);
			//System.out.println("Removed "+ chestChoice + " from cycledCommunityChest, size now "+ cycledCommunityChest.size());
			if(cycledCommunityChest.size() == 0) {
				for (int z = 0; z < communityChestCards.length; z++) {
					cycledCommunityChest.add(communityChestCards[z]);
				}
			}
			for (int j = 0; j < squares.length; j++) {
				if(chestChoice.contains(squares[j].getName())) {
					playersArray[FPC].setSpaceOn(j);
					squares[j].addLanded();
					//trial
					movesTotal++;
					//System.out.println("sent to "+ squares[j].getName() + " with community chest");
				}
			}
		}
	}
	

	public int getMovesTotal() {
		return movesTotal;
	}
	public Square[] getSquareArray(){
		return squares;
	}
	
	public void reset() {
		for (int i = 0; i < playersArray.length; i++) {
			playersArray[i].setSpaceOn(0);
			playersArray[i].setCycles(0);
		}
		for (int i = 0; i < cycledChance.size(); i++) {
			cycledChance.remove(i);
		}for (int i = 0; i < chanceCards.length; i++) {
			cycledChance.add(chanceCards[i]);
		}
		for (int z = 0; z < communityChestCards.length; z++) {
			cycledCommunityChest.remove(z);
		}
		for (int z = 0; z < communityChestCards.length; z++) {
			cycledCommunityChest.add(communityChestCards[z]);
		}
	}
	
	
	
}
