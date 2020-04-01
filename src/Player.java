
public class Player {
	int spaceOn;
	int cycles;
	Player(){
	}
	
	public void addSpaces(int added) {
		spaceOn+=added;
	}
	public int getSpaceOn() {
		return spaceOn;
	}
	public void setSpaceOn(int space) {
		spaceOn = space;
	}
	public int getCycles() {
		return cycles;
	}
	public void addCycle() {
		cycles+=1;
	}
	public void setCycles(int setter) {
		cycles = setter;
	}
	
}
