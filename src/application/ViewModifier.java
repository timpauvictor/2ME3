package application;

import application.BoardController.playerColor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/*
	This class is a helper class for BoardController.java. It manipulate the colour
	of each Node
*/

public class ViewModifier {

	//Change the given Node to the given colour and output error message if needed
	public static void changeNodeColor(Circle node, playerColor currentColor) {
		if (currentColor == playerColor.Red) {
			node.setFill(Color.RED);
		} else if (currentColor == playerColor.Blue) {
			node.setFill(Color.BLUE);
		} else {
			System.out.println("Error! Color not found"); //I sure hope this never happens
		}
	}

	//Clear the colour for the given Node array
	public static void clearColors(Circle[] nodes) {
		for (int i = 0; i < nodes.length; i++) {
			nodes[i].setFill(Color.BLACK);
		}
	}
}
