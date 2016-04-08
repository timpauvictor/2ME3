package model;


import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class AI {
	Board board;
	boolean exists;
	Circle[][] circles;
	boolean debug = true;
	int[] selectedNode;
	public AI(Board b, Circle[][] circles) {
		this.exists = true;
		this.board = b;
		this.circles = circles;
	}
	
	private void log(String s) {
		System.out.println("AI: " + s);
	}
	
	/**
	 * Complete a turn for the AI depending on what phase we are in. Just pass in the phase and the following gets completed:
	 * 	if (p == Planning):
	 * 		We get a valid node to be placed on and the coordinates get returned to the controller
	 * 	if (p == moveTo):
	 * 		We select one of our current blue colored circles and move it to an empty adajcent location
	 * @param p - the current phase our game is in
	 */
	
	public int[] doTurn(Phases p) {
//		if (debug) { log("AI Phase: " + p); }
		if (p == Phases.Planning) {
			int[] x = genPlan();
			if (debug) { log("Planning - Picked " + x[0] + " " + x[1]); }
			return x;
		} else if (p == Phases.selMove) {
			int[] x = genSelection();
			if (debug) { log("selMove - Selected " + x[0] + " " + x[1] + "to move from"); }
			return x;
		} else if (p == Phases.moveTo) {
			int[] x = genMoveTo();
			if (debug) { log("moveTo - Selected " + x[0] + " " + x[1] + "to move to with a color of " + circles[x[0]][x[1]].getFill()); }
			return x;
		} else if (p == Phases.millFound) {
			int[] x = genKill();
			if (debug) { log("millFound - Killing node at " + x[0] + " " + x[1]); }
			return x;
		}
		return new int[]{0, 0};
	}
	
	private boolean isRed(int row, int column) {
		return circles[row][column].getFill() == Color.RED;
	}
	
	private int[] genKill() {
		boolean valid = false;
		int[] x;
		do {
			x = randomSpot();
			valid = isRed(x[0], x[1]);
		} while (!valid);
		return x;
	}
	
	private int[] genMoveTo() {
		if (debug) { log( "Previously selected: " + Arrays.toString(selectedNode)); }
		Node n;
		boolean valid = false;
		ArrayList<Node> adjacentNodes = board.getNode(selectedNode[0], selectedNode[1]).getAdjacent();
//		System.out.println(adjacentNodes.get(0));
		int direction;
		int[] newLocation;
		do {
			direction = randomNumberInRange(3, 0);
			if (direction == 0) { //try going up
				try {
					n = board.getNode(selectedNode[0] + 1, selectedNode[1]);
					if (adjacentNodes.contains(n) && n.getColor() == Setting.Empty) {
						valid = true;
						return new int[]{selectedNode[0] + 1, selectedNode[1]};
					} else {
						valid = false;
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					valid = false;
				}
			} else if (direction == 1) { //try going down
				try {
					n = board.getNode(selectedNode[0] - 1, selectedNode[1]);
					if (adjacentNodes.contains(n) && n.getColor() == Setting.Empty) {
						valid = true;
						return new int[]{selectedNode[0] - 1, selectedNode[1]};

					} else {
						valid = false;
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					valid = false;
				}
			} else if (direction == 2) { //try going left
				try {
					n = board.getNode(selectedNode[0], selectedNode[1] - 1);
					if (adjacentNodes.contains(n) && n.getColor() == Setting.Empty) {
						valid = true;
						return new int[]{selectedNode[0], selectedNode[1] - 1};
					} else {
						valid = false;
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					valid = false;
				}
			} else if (direction == 3) { //finally, try going right
				try {
					n = board.getNode(selectedNode[0], selectedNode[1] + 1);
					if (adjacentNodes.contains(n) && n.getColor() == Setting.Empty) {
						valid = true;
						return new int[]{selectedNode[0], selectedNode[1] + 1};
					} else {
						valid = false;
					}
				} catch (ArrayIndexOutOfBoundsException ex) {
					valid = false;
				}
			}
		} while (!valid);
		return new int[]{0,0};
	}
	
	public boolean exists() {
		return this.exists;
	}
	
	private int randomNumberInRange(int max, int min) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	private int[] genSelection() {
		Node n;
		boolean valid = false;
		int[] x;
		do {
			x = randomSpot();
			if (circles[x[0]][x[1]].getFill() == Color.BLUE) { //if we've selected a blue circle
				valid = true; //it's valid
			} else { //otherwise
				valid = false; //it's invalid
			}
			if (valid) {
				n = board.getNode(x[0], x[1]);
				int counter = 0;
				for (Node other: n.getAdjacent()) {
					if (other.getColor() == Setting.Empty) {
						counter++;
					}
				}
				if (counter > 0) {
					valid = true;
				} else {
					valid = false;
				}
			}
		} while (!valid); //keep trying until it's valid or if it is valid, keep trying until we find a spot with adjacent nodes
		selectedNode = x;
		return x;
	}
	
	private boolean checkAdj(int[] x) { return board.getNode(x[0], x[1]).getAdjacent().size() > 0; } //make sure we didn't pick a node with no valid adjacent locations
	
	private int[] randomSpot() {
		int row = 0 + (int)(Math.random() * ((4 - 0) + 1));
		int column;
		if (row == 2) {
			column = 0 + (int)(Math.random() * ((3 - 0) + 1)); // third row has 4 locations
		} else {
			column = 0 + (int)(Math.random() * ((2 - 0) + 1)); // while the rest have 3
		}
		return new int[]{row, column};
	}

	private int[] genPlan() {
		Node n;
		int[] plannedSpot;
		do {
			plannedSpot = randomSpot();
			n = board.getNode(plannedSpot[0], plannedSpot[1]);
		} while (!n.isValid());
//		colorCircle(row, column);
		return new int[]{plannedSpot[0], plannedSpot[1]};
	}
}
