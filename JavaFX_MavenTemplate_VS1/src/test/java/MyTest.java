import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	Keno game;
	BetCard card;
	
	@BeforeEach
	void setUp() {
		game = new Keno();
		card = new BetCard();
	}
	
	//BetCard constructor test to make sure everything is initialized correctly.
	@Test
	void betCardConstructor() {
		assertEquals(0, card.currDraw, "currDraw did not initialize to zero correctly");
		assertEquals(0, card.roundWinnings, "roundWinnings did not initialize to zero correctly");
		assertEquals(0, card.drawWinnings, "drawWinnings did not initialize to zero correctly");
		assertEquals(0, card.amountDraws, "amountDraws did not initialize to zero correctly");
		assertEquals(0, card.amountSpots, "amountSpots did not initialize to zero correctly");
		assertEquals(0, card.winningSpots.size(), "winningSpots size did not initialize to zero correctly");
		assertEquals(0, card.hits.size(), "hits size did not initialize to zero correctly");
		assertEquals(0, card.spotsChosen.size(), "spotsChosen size did not initialize to zero correctly");

	}
	
	@Test
	void kenoConstructor() {
		assertEquals(0, game.totalWinnings, "totalWinnings did not initialize to zero correctly");
		int[] oddsOne = {0, 2};
		int[] oddsFour = {0,0,1,5,75};
		int[] oddsEight = {0,0,0,0,2,12,50,750,10000};
		int[] oddsTen = {5,0,0,0,0,2,15,40,450,4250,100000};
		assertArrayEquals(oddsOne, game.oneOdds, "oneOdds did notinitialize correctly");
		assertArrayEquals(oddsFour, game.fourOdds, "fourOdds did notinitialize correctly");
		assertArrayEquals(oddsEight, game.eightOdds, "eightOdds did notinitialize correctly");
		assertArrayEquals(oddsTen, game.tenOdds, "tenOdds did notinitialize correctly");
	}

	
	//
	//
	//betCard methods
	@Test
	void fillTheWins() {
		card.fillWins();
		assertEquals(20, card.winningSpots.size(), "fillWins() did not fill the winning spots with the correct amount of spots");
	}
	
	@Test
	void setAndGetSpots() {
		game.setSpots(10);
		assertEquals(10, game.card.amountSpots, "setSpots() did not fill the amountSpots with the correct amount of spots");
		assertEquals(10, game.getSpots(), "getSpots() did not return the right amount of spots");

	}
	
	@Test
	void setAndGetDraws() {
		game.setDraws(4);
		assertEquals(4, game.card.amountDraws, "setDraws() did not fill the amountDraws with the correct amount of draws");
		assertEquals(4, game.getDraws(), "getDraws() did not return the right amount of draws");

	}
	
	@Test
	void quickPick() {
		game.setSpots(10);
		game.card.quickPick();
		assertEquals(10, game.card.spotsChosen.size(), "quickPick() did not fill the spotsChosen with the correct amount of spots");
		
	}
	
	@Test
	void choiceTest() {
		game.setSpots(1);
		assertEquals(game.card.amountSpots,1, "the amount of spots is not 1");
		assertTrue(game.choice(10), "game.choice() did not return the desired value (true)");
		assertEquals(1, game.card.spotsChosen.size(), "the size of the spots chosen array is not 1");
	}
	@Test
	void choiceTest2() {
		game.setSpots(2);
		assertEquals(game.card.amountSpots,2, "the amount of spots is not 2");
		assertFalse(game.choice(10), "game.choice() did not return the desired value (true)");
		assertEquals(1, game.card.spotsChosen.size(), "the size of the spots chosen array is not 1");
	}
	@Test
	void clearChoices() {
		game.setSpots(10);
		game.card.quickPick();
		assertEquals(10, game.card.spotsChosen.size(), "quickPick() did not fill the spotsChosen with the correct amount of spots");
		game.card.clearChoices();
		assertEquals(0, game.card.spotsChosen.size(), "clearChoices() did not clear the spotsChosen");
	}
	@Test
	void intersect() {
		game.card.winningSpots.add(1);
		game.card.winningSpots.add(2);
		game.card.winningSpots.add(3);
		game.card.winningSpots.add(4);
		game.setSpots(4);
		game.choice(1);
		game.choice(2);
		game.choice(5);
		game.choice(6);
		assertEquals(2, game.card.intersect(), "intersect does not correctly determine the amount of hits");
		assertEquals(1, game.card.hits.get(0),"intersect did not add the correct hit (1)");
		assertEquals(2, game.card.hits.get(1), "intersect did not add the correct hit (2)");
	}
	
	@Test
	void resetDrawTest() {
		game.card.hits.add(1);
		game.card.drawWinnings = 100;
		game.card.fillWins();
		assertEquals(20, game.card.winningSpots.size(), "fillWins() did not fill the winning spots with the correct amount of spots");
		game.card.resetDraw();
		assertEquals(0, game.card.winningSpots.size(), "the amount of winning spots is not reset to 0");
		assertEquals(0, game.card.drawWinnings, "the draw winnings is not reset to 0");
		assertEquals(0, game.card.hits.size(), "the amount of hits is not reset to 0");
	}
	
	@Test
	void resetRound() {
		game.card.hits.add(1);
		game.card.drawWinnings = 100;
		game.card.currDraw = 2;
		game.card.roundWinnings = 120;
		game.card.amountDraws = 4;
		game.card.amountSpots = 2;
		game.card.spotsChosen.add(9);
		game.card.fillWins();
		assertEquals(20, game.card.winningSpots.size(), "fillWins() did not fill the winning spots with the correct amount of spots");
		game.card.resetRound();
		assertEquals(0, game.card.winningSpots.size(), "the winning spots is not reset to 0");
		assertEquals(0, game.card.drawWinnings, "the draw winnings is not reset to 0");
		assertEquals(0, game.card.hits.size(), "the amount of hits is not reset to 0");
		assertEquals(0, game.card.spotsChosen.size(), "the amount of spots chosen is not reset to 0");
		assertEquals(0, game.card.currDraw, "the current draw is not reset to 0");
		assertEquals(0, game.card.roundWinnings, "the round winnings is not reset to 0");
		assertEquals(0, game.card.amountDraws, "the amount of draws is not reset to 0");
		assertEquals(0, game.card.amountSpots, "the amount of spots is not reset to 0");
	}
	@Test
	void contin() {
		game.card.currDraw = 1;
		game.card.hits.add(1);
		game.setDraws(3);
		assertFalse(game.card.contin(), "the contin() function did not return false");
		assertEquals(2, game.card.currDraw, "the current draw did not increment");
		assertEquals(0, game.card.hits.size(), "the hits were not reset to 0");
		assertEquals(20, game.card.winningSpots.size(), "the winning spots size is not 20");
	}
	@Test
	void contin2() {
		game.card.currDraw = 4;
		game.setDraws(4);
		game.choice(4);
		assertTrue(game.card.contin());
		assertEquals(0, game.card.spotsChosen.size());
		assertEquals(20, game.card.winningSpots.size(), "fillWins() did not fill the winning spots with the correct amount of spots");
	}
	@Test
	void calculateWinnings() {
		game.setSpots(4);
		game.card.roundWinnings = 2;
		game.totalWinnings = 50;
		game.card.drawWinnings = 0;
		game.card.spotsChosen.add(1);
		game.card.spotsChosen.add(2);
		game.card.spotsChosen.add(3);
		game.card.spotsChosen.add(4);
		game.card.winningSpots.add(1);
		game.card.winningSpots.add(2);
		game.card.winningSpots.add(50);
		game.card.winningSpots.add(42);
		game.calculateWinnings();
		assertEquals(3, game.card.roundWinnings, "round winnings are not correct");
		assertEquals(1, game.card.drawWinnings, "draw winnings are not correct");
		assertEquals(51, game.totalWinnings, "total winnings are not correct");
	}
}
