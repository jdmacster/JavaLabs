package cs440.c4;

public class MinimaxAgent implements Agent {

	private GameBoard board;
	public final int WIN = 4;   // Four adjacent checkers wins the game.
	public static final int MAX_COLS = 7;
	public static final int MAX_ROWS = 6;

	@Override
	public void initializeWithBoard(GameBoard board) throws Exception {
		this.board = board;
		
	}

	@Override
	public int nextAction() throws Exception {
		int bestCol = -1;
		int bestScore = Integer.MIN_VALUE;
		
		for (int col = 0; col < MAX_COLS; col++) {
			if (!board.isColumnFull(col)) {
				GameBoard tempBoard = tempBoard(board);
				int row = tempBoard.addDisk(col, GameBoard.AGENT);
				int score = minimax(tempBoard, 1, false);
				
				if (score > bestScore) {
					bestScore = score;
					bestCol = col;
				}
			}
				
		}
		
		
		return bestCol;
	}
	
	public int minimax(GameBoard board, int depth, boolean agent) {
		// i just learned about the ? tertiary operation. cool stuff
		int player = agent ? GameBoard.AGENT : GameBoard.USER; // if agent is true, then the gameboard knows its the agents turn, if not, its the users turn
		int opponent = agent ? GameBoard.USER : GameBoard.AGENT;
		
		if (depth == 0 || board.gameOver()) {
			return evaluate(board);
		}
		
		if (agent) {
			int maxScore = Integer.MIN_VALUE;
			for (int col = 0; col < MAX_COLS; col++) {
				if (!board.isColumnFull(col)) {
					GameBoard tempBoard = tempBoard(board);
					int row = tempBoard.addDisk(col, player);
					int score = minimax(tempBoard, depth - 1, false);
					maxScore = Math.max(maxScore, score);
				}
			}
			return maxScore;
		}
		else {
			int minScore = Integer.MAX_VALUE;
			for (int col = 0; col < MAX_COLS; col++) {
				if (!board.isColumnFull(col)) {
					GameBoard tempBoard = tempBoard(board);
					int row = tempBoard.addDisk(col, player);
					int score = minimax(tempBoard, depth - 1, true);
					minScore = Math.min(minScore, score);
				}
			}
			return minScore;
		}
	}
	
	public int evaluate(GameBoard board) {
		int agentWin = GameBoard.AGENT * WIN;
		int userWin = GameBoard.USER * WIN;
		
		if (board.connected() == GameBoard.AGENT) {
			return agentWin;
		}
		else if (board.connected() == GameBoard.USER) {
			return -userWin;
		}
		else {
			return 0;
		}
	}
	
	public GameBoard tempBoard(GameBoard board) {
		int[][] tBoard = new int[MAX_ROWS][MAX_COLS];
		for (int r = 0; r < MAX_ROWS; r++) {
			for (int c = 0; c < MAX_COLS; c++) {
				tBoard[r][c] = board.getBoard()[r][c];
			}
		}
		
		GameBoard tempBoard = new ConnectBoard(MAX_ROWS, MAX_ROWS);
		
		for (int r = 0; r < MAX_ROWS; r++) {
			for (int c = 0; c < MAX_COLS; c++) {
				tempBoard.getBoard()[r][c] = tBoard[r][c];
			}
		}
		
		return tempBoard;
	}

	@Override
	public void initializeWithBoard(ConnectBoard board) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
