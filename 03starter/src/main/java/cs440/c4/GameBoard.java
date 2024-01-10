package cs440.c4;

/**
 * An object instance that implements the integer will 
 */
public interface GameBoard {

	public final int WIN = 5;   // Four adjacent checkers wins the game.
	
	public final int NONE = 0;
	public final int AVAIL = 0;
	public final int USER = 1;
	public final int AGENT = -1;

	/**
	 * Checks the gameboard for N connected checker pieces.
	 * 
	 * @return int user constant identifying which player is the winner ((GameBoard.USER or GameBoard.AGENT). 
	 */
	public int connected();

	/**
	 * answers true if game is over, false otherwise
	 * 
	 * @return boolean indicating if game is over
	 */
	public boolean gameOver();

	
	/**
	 * Provides access to the game board's physical representation -- a 2D array of 
	 * integers.
	 * 
	 * @return a 2D int array handle
	 */
	public int[][] getBoard();
	
	
	/**
	 * Checks the specified column to see if it is full of checkers.
	 * 
	 * @param c int specifying which column to check
	 * @return true if column is full; false otherwise;
	 */
	public boolean isColumnFull(int c);

	/**
	 * a method to add disks to the board, or make the state in the 2d array no longer available, given the column and the player
	 */
	int addDisk(int column, int player, ConnectBoard con);

}