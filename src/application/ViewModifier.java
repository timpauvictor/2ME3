package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.PlayerColor;

/*
	This class is a helper class for BoardController.java. It manipulate the colour
	of each Node
*/

public class ViewModifier {

	//Change the given Node to the given colour and output error message if needed
	public static void changeNodeColor(Circle node, PlayerColor currentColor) {
		if (currentColor == PlayerColor.Red) {
			node.setFill(Color.RED);
		} else if (currentColor == PlayerColor.Blue) {
			node.setFill(Color.BLUE);
		} else if (currentColor == PlayerColor.Black){
			
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
