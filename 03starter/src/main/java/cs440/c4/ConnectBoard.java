package cs440.c4;

public class ConnectBoard implements GameBoard {
	private int[][] board; // 2d array of ints to hold the state of the board
	
	int lastRow = 0;
	int lastCol = 0;
	public static final int MAX_COLS = 8;
	public static final int MAX_ROWS = 7;
	ImprovedMinimaxAgent mini;
	
	/**
	 * constructor
	 * @param rows; the max rows of the board
	 * @param cols; the max columns of the board
	 */
	public ConnectBoard(int rows, int cols) {
		board = new int[rows][cols];
		initializeBoard();
	}
	
	/**
	 * a method to initiate the board by setting all of the states in the board to available
	 */
	public void initializeBoard() {
		for (int r = 0; r < board.length; r++) { // for all the spots in the board
			for (int c = 0; c < board[r].length; c++) {
				board[r][c] = AVAIL; // set to available
			}
		}
	}

	/**
	 * a method to add disks to the board, or make the state in the 2d array no longer available, given the column and the player
	 */
	@Override
	public int addDisk(int column, int player, ConnectBoard con) {
		for (int row = board.length - 1; row >= 0; row--) {
			if (board[row][column] == AVAIL) {
				board[row][column] = player;
				lastRow = row;
				lastCol = column;
				return row;
			}
		}
		
		return -1;
	}

	/**
	 * a method to answer true if either player has won or false if not. runs the checkConnected method to answer the call
	 */
	@Override
	public int connected() {
		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				int player = board[r][c]; // in the board, where the player is
				if (player != AVAIL) {
					if (checkConnected(player, r, c)) { // run the checkConnected method to check if the player is not
						return player; // if the player has, then return the player
					}
				}
			}
		}
		return NONE; // if not, return NONE as there is no winner
	}
	
	/**
	 * a method to check if either player has won, or has connected the needed amount of checkers to finish the game. returns true if they have and false if not
	 * @param player; the current action taker interacting with the world
	 * @param row; current row that was most recently interacted with
	 * @param col; current column that was most recently interacted with
	 * @return
	 */
	public boolean checkConnected(int player, int row, int col) {
		int count = 0;
		// checks horizontally
		for (int c = col; c < board[row].length && board[row][c] == player; c++) { // check the row where the player has most recently placed a checker, and all the columns for that row
			count++; // every time there is a checker of the player found, add 1 to the count
			if (count == WIN) { // if it matched the number needed to win the game, return true
				return true;
			}
		}
		
		// check vertically, following the same rules and logic as the horizontal
		count = 0;
		for (int r = row; r < board.length && board[r][col] == player; r++) {
			count++;
			if (count == WIN) {
				return true;
			}
		}
		
		// checks diagonally, if the diagonal started with the lower part towards the left, following the same rules and logic as the vertical and horizontal
		count = 0;
		for (int r = row, c = col; r < board.length && c < board[row].length && board[r][c] == player; r++, c++) {
			count++;
			if (count == WIN) {
				return true;
			}
		}
		
		// checks diagonally, if the diagonal started with the lower part towards the right, following the same rules and logic as the vertical and horizontal
		count = 0;
		for (int r = row, c = col; r < board.length && c >= 0 && board[r][c] == player; r++, c--) {
			count++;
			if (count == WIN) {
				return true;
			}
		}
		
		return false;
	}
	
	public int countConnected(int player, int row, int col) {
		int countHor = 0;
		// checks horizontally
		for (int c = col; c < board[row].length && board[row][c] == player; c++) { // check the row where the player has most recently placed a checker, and all the columns for that row
			countHor++; // every time there is a checker of the player found, add 1 to the count
		}
		
		// check vertically, following the same rules and logic as the horizontal
		int countVert = 0;
		for (int r = row; r < board.length && board[r][col] == player; r++) {
			countVert++;
		}
		
		// checks diagonally, if the diagonal started with the lower part towards the left, following the same rules and logic as the vertical and horizontal
		int countDiagOne = 0;
		for (int r = row, c = col; r < board.length && c < board[row].length && board[r][c] == player; r++, c++) {
			countDiagOne++;
		}
		
		// checks diagonally, if the diagonal started with the lower part towards the right, following the same rules and logic as the vertical and horizontal
		int countDiagTwo = 0;
		for (int r = row, c = col; r < board.length && c >= 0 && board[r][c] == player; r++, c--) {
			countDiagTwo++;
		}
		
		return Math.max(Math.max(countHor, countVert), Math.max(countDiagOne, countDiagTwo));
	}
	
	/**
	 * method to detect if the opponent is about to win. uses the count connected method to see if there are any places were the opponent is 1 off winning
	 * @param player
	 * @param tBoard
	 * @return
	 */
	public boolean isThreat(int player, ConnectBoard tBoard) {
		boolean threat = false;
		
		for (int row = 0; row < MAX_ROWS; row++) {
			for (int col = 0; col < MAX_COLS; col++) {
				if (tBoard.countConnected(player, row, col) >= WIN -1) {
					threat = true;
				}
			}
		}
		
		return threat;
	}
	
	/**
	 * similar to the isThreat method, but used by the agent to see if it can win.
	 * @param player
	 * @param tBoard
	 * @return
	 */
	public boolean isWinnable(int player, ConnectBoard tBoard) {
		boolean winnable = false;
		
		for (int row = 0; row < MAX_ROWS; row++) {
			for (int col = 0; col < MAX_COLS; col++) {
				if (tBoard.countConnected(player, row, col) == WIN -1) {
					winnable = true;
				}
			}
		}
		
		return winnable;
	}

	/**
	 * a method to check if the game is over. runs the connected method to see if someone has won, and the isBoardFull method to see if there are any states still 
	 * available. returns either that someone has won or the board is full
	 * 
	 */
	@Override
	public boolean gameOver() {
		return connected() != NONE || isBoardFull();
	}
	
	/**
	 * a method to see if the board has any available states for the current player to place a checker. If the board is full returns true if not returns false
	 * uses the isColumnFull method
	 * @return
	 */
	public boolean isBoardFull() {
		for (int c = 0; c < board[0].length; c++) { // for all the columns
			if (!isColumnFull(c)) { // if any of the columns are not full
				return false;
			}
		}
		return true; // if all the columns are full, return true
	}

	/**
	 * returns the 2d array that represents the board
	 */
	@Override
	public int[][] getBoard() {
		return board;
	}

	/**
	 * checks to see if a column of the board is full. used in the isBoardFull method
	 */
	@Override
	public boolean isColumnFull(int c) {
		return board[0][c] != AVAIL;
	}

	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	public int getLastCol() {
		return lastCol;
	}

	public void setLastCol(int lastCol) {
		this.lastCol = lastCol;
	}

	
	
	
}
