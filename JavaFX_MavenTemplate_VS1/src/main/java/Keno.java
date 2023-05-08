//class for the Keno Game

public class Keno extends BetCard{
	BetCard card;
	int totalWinnings;
	
	//corresponding winnings depending on amount of spots
	//the index corresponds to the number of hits, the element in that index is the prize
	int oneOdds[] = {0,2};
	int fourOdds[] = {0,0,1,5,75};
	int eightOdds[] = {0,0,0,0,2,12,50,750,10000};
	int tenOdds[] = {5,0,0,0,0,2,15,40,450,4250,100000};
	
	public Keno() {
		card = new BetCard();
		totalWinnings = 0;
	}
	
	//takes user-input integer which is the num of spots they will choose
	public void setSpots(int spots) {
		card.amountSpots = spots;
	}
	//takes user-input integer which is the num of draws they will want in this round
	public void setDraws(int draws) {
		card.amountDraws = draws;
	}
	
	//takes user-input integer which is the num of spots they will choose
	public int getSpots() {
		return card.amountSpots;
	}
	//takes user-input integer which is the num of draws they will want in this round
	public int getDraws() {
		return card.amountDraws;
	}
	
	//takes value to add to spotsChosen, returns true if spotsChosen becomes full
	public boolean choice(int value) {
		card.spotsChosen.add(value);
		if(card.spotsChosen.size() == card.amountSpots) {
			return true;
		}
		return false;
	}
	
	public void calculateWinnings() {
		int hits = card.intersect();//gets the amount of hits the user acheived
		//switch case to determine which array to use for the prize calculation
		switch(card.amountSpots) {
			case 1:
				totalWinnings += oneOdds[hits];
				card.roundWinnings += oneOdds[hits];
				card.drawWinnings += oneOdds[hits];
				break;
			case 4:
				totalWinnings += fourOdds[hits];
				card.roundWinnings += fourOdds[hits];
				card.drawWinnings += fourOdds[hits];
				break;
			case 8:
				totalWinnings += eightOdds[hits];
				card.roundWinnings += eightOdds[hits];
				card.drawWinnings += eightOdds[hits];
				break;
			case 10:
				totalWinnings += tenOdds[hits];
				card.roundWinnings += tenOdds[hits];
				card.drawWinnings += tenOdds[hits];
				break;
			
		}
	}
	
}