package application;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

public class BoardController {
	@FXML //these @FXML fields must be added to relate it back to the FXML file
	private Circle
	R0C0, R0C1, R0C2,
	R1C0, R1C1, R1C2,
	R2C0, R2C1, R2C2, R2C3,
	R3C0, R3C1, R3C2,
	R4C0, R4C1, R4C2;
	private playerColor currentColor = null;
	
	
	enum playerColor {
			Blue, Red;
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
	
	public void nodeClick(MouseEvent event) {
		System.out.println("Node Clicked");
		ViewModifier.changeNodeColor((Circle) event.getSource(), currentColor);
	}
	
}
