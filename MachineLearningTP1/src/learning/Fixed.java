package learning;

import gameLogic.Board;

public class Fixed extends YoungGrasshopper {

	double[] fixed = {1,-2,5,-10,30,0};
	public Fixed(Board b, int jug, String name) {
		super(b, jug, name);
		this.weights=fixed;
		//printWeights();
		// TODO Auto-generated constructor stub
	}

	public void learn(double end) {
		return;
	}
	
}
