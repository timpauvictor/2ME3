package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PlayerColor;

import java.util.Random;

//This is the starting point of the who;e application
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

	//This decide the order of play randomly, and set the current colour
	private static void orderPlayRandom() {
		System.out.println("Generate order of play randomly..");
		Random random = new Random();
		int num = random.nextInt(99) + 0;//Generate random number
		if (num < 50) {
			System.out.println("Red play first!");
			BoardController.setCurretnColor(PlayerColor.Red);
		} else {
			System.out.println("Blue play first!");
			BoardController.setCurretnColor(PlayerColor.Blue);
		}
	}
	public static void main(String[] args) {
		orderPlayRandom();//Decide the order of play
		launch(args);//Lauch the game board
		
	}

	
}
