import java.io.File;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.text.*;


//Made by Syed Shaban and Syed Mehdi
//CS 342 Spring 2023
//Prof. Mark Hallenbeck

//The JavaFx class for the game


public class JavaFXTemplate extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		javafx.scene.text.Font.getFamilies();
		javafx.scene.text.Font.getFontNames();
		
		final IntegerProperty newLookCount = new SimpleIntegerProperty(0);
		
		final ArrayList<Integer> gameProgression = new ArrayList<Integer>();

		primaryStage.setTitle("Welcome to Keno");
		Keno game = new Keno();
		
		//menu bar (drop down)
		
		MenuBar menu = new MenuBar();
		Menu options = new Menu("Options");	
		options.setGraphic(new ImageView("file:src/main/resources/settings.png"));
		menu.getMenus().add(options);
		
		MenuItem rules = new MenuItem("How To Play");
		MenuItem odds = new MenuItem("See Odds");
		MenuItem home = new MenuItem("Home");
		MenuItem newLook = new MenuItem("New Look");
		MenuItem exit = new MenuItem("Exit");
		
		options.getItems().add(home);
		options.getItems().add(rules);
		options.getItems().add(odds);
		options.getItems().add(newLook);
		options.getItems().add(exit);
		
		//
		//
		//Home Page
		Text title = new Text(10,50, "keno");
		title.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,200 ));
		//title.setStyle(FontPosture.ITALIC);
		title.setFill(Color.WHITE);
		title.setStrokeWidth(3);
		title.setStroke(Color.RED);
		
		
		
		//the play button
		Button play = new Button("Play");
		play.setPrefHeight(250);
		play.setPrefWidth(550);
		play.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,100 ));
		play.setTextFill(Color.WHITE);
		play.setStyle("-fx-background-color: #000000;");
		play.setOnMouseEntered(e->play.setStyle("-fx-background-color: #0bfc03;"));
		play.setOnMouseExited(e->play.setStyle("-fx-background-color: #000000;"));
		
		//the button to quit the game
		Button quitGame = new Button("Quit");
		quitGame.setPrefHeight(200);
		quitGame.setPrefWidth(500);
		quitGame.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,100 ));
		quitGame.setTextFill(Color.WHITE);
		quitGame.setStyle("-fx-background-color: #000000;");
		quitGame.setOnMouseEntered(e->quitGame.setStyle("-fx-background-color: #ff6969;"));
		quitGame.setOnMouseExited(e->quitGame.setStyle("-fx-background-color: #000000;"));
		
		//the button that lets you view the odds
		Button seeOdds = new Button("See Odds");
		seeOdds.setPrefHeight(220);
		seeOdds.setPrefWidth(520);
		seeOdds.setTextFill(Color.WHITE);
		seeOdds.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,70 ));
		seeOdds.setStyle("-fx-background-color: #000000;");
		seeOdds.setOnMouseEntered(e->seeOdds.setStyle("-fx-background-color: #63ffd8;"));
		seeOdds.setOnMouseExited(e->seeOdds.setStyle("-fx-background-color: #000000;"));
		
		//the button that leads you to the rules of the game
		Button seeRules = new Button("Rules");
		seeRules.setPrefHeight(100);
		seeRules.setPrefWidth(200);
		seeRules.setTextFill(Color.WHITE);
		seeRules.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,40 ));
		seeRules.setStyle("-fx-background-color: #000000;");
		seeRules.setOnMouseEntered(e->seeRules.setStyle("-fx-background-color:  #993399;"));
		seeRules.setOnMouseExited(e->seeRules.setStyle("-fx-background-color: #000000"));
		
		
		HBox optionBox = new HBox(seeOdds,play, quitGame);//horz row for buttons
		optionBox.setAlignment(Pos.CENTER);
		optionBox.setSpacing(15);
		VBox homeBox = new VBox(title, optionBox, seeRules);//has title, row of buttons and rules button
		homeBox.setAlignment(Pos.CENTER);
		homeBox.setSpacing(80);
		VBox.setMargin(optionBox, new Insets(100));
		
		
		
		BorderPane homePane = new BorderPane();
		homePane.setCenter(homeBox);
		homePane.setStyle("-fx-background-color: #202020;");
		
		Scene homeScreen = new Scene(homePane, 1920,1080);
		
		//Odds Page
		BorderPane oddBP = new BorderPane();
		Scene oddScreen = oddPage(oddBP);//function to create oddPage
		
		//Rules Page
		BorderPane rulesBP = new BorderPane();
		Scene rulesScreen = rulesPage(rulesBP); //function to create rulesPage
		
		//Game Page
		BorderPane gamePane = new BorderPane();
		GridPane gameGrid = new GridPane();
		
		//fill the gridPane
		Button spots[][] = new Button[10][8];
		for(int i = 1; i < 9; i++) {
			for(int j = 1; j< 11; j++) {
				int row = j-1;
				int col = i-1;
				//adds buttons to game grid
				spots[row][col] = new Button(String.valueOf(row+(10*col)+1));
				//event handler for button in terms of style
				/*spots[row][col].setOnAction(e->{
					spots[row][col].setStyle("-fx-background-color: #ff0000");
					spots[row][col].setDisable(true);
					spots[row][col].setOnMouseEntered(f->spots[row][col].setStyle("-fx-background-color: #ff0000;"));
					spots[row][col].setOnMouseExited(f->spots[row][col].setStyle("-fx-background-color: #ff0000;"));
				});*/
				spots[row][col].setPrefSize(125, 125);
				spots[row][col].setTextFill(Color.WHITE);
				spots[row][col].setStyle("-fx-background-color: #000000");
				spots[row][col].setOnMouseEntered(e->spots[row][col].setStyle("-fx-background-color: #ff0000;"));
				spots[row][col].setOnMouseExited(e->spots[row][col].setStyle("-fx-background-color: #000000;"));
				spots[row][col].setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
				spots[row][col].setDisable(true);
				gameGrid.add(spots[row][col], row, col);
			}
		}
		
		//grid customizations 
		gameGrid.setPrefSize(750, 750);
		gameGrid.setAlignment(Pos.CENTER);
		
		//
		//
		//Asking how many spots
		Text spotQ = new Text("How many spots would\nyou like to choose?");
		spotQ.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
		spotQ.setFill(Color.WHITE);
		spotQ.setStrokeWidth(1);
		spotQ.setStroke(Color.RED);
		
		//array of options (1, 4, 8, 10)
		Button spotInputArr[] = new Button[4];
		
		
		spotInputArr[0] = new Button("1");
		spotInputArr[1] = new Button("4");
		spotInputArr[2] = new Button("8");
		spotInputArr[3] = new Button("10");
		
		
		HBox spotButtons = new HBox();
		
		//the event handlers for the user input
		for(Button x : spotInputArr) {
			x.setPrefSize(75, 125);
			spotButtons.getChildren().add(x);
			x.setStyle("-fx-background-color: #000000");
			x.setOnMouseEntered(e->x.setStyle("-fx-background-color: #99ff99;"));
			x.setOnMouseExited(e->x.setStyle("-fx-background-color: #000000;"));
			x.setTextFill(Color.WHITE);
			x.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		}
		spotButtons.setAlignment(Pos.CENTER);
		spotButtons.setSpacing(25);
		
		
		//first question and answer VBox
		VBox spotInputs = new VBox(spotQ, spotButtons);
		spotInputs.setAlignment(Pos.CENTER_LEFT);
		spotInputs.setSpacing(50);
		
		//
		//
		//Asking how many draws
		Text drawQ = new Text("How many draws would\nyou like to choose?");
		drawQ.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
		drawQ.setFill(Color.WHITE);
		drawQ.setStrokeWidth(1);
		drawQ.setStroke(Color.RED);
		
		//array of options (1, 4, 8, 10)
		Button drawInputArr[] = new Button[4];
		
		
		drawInputArr[0] = new Button("1");
		drawInputArr[1] = new Button("2");
		drawInputArr[2] = new Button("3");
		drawInputArr[3] = new Button("4");
		
		
		HBox drawButtons = new HBox();
		
		//the event handlers for the user input
		for(Button x : drawInputArr) {
			x.setPrefSize(75, 125);
			drawButtons.getChildren().add(x);
			x.setStyle("-fx-background-color: #000000");
			x.setOnMouseEntered(e->x.setStyle("-fx-background-color: #99ff99;"));
			x.setOnMouseExited(e->x.setStyle("-fx-background-color: #000000;"));
			x.setTextFill(Color.WHITE);
			x.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		}
		drawButtons.setAlignment(Pos.CENTER);
		drawButtons.setSpacing(25);
		
		//second question and answer VBox
		VBox drawInputs = new VBox(drawQ, drawButtons);
		drawInputs.setAlignment(Pos.CENTER_LEFT);
		drawInputs.setSpacing(50);
		
		
		//initial setupGameScreen's contents (left and right side)
		HBox gameContents = new HBox(spotInputs,gameGrid);
		gameContents.setAlignment(Pos.CENTER);
		HBox.setMargin(gameGrid, new Insets(150));
		gameContents.setAlignment(Pos.TOP_CENTER);
		
		Scene setupGameScreen = new Scene(gamePane, 1920,1080);
		
		gamePane.setCenter(gameContents);
		gamePane.setStyle("-fx-background-color: #202020;");
		
		
		//Game Screen while playing
		BorderPane gameBP = new BorderPane();
		HBox playingBox = new HBox();
		
		//
		//
		//left handside of the game screen
		
		//winning numbers
		VBox winNums = new VBox();
		Text winNumText = new Text("Winning Nums: ");
		winNumText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
		winNumText.setFill(Color.WHITE);
		winNumText.setStrokeWidth(0.5);
		winNumText.setStroke(Color.RED);
		
		winNums.getChildren().add(winNumText);
		VBox.setMargin(winNumText, new Insets(100));
		
		//clear and quickpick button
		Button quickPickButton = new Button("Quick Pick");
		quickPickButton.setStyle("-fx-background-color: #00cc00");
		quickPickButton.setTextFill(Color.WHITE);
		quickPickButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		quickPickButton.setStyle("-fx-background-color: #00cc00");
		quickPickButton.setOnMouseEntered(f->quickPickButton.setStyle("-fx-background-color:  #009900;"));
		quickPickButton.setOnMouseExited(f->quickPickButton.setStyle("-fx-background-color: #00cc00"));
		
		Button clear = new Button("Clear");
		clear.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		clear.setStyle("-fx-background-color: #ff0000");
		clear.setTextFill(Color.WHITE);
		clear.setOnMouseEntered(f->clear.setStyle("-fx-background-color:  #C45050;"));
		clear.setOnMouseExited(f->clear.setStyle("-fx-background-color: #ff0000"));
		
		HBox clearAndQP = new HBox(clear);
		
		VBox leftHandSide = new VBox(winNums, clearAndQP);
		leftHandSide.setSpacing(30);
		clearAndQP.setSpacing(10);
		clearAndQP.setAlignment(Pos.CENTER);
		playingBox.getChildren().add(leftHandSide);
		
		//bottom part of the game screen
		Text drawCount = new Text("Current Draw: " + game.card.currDraw);
		drawCount.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		drawCount.setFill(Color.WHITE);
		drawCount.setStrokeWidth(0.5);
		drawCount.setStroke(Color.RED);
		
		Text drawProfit = new Text("Draw Profit: $" + game.card.drawWinnings);
		drawProfit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		drawProfit.setFill(Color.WHITE);
		drawProfit.setStrokeWidth(0.5);
		drawProfit.setStroke(Color.RED);
		
		Text roundProfit = new Text("Round Profit: $" + game.card.roundWinnings);
		roundProfit.setFill(Color.WHITE);
		roundProfit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		roundProfit.setStrokeWidth(0.5);
		roundProfit.setStroke(Color.RED);
		
		Text totalProfit = new Text("Total Profit: $" + game.totalWinnings);
		totalProfit.setFill(Color.WHITE);
		totalProfit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
		totalProfit.setStrokeWidth(0.5);
		totalProfit.setStroke(Color.RED);
		
		Button continueButton = new Button("Continue");
		continueButton.setPrefSize(150, 100);
		continueButton.setStyle("-fx-background-color: #55cfba");
		continueButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
		continueButton.setTextFill(Color.BLACK);
		continueButton.setOnMouseEntered(e->continueButton.setStyle("-fx-background-color:  #36A6B6;"));
		continueButton.setOnMouseExited(e->continueButton.setStyle("-fx-background-color: #55cfba"));
		
		HBox stats = new HBox(drawCount, drawProfit, roundProfit, totalProfit, continueButton);
		stats.setAlignment(Pos.BASELINE_RIGHT);
		stats.setSpacing(100);
		HBox.setMargin(continueButton, new Insets(0, 40, 40, 200));
		
		Scene gameScreen = new Scene(gameBP, 1920, 1080);
		gameBP.setStyle("-fx-background-color: #202020;");
		gameBP.setBottom(stats);
		
		BorderPane betweenBP = new BorderPane();
		Text queryForAgain = new Text("Want to Play Again?");
		queryForAgain.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,100 ));
		queryForAgain.setFill(Color.WHITE);
		queryForAgain.setStrokeWidth(1);
		queryForAgain.setStroke(Color.RED);
		
		Button exitGame = new Button("No...");
		exitGame.setPrefHeight(200);
		exitGame.setPrefWidth(500);
		exitGame.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,100 ));
		exitGame.setTextFill(Color.WHITE);
		exitGame.setStyle("-fx-background-color: #000000;");
		exitGame.setOnMouseEntered(e->exitGame.setStyle("-fx-background-color: #ff6969;"));
		exitGame.setOnMouseExited(e->exitGame.setStyle("-fx-background-color: #000000;"));
		exitGame.setOnAction(e->{
			primaryStage.close();
		});
		
		Button playAgain = new Button("Play Again");
		playAgain.setPrefHeight(200);
		playAgain.setPrefWidth(500);
		playAgain.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,50 ));
		playAgain.setTextFill(Color.WHITE);
		playAgain.setStyle("-fx-background-color: #000000;");
		playAgain.setOnMouseEntered(e->playAgain.setStyle("-fx-background-color: #2fc381;"));
		playAgain.setOnMouseExited(e->playAgain.setStyle("-fx-background-color: #000000;"));
		playAgain.setOnAction(e->{
			game.card.resetRound();
			if(gameProgression.size() > 0)
				gameProgression.remove(0);
			primaryStage.setScene(setupGameScreen);
			gamePane.setTop(menu);
			gameContents.getChildren().clear();
			gameContents.getChildren().addAll(spotInputs, gameGrid);
			disableButtons(true, spots);
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 10; j++) {
					spots[j][i].setStyle("-fx-background-color: #000000");
				}
			}
			quickPickButton.setDisable(false);
			if(!clearAndQP.getChildren().contains(quickPickButton))
				clearAndQP.getChildren().add(quickPickButton);
			winNums.getChildren().clear();
			winNums.getChildren().add(winNumText);
			stats.getChildren().clear();
			//update profits
			drawCount.setText("Current Draw: " + game.card.currDraw);
			drawProfit.setText("Draw Profit: $" + game.card.drawWinnings);
			roundProfit.setText("Round Profit: $" + game.card.roundWinnings);
			totalProfit.setText("Total Profit: $" + game.totalWinnings);
			stats.getChildren().addAll(drawCount, drawProfit, roundProfit, totalProfit, continueButton);
			if(!leftHandSide.getChildren().contains(clearAndQP))
			leftHandSide.getChildren().add(clearAndQP);
		});
		
		HBox betweenButtons = new HBox(playAgain, exitGame);
		betweenButtons.setSpacing(100);
		betweenButtons.setAlignment(Pos.CENTER);
		
		Text incentiveToPlayAgain = new Text("Total Winnings so far: $" + game.totalWinnings);
		incentiveToPlayAgain.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC, 100));
		incentiveToPlayAgain.setFill(Color.WHITE);
		incentiveToPlayAgain.setStrokeWidth(1);
		incentiveToPlayAgain.setStroke(Color.RED);
		
		VBox betweenBox = new VBox(queryForAgain, betweenButtons, incentiveToPlayAgain); 
		betweenBox.setAlignment(Pos.CENTER);
		betweenBox.setSpacing(100);
		
		betweenBP.setCenter(betweenBox);
		Scene betweenScene = new Scene(betweenBP, 1920, 1080);
		betweenBP.setStyle("-fx-background-color: #202020;");
		
		
		
		//continue button's event handler
		continueButton.setOnAction(e->{
			if(leftHandSide.getChildren().contains(clearAndQP)) {
				leftHandSide.getChildren().clear();
				leftHandSide.getChildren().add(winNums);
			}
			if(game.card.contin()){//fills the wins, increments draw
				betweenBP.setTop(menu);
				incentiveToPlayAgain.setText("Total Winnings so far: $" + game.totalWinnings);
				primaryStage.setScene(betweenScene);
			}
			else	{
				game.calculateWinnings();//adds the amount of money for current draw, current round, and total winnings
				stats.getChildren().clear();
				//update profits
				drawCount.setText("Current Draw: " + game.card.currDraw);
				drawProfit.setText("Draw Profit: $" + game.card.drawWinnings);
				roundProfit.setText("Round Profit: $" + game.card.roundWinnings);
				totalProfit.setText("Total Profit: $" + game.totalWinnings);
				stats.getChildren().addAll(drawCount, drawProfit, roundProfit, totalProfit, continueButton);
				winNums.getChildren().clear();
				winNums.getChildren().add(winNumText);
				//resets the grid to only the spots that are chosen before revealing the winning numbers
				for(int i = 0; i < 8; i++) {
					for(int j = 0; j< 10; j++) {
						spots[j][i].setStyle("-fx-background-color: #000000");
						if (game.card.spotsChosen.contains(Integer.valueOf(spots[j][i].getText()))) {
							spots[j][i].setStyle("-fx-background-color: #ff0000");
						}
					}
				}
				continueButton.setDisable(true);
				final IntegerProperty iteratorNum = new SimpleIntegerProperty(0);
				final IntegerProperty textIterator = new SimpleIntegerProperty(0);
				Timeline timeline = new Timeline(
						new KeyFrame(
							Duration.millis(500),
							event -> {
								int iterator = iteratorNum.get();
								int textIt = textIterator.get();
								int winNum = game.card.winningSpots.get(iterator); 
								int row, col;
								if((winNum % 10) == 0) {
									row = 9;
									col = (winNum / 10) - 1;
								}
								else {
									row = (winNum % 10) - 1;
									col = (winNum / 10);
								}
								if(game.card.hits.contains(winNum)) {
									spots[row][col].setStyle("-fx-background-color: #35BF8C");
								}
								else {
									spots[row][col].setStyle("-fx-background-color: #204584");
								}
								iteratorNum.set(iterator+1);
								if(iterator == 19) {
									continueButton.setDisable(false);

								}
							}
						)
				);
				timeline.setCycleCount(20);
				timeline.play();
				for(int i = 0; i < 20; i+=2) {
					Text spot = new Text(String.valueOf(game.card.winningSpots.get(i)) + "                 " + game.card.winningSpots.get(i+1));
					spot.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
					spot.setFill(Color.WHITE);
					spot.setStrokeWidth(0.5);
					spot.setStroke(Color.RED);
					winNums.getChildren().add(spot);
					VBox.setMargin(spot, new Insets(0, 0, 10, 115));
					
				}

			}
		});
		
		//clear button's event handler
		clear.setOnAction(e->{
			game.card.clearChoices();
			if (!clearAndQP.getChildren().contains(quickPickButton))
				clearAndQP.getChildren().add(quickPickButton);
			clear.setDisable(true);
			disableButtons(false, spots);
			quickPickButton.setDisable(false);
			if(gameProgression.size() > 0)
				gameProgression.remove(0);
			continueButton.setDisable(true);
		});
		
		//setting the default scene to home
		primaryStage.setScene(homeScreen);
		primaryStage.show();
		
		//
		//
		//Event Handlers
		
		//Menu Bar Items
		exit.setOnAction(e->primaryStage.close());
		
		home.setOnAction(e->{
			primaryStage.setScene(homeScreen);
			//homePane.setTop(menu);
		});
		odds.setOnAction(e->{
			primaryStage.setScene(oddScreen);
			oddBP.setTop(menu);
		});
		
		rules.setOnAction(e->{
			primaryStage.setScene(rulesScreen);
			rulesBP.setTop(menu);
		});
		
		newLook.setOnAction(e->{
			if(newLookCount.get() == 0) {
				newLookCount.set(1);
				//change color of all BPs
				homePane.setStyle("-fx-background-color: #0033cc");
				oddBP.setStyle("-fx-background-color: #0033cc");
				rulesBP.setStyle("-fx-background-color: #0033cc");
				gamePane.setStyle("-fx-background-color: #0033cc");
				gameBP.setStyle("-fx-background-color: #0033cc");
				betweenBP.setStyle("-fx-background-color: #0033cc");
				
				//change font and color of all Texts
				title.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC,200 ));
				title.setStroke(Color.PURPLE);
				
				spotQ.setFont(Font.font("Arial", FontWeight.BOLD, 70));
				spotQ.setStroke(Color.PURPLE);
				
				drawQ.setFont(Font.font("Arial", FontWeight.BOLD, 70));
				drawQ.setStroke(Color.PURPLE);
				
				winNumText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
				winNumText.setStroke(Color.PURPLE);
				
				drawCount.setFont(Font.font("Arial", FontWeight.BOLD, 30));
				drawCount.setStroke(Color.PURPLE);
				
				drawProfit.setFont(Font.font("Arial", FontWeight.BOLD, 30));
				drawProfit.setStroke(Color.PURPLE);
				
				roundProfit.setFont(Font.font("Arial", FontWeight.BOLD, 30));
				roundProfit.setStroke(Color.PURPLE);
				
				totalProfit.setFont(Font.font("Arial", FontWeight.BOLD, 30));
				totalProfit.setStroke(Color.PURPLE);
				
				queryForAgain.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,100 ));
				queryForAgain.setStroke(Color.PURPLE);
				
				incentiveToPlayAgain.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC, 100));
				incentiveToPlayAgain.setStroke(Color.PURPLE);
				
				//change font and color of all Buttons
				play.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,100 ));
				play.setTextFill(Color.WHITE);
				play.setStyle("-fx-background-color: #0bfc03;");
				play.setOnMouseEntered(f->play.setStyle("-fx-background-color: #000000;"));
				play.setOnMouseExited(f->play.setStyle("-fx-background-color: #0bfc03;"));
				
				quitGame.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,100 ));
				quitGame.setTextFill(Color.WHITE);
				quitGame.setStyle("-fx-background-color: #ff6969;");
				quitGame.setOnMouseEntered(f->quitGame.setStyle("-fx-background-color: #000000;"));
				quitGame.setOnMouseExited(f->quitGame.setStyle("-fx-background-color: #ff6969;"));
				
				seeOdds.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,70 ));
				seeOdds.setStyle("-fx-background-color: #63ffd8;");
				seeOdds.setOnMouseEntered(f->seeOdds.setStyle("-fx-background-color: #000000;"));
				seeOdds.setOnMouseExited(f->seeOdds.setStyle("-fx-background-color: #63ffd8;"));
				
				seeRules.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,40 ));
				seeRules.setStyle("-fx-background-color: #993399;");
				seeRules.setOnMouseEntered(f->seeRules.setStyle("-fx-background-color:  #000000;"));
				seeRules.setOnMouseExited(f->seeRules.setStyle("-fx-background-color: #993399"));
				
				quickPickButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				quickPickButton.setTextFill(Color.WHITE);
				quickPickButton.setStyle("-fx-background-color: #009900");
				quickPickButton.setOnMouseEntered(f->quickPickButton.setStyle("-fx-background-color:  #00cc00;"));
				quickPickButton.setOnMouseExited(f->quickPickButton.setStyle("-fx-background-color: #009900"));
				
				clear.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				clear.setStyle("-fx-background-color: #C45050");
				clear.setOnMouseEntered(f->clear.setStyle("-fx-background-color:  #ff0000;"));
				clear.setOnMouseExited(f->clear.setStyle("-fx-background-color: #C45050"));
				
				continueButton.setStyle("-fx-background-color: #36A6B6");
				continueButton.setFont(Font.font("Arial", FontWeight.BOLD, 25));
				continueButton.setTextFill(Color.BLACK);
				continueButton.setOnMouseEntered(f->continueButton.setStyle("-fx-background-color:  #55cfba;"));
				continueButton.setOnMouseExited(f->continueButton.setStyle("-fx-background-color: #36A6B6"));
				
				exitGame.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,100 ));
				exitGame.setTextFill(Color.WHITE);
				exitGame.setStyle("-fx-background-color: #ff6969;");
				exitGame.setOnMouseEntered(f->exitGame.setStyle("-fx-background-color: #000000;"));
				exitGame.setOnMouseExited(f->exitGame.setStyle("-fx-background-color: #ff6969;"));
				
				playAgain.setFont(Font.font("Arial",FontWeight.BOLD, FontPosture.ITALIC,50 ));
				playAgain.setTextFill(Color.WHITE);
				playAgain.setStyle("-fx-background-color: #2fc381;");
				playAgain.setOnMouseEntered(f->playAgain.setStyle("-fx-background-color: #000000;"));
				playAgain.setOnMouseExited(f->playAgain.setStyle("-fx-background-color: #2fc381;"));
				
				menu.setStyle("-fx-background-color:  #55cfba;");
			}
			else if (newLookCount.get() == 1) {
				newLookCount.set(0);
				//change color of all BPs
				homePane.setStyle("-fx-background-color: #202020");
				oddBP.setStyle("-fx-background-color: #202020");
				rulesBP.setStyle("-fx-background-color: #202020");
				gamePane.setStyle("-fx-background-color: #202020");
				gameBP.setStyle("-fx-background-color: #202020");
				betweenBP.setStyle("-fx-background-color: #202020");
				
				//change font and color of all Texts
				title.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC,200 ));
				title.setStroke(Color.RED);
				
				spotQ.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
				spotQ.setStroke(Color.RED);
				
				drawQ.setFont(Font.font("Times New Roman", FontWeight.BOLD, 70));
				drawQ.setStroke(Color.RED);
				
				winNumText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 50));
				winNumText.setStroke(Color.RED);
				
				drawCount.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
				drawCount.setStroke(Color.RED);
				
				drawProfit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
				drawProfit.setStroke(Color.RED);
				
				roundProfit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
				roundProfit.setStroke(Color.RED);
				
				totalProfit.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
				totalProfit.setStroke(Color.RED);
				
				queryForAgain.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,100 ));
				queryForAgain.setStroke(Color.RED);
				
				incentiveToPlayAgain.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC, 100));
				incentiveToPlayAgain.setStroke(Color.RED);
				
				//change font and color of all Buttons
				play.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,100 ));
				play.setTextFill(Color.WHITE);
				play.setStyle("-fx-background-color: #000000;");
				play.setOnMouseEntered(f->play.setStyle("-fx-background-color: #0bfc03;"));
				play.setOnMouseExited(f->play.setStyle("-fx-background-color: #000000;"));
				
				quitGame.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,100 ));
				quitGame.setTextFill(Color.WHITE);
				quitGame.setStyle("-fx-background-color: #000000;");
				quitGame.setOnMouseEntered(f->quitGame.setStyle("-fx-background-color: #ff6969;"));
				quitGame.setOnMouseExited(f->quitGame.setStyle("-fx-background-color: #000000;"));
				
				seeOdds.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,70 ));
				seeOdds.setStyle("-fx-background-color: #000000;");
				seeOdds.setOnMouseEntered(f->seeOdds.setStyle("-fx-background-color: #63ffd8;"));
				seeOdds.setOnMouseExited(f->seeOdds.setStyle("-fx-background-color: #000000;"));
				
				seeRules.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,40 ));
				seeRules.setStyle("-fx-background-color: #000000;");
				seeRules.setOnMouseEntered(f->seeRules.setStyle("-fx-background-color:  #993399;"));
				seeRules.setOnMouseExited(f->seeRules.setStyle("-fx-background-color: #000000"));
				
				quickPickButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
				quickPickButton.setTextFill(Color.WHITE);
				quickPickButton.setStyle("-fx-background-color: #00cc00");
				quickPickButton.setOnMouseEntered(f->quickPickButton.setStyle("-fx-background-color:  #009900;"));
				quickPickButton.setOnMouseExited(f->quickPickButton.setStyle("-fx-background-color: #00cc00"));
				
				clear.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
				clear.setStyle("-fx-background-color: #ff0000");
				clear.setOnMouseEntered(f->clear.setStyle("-fx-background-color:  #C45050;"));
				clear.setOnMouseExited(f->clear.setStyle("-fx-background-color: #ff0000"));
				
				continueButton.setStyle("-fx-background-color: #55cfba");
				continueButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
				continueButton.setTextFill(Color.BLACK);
				continueButton.setOnMouseEntered(f->continueButton.setStyle("-fx-background-color:  #36A6B6;"));
				continueButton.setOnMouseExited(f->continueButton.setStyle("-fx-background-color: #55cfba"));
				
				exitGame.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,100 ));
				exitGame.setTextFill(Color.WHITE);
				exitGame.setStyle("-fx-background-color: #000000;");
				exitGame.setOnMouseEntered(f->exitGame.setStyle("-fx-background-color: #ff6969;"));
				exitGame.setOnMouseExited(f->exitGame.setStyle("-fx-background-color: #000000;"));
				
				playAgain.setFont(Font.font("Times New Roman",FontWeight.BOLD, FontPosture.ITALIC,50 ));
				playAgain.setTextFill(Color.WHITE);
				playAgain.setStyle("-fx-background-color: #000000;");
				playAgain.setOnMouseEntered(f->playAgain.setStyle("-fx-background-color: #2fc381;"));
				playAgain.setOnMouseExited(f->playAgain.setStyle("-fx-background-color: #000000;"));
				
				menu.setStyle("-fx-background-color:  #ffffff;");
			}
		});
		
		//
		//home page items
		quitGame.setOnAction(e->primaryStage.close());
		seeOdds.setOnAction(e->{
			primaryStage.setScene(oddScreen);
			oddBP.setTop(menu);
		});
		play.setOnAction(e->{
			if(gameProgression.size() == 0) {
				primaryStage.setScene(setupGameScreen);
				gamePane.setTop(menu);
			}
			if (gameProgression.size() == 1) {
				primaryStage.setScene(gameScreen);
				gameBP.setTop(menu);
			}
		});
		
		seeRules.setOnAction(e->{
			primaryStage.setScene(rulesScreen);
			rulesBP.setTop(menu);
		});
		
		//
		//
		//game page items
		
		//Spot Question Event Handler to move to Draw Question
		for(Button x : spotInputArr) {
			x.setOnAction(e->{
				game.setSpots(Integer.valueOf(x.getText()));
				gameContents.getChildren().clear();
				gameContents.getChildren().add(drawInputs);
				gameContents.getChildren().add(gameGrid);
			});
		}
		
		//Draw Question Event handler to move to Game
		for(Button x : drawInputArr) {
			x.setOnAction(e->{
				game.setDraws(Integer.valueOf(x.getText()));
				
				gameContents.getChildren().clear();
				
				//
				//
				//the left-hand side of the game while picking spots
				Text promptForSpots = new Text("Choose " + game.getSpots() + " spots\n  or quick pick");
				promptForSpots.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
				promptForSpots.setFill(Color.WHITE);
				promptForSpots.setStrokeWidth(1);
				promptForSpots.setStroke(Color.RED);
				
				//event handler for quickPickButton
				quickPickButton.setOnAction(f->{
					
					game.card.quickPick();
					for(int i = 0; i < 8; i++) {
						for(int j = 0; j < 10; j++) {
							if(game.card.spotsChosen.contains(Integer.valueOf(spots[j][i].getText()))) {
								Button curr = spots[j][i];
								curr.setStyle("-fx-background-color: #ff0000");
								curr.setOnMouseEntered(g->curr.setStyle("-fx-background-color: #ff0000;"));
								curr.setOnMouseExited(g->curr.setStyle("-fx-background-color: #ff0000;"));
							}
						}
					}
					disableButtons(true, spots);
					
					gameProgression.add(0);
					quickPickButton.setDisable(true);
					continueButton.setDisable(false);
					
					
					clear.setDisable(false);

					if(primaryStage.getScene() != gameScreen) {
						gameBP.setTop(menu);
						playingBox.getChildren().add(gameGrid);
						gameBP.setCenter(playingBox);
						gameGrid.setAlignment(Pos.CENTER);				
						primaryStage.setScene(gameScreen);

					}
				});
				
				VBox gameInfoAndOptions = new VBox(promptForSpots, quickPickButton);
				
				VBox.setMargin(promptForSpots, new Insets(300));
				gameInfoAndOptions.setAlignment(Pos.CENTER);
				gameContents.getChildren().add(gameInfoAndOptions);
				//enables the gameGrid to be clickable
				disableButtons(false, spots);
				gameContents.getChildren().add(gameGrid);
			});
			
		}
		
		//event handler for gamegrid buttons
		//changes scene from picking spots to getting results
		for(int i = 1; i < 9; i++) {
			for(int j = 1; j< 11; j++) {
				int row = j-1;
				int col = i-1;
				spots[row][col] = new Button(String.valueOf(row+(10*col)+1));
				//event handler for what happens when clicking one of the spots on the game grid
				spots[row][col].setOnAction(e->{
					spots[row][col].setStyle("-fx-background-color: #ff0000");
					spots[row][col].setDisable(true);
					spots[row][col].setOnMouseEntered(f->spots[row][col].setStyle("-fx-background-color: #ff0000;"));
					spots[row][col].setOnMouseExited(f->spots[row][col].setStyle("-fx-background-color: #ff0000;"));
					game.choice(Integer.valueOf(spots[row][col].getText()));
					//checks if we are allowed to choose anymore spots, if not, disables grid and moves to results
					if(game.card.spotsChosen.size() == game.getSpots()) {
						disableButtons(true, spots);
						gameBP.setTop(menu);
						if (!playingBox.getChildren().contains(gameGrid)){
							playingBox.getChildren().add(gameGrid);
							gameBP.setCenter(playingBox);
							gameGrid.setAlignment(Pos.CENTER);
						}
						
						gameProgression.add(0);
						continueButton.setDisable(false);
						primaryStage.setScene(gameScreen);
					}
					quickPickButton.setDisable(true);
					clear.setDisable(false);

				});
				spots[row][col].setPrefSize(125, 125);
				spots[row][col].setTextFill(Color.WHITE);
				spots[row][col].setStyle("-fx-background-color: #000000");
				spots[row][col].setOnMouseEntered(e->spots[row][col].setStyle("-fx-background-color: #ff0000;"));
				spots[row][col].setOnMouseExited(e->spots[row][col].setStyle("-fx-background-color: #000000;"));
				spots[row][col].setFont(Font.font("Times New Roman", FontWeight.BOLD, 25));
				spots[row][col].setDisable(true);
				gameGrid.add(spots[row][col], row, col);
				}
			}
		}
	
	
	public Scene oddPage(BorderPane oddBP) {
		//the Odds
		
		//the panes of the odd page
		GridPane oddGP = new GridPane();
		oddGP.setMinSize(700, 700);
		oddGP.setVgap(150);
		oddGP.setHgap(300);
		oddGP.setAlignment(Pos.CENTER);
		
		//the images of the odd page
		oddGP.add(new ImageView("file:src/main/resources/oneOdd.png"),0,0);
		oddGP.add(new ImageView("file:src/main/resources/4odds.png"),1,0);
		oddGP.add(new ImageView("file:src/main/resources/8odds.png"),0,1);
		oddGP.add(new ImageView("file:src/main/resources/10odds.png"),1,1);
		
		//title of odd page
		Text oddTitle = new Text("Odds");
		oddTitle.setFont(Font.font("Times New Roman",200));
		oddTitle.setFill(Color.WHITE);
		oddTitle.setStrokeWidth(3);
		oddTitle.setStroke(Color.RED);
		
		
		VBox oddBox = new VBox(oddTitle,oddGP);
		oddBox.getChildren().get(0);
		oddBox.setAlignment(Pos.CENTER);
		VBox.setMargin(oddGP, new Insets(50));
		
		
		oddBP.setCenter(oddBox);
		oddBP.setStyle("-fx-background-color: #202020;");
	
		//the scene
		Scene oddScreen = new Scene(oddBP,1920,1080);
		//primaryStage.setScene(oddScreen);
		return oddScreen;
	}
	
	public Scene rulesPage(BorderPane rulesBP) {
		
		//title of odd page
		Text rulesTitle = new Text("Rules");
		rulesTitle.setFont(Font.font("Times New Roman", 150));
		rulesTitle.setFill(Color.WHITE);
		rulesTitle.setStrokeWidth(3);
		rulesTitle.setStroke(Color.RED);
		
		
		//All the rules placed into an array
		Text[] ruleArr = new Text[5];
		ruleArr[0] = new Text("1. Choose how many spots to play (Your options are 1, 4, 8, and 10). Click 'See Odds' in the options menu to see more!");
		ruleArr[1] = new Text("2. Choose how many consecutive draws to play (between 1 and 4). This cannot be changed once you start to play.");
		ruleArr[2] = new Text("3. Pick as many numbers from the grid as you specified for the amount of spots to play on the grid shown\n on the right-hand side."
				+ " You can choose Quick Pick to let the computer randomly pick some for you!");
		ruleArr[3] = new Text("4. The game will repeat for the amount of consecutive draws you chose to play with the same spots that were chosen.");
		ruleArr[4] = new Text("5. When you reach the amount of consecutive draws, you may choose to play another round or quit the game.");
		

		VBox rulesBox = new VBox(rulesTitle);
		//for each loop to customize each text rule and add to VBox
		for(Text x : ruleArr) {
			x.setFont(Font.font("Times New Roman", FontWeight.BOLD, 35));
			x.setFill(Color.WHITE);
			x.setStrokeWidth(0.5);
			x.setStroke(Color.RED);
			rulesBox.getChildren().add(x);
			VBox.setMargin(x, new Insets(25));
		}
		rulesBox.setAlignment(Pos.TOP_LEFT);
		rulesBox.setSpacing(10);
		//rulesTitle.setTextAlignment(TextAlignment.CENTER);
		VBox.setMargin(rulesTitle, new Insets(50, 0, 50, 750));
		
		
		rulesBP.setCenter(rulesBox);
		rulesBP.setStyle("-fx-background-color: #202020;");
	
		//the scene
		Scene rulesScreen = new Scene(rulesBP,1920,1080);
		return rulesScreen;
		
	}
	
	//used to disable or enabled the gridPane of buttons to select one's spots
	public void disableButtons(boolean disable, Button spots[][]) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j< 10; j++) {
				if(disable == false) {
					Button curr = spots[j][i];
					curr.setDisable(false);
					curr.setStyle("-fx-background-color: #000000");
					curr.setOnMouseEntered(e->curr.setStyle("-fx-background-color: #ff0000;"));
					curr.setOnMouseExited(e->curr.setStyle("-fx-background-color: #000000;"));
				}
				else {
					spots[j][i].setDisable(true);
				}
			}
		}
	}
	
	
}

