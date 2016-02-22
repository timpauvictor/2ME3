package application;

import javafx.fxml.FXML;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

//Thr Controller part of the application
public class BoardController {
	@FXML //these @FXML fields must be added to relate it back to the FXML file
	private Circle//Graphic representation of the Nodes
	R0C0, R0C1, R0C2,
	R1C0, R1C1, R1C2,
	R2C0, R2C1, R2C2, R2C3,
	R3C0, R3C1, R3C2,
	R4C0, R4C1, R4C2;
	private static playerColor currentColor = null;//Set default player colour to null
	
	
	enum playerColor {//Two colours of the player
			Blue, Red;
	}

	//Set curentColor to the given color
	public static void setCurretnColor(playerColor str) {
		currentColor = str;
	}
	
	// Event Listener on Circle.onMouseClicked
	@FXML
	public void blueClick(MouseEvent event) {
		System.out.println("Blue Piece Clicked");
		currentColor = playerColor.Blue;
	}
	
	// Event Listener on Circle[#redButton].onMouseClicked
	@FXML
	public void redClick(MouseEvent event) {
		System.out.println("Red Piece Clicked");
		currentColor = playerColor.Red;
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void checkButton(ActionEvent event) {
		System.out.println("Check Button Pressed");
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void clearButton(ActionEvent event) {
		System.out.println("Clear Button Pressed");
		Circle[] nodes = {
				R0C0, R0C1, R0C2,
				R1C0, R1C1, R1C2,
				R2C0, R2C1, R2C2, R2C3,
				R3C0, R3C1, R3C2,
				R4C0, R4C1, R4C2
		};
		ViewModifier.clearColors(nodes);
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void restartButton(ActionEvent event) {
		System.out.println("Restart Button Pressed");
		Circle[] nodes = {
				R0C0, R0C1, R0C2,
				R1C0, R1C1, R1C2,
				R2C0, R2C1, R2C2, R2C3,
				R3C0, R3C1, R3C2,
				R4C0, R4C1, R4C2
		};
		ViewModifier.clearColors(nodes);
		currentColor = null;
		orderPlayRandom();
		
	}
	// Event Listener on Button.onAction
	public void nodeClick(MouseEvent event) {
		System.out.println("Node Clicked");
		ViewModifier.changeNodeColor((Circle) event.getSource(), currentColor);
	}
	
	//Randomly generate the order of play and set the colour for the player
	private void orderPlayRandom() {
		System.out.println("Generate order of play randomly..");
		Random random = new Random();
		int num = random.nextInt(99) + 0;
		if (num < 50) {
			System.out.println("Red play first!");
			currentColor = playerColor.Red;
		} else {
			System.out.println("Blue play first!");
			currentColor = playerColor.Blue;
		}
	}
	
}
