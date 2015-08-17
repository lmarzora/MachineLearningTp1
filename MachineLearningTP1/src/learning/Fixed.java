package learning;

import gameLogic.Board;

public class Fixed extends YoungGrasshopper {

	double[] fixed = {5,0.1,20,-100,1000,-12,10};
	public Fixed(Board b, int jug, String name) {
		super(b, jug, name);
		this.weights=fixed;
		//printWeights();
		// TODO Auto-generated constructor stub
	}

	public void learn(double end) {
		clearBoard();
		return;
	}
	
}
