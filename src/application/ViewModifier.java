package application;

import application.BoardController.playerColor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class ViewModifier {

	public static void changeNodeColor(Circle node, playerColor currentColor) {
		if (currentColor == playerColor.Red) {
			node.setFill(Color.RED);
		} else if (currentColor == playerColor.Blue) {
			node.setFill(Color.BLUE);
		}
		
	}

	
	

}
