package learning;

import java.awt.Point;
import java.util.*;

import gameLogic.Board;

public class YoungGrasshopper {
	
	Deque<int[]> game;
	double[] weights;
	Board board;
	int jug 	;
	double train;
	
	public YoungGrasshopper(Board b, int jug) {
		this.train = 0.001;
		this.jug = jug;
		this.weights = new double[5];
		for (double i : weights) {
		i = 0;
		}
		board = new Board();
		this.game = new LinkedList<int[]>();
		setBoard(b);
		
	}
	public void setTrain(double t) {
		this.train = t;
	}
	public void setWeights(double[] w) {
		for( int i = 0  ; i < 5 ; i++) {
			weights[i] = w[i];
		}
	}
	
	public void setBoard(Board b) {
		Point p = new Point();
		for(int i = 0 ; i < 3 ; i++) {
			for(int j = 0 ; j < 3 ; j++) {
				p.x=i;p.y=j;
				board.put(p, b.get(p));
			}
		}
	}
	
	public Point getMove() {
		game.push(getVars(board));
		//board.print();
		//printVars(getVars(board));
		Point mov = new Point();
		Point best = new Point();
		double val = 0;
		double max = 0;
		boolean first= true;
		for( int i = 0 ; i < 3 ; i ++) {
			for ( int j = 0 ; j < 3 ; j++) {
				mov.x=i;mov.y=j;
				if(board.isValid(mov)) {
					board.put(mov, jug);
					//board.print();
					int[] vars = getVars(board);
					val = vAprox(vars);
					if(first) {
						max = val;
						first = false;
					}
					if (val >= max) {
						max = val;
						best.x = mov.x;
						best.y = mov.y;
						
					}
					board.undo(mov);
				}
			}
		}
		//guardar paso
		board.put(best,jug);
		//board.print();
		//printVars(getVars(board));
		game.push(getVars(board));
		//System.out.println(best);
		//System.out.println(best);
		//board.print();
		return best;

	}
	private void printVars(int[] vars) {
		for (int var : vars) {
			System.out.print(var + " ");
		}
		System.out.println("");
	}
	private double vAprox(int[] vars) {
						
		return weights[0]*vars[0] + weights[1]*vars[1] + weights[2]*vars[2]*vars[2] + weights[3]*vars[3]*vars[3] +weights[4]*vars[4]*vars[4]*vars[4]  ;
	}
	
	int [] getVars(Board board) {
		
		int [] vars = new int[5];
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
	
			
			if(sum==1*jug) {
				if(check0==0)
					vars[0]++;
			}
			
			
			if(sum==-1*jug) {
				if(check0==0)
					vars[1]++;
			}
			
			
			if(sum==2*jug) {
				vars[2]++;
				//vars[0]++;
			}
			
			
			if(sum==-2*jug) {
				vars[3]++;
				//vars[1]++;
			}
			
			if(sum==3*jug) {
				vars[4]++;
			}
			

		}
		return vars;
	}
		
	
	public double [] getWeigths() {
		
		return weights;
	}
	
	private List<TrainExample> getTrainExamples(double end) {
		List<TrainExample> examples = new LinkedList<TrainExample>();
		int[] current;
		while(!game.isEmpty()) {
			current = game.pop();
			examples.add( new TrainExample(current, end));			
		}
		/*for (TrainExample trainExample : examples) {
			for (int var : trainExample.vars) {
				System.out.print(var + " ");
			}
			System.out.println("");
			System.out.println(trainExample.val);
		}*/
		return examples;
	}
	
	private double[] updateWeights(List<TrainExample> examples) {
		double[] currentWeights = new double[5];
		for ( int i = 0 ; i < 5 ; i++) {
			currentWeights[i]=weights[i];
		}
		
		for (TrainExample trainExample : examples) {
			for (int i = 0 ; i < 5 ; i++) {
				currentWeights[i] = currentWeights[i] + train*(trainExample.val - vAprox(trainExample.vars))*trainExample.vars[i];
			}
		}
		
		return currentWeights;
	}
	
	public void setJug(int jug) {
		this.jug = jug;
	}
	
	public void learn(double end) {
		//board.print();
		//printVars(getVars(board));
		game.push(getVars(board));
		double t = jug*end;
		weights = updateWeights(getTrainExamples(t));
		printWeights();
	}
	public void printWeights() {
		for (double weight : weights) {
			System.out.print(weight + " ");
		}
		; System.out.println();
	}
	
}
