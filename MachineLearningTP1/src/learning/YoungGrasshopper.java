package learning;

import java.awt.Point;
import java.util.*;

import gameLogic.Board;

public class YoungGrasshopper {
	
	private Deque<int[]> game;
	protected double[] weights;
	protected Board board;
	private int jug 	;
	private double train;
	private Sensei sensei;
	private String name;
	
	public YoungGrasshopper(Board b, int jug, String name) {
		//Saver.check();
		this.name = name;
		this.sensei= new Sensei();
		this.jug = jug;
		if(this instanceof Fixed || this instanceof Dummy) {
			//System.out.println("aaa");
		}else {
			if(Saver.fileExists(name))
				weights = Saver.readWeights(name, weights);
			else
				this.weights = new double[6];
		}
		//printWeights();
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

		game.push(sensei.getVars(board,jug));

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

					int[] vars = sensei.getVars(board,jug);
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
	
		game.push(sensei.getVars(board, jug));

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
	
	
		
	
	public double [] getWeigths() {
		
		return weights;
	}
	/*
	private List<TrainExample> getTrainExamples(double end) {
		List<TrainExample> examples = new LinkedList<TrainExample>();
		int[] current;
		while(!game.isEmpty()) {
			current = game.pop();
			examples.add( new TrainExample(current, end));			
		}
		for (TrainExample trainExample : examples) {
			for (int var : trainExample.vars) {
				System.out.print(var + " ");
			}
			System.out.println("");
			System.out.println(trainExample.val);
		}
		return examples;
	}
	
	private double[] updateWeights(List<TrainExample> examples) {
		double[] currentWeights = new double[6];
		for ( int i = 0 ; i < 6 ; i++) {
			currentWeights[i]=weights[i];
		}
		
		for (TrainExample trainExample : examples) {
			for (int i = 0 ; i < 6 ; i++) {
				currentWeights[i] = currentWeights[i] + train*(trainExample.val - vAprox(trainExample.vars))*trainExample.vars[i];
			}
		}
		
		return currentWeights;
	}
*/	
	public void setJug(int jug) {
		this.jug = jug;
	}
	public int getJug() {
		return jug;
	}
	public void learn(double end) {

		if(end>0) {
			System.out.println("gane");
		}
		else
			if(end<0) {
				System.out.println("perdi");
			}
			else
				System.out.println("empate");
		game.push(sensei.getVars(board,jug));
		double t = end*10;
		weights = sensei.teach(game,end,weights);
		Saver.saveWeights(weights, name);
		Saver.readWeights(name, weights);

	}
	public void printWeights() {
		for (double weight : weights) {
			System.out.print(weight + " ");
		}
		; System.out.println();
	}
	
}
