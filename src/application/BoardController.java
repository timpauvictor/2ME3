package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import model.Board;
import model.PlayerColor;
import model.Save_Load;

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
	private Label message = new Label();
	
	private Board board = new Board();
	
	//Update the label to given string 
	
	
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
	@FXML
	private void blueClick(MouseEvent event) {
		updateMessage("Blue Piece Clicked");
		currentColor = PlayerColor.Blue;
	}

	// Event Listener on Circle[#redButton].onMouseClicked
	@FXML
	private void redClick(MouseEvent event) {
		updateMessage("Red Piece Clicked");
		currentColor = PlayerColor.Red;
	}

	/*
	 * // Event Listener on Button.onAction
	 * 
	 * @FXML public void checkButton(ActionEvent event) {
	 * System.out.println(board.isLegalString()); }
	 */

	/*
	 * // Event Listener on Button.onAction
	 * 
	 * @FXML public void clearButton(ActionEvent event) { System.out.println(
	 * "Clear Button Pressed"); Circle[] nodes = { R0C0, R0C1, R0C2, R1C0, R1C1,
	 * R1C2, R2C0, R2C1, R2C2, R2C3, R3C0, R3C1, R3C2, R4C0, R4C1, R4C2 };
	 * ViewModifier.clearColors(nodes); board = new Board(); }
	 */

	// Event Listener on Button.onAction
	@FXML
	private void saveButton(ActionEvent event) {
		updateMessage("Save Button Pressed");
		Circle[] nodes = { R0C0, R0C1, R0C2, R1C0, R1C1, R1C2, R2C0, R2C1, R2C2, R2C3, R3C0, R3C1, R3C2, R4C0, R4C1,
				R4C2 };
		Save_Load.saveGame(currentColor, nodes);
	}

	// Event Listener on Button.onAction
	@FXML
	private void loadButton(ActionEvent event) {
		updateMessage("Load Button Pressed");
		ArrayList<PlayerColor> load = Save_Load.loadGame();
		Circle[] nodes = { R0C0, R0C1, R0C2, R1C0, R1C1, R1C2, R2C0, R2C1, R2C2, R2C3, R3C0, R3C1, R3C2, R4C0, R4C1,
				R4C2 };
		ViewModifier.clearColors(nodes);
		setCurrentColor(load.get(0));//the first color in arraylist is the currentColor
		
		for (int i = 0; i < nodes.length; i++) {
			//i + 1 to jump the first color in arraylist
			ViewModifier.changeNodeColor(nodes[i], load.get(i + 1));
		}
		//update node information accordingly
		board.update(jaggedCircles());
	}

	// Event Listener on Button.onAction
	@FXML
	private void restartButton(ActionEvent event) {
		updateMessage("Restart Button Pressed");
		Circle[] nodes = { R0C0, R0C1, R0C2, R1C0, R1C1, R1C2, R2C0, R2C1, R2C2, R2C3, R3C0, R3C1, R3C2, R4C0, R4C1,
				R4C2 };
		ViewModifier.clearColors(nodes);
		currentColor = null;
		orderPlayRandom();
		board = new Board();
	}

	// Event Listener on Button.onAction
	public void nodeClick(MouseEvent event) {
		updateMessage("Node Clicked");
		ViewModifier.changeNodeColor((Circle) event.getSource(), currentColor);
		board.update(jaggedCircles());
	}

	// Randomly generate the order of play and set the colour for the player
	private void orderPlayRandom() {
		Random random = new Random();
		int num = random.nextInt(99) + 0;
		if (num < 50) {
			updateMessage("Generate order of play...Red play first!");
			currentColor = PlayerColor.Red;
		} else {
			updateMessage("Generate order of play...Blue play first!");
			currentColor = PlayerColor.Blue;
		}
	}

	// return circles in board format
	private Circle[][] jaggedCircles() {
		Circle[][] nodes = { { R0C0, R0C1, R0C2 }, { R1C0, R1C1, R1C2 }, { R2C0, R2C1, R2C2, R2C3 },
				{ R3C0, R3C1, R3C2 }, { R4C0, R4C1, R4C2 } };
		return nodes;
	}
	

}
