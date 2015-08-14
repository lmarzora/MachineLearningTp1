package learning;

import gameLogic.Game;
import visual.GameApplet;

public class Arena {
	private YoungGrasshopper student1;
	private YoungGrasshopper student2;
	private YoungGrasshopper dummy;
	private Game game;
	double[] weights = {0.1,-0.1,0.5,-0.5,1,-1};
	public Arena() {
		game = new Game();
		int jug1=-1;
		int jug2= 1;
		
		student1 = new Fixed(game.getBoard(),jug1,"fixed",weights);
		//student1 = new Dummy(game.getBoard(),jug1);
		student2 = new YoungGrasshopper(game.getBoard(),jug1,"hop1");
		//student1 = new YoungGrasshopper(game.getBoard(),jug2,"hop2");

		int turn = 1;
		int count = 0;
		int endSession1 = 20000;
		int endSession2 = 40000;
		int endSession3 = 60000;
		int endSession4 = 80000;
		int end = 100000;
		int cant = 1;
		while(count < cant) {
			while(game.hasEnded()==-2) {
				student1.setBoard(game.getBoard());
				student2.setBoard(game.getBoard());
				if(turn>0) {
					game.play(student1.getMove());
				}
				else {
					game.play(student2.getMove());
				}
				turn *= -1;	
			}
			
			student1.learn(game.hasEnded());
			student2.learn(game.hasEnded());
			game.clearBoard();
			count ++;
			System.out.println(count);
			student1.setJug(jug2);
			student2.setJug(jug1);
			/*
			if ( count == endSession1 || count == endSession3 || count == end) {
				student1 = new YoungGrasshopper(game.getBoard(),jug2);
				student1.setWeights(student2.getWeigths());
			}
			if ( count == endSession2 || count == endSession4) {
				student1 = new Dummy(game.getBoard(),jug2);

			}
			
			 */
		
		}
		
 		GameApplet g = new GameApplet(student2);
	}
	
	
}
