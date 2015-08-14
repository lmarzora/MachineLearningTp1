package learning;

import java.awt.Point;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import gameLogic.Board;

public class Sensei {

	
	double train;
	
	public Sensei() {
		this.train = 0.01;
	}
	
	public double[] teach(Deque<int[]> game, double end , double[] weights) {
		weights = updateWeights(getTrainExamples(end,game),weights);
		return weights;
	}
	
	
	
	private List<TrainExample> getTrainExamples(double end , Deque<int[]> game) {
		List<TrainExample> examples = new LinkedList<TrainExample>();
		int[] current;
		while(!game.isEmpty()) {
			current = game.pop();
			examples.add( new TrainExample(current, end));			
		}/*
		for (TrainExample trainExample : examples) {
			for (int var : trainExample.vars) {
				System.out.print(var + " ");
			}
			System.out.println("");
			System.out.println(trainExample.val);
		}*/
		return examples;
	}
	
	private double[] updateWeights(List<TrainExample> examples , double[] weights) {
		double[] currentWeights = new double[6];
		for ( int i = 0 ; i < 6 ; i++) {
			currentWeights[i]=weights[i];
		}
		
		for (TrainExample trainExample : examples) {
			for (int i = 0 ; i < 6 ; i++) {
				currentWeights[i] = currentWeights[i] + train*(trainExample.val - vAprox(trainExample.vars,weights))*trainExample.vars[i];
			}
		}
		
		return currentWeights;
	}
	
	
	private void printVars(int[] vars) {
		
		for (int var : vars) {
			System.out.print(var + " ");
		}
		System.out.println("");
	}
	private double vAprox(int[] vars, double[] weights) {
						
		return weights[0]*vars[0] + weights[1]*vars[1] + weights[2]*vars[2]*vars[2] + weights[3]*vars[3]*vars[3] +weights[4]*vars[4]*vars[4]*vars[4]  ;
	}
	
	int [] getVars(Board board ,int jug) {
		
		int [] vars = new int[6];
		for (int i : vars) {
			i = 0;
		}

		Point p1 = new Point(),p2 = new Point(),p3 = new Point();
		p1.y=0;p2.y=1;p3.y=2;
		for( int i = 0 ; i < 3 ; i++) {
			p1.x=p2.x=p3.x=i;
			vars = updateVars(vars,p1,p2,p3,jug,board);
		}	
		
		p1.x=0;p2.x=1;p3.x=2;
		for( int i = 0 ; i < 3 ; i++) {
			p1.y=p2.y=p3.y=i;
			vars = updateVars(vars,p1,p2,p3,jug,board);
		}	
		
		p1.x=p3.y=0;p2.x=p2.y=1;p3.x=p1.y=2;
		vars = updateVars(vars,p1,p2,p3,jug,board);
		
		p1.x=p1.y=0;p2.x=p2.y=1;p3.x=p3.y=2;
		vars = updateVars(vars,p1,p2,p3,jug,board);
		
		
		
		return vars;
	}
	
	private int[] updateVars(int[] vars , Point p1, Point p2, Point p3, int jug , Board board) {
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
				vars[0]++;
			}
			
			
			if(sum==-2*jug) {
				vars[3]++;
				vars[1]++;
			}
			
			if(sum==3*jug) {
				vars[4]++;
			}
			
			if(sum==-3*jug) {
				vars[5]++;
			}

		}
		return vars;
	}
		

}
