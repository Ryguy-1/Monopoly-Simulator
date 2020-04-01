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
		System.out.println(cycledChance.size());
		System.out.println(cycledCommunityChest.size());
		
		for (int i = 0; i < players; i++) {
			playersArray[i] = new Player();
			System.out.println("array of players initialized");
		}
		System.out.println("started running");
		while(hasCycled == false) {
			
			Random r = new Random();
			//goes through each player and lets them roll and calculates consequences
			for (int i = 0; i < playersArray.length; i++) {
				
				//rolls dice and sets default space
				int numRolled = 0;
				for (int j = 0; j < numDice; j++) {
					numRolled = numRolled + 1 + r.nextInt(numsPerDice);
				}
				if(playersArray[i].getSpaceOn()+numRolled>=40) {
					System.out.println("Player" + i + "Rolled a " + numRolled);
					int temp = 40-playersArray[i].spaceOn;
					playersArray[i].setSpaceOn(temp);
					squares[playersArray[i].getSpaceOn()].addLanded();
					movesTotal+=1;
					playersArray[i].addCycle();
					numRolled = 0;
					System.out.println("Moved GO");
				}else {
					playersArray[i].addSpaces(numRolled);
					squares[playersArray[i].getSpaceOn()].addLanded();
					movesTotal+=1;
					System.out.println("Player" + i + "Rolled a " + numRolled);
					System.out.println("Moved not GO");
					numRolled = 0;
				}
				
				
				if(squares[playersArray[i].getSpaceOn()].getName().contains("Go To Prison")) {
					playersArray[i].setSpaceOn(9);
					squares[9].addLanded();
					System.out.println("went to jail");
				}
				
				
				//checking chance consequences
				else if(squares[playersArray[i].getSpaceOn()].getName().contains("Chance")) {
					
					int chanceInt = r.nextInt(cycledChance.size());
					String chanceChoice = cycledChance.get(chanceInt);
					cycledChance.remove(chanceInt);
					System.out.println("Removed "+ chanceChoice + " now cycledChance is " + cycledChance.size() + " large");
					if(cycledChance.size() == 0) {
						for (int z = 0; z < chanceCards.length; z++) {
							cycledChance.add(chanceCards[z]);
						}
					}
					
					if(chanceChoice.contains("Nearest Utility")) {
						for (int j2 = playersArray[i].getSpaceOn(); j2 < squares.length; j2++) {
							if(squares[j2].getName()=="Electric Company" || squares[j2].getName()=="Water Works") {
								playersArray[i].setSpaceOn(j2);
								squares[j2].addLanded();
								System.out.println("sent to nearest nearest utility by chance");
								break;
							}else if(j2==squares.length-1) {
								//space 11 is the electric company
								playersArray[i].setSpaceOn(11);
								System.out.println("sent to nearest nearest utility by chance");
							}
						}
					
					}else if (chanceChoice.contains("Railroad")&& !chanceChoice.contains("Reading Railroad")) {
						for (int j2 = playersArray[i].getSpaceOn(); j2 < squares.length; j2++) {
							if(squares[j2].getName()=="Reading Railroad" || squares[j2].getName()=="Pennsylvania Railroad" || squares[j2].getName()=="B.& O. Railroad" || squares[j2].getName()=="Short Line Railroad") {
								playersArray[i].setSpaceOn(j2);
								squares[j2].addLanded();
								System.out.println("Sent to "+ squares[j2].getName() + " with chance");
								break;
							}else if(j2==squares.length-1) {
								//space 11 is the electric company
								playersArray[i].setSpaceOn(4);
								System.out.println("Sent to Reading Railroad with Chance");
							}
						}
						
						
					}else if (chanceChoice.contains("Go Back 3 Spaces")) {
						playersArray[i].setSpaceOn(playersArray[i].getSpaceOn()-3);
						squares[playersArray[i].getSpaceOn()-3].addLanded();
					}else {
					for (int j = 0; j < squares.length; j++) {	
						if(chanceChoice.contains(squares[j].getName())) {
							playersArray[i].setSpaceOn(j);
							squares[j].addLanded();
							System.out.println("sent to "+ squares[j].getName() + " with chance");
						}
					}
					}
					
					
					
				}
				//checking community chest consequences
				else if(squares[playersArray[i].getSpaceOn()].getName().contains("Community Chest")){
					
					
					int chestInt = r.nextInt(cycledCommunityChest.size());
						
					String chestChoice = cycledCommunityChest.get(chestInt);
					cycledCommunityChest.remove(chestInt);
					System.out.println("Removed "+ chestChoice + " from cycledCommunityChest, size now "+ cycledCommunityChest.size());
					if(cycledCommunityChest.size() == 0) {
						for (int z = 0; z < communityChestCards.length; z++) {
							cycledCommunityChest.add(communityChestCards[z]);
						}
					}
					
					
					for (int j = 0; j < squares.length; j++) {
						if(chestChoice.contains(squares[j].getName())) {
							playersArray[i].setSpaceOn(j);
							squares[j].addLanded();
							System.out.println("sent to "+ squares[j].getName() + " with community chest");
						}
					}
				}
				
				
				if(playersArray[i].getCycles()>lapsAround) {
					hasCycled = true;
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
	
	
	
	
}
