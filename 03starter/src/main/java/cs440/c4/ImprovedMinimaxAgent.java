package cs440.c4;

public class ImprovedMinimaxAgent implements Agent {
	private ConnectBoard board;
	public final int WIN = 5;   // this many adjacent checkers wins the game.
	public static final int MAX_COLS = 8;
	public static final int MAX_ROWS = 7;
	int nodeCount;

	@Override
	public void initializeWithBoard(ConnectBoard board) throws Exception {
		this.board = board;
		
	}

	/**
	 * method to choose which column the agent will select. runs the minimax to return the column
	 */
	@Override
	public int nextAction() throws Exception {
		ConnectBoard tBoard = tempBoard(board);
		nodeCount = 0; // keeps track of the total nodes explored
		
		double time = System.nanoTime(); // used to find how long it took the minimax to search
		int move = minimax(tBoard, GameBoard.AGENT, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
		System.out.println("total seconds it took to search = " + (System.nanoTime() - time) / 1000000000.0);
		return move;
	}
	
	/**
	 * the minimax method runs a minimax search with alpha beta pruning to determine which column is the best option for the agent
	 * @param board
	 * @param player
	 * @param depth
	 * @param a
	 * @param b
	 * @return
	 */
	public int minimax(ConnectBoard board,int player, int depth, int a, int b) {
		int bestColumn = -1; // keeps track of the best column in the search
		int bestScore = 0;
	
		if (board.gameOver() || depth >= 10) { // if the game ends or the depth gets into double digits, return the best option so far
			return evaluate(board, player); 
		}
		
		// the max side of alpha beta
		if (player == GameBoard.AGENT) {
			nodeCount++;
			bestScore = Integer.MIN_VALUE;
			bestColumn = -1;
			for (int c = 0; c < MAX_COLS; c++) {
				if (!board.isColumnFull(c)) {
					ConnectBoard tBoard = tempBoard(board);
					int row = tBoard.addDisk(c, GameBoard.AGENT, tBoard);
					int child = minimax(tBoard, GameBoard.USER, depth + 1, a, b);
					if (child > bestScore) {
						bestScore = child;
						bestColumn = c;
					}
					
					a = Math.max(a, bestScore);
					if (b <= a) {
						break;
					}
				}
			}
		}
		// the min side of alpha beta
		else {
			nodeCount++;
			bestScore = Integer.MAX_VALUE;
			bestColumn = -1;
			for (int c = 0; c < MAX_COLS; c++) {
				if (!board.isColumnFull(c)) {
					ConnectBoard tBoard = tempBoard(board);
					int row = tBoard.addDisk(c, GameBoard.USER, tBoard);
					int child = minimax(tBoard, GameBoard.AGENT, depth + 1, a, b);
					nodeCount++;
					if (child < bestScore) {
						bestScore = child;
						bestColumn = c;
					}
					
					b = Math.min(b, bestScore);
					if (b <= a) {
						break;
					}
				}
			}
		}
		
		if (depth == 0) { // when the minimax gets back to the top of the tree, return the best column
			System.out.println("total nodes searched = " + nodeCount);
			return bestColumn;
		} 
		else { // if its not the top of the tree, return the score
			return bestScore;
		}
	}
	
	/**
	 * method to evaluate how good a column selection is. returns the score of the column
	 * @param board
	 * @param player
	 * @return
	 */
	public int evaluate(ConnectBoard board, int player) {
		int op = -player;
		int score = 0;
		int lastRow = board.getLastRow();
		int lastCol = board.getLastCol();
		
		if (board.connected() == player) {
			return Integer.MAX_VALUE;
		}
		else if (board.connected() == op) {
			return Integer.MIN_VALUE;
		}
		
		// prioritize center control
		int center = board.MAX_COLS / 2;
		if (lastCol == center) {
			score += 2;
		}
		
		// if you can connect to pieces that are already placed, add to the score
		int numConnected = board.countConnected(player, lastRow, lastCol);
		score += numConnected;
		
		// if the opponent is about to win, make it a top priority
		if (board.isThreat(op, board)) {
			score += 50;
		}
		
		// if the agent can win, it will take that action
		if (board.isWinnable(player, board)); {
			score += 200;
		}
		
		return score;
	}
	
	/**
	 * used as a temporary board to be modified in place of the actual board
	 * @param board
	 * @return
	 */
	public ConnectBoard tempBoard(ConnectBoard board) {
		int[][] tBoard = new int[MAX_ROWS][MAX_COLS];
		for (int r = 0; r < MAX_ROWS; r++) {
			for (int c = 0; c < MAX_COLS; c++) {
				tBoard[r][c] = board.getBoard()[r][c];
			}
		}
		
		ConnectBoard tempBoard = new ConnectBoard(MAX_ROWS, MAX_COLS);
		
		for (int r = 0; r < MAX_ROWS; r++) {
			for (int c = 0; c < MAX_COLS; c++) {
				tempBoard.getBoard()[r][c] = tBoard[r][c];
			}
		}
		
		return tempBoard;
	}

	@Override
	public void initializeWithBoard(GameBoard board) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
