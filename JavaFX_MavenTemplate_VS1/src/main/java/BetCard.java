import java.util.ArrayList;
import java.util.Random;


//the class for the bet card

public class BetCard{
	
	//data members for the betcard
	ArrayList<Integer> winningSpots; // stores the winning numbers of the current draw
	ArrayList<Integer> spotsChosen;// stores the user-chosen spots
	
	int amountSpots; //stores the user-chosen amount of spots they would like to choose 
	int amountDraws; //stores the amount of replays (also user-chosen) for one betcard
	
	int drawWinnings; //prize from one draw
	int roundWinnings; //prize from one round
	int currDraw; // current draw round
	
	ArrayList<Integer> hits; //stores the intersection between spotsChosen and winningSpots
	
	//constructor that intializes every data member
	public BetCard() {
		currDraw = 0;
		roundWinnings = 0;
		drawWinnings = 0;
		amountDraws = 0;
		amountSpots = 0;
		winningSpots = new ArrayList<Integer>();
		spotsChosen = new ArrayList<Integer>();
		hits = new ArrayList<Integer>();
	}
	
	//adds random integers from 1-80 to a list that contains winning numbers
	public void fillWins() {
		winningSpots.clear();
		Random rand = new Random();
		int num;
		for(int i = 0; i < 20; i++) {
			num = rand.nextInt(80)+1;//generate random number
			if(winningSpots.contains(num)) {//check if number is already in list
				i--;
				continue;
			}
			winningSpots.add(num);
		}
	}
	
	//picks random numbers on the bet card for the user
	public void quickPick() {
		spotsChosen.clear();
		Random rand = new Random();
		int num;
		for(int i = 0; i < amountSpots; i++) {
			num = rand.nextInt(80)+1;//generate random number 
			if(spotsChosen.contains(num)) {//check if number is already in list
				i--;
				continue;
			}
			spotsChosen.add(num);
		}
	}
	
	public void clearChoices() {
		spotsChosen.clear();
	}
	
	//prepares for next draw
	public void resetDraw() {
		drawWinnings = 0;
		winningSpots = new ArrayList<Integer>();
		hits = new ArrayList<Integer>();
	}
	
	//resets the whole round
	public void resetRound() {
		currDraw = 0;
		drawWinnings = 0;
		roundWinnings = 0;
		amountDraws = 0;
		amountSpots = 0;
		winningSpots = new ArrayList<Integer>();
		spotsChosen = new ArrayList<Integer>();
		hits = new ArrayList<Integer>();
	}
	
	//continue to next draw/round or the game can be finished which will be implemented in JavaFX
	//if true is returned, then the current round is finished
	public boolean contin() {
		if(currDraw == amountDraws) {
			resetRound();
			fillWins();
			return true;
		}
		else {
			currDraw++;
			resetDraw();
			fillWins();
		}
		return false;
	}
	
	//gets the intersection of the winningSpots and the spotsChosen
	public int intersect() {
		hits.clear();
		for(Integer i: spotsChosen) {
			if(winningSpots.contains(i)) {
				hits.add(i);
			}
		}
		return hits.size();
	}
}