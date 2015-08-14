package learning;

import java.util.*;

import gameLogic.Game;
import visual.GameApplet;

public class Arena {
	private YoungGrasshopper student1;
	private YoungGrasshopper student2;
	private Game game;
    double[] weights = {5,-7,20,-100,1000,-10000.00};
	public Arena() {
		game = new Game();
		int jug1=-1;
		int jug2= 1;
		
		
		//student1 = new Fixed(game.getBoard(),jug2,"fixed");
		student1 = new Dummy(game.getBoard(),jug2,"hop1");
		//student2 = new Dummy(game.getBoard(),jug2,"hop2");
		//student1 = new YoungGrasshopper(game.getBoard(),jug1,"hop1");
		student2 = new YoungGrasshopper(game.getBoard(),jug1,"hop2");
		//student2.setWeights(weights);
		
		int turn = 1;
		int count = 0;
		int fix = 5000;
		int dummy = 20000;
		int hopper = 30000;
		int end = 10000;
		int cant = 10000 ;
		List<Integer> history = new ArrayList<Integer>(cant+1);
		while(count < cant) {
			while(game.hasEnded()==-2) {
				student1.setBoard(game.getBoard());
				student2.setBoard(game.getBoard());
				if(turn==student1.getJug()) {
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
			history.add(game.hasEnded());
			game.clearBoard();
			count ++;
			System.out.println(game.hasEnded());
			student1.printWeights();
			student2.printWeights();
			
			if(count == fix) {
				student1 = new Fixed(game.getBoard(),jug2,"fixed");
			}
				

		}

		//student2.setWeights(student1.getWeigths());
		student2 = new YoungGrasshopper(game.getBoard(), jug1, "hop2");
		Saver.learningCurve(history, "test3");
 		GameApplet g = new GameApplet(student2);
	}
	
	
}
