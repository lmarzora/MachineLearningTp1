package learning;

import gameLogic.Game;
import visual.GameApplet;

public class Arena {
	private YoungGrasshopper student1;
	private YoungGrasshopper student2;
	private Game game;
	public Arena() {
		game = new Game();
		int jug1=-1;
		int jug2= 1;
		
		
		//student1 = new Fixed(game.getBoard(),jug1,"fixed");
		student1 = new Dummy(game.getBoard(),jug1,"dummy");
		//student1 = new YoungGrasshopper(game.getBoard(),jug1,"hop1");
		student2 = new YoungGrasshopper(game.getBoard(),jug2,"hop2");
		
		
		int turn = 1;
		int count = 0;
		int fix = 10000;
		int dummy = 20000;
		int hopper = 30000;
		int end = 10000;
		int cant = 50000;
		while(count < cant) {
			while(game.hasEnded()==-2) {
				student1.setBoard(game.getBoard());
				student2.setBoard(game.getBoard());
				if(turn>0) {
					game.play(student1.getMove(),student1.getJug());
				}
				else {
					game.play(student2.getMove(),student2.getJug());
				}
				turn *= -1;	
			}
			student1.setBoard(game.getBoard());
			student2.setBoard(game.getBoard());
			student1.learn(game.hasEnded());
			student2.learn(game.hasEnded());
			game.clearBoard();
			count ++;
			System.out.println(count);

			
			/*
			if ( count == hopper) {
				student1 = new YoungGrasshopper(game.getBoard(),jug2,"hop2");
				student1.setWeights(student2.getWeigths());
			}
			*/
			
			if ( count == dummy) {
				student1 = new Dummy(game.getBoard(),jug2,"dummy");

			}
			
			
			if ( count == fix ) {
				student1 = new Fixed(game.getBoard(),jug1,"fixed");

			}
			
		}
		
 		GameApplet g = new GameApplet(student2);
	}
	
	
}
