package learning;

import gameLogic.Game;
import visual.GameApplet;

public class Arena {
	private YoungGrasshopper student1;
	private YoungGrasshopper student2;
	private Game game;
	
	public Arena() {
		game = new Game();
		
		student1 = new YoungGrasshopper(game.getBoard(),1);
		student2 = new YoungGrasshopper(game.getBoard(),-1);
		
		int turn = 1;
		int count = 0;
		while(count < 100) {
			while(game.hasEnded()==-2) {
				student1.setBoard(game.getBoard());
				student2.setBoard(game.getBoard());
				if(turn>0)
					game.play(student1.getMove());
				else
					game.play(student2.getMove());
				
				turn *= -1;	
			}
			
			student1.learn(game.hasEnded());
			student2.learn(game.hasEnded());
			game.clearBoard();
			count ++;
		}
		GameApplet g = new GameApplet(student1);
	}
	
	
}
