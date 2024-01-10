package cs440.c4;

import java.util.concurrent.TimeUnit;


/**
 * An instance of this class works with our main view staged in the ConnectFourApp class.
 * The mechanics of our game is simple, we allow the the user to play by choosing a column
 * in which to drop their checker.  After which, we let an AI agent play.  The controller
 * works with an instance of the GameBoard to maintain the state of the game and implement
 * helpful game algorithms (like detecting 4 in a row).
 * 
 */
public class GameController {

	
	private ConnectBoard gb;
	
	private Agent ai;
	
	private int rows;
	
	private int cols;
	
	
	/**
	 * Our  primary constructor, 
	 * @param maxRows
	 * @param maxCols
	 * @throws Exception 
	 */
	public GameController(int maxRows, int maxCols) throws Exception {
		
		this.rows = maxRows;
		this.cols = maxCols;
		
		this.gb = new ConnectBoard(maxRows, maxCols);
		
    	ai = new ImprovedMinimaxAgent();
    	ai.initializeWithBoard(gb);
	}
	
	
	
	/**
	 * Updates the game board by dropping the user's checker into the specified 
	 * column.
	 * 
	 * @param col
	 */
	public void userPlay( int col) {
		
        int r = gb.addDisk(col, GameBoard.USER, gb);

	}
	
	/**
	 * With the help of the gameboard, checks the board to determine if the game
	 * is over. The gameboard will report which user (see gameboard constants) has
	 * connected to win the game
	 * 
	 * @return boolean true if game if a winner is found.
	 */
	public boolean gameIsOver() {
		return this.winner() != GameBoard.NONE;
	}
	
	/**
	 * Convenience method reporting which user is the winner
	 *  
	 * @return integer constant representing the user, agent or none
	 */
	public int winner() {
		return gb.connected();
	}
	
	
	/**
	 * wrapper method allowing the view to query the board state at 
	 * a specified row-col coordinate (r,c).
	 * 
	 * @return int GameBoard.USER, GameBoard.AGENT, GameBoard.AVAIL
	 */
	public int boardAt(int r, int c) {
		return gb.getBoard()[r][c];
	}


	/**
	 * Asks the agent for the action to choose and drops the agents
	 * checker on that column.  The gameboard maintains the state.
	 * 
	 */
	void playAgent() {

		try {
			
	        int c = ai.nextAction();
	        int r = gb.addDisk(c, GameBoard.AGENT, gb);
	        
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("you're too good at this so i quit!");
		}

	}


	public void initGame() {
		gb.initializeBoard();
		
	}
	
	
}
