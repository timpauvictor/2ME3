package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.AI;
import model.Board;
import model.Node;
import model.Phases;
import model.PlayerColor;
import model.Setting;
import java.util.Random;

//The Controller part of the application
public class BoardController {
	@FXML // these @FXML fields must be added to relate it back to the FXML file
	private Circle// Graphic representation of the Nodes
	R0C0, R0C1, R0C2, R1C0, R1C1, R1C2, R2C0, R2C1, R2C2, R2C3, R3C0, R3C1, R3C2, R4C0, R4C1, R4C2;
	private static PlayerColor currentColor = null;// Set default player colour
													// to null
	private static PlayerColor millFor = null;
	
	
	
	@FXML
	private Circle redToken1, redToken2, redToken3, redToken4, redToken5, redToken6, 
					blueToken1, blueToken2, blueToken3, blueToken4, blueToken5, blueToken6;
	
	@FXML
	private Circle blueTrophy1, blueTrophy2, blueTrophy3, blueTrophy4, blueTrophy5, blueTrophy6, 
					redTrophy1, redTrophy2, redTrophy3, redTrophy4, redTrophy5, redTrophy6;
	
	private int blueTokenCount = 6;
	private int redTokenCount = 6;
	
	private Phases prevPhase = null;
	
	@FXML
	private Label message = new Label();

	private Board board;
	private AI ai;
	private int players;
	
	private Circle prevCircle = null;
	private Phases currentPhase = null;

	// private PlayerColor currentPlayer = null;

	// Update the label to given string

	private void updateMessage(String str) {
		StringProperty value = new SimpleStringProperty();
		message.textProperty().bind(value);
		value.set(str);
	}

	// Set curentColor to the given color
	public static void setCurrentColor(PlayerColor str) {
		currentColor = str;
	}


	@FXML
	private void startButton(ActionEvent event) {
		genericStart();
		players = 2;
	}
	
	@FXML
	private void vsAIStart() {
		genericStart();
		players = 1;
		if (currentColor == PlayerColor.Blue) {
			AIPlanTurn(board);
		}
	}
	
	public void genericStart() {
		// get first player
				ViewModifier.makeTrans(redTrophies());
				ViewModifier.makeTrans(blueTrophies());
				currentPhase = Phases.Planning;
				currentColor = orderPlayRandom();

		        blueTokenCount = 6;
		        redTokenCount = 6;
		        ViewModifier.clearColors(new Circle[]{R0C0, R0C1, R0C2, R1C0, R1C1, R1C2, R2C0, R2C1, R2C2, R2C3, R3C0, R3C1, R3C2, R4C0, R4C1, R4C2});
		        for (Circle c: redTokens())
		            ViewModifier.changeNodeColor(c, PlayerColor.Red);
		        for (Circle c: blueTokens())
		            ViewModifier.changeNodeColor(c, PlayerColor.Blue);
		        board = new Board();
		        board.update(jaggedCircles());
				sayStart(currentColor);
	}

	private void sayStart(PlayerColor starter) {
		if (starter == PlayerColor.Red) {
			updateMessage("Planning Phase: Red Starts");
		} else if (starter == PlayerColor.Blue) {
			updateMessage("Planning Phase: Blue Starts");
			if (players == 1) {
				AIPlanTurn(board);	
			}
		}
	}

	private Circle[] redTokens() { //make an array from the tokens and return it
		Circle[] toReturn = {
				redToken1, redToken2, redToken3, 
				redToken4, redToken5, redToken6
		};
		return toReturn;
	}
	
	private Circle[] blueTokens() { //make an array from the tokens and return it
		Circle[] toReturn = {
				blueToken1, blueToken2, blueToken3,
				blueToken4, blueToken5, blueToken6
		};
		return toReturn;
	}
	 
	private Circle[] blueTrophies() { //make an array from the trophies and return it
		Circle[] toReturn = {
				blueTrophy1, blueTrophy2, blueTrophy3,
				blueTrophy4, blueTrophy5, blueTrophy6
		};
		return toReturn;
	}
	
	private Circle[] redTrophies() { //make an array from the trophies and return it
		Circle[] toReturn = {
				redTrophy1, redTrophy2, redTrophy3,
				redTrophy4, redTrophy5, redTrophy6
		};
		return toReturn;
	}
	
