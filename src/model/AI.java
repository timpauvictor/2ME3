package model;

public class AI {
	
	
	boolean exists;
	public AI() {
		this.exists = true;
	}
	
	/**
	 * 
	 * @param p - the current phase our game is in
	 */
	public void doTurn(Phases p) {
		if (p == Phases.Planning) {
			int x = genPlan();
		}
	}

	private int genPlan() {
		
		return 0;
	}
}
