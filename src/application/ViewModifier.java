package application;

import application.BoardController.playerColor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ViewModifier {

	public static void changeNodeColor(Circle node, playerColor currentColor) {
		if (currentColor == playerColor.Red) {
			node.setFill(Color.RED);
		} else if (currentColor == playerColor.Blue) {
			node.setFill(Color.BLUE);
		} else {
			System.out.println("Error! Color not found"); //I sure hope this never happens
		}
	}
}
