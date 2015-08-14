package learning;

import java.awt.Point;
import java.util.Random;

import gameLogic.Board;

public class Dummy extends YoungGrasshopper {

	public Dummy(Board b, int jug, String name) {
		super(b, jug, name);
		// TODO Auto-generated constructor stub
	}
	public Point getMove() {
		return getMoveRand();
	}


	
}
