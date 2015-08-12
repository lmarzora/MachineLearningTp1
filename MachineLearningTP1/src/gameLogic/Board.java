package gameLogic;

import java.awt.Point;

public class Board {

	int[][] board;
	
	public void put(Point mov,int jug) {
			this.board[mov.x][mov.y] = jug ;
	}
	
	public int get(Point mov) {
		return board[mov.x][mov.y];
	}
	public void undo(Point mov) {
		board[mov.x][mov.y] = 0;
	}
	
	public boolean isValid(Point mov){
		System.out.println("x=" + mov.getX()+ ",y=" + mov.getY());
		if(mov.x>=0 && mov.x<=2 && mov.y>=0 && mov.y<=2) {
			if(board[mov.x][mov.y] == 0) { 
				System.out.println("true");
				return true;
			}
		}
		return false;
	}
	
	public Board() {
		board = new int[3][3];
		clearBoard();
	}
	public Board(Board b){
		board = new int[3][3];
		Point p = new Point();
		for(int i=0;i<3;i++) {
			p.x=i;
			for(int j=0;j<3;j++) {
				p.y=j;
				this.board[i][j]=b.get(p);
			}
		}
		
	}
	
	public  void clearBoard() {
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				this.board[i][j]=0;
	}
	
	public void print() {
		for(int i=0;i<3;i++) {
			System.out.println(" " + board[0][i] + board[1][i] + board[2][i]);
		}
		System.out.println("");
	}
}
