package cs440.c4;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class RandomAgent implements Agent {
	
	GameBoard board;
	
	Random randy = new Random();
	
	public RandomAgent(GameBoard gb) {
		this.board = gb;
	}
	
	
	
	@Override
	public int nextAction() throws Exception {
		TimeUnit.SECONDS.sleep(1);
		
		int col;
		do {
			col = randy.nextInt(board.getBoard().length); 
		} while (board.isColumnFull(col));
		
		return col;
	}



	@Override
	public void initializeWithBoard(GameBoard anyBoard) throws Exception {
		this.board = anyBoard;
		
	}



	@Override
	public void initializeWithBoard(ConnectBoard board) throws Exception {
		// TODO Auto-generated method stub
		
	}



}
