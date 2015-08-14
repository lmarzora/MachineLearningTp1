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
		Point mov = new Point();

		Random rand = new Random();
		do{
			mov.x = rand.nextInt(3);
			mov.y = rand.nextInt(3);
		}while(!board.isValid(mov));
		
		return mov;
	}
	public void learn(double end) {
		return;
	}
	
	
}
