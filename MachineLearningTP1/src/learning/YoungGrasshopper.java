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
	private boolean fix;
	double p;
	double m;
	double t;
	public YoungGrasshopper(Board b, int jug, String name) {
		//Saver.check();
		this.name = name;
		this.sensei= new Sensei();
		this.jug = jug;
		this.m=1.0;
		this.t = 0;
		fix=false;
		if(this instanceof Fixed	) {
			//System.out.println("aaa");
		}else {
			if(Saver.fileExists(name)) {
				
				weights = Saver.readWeights(name, weights);
				
			}
			else
				this.weights = new double[8];
		}
		//printWeights();
		board = new Board();
		this.game = new LinkedList<int[]>();
		setBoard(b);
		
	}
	public void setFix(boolean fix) {
		this.fix=fix;
	}
	public void setTrain(double t) {
		this.train = t;
	}
	public void setWeights(double[] w) {
		for( int i = 0  ; i < 5 ; i++) {
			weights[i] = w[i];
		}
	}
	
	public void clearBoard() {
		board.clearBoard();
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
	
	public Point getMoveRand() {
		Point mov = new Point();

		Random rand = new Random();
		do{
			mov.x = rand.nextInt(3);
			mov.y = rand.nextInt(3);
		}while(!board.isValid(mov));
		
		return mov;
	}
	
	public Point getMoveV() {
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
					val = sensei.vAprox(vars,weights);
					if(first) {
						max = val;
						best.x = mov.x;
						best.y = mov.y;
						first = false;
					}
					if (val > max) {
						max = val;
						best.x = mov.x;
						best.y = mov.y;
						
					}
					
					board.undo(mov);
				}
			}
		}

		return best;

	}
	
	
	public Point getMoveTrain(boolean xplor) {
		p = Math.random();
		m = Math.pow((Math.E),-1*t);
		//System.out.println(m);
		t+= 0.000001;
		Point mov;
		if(p>m||!xplor) {
			mov = getMoveV();
		}
		else
			mov = getMoveRand();
					
		board.put(mov,jug);
		
		game.push(sensei.getVars(board, jug));
			

		
			//System.out.println(m);
			return mov;
	}

	public Board getBoard() {
		return this.board;
	}
	private void printVars() {
		for(int i: sensei.getVars(board, jug))
			System.out.print(i + " ");
		System.out.println();
	}
		
	
	public double [] getWeigths() {
		
		return weights;
	}
	

	public int getJug() {
		return jug;
	}
	public void learn(double end) {
			
				
		//board.print();
		game.push(sensei.getVars(board,jug));
		//System.out.println(jug);
		double t = 100*jug*end;
		if(!fix)
			weights = sensei.teach(game,t,weights);
		//Saver.saveWeights(weights, name);
		//Saver.readWeights(name, weights);
		//printWeights();
		board.clearBoard();

	}
	public void printWeights() {
		for (double weight : weights) {
			System.out.print(weight + " ");
		}
		; System.out.println();
	}
	
}
