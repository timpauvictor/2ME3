package application;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import javafx.scene.input.MouseEvent;

public class BoardController {

	// Event Listener on Circle.onMouseClicked
	@FXML
	public void blueClick(MouseEvent event) {
		System.out.println("Blue Piece Clicked");
	}
	
	// Event Listener on Circle[#redButton].onMouseClicked
	@FXML
	public void redClick(MouseEvent event) {
		System.out.println("Red Piece Clicked");
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void checkButton(ActionEvent event) {
		System.out.println("Check Button Pressed");
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void quitButton(ActionEvent event) {
		System.out.println("Quit Button Pressed");
	}
	
	// Event Listener on Button.onAction
	@FXML
	public void clearButton(ActionEvent event) {
		System.out.println("Clear Button Pressed");
	}
	
	public void nodeClick(MouseEvent event) {
		System.out.println("Node Clicked");
	}
	
}
