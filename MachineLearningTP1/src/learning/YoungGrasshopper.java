package learning;

import java.awt.Point;
import java.util.*;

import gameLogic.Board;

public class YoungGrasshopper {
	
	Deque<int[]> game;
	double[] weights;
	Board board;
	int jug = 1	;
	
	public YoungGrasshopper(Board b) {
		this.weights = new double[4];
		for (double i : weights) {
			i = 0;
		}
		this.game = new LinkedList<int[]>();
		this.board = b;
		
	}
	public void setBoard(Board b) {
		this.board = b;
	}
	
	public Point getMove() {
		Point mov = new Point();
		Point best = new Point();
		double val = 0;
		double max = -100000000;
		for( int i = 0 ; i < 3 ; i ++) {
			for ( int j = 0 ; j < 3 ; j++) {
				mov.x=i;mov.y=j;
				if(board.isValid(mov)) {
					board.put(mov, jug);
					board.print();
					int[] vars = getVars(board);
					val = vAprox(vars);
					System.out.println("val="+ val);
					System.out.println("max="+ max);
					if (val > max) {
						max = val;
						best.x = mov.x;
						best.y = mov.y;

						
					}
					board.undo(mov);
				}
			}
		}
		//guardar paso
		game.push(getVars(board));
		System.out.println(best);
		return best;

	}
	
	double vAprox(int[] vars) {
				
		double[] currentWeights = getCurrentWeigths();
		
		return weights[0]*vars[0] - weights[1]*vars[1] + weights[2]*vars[2] - weights[3]*vars[3];
	}
	
	int [] getVars(Board board) {
		
		int [] vars = new int[4];
		for (int i : vars) {
			i = 0;
		}

		Point p1 = new Point(),p2 = new Point(),p3 = new Point();
		p1.y=0;p2.y=1;p3.y=2;
		for( int i = 0 ; i < 3 ; i++) {
			p1.x=p2.x=p3.x=i;
			vars = updateVars(vars,p1,p2,p3);
		}	
		
		p1.x=0;p2.x=1;p3.x=2;
		for( int i = 0 ; i < 3 ; i++) {
			p1.y=p2.y=p3.y=i;
			vars = updateVars(vars,p1,p2,p3);
		}	
		
		p1.x=p3.y=0;p2.x=p2.y=1;p3.x=p1.y=2;
		vars = updateVars(vars,p1,p2,p3);
		
		p1.x=p1.y=0;p2.x=p2.y=1;p3.x=p3.y=2;
		vars = updateVars(vars,p1,p2,p3);
		
		
		
		return vars;
	}
	
	private int[] updateVars(int[] vars , Point p1, Point p2, Point p3) {
		int sum = board.get(p1)+board.get(p2)+board.get(p3);
		int check0=board.get(p1)*board.get(p2)*board.get(p3);
		
		if(sum!=0) {
			if(sum==1) {
				if(check0==0)
					vars[0]++;
			}
			if(sum==-1) {
				if(check0==0)
					vars[1]++;
			}
			if(sum==2) {
				vars[2]++;
				vars[0]++;
			}
			if(sum==-2) {
				vars[3]++;
				vars[1]++;
			}
		}
		return vars;
	}
		
	
	private double [] getCurrentWeigths() {
		
		return weights;
	}
	
	private List<TrainExample> getTrainExamples(double end) {
		List<TrainExample> examples = new LinkedList<TrainExample>();
		double val = end;
		int[] current;
		while(!game.isEmpty()) {
			current = game.pop();
			examples.add( new TrainExample(current, end));			
		}
		return examples;
	}
	
	private double[] updateWeights(List<TrainExample> examples) {
		double[] currentWeights = new double[4];
		for ( int i = 0 ; i < 3 ; i++) {
			currentWeights[i]=weights[i];
		}
		
		for (TrainExample trainExample : examples) {
			for (int i = 0 ; i < 3 ; i++) {
				currentWeights[i] = currentWeights[i] + 0.1*(trainExample.val - vAprox(trainExample.vars))*trainExample.vars[i];
			}
		}
		
		return currentWeights;
	}
	
	
	public void learn(double end) {
		weights = updateWeights(getTrainExamples(end));
		for (double weight : weights) {
			System.out.print(weight + " ");
		}
		; System.out.println();
	}
}
