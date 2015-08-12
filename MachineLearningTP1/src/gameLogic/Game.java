package gameLogic;

import java.awt.Point;

import learning.YoungGrasshopper;


public class Game {

	private Board board;
	private int currentPlayer;
	
	public Game() {
		this.board = new Board();
		this.currentPlayer=1;
				
	}
	
	public Board getBoard() {
		return board;
	}
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int hasEnded(){
		Point p1 = new Point();
		Point p2 = new Point();
		Point p3 = new Point();
		//filas
		p1.x=0;
		p2.x=1;
		p3.x=2;
		for(int i = 0 ; i < 3 ; i++) {
			p1.y=p2.y=p3.y=i;
			if(board.get(p1) == board.get(p2) && board.get(p2) == board.get(p3) && board.get(p2)!=0) {
				return board.get(p1);
			}
		}
		//columnas
				p1.y=0;
				p2.y=1;
				p3.y=2;
				for(int i = 0 ; i < 3 ; i++) {
					p1.x=p2.x=p3.x=i;
					if(board.get(p1) == board.get(p2) && board.get(p2) == board.get(p3) && board.get(p2)!=0) {
						return board.get(p1);
					}
				}
				
		//diag
		p1.x=p1.y=0;
		p2.x=p2.y=1;
		p3.x=p3.x=2;
		
		if(board.get(p1) == board.get(p2) && board.get(p2) == board.get(p3) && board.get(p2)!=0) {
			System.out.println("diag1");
			return board.get(p1);
		}
		
		p1.x=p3.y=0;
		p2.x=p2.y=1;
		p3.x=p1.y=2;
		
		if(board.get(p1) == board.get(p2) && board.get(p2) == board.get(p3) && board.get(p2)!=0) {
			return board.get(p1);
		}
		Point p = new Point();
		int count = 1;
		for( int i = 0 ; i < 3 ; i ++) {
				p.x=i;
			for (int j = 0 ; j < 3 ; j ++) {
				p.y=j;
				count *= board.get(p);
			}
		}
		if (count != 0)
			return 0;
			
		return -2;
	}


	public boolean play(Point mov) {
		if(board.isValid(mov)) {
			board.put(mov,currentPlayer);
			currentPlayer=currentPlayer*(-1);
			board.print();
			int p  = hasEnded();
			if(p != 0) {
				System.out.println("gano " + p);
			}
			return true;
		}
		return false;
			
	}
	public void clearBoard() {
		board.clearBoard();
	}
}
