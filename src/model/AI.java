package model;

import application.ViewModifier;
import javafx.scene.shape.Circle;

public class AI {
	Board board;
	boolean exists;
	Circle[][] circles;
	public AI(Board b, Circle[][] circles) {
		this.exists = true;
		this.board = b;
		this.circles = circles;
	}
	
	/**
	 * Complete a turn for the AI depending on what phase we are in. Just pass in the phase and the following gets completed:
	 * 	if (p == Planning):
	 * 		We get a valid node to be placed on and change it's color
	 * 	if (p == selMode):
	 * 		
	 * @param p - the current phase our game is in
	 */
	
	public int[] doTurn(Phases p) {
		if (p == Phases.Planning) {
			int[] x = genPlan();
			return x;
		} else if (p == Phases.selMove) {
			
		}
		return new int[]{0, 0};
	}
	
	public boolean exists() {
		return this.exists;
	}

	private int[] genPlan() {
		Node n;
		int row = randomWithinRange(0, 3);
		int column = 0;
		do {
			System.out.println("failed");
			if (row == 2) {
				column = randomWithinRange(0, 3);
			} else {
				column = randomWithinRange(0, 2);
			}
			n = board.getNode(row, column);
		} while (!n.isValid());
//		colorCircle(row, column);
		return new int[]{row, column};
	}
	
	
	
//	private void colorCircle(int row, int column) {
//		ViewModifier.changeNodeColor(circles[row][column], PlayerColor.Blue);
//	}

	private int randomWithinRange(int min, int max) {
		int range = (max - min) + 1;
		return (int)(Math.random() * range) + min;
	}
}