	public int getTrophyCount(PlayerColor color) {
		if (color == PlayerColor.Red) {
			return countRedTrophies();
		} else if (color == PlayerColor.Blue) {
			return countBlueTrophies();	
		}
		return 0;
	}
	
	public int countRedTrophies() {
		Circle[] troph = redTrophies();
		int counter = 0; 
		for (int i = 0; i < troph.length; i++) {
			if (troph[i].getFill() == Color.RED) {
				counter++;
			}
		}
		return counter;
	}
	
	public int countBlueTrophies() {
		Circle[] troph = blueTrophies();
		int counter = 0;
		for (int i = 0; i < troph.length; i++) {
			if (troph[i].getFill() == Color.BLUE) {
				counter++;
			}
		}
		return counter;
	}

	private PlayerColor toColor(Node input) { //change the color to the input
		if (input.getColor() == Setting.Red)
			return PlayerColor.Red;
		else if (input.getColor() == Setting.Blue)
			return PlayerColor.Blue;
		else
			return PlayerColor.Black;
	}
	

	@FXML
	private void saveButton(ActionEvent event) { //save button functionality
		updateMessage("Game saved");
        board.update(jaggedCircles());
		board.toFile("data/storeGame.txt", currentColor);
	}

	// Event Listener on Button.onAction
	@FXML
	private void loadButton(ActionEvent event) {

		board = new Board("data/storeGame.txt");
		Circle[][] nodes = {{R0C0, R0C1, R0C2}, {R1C0, R1C1, R1C2}, {R2C0, R2C1, R2C2, R2C3}, {R3C0, R3C1, R3C2}, {R4C0, R4C1,
				R4C2}};
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (i != 2) {
				for (int j = 0; j < 3; j++) {
					ViewModifier.changeNodeColor(nodes[i][j], toColor(board.getNode(i, j)));
				}
			} else {
				for (int j = 0; j < 4; j++) {
					ViewModifier.changeNodeColor(nodes[i][j], toColor(board.getNode(i, j)));
				}
			}

		}
		updateMessage("Load Button Pressed " + currentColor + " play first");
		currentColor = board.getTurn();
	}
	
	public void AIPlanTurn(Board board) {
		ai = new AI(board, jaggedCircles());
		int[] plan = ai.doTurn(Phases.Planning);
		ViewModifier.changeNodeColor(jaggedCircles()[plan[0]][plan[1]], PlayerColor.Blue);
		board.update(jaggedCircles());
		blueTokenCount--;
		ViewModifier.removeToken(blueTokens());
		if (!checkMills()) {
			alternateTurn();
		}
	}
	
	public void AISelMoveTurn(Board board) {
		ai = new AI(board, jaggedCircles());
		int[] plan = ai.doTurn(Phases.selMove);
		ViewModifier.changeNodeColor(jaggedCircles()[plan[0]][plan[1]], PlayerColor.Black);
		board.update(jaggedCircles());
		plan = ai.doTurn(Phases.moveTo);
		ViewModifier.changeNodeColor(jaggedCircles()[plan[0]][plan[1]], PlayerColor.Blue);
		board.update(jaggedCircles());
		if (!checkMills()) {
			alternateTurn();
		}
	}

	// Event Listener on Button.onAction
	public void nodeClick(MouseEvent event) { //this event is called whenever a node is clicked on
		System.out.println(checkMills());
        board.update(jaggedCircles());
        System.out.println("click during phase: " + currentPhase);
        if (currentPhase == Phases.Planning) { //check if in planning phase
			Node temp = new Node((Circle) event.getSource());
			if (currentColor == PlayerColor.Red && redTokenCount > 0 && temp.isValid()) { //if it's red's turn and he has tokens available
				ViewModifier.changeNodeColor((Circle) event.getSource(), PlayerColor.Red); //change the color of what he clicks on
				board.update(jaggedCircles());
				redTokenCount--; //decrement his count
				ViewModifier.removeToken(redTokens()); //remove a token from the side
				if (!checkMills()) {
					alternateTurn();
				}
				if (players == 1 && blueTokenCount > 0) {
					AIPlanTurn(board);
				}
			} else if (currentColor == PlayerColor.Blue && blueTokenCount > 0 && temp.isValid() && players == 2) { //if it's blue's turn and he has tokens available
				ViewModifier.changeNodeColor((Circle) event.getSource(), PlayerColor.Blue); //change the color of what he clicks on to blue
				board.update(jaggedCircles());
				blueTokenCount--; //decrement his count of available tokens
				ViewModifier.removeToken(blueTokens()); //remove a token from the side
				if (!checkMills()) {
					alternateTurn();
				}
			} else if (currentColor == PlayerColor.Blue && players == 1) {
				AIPlanTurn(board);
			}
			if (redTokenCount <= 0 && blueTokenCount <= 0) { //if both players have 0 tokens left
				currentPhase = Phases.selMove; //we change phases
				updateMessage(currentColor.toString() + ": Choose a circle to move"); //tell the user
				if (players == 1) {
					AISelMoveTurn(board);
				}
				
			}
		} else if (currentPhase == Phases.selMove) { //check if in the selecting node to move phase
//			System.out.println("controller - entered selMove");
			prevCircle = (Circle) event.getSource(); //store the clicked on node to prevNode
			if (currentColor == PlayerColor.Red && prevCircle.getFill() == Color.RED) {
				//this is valid
				currentPhase = Phases.moveTo; //change phase to moving
			} else if (currentColor == PlayerColor.Blue && prevCircle.getFill() == Color.BLUE) {
				currentPhase = Phases.moveTo; //change phase to moving
			} else { //any other combinations are invalid
				updateMessage("Invalid, click your color: " + currentColor.toString()); //inform the user of the misclick
			}
			
			if (currentColor == PlayerColor.Blue && players == 1) {
				currentPhase = Phases.moveTo;
				AISelMoveTurn(board);
			}
		} else if (currentPhase == Phases.moveTo) { //check if in the moving phase
			updateMessage("Choose an adjacent location to move to");
			Circle newCircle = (Circle) event.getSource(); //make a new circle from the one we just clicked on
			Node newNode = board.getNode(newCircle.getId()); //make a new Node from the circle we just made
			Node prevNode = board.getNode(prevCircle.getId()); //make a new node from the previous circle that was clicked
			if (newNode.adjacentTo(prevNode) && newNode.isValid()) { //if the new node is adjacent to the old one
				ViewModifier.changeNodeColor(prevCircle, PlayerColor.Black); //remove the node by setting the color to black
				ViewModifier.changeNodeColor(newCircle, currentColor); //change the color of the new node to the current player
				board.update(jaggedCircles());
				if (!checkMills()) {
					if (players == 1) {
						AISelMoveTurn(board);
					}
					alternateTurn(); //change turns
					currentPhase = Phases.selMove; //switch phase
				} else {
					currentPhase = Phases.millFound;
				}
			} else { //else we give an error message and make them do it again
				updateMessage("Invalid Node, select a valid node and try again");
				currentPhase = Phases.selMove;
			}
			} else if (currentPhase == Phases.millFound) { //if we're in the mill phase
				if (millFor == PlayerColor.Red) { //and there's a mill for red 
					Circle clickedCircle = (Circle) event.getSource(); //make a new circle from the event
					if (clickedCircle.getFill() == Color.BLUE) { //if the circle we clicked on is blue (and we're red)
						ViewModifier.changeNodeColor(clickedCircle, PlayerColor.Black); // black to get rid of it
						ViewModifier.addTrophy(redTrophies(), PlayerColor.Blue); //give a kill trophy
						currentPhase = Phases.selMove; // change the phase to where we were
						if (redTokenCount <= 0 || blueTokenCount <= 0) {
							currentPhase = Phases.Planning;
						}
						alternateTurn(); // change turns
					} // the below chunk is identical 'cept for swapping the colors
						// around
				} else if (millFor == PlayerColor.Blue && players == 1) {
					ai = new AI(board, jaggedCircles());
					int[] x = ai.doTurn(Phases.millFound);
					ViewModifier.changeNodeColor(jaggedCircles()[x[0]][x[1]], PlayerColor.Black);
					ViewModifier.addTrophy(blueTrophies(), PlayerColor.Red);
					redTokenCount--;
					if (redTokenCount <= 0 && blueTokenCount <= 0) {
						currentPhase = Phases.selMove;
						if (redTokenCount <= 0) {
							currentColor = PlayerColor.Blue;
						} else if (blueTokenCount <= 0) {
							currentColor = PlayerColor.Red;
						}
					} else {
						currentPhase = Phases.Planning; // change the phase to where we were
					}
					alternateTurn();
				} else if (millFor == PlayerColor.Blue && players == 2) {
					Circle clickedCircle = (Circle) event.getSource();
					if (clickedCircle.getFill() == Color.RED) {
						ViewModifier.changeNodeColor(clickedCircle, PlayerColor.Black);
						ViewModifier.addTrophy(blueTrophies(), currentColor);
						redTokenCount--;
						if (redTokenCount <= 0 && blueTokenCount <= 0) {
							currentPhase = Phases.selMove;
							if (redTokenCount <= 0) {
								currentColor = PlayerColor.Blue;
							} else if (blueTokenCount <= 0) {
								currentColor = PlayerColor.Red;
							}
						} else {
							currentPhase = Phases.Planning;
						}
						alternateTurn();
					}
				}

		} else if (currentPhase == Phases.gameOver) { // if the game is over
			// do nothing
		}
		if (checkWin(redTrophies())) { // if red has won
			updateMessage("Red wins");
			currentPhase = Phases.gameOver;
		}
		if (checkWin(blueTrophies())) { // if blue has won
			updateMessage("Blue wins");
			currentPhase = Phases.gameOver;
		}
		board.update(jaggedCircles()); // updates the nodes for our board object
	}

	public boolean checkWin(Circle[] circleArr) { // check if we've won
		int counter = 0; // make a counter
		for (int i = 0; i < circleArr.length; i++) { // go through the trophies
			if (circleArr[i].getFill() != Color.TRANSPARENT) { // if there are
																// non-transparent
																// circles
				counter++; // increment the counter
			}
		}
		if (counter == 4) { // if the counter is 6, we've won!
			return true;
		} else {
			return false; // else we haven't
		}
	}

	private boolean checkMills() { // function to check for mills
//		System.out.println(currentPhase);
		if (currentColor == PlayerColor.Red && board.hasMills(Setting.Red)) { // if it's reds turn and he has a mill
			if (currentPhase == Phases.moveTo) {
				prevPhase = Phases.selMove;
			} else {
				prevPhase = currentPhase; // keep track of the phase we left in
			}
			currentPhase = Phases.millFound; // change the current phase to one
												// where mill is found
			updateMessage("Mill for red"); // tell user
			millFor = PlayerColor.Red; // set who the mill is for
			return true;
		} else if (currentColor == PlayerColor.Blue && board.hasMills(Setting.Blue)) {
			prevPhase = currentPhase;
			currentPhase = Phases.millFound;
			updateMessage("Mill for blue");
			millFor = PlayerColor.Blue;
			return true;
		} else { // if a mill is not found
			return false;
		}
	}

	private void alternateTurn() { // change turns
		if (currentColor == PlayerColor.Red) { // if the current color is red
			currentColor = PlayerColor.Blue; // change it to blue
			updateMessage("Blue's Turn"); // notify the user
		} else if (currentColor == PlayerColor.Blue) { // if the current color
														// is blue
			currentColor = PlayerColor.Red; // change it to red
			updateMessage("Red's Turn"); // notify the user
		}

	}

	// Randomly generate the order of play and set the colour for the player
	private PlayerColor orderPlayRandom() {
		Random random = new Random();
		int num = random.nextInt(99) + 0;
		if (num < 50) {
			return PlayerColor.Red;
		} else {
			return PlayerColor.Blue;
		}
	}

	// return circles in board format
	private Circle[][] jaggedCircles() {
		Circle[][] nodes = { { R0C0, R0C1, R0C2 }, { R1C0, R1C1, R1C2 }, { R2C0, R2C1, R2C2, R2C3 },
				{ R3C0, R3C1, R3C2 }, { R4C0, R4C1, R4C2 } };
		return nodes;
	}

}
