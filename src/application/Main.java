package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PlayerColor;

import java.util.Random;

//This is the starting point of the whole application
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Load FXML file
		    Parent root = FXMLLoader.load(getClass().getResource("Board.fxml"));
			Scene scene = new Scene(root,615,400);//Set scene and size
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();//throw the proper exception
		}
	}

	//This decide the order of play randomly, and set the current color
	private static void orderPlayRandom() {
		Random random = new Random();
		int num = random.nextInt(99) + 0;//Generate random number
		if (num < 50) {
			//BoardController.setDefault();
			BoardController.setCurrentColor(PlayerColor.Red);
		} else {
			//BoardController.updateMessage("Generate order of play randomly..Blue play first!");
			BoardController.setCurrentColor(PlayerColor.Blue);
		}
	}
	public static void main(String[] args) {
		orderPlayRandom();//Decide the order of play
		launch(args);//Lauch the game board
		
	}

	
}
