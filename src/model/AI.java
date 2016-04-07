package model;

public class AI {
	boolean exists;
	public AI() {
		this.exists = true;
	}
	
	public void doTurn(Phases p) {
		if (p == Phases.Planning) {
			int x = genPlan();
		}
	}

	private int genPlan() {
		
		return 0;
	}
}
