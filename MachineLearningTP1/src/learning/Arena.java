package learning;

import java.util.*;

import gameLogic.Game;
import visual.GameApplet;

public class Arena {
	private YoungGrasshopper student1;
	private YoungGrasshopper student2;
	private Game game;
    double[] weights = {5,0,20,-100,1000,-10000.00};
	public Arena() {
		game = new Game();
		int jug1=-1;
		int jug2= 1;
		
		//student1 = new Fixed(game.getBoard(),jug1,"fixed");
		//student1 = new Dummy(game.getBoard(),jug1,"hop1");
		
		//student1 = new YoungGrasshopper(game.getBoard(),jug1,"jug5");
		student2 = new YoungGrasshopper(game.getBoard(),jug2,"jug1");
		//student2.setWeights(weights);
		YoungGrasshopper hard = new Fixed(game.getBoard(),jug1,"hard");
		YoungGrasshopper med = new YoungGrasshopper(game.getBoard(), jug1, "jug1");
		YoungGrasshopper easy = new Dummy(game.getBoard(),jug1,"easy");
		student2.setFix(false);
		student1 = easy;
		//student1 = med;
		
		//student1 = hard;
		int turn = 1;
		int count = 0;
		/*
		int change1 =  500000 ;
		int change2 =  750000 ;
		*/
		int cant   = 1000000 ;
		
		List<Integer> history = new ArrayList<Integer>(cant+1);
		while(count < cant) {
			//System.out.println("new game");
			while(game.hasEnded()==-2) {

				student1.setBoard(game.getBoard());
				student2.setBoard(game.getBoard());
				if(turn==student1.getJug()) {
					//System.out.println("jug1");
					game.play(student1.getMoveTrain(false),student1.getJug());
					//game.getBoard().print();
					turn=student2.getJug();
				}
				else {
					//System.out.println("jug2");
					game.play(student2.getMoveTrain(true),student2.getJug());
					//game.getBoard().print();
					turn = student1.getJug();
				}

			}
			/*if(game.hasEnded()==-1) {
				System.out.println("what");
				game.getBoard().print();
				System.out.println("jug1");
				student1.getBoard().print();
				System.out.println("jug2");
				student2.getBoard().print();
				System.exit(-1);
			}
			*/
			student1.setBoard(game.getBoard());
			student2.setBoard(game.getBoard());
			
			student1.learn(game.hasEnded());
			student2.learn(game.hasEnded());
			history.add(game.hasEnded());
			game.clearBoard();
		
			count ++;

			System.out.println(count);
			/*
			if(count==change1) {
				student1=med;
				student1.setWeights(student2.getWeigths());
			}
			if(count==change2)
				student1=hard;
			*/

		}
		
		//student2.setWeights(student1.getWeigths());
		//student2 = new YoungGrasshopper(game.getBoard(), jug1, "hop2");
		student2.printWeights();
		Saver.saveWeights(student2.getWeigths(), "test1");
		Saver.learningCurve(history, "test15");
 		GameApplet g = new GameApplet(student2);
	}
	
	
}
