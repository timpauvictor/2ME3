package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;



public class Save_Load {
	/**
	 * load a game from a text file and return information
	 * in order to update the board
	 * @return an Arraylist contains PlayerColor
	 */
	public static ArrayList<PlayerColor> loadGame() {
		ArrayList<PlayerColor> result = new ArrayList<PlayerColor>();
		try {
			BufferedReader input = new BufferedReader(new FileReader("data/storeGame.txt"));
			
			String line = input.readLine();
			result.add(PlayerColor.converter(line));
			
			while ((line = input.readLine()) != null) {
				String[] temp = line.split(", ");
				for (int i = 0; i < temp.length; i++) {
					//Call a helper function to convert a string to a PlayerColor
					result.add(PlayerColor.converter(temp[i]));
				}
				
			}
			
			input.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Save the current game, including current color of play and
	 * each node's state
	 * @param current: current order of play
	 * @param nodes: array of nodes that have different color
	 */
	public static void saveGame(PlayerColor current, Circle[] nodes) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter("data/storeGame.txt", false));
			output.write(current.toString());//write the current color of play to the file
			output.newLine();
			
			int count = 0;//keep track of each row of board
			int num = 0;//keep track each element of array
			while (count <= 4) {
				if (count == 2) {//the third row has different number of nodes
					for (int i = 0; i < 4; i++) {
						if (nodes[num].getFill() == Color.RED) {
							if (i != 3) {output.write("Red, ");}
							else {
								output.write("Red");
								output.newLine();
							}
							
						}else if (nodes[num].getFill() == Color.BLUE) {
							if (i != 3) {output.write("Blue, ");}
							else {
								output.write("Blue");
								output.newLine();
							}
							
						} else {
							if (i != 3) {output.write("Black, ");}
							else {
								output.write("Black");
								output.newLine();
							}
						}num++;
					}
					
				} else {
					for (int i = 0; i < 3; i++) {
						if (nodes[num].getFill() == Color.RED) {
							if (i != 2) {output.write("Red, ");}
							else {
								output.write("Red");
								output.newLine();
							}
							
						}else if (nodes[num].getFill() == Color.BLUE) {
							if (i != 2) {output.write("Blue, ");}
							else {
								output.write("Blue");
								output.newLine();
							}
							
						} else {
							if (i != 2) {output.write("Black, ");}
							else {
								output.write("Black");
								output.newLine();
							}
						}num++;
					}
					
				}
				count++;
				
			}
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		loadGame();
	}
}
