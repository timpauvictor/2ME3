package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Board;
import model.Node;
import model.Phases;
import model.PlayerColor;
import model.Setting;

import java.util.ArrayList;
import java.util.Random;

//The Controller part of the application
public class BoardController {
	@FXML // these @FXML fields must be added to relate it back to the FXML file
	private Circle// Graphic representation of the Nodes
	R0C0, R0C1, R0C2, R1C0, R1C1, R1C2, R2C0, R2C1, R2C2, R2C3, R3C0, R3C1, R3C2, R4C0, R4C1, R4C2;
	private static PlayerColor currentColor = null;// Set default player colour
													// to null

	@FXML
	private Circle redToken1, redToken2, redToken3, redToken4, redToken5, redToken6, blueToken1, blueToken2, blueToken3,
			blueToken4, blueToken5, blueToken6;
	
	private int blueTokenCount = 6;
	private int redTokenCount = 6;

	@FXML
	private Label message = new Label();

	private Board board = new Board();

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

	// Event Listener on Circle.onMouseClicked
	// @FXML
	// private void blueClick(MouseEvent event) {
	// updateMessage("Blue Piece Clicked");
	// currentColor = PlayerColor.Blue;
	// }

	// Event Listener on Circle[#redButton].onMouseClicked
	// @FXML
	// private void redClick(MouseEvent event) {
	// updateMessage("Red Piece Clicked");
	// currentColor = PlayerColor.Red;
	// }

	@FXML
	private void startButton(ActionEvent event) {
		// get first player
		currentPhase = Phases.Planning;
		currentColor = orderPlayRandom();
		sayStart(currentColor);
	}

	private void sayStart(PlayerColor starter) {
		if (starter == PlayerColor.Red) {
			updateMessage("Planning Phase: Red Starts");
		} else if (starter == PlayerColor.Blue) {
			updateMessage("Planning Phase: Blue Starts");
		}
	}

	private Circle[] redTokens() {
		Circle[] toReturn = {
				redToken1, redToken2, redToken3, 
				redToken4, redToken5, redToken6
		};
		return toReturn;
	}
	
	private Circle[] blueTokens() {
		Circle[] toReturn = {
				blueToken1, blueToken2, blueToken3,
				blueToken4, blueToken5, blueToken6
		};
		return toReturn;
	}

	private PlayerColor toColor(Node input) {
		if (input.getColor() == Setting.Red)
			return PlayerColor.Red;
		else if (input.getColor() == Setting.Blue)
			return PlayerColor.Blue;
		else
			return PlayerColor.Black;
	}
	

	@FXML
	private void saveButton(ActionEvent event) {
		updateMessage("Save Button Pressed");
		board.toFile("data/storeGame.txt", currentColor);
	}

	// Event Listener on Button.onAction
	@FXML
	private void loadButton(ActionEvent event) {

		board = new Board("data/storeGame.txt");
		Circle[] nodes = { R0C0, R0C1, R0C2, R1C0, R1C1, R1C2, R2C0, R2C1, R2C2, R2C3, R3C0, R3C1, R3C2, R4C0, R4C1,
				R4C2 };
		int count = 0;
		for (int i = 0; i < 5; i++) {
			if (i != 2) {
				for (int j = 0; j < 3; j++) {
					ViewModifier.changeNodeColor(nodes[count], toColor(board.getNode(i, j)));
				}
			} else {
				for (int j = 0; j < 4; j++) {

					ViewModifier.changeNodeColor(nodes[count], toColor(board.getNode(i, j)));

				}
			}
			count++;
		}
		updateMessage("Load Button Pressed " + currentColor + " play first");
	}

	// Event Listener on Button.onAction
	public void nodeClick(MouseEvent event) {
		Node temp = new Node((Circle) event.getSource());
		if (!temp.isValid()) {
			updateMessage("Invalid move!");
		} else {
			if (currentPhase == Phases.Planning) {
				if (currentColor == PlayerColor.Red && redTokenCount > 0) {
					ViewModifier.changeNodeColor((Circle) event.getSource(), PlayerColor.Red);
					redTokenCount--;
					ViewModifier.removeToken(redTokens());
					alternateTurn();
				} else if (currentColor == PlayerColor.Blue && blueTokenCount > 0) {
					ViewModifier.changeNodeColor((Circle) event.getSource(), PlayerColor.Blue);
					blueTokenCount--;
					ViewModifier.removeToken(blueTokens());
					alternateTurn();
				}
				board.update(jaggedCircles());
				checkMills();
				if (redTokenCount == 0 && blueTokenCount == 0) {
					currentPhase = Phases.Fighting;
					updateMessage("Fighting Stage Begins");
				}
				
			} else if (currentPhase == Phases.Fighting) {
				Circle clickedNode = (Circle) event.getSource();
				//highlightAdj(clickedNode);
			}
		}

	}

	private void checkMills() {
		if (currentColor == PlayerColor.Red && board.hasMills(Setting.Blue)) {
			updateMessage("Find a mill! Remove a Red disc");
			}
		if (currentColor == PlayerColor.Blue && board.hasMills(Setting.Red)) {
			updateMessage("Find a mill! Remove a Blue disc");
		}
		
	}

	private void alternateTurn() {
		if (currentColor == PlayerColor.Red) {
			currentColor = PlayerColor.Blue;
			updateMessage("Blue's Turn");
		} else if (currentColor == PlayerColor.Blue) {
			currentColor = PlayerColor.Red;
			updateMessage("Red's Turn");
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
