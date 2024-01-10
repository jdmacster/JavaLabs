package cs440.c4;

public interface Agent {

	
	/**
	 * Method allows the agent to prepare for the game.  Can also be used
	 * to reset the game to start over or play again.   Implementers may
	 * completely ignore if not needed.
	 */
	public void initializeWithBoard(GameBoard board) throws Exception;
	
	
	/**
	 * Method to decide which action to play.  In this game, the action
	 * represents dropping a checker into one of the columns.  So the int
	 * returned identifies the column into which the agent wants to drop
	 * a checker.
	 * 
	 * @return
	 * @throws Exception
	 */
	public int nextAction() throws Exception;


	void initializeWithBoard(ConnectBoard board) throws Exception;

}