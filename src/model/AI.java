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
		board.getNode(2, 3);
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
		int row = 0 + (int)(Math.random() * ((4 - 0) + 1));
		int column = 0;
		do {
			if (row == 2) {
				column = 0 + (int)(Math.random() * ((3 - 0) + 1));
			} else {
				column = 0 + (int)(Math.random() * ((2 - 0) + 1));
			}
			System.out.println(row);
			System.out.println(column);
			System.out.println("failed");
			n = board.getNode(row, column);
		} while (!n.isValid());
//		colorCircle(row, column);
		return new int[]{row, column};
	}
}
