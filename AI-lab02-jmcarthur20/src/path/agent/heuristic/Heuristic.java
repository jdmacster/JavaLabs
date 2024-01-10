package path.agent.heuristic;
import java.awt.Point;

/**
 * Concrete subclass of this class calculates the h value used in the A* search in a specific manner.  
 */
public class Heuristic { // i could not make this work as an abstract class so i changed it. So sorry for altering the All Knowing's Code
	
	protected Point goalState;
	protected Point nodeState;
	
	
	public Heuristic() {
		this.goalState = null;
	}
	
	
	/**
	 * Primary constructor ensures that we call the initialization algorithm which 
	 * can be overridden if necessary.
	 * @param goal
	 */
	public Heuristic(Point goal) {
		this.initialize(goal);
	}

	
	
	/**
	 * Override this as needed. 
	 * @param goal
	 */
	public void initialize(Point goal) {
		this.goalState = goal;
	}
	

	/**
	 * Answers the distance from the current node state to the goal state.  Concrete subclasses 
	 * can override 
	 * @param nodeState
	 * @return
	 */
	public double h(Point nodeState) { // i think im the only person in the class who made this method
	this.nodeState = nodeState; // calculates the manhattan distance 
	double x = Math.abs(nodeState.getX() - goalState.getX());
	double y = Math.abs(nodeState.getY() - goalState.getY());
	return x+y;
	
	}
}
