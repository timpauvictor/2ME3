package application;
	
import java.util.Random;

import application.BoardController.playerColor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    Parent root = FXMLLoader.load(getClass().getResource("Board.fxml"));
			Scene scene = new Scene(root,615,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void orderPlayRandom() {
		System.out.println("Generate order of play randomly..");
		Random random = new Random();
		int num = random.nextInt(99) + 0;
		if (num < 50) {
			System.out.println("Red play first!");
			BoardController.setCurretnColor(playerColor.Red);
		} else {
			System.out.println("Blue play first!");
			BoardController.setCurretnColor(playerColor.Blue);
		}
	}
	public static void main(String[] args) {
		orderPlayRandom();
		launch(args);
		
	}

	
}
