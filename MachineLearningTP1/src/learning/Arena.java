package learning;

import gameLogic.Game;
import visual.GameApplet;

public class Arena {
	private YoungGrasshopper student1;
	private YoungGrasshopper student2;
	private Game game;
	
	public Arena() {
		game = new Game();
		int jug1=1;
		int jug2=-1;
		
		student1 = new YoungGrasshopper(game.getBoard(),jug1);
		student2 = new YoungGrasshopper(game.getBoard(),jug2);
		
		int turn = 1;
		int count = 0;
		int cant = 1000;
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
			student1.setJug(jug1);
			
		}
		
 		GameApplet g = new GameApplet(student2);
	}
	
	
}
