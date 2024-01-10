package path.agent;

import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import path.agent.heuristic.Heuristic;
import path.level.Level;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 * All search agents for this program will extend this class.  The agents will be aware (have 
 * a handle on the level) as well as the start state and goal state.  Here the states are way points
 * in the level geometry.   After fully configuring the agent, we can ask the agent to plan, which
 * results in a path and a search tree rooted at root.
 * <p>
 * After an agent plans, we will retain the tree rooted by the root handle and the path so we can
 * interrogate the agent about the results of search.
 * <p>
 * NOTE:  The programmer must clear/reset the agent to return to initial configuration/before planning.
 * 
 */
public abstract class PathAgent {

	protected Level level;   // just in case we need to ask the level for information

	protected Point start;   // starting point established by operator
	
	protected Point goal;	 // ending point established by operator

	protected List<Point> path = null;   // a path resulting from planning; null means path not available

	protected Node root;   // a handle on the resulting search tree after planning.  null means no plan yet.


	/**
	 * All agents are born with a knowledge of the current level...for convenience.  May not
	 * be needed by some agents.
	 * 
	 * @param lvl
	 */
	public PathAgent(Level lvl) {
		this.level = lvl;
	}

	public Point getStart() {
		return start;
	}

	public void setStart(Point start) {
		this.start = start;
	}

	public Point getGoal() {
		return goal;
	}

	public void setGoal(Point goal) {
		this.goal = goal;
	}

	public List<Point> getPath() {
		return path;
	}
	

	/**
	 * Implements the search, building the resulting search tree and
	 * establishing the path (see setter method) as well as returning 
	 * the path (as a programmer's convenience).
	 * 
	 * @return
	 */
	public abstract List<Point> findPath();
	
	
	/**
	 * Walks back up the search tree from the specified search node providing the list
	 * of states (ie, way points) that will be traveled along this path.  NOTE: this is not
	 * the list of actions to take.  This is the list of states the define the path.  
	 * 
	 * @param current the current node (presumably the goal node)
	 * @return a list of points to travel to get to the goal from the start of search
	 */
	public List<Point> pathFromNode(Node current) {

		List<Point> pth = new LinkedList<>();

		while (current != null) {
			pth.add(current.getState());
			current = current.getParent();
		}

		Collections.reverse(pth);

		return pth;
	}

	
	/**
	 * Return the states of the entire search tree. 
	 * 
	 * @return collection of states/points reached or null if no tree there.
	 */
	public List<Point> searchTreeStates()  {
		
		if (root == null) { 		// if the search tree does not exist, return null
	        return null;
	    }

	    List<Point> states = new ArrayList<>();
	    Queue<Node> priorityQueue = new PriorityQueue<>((node1, node2) -> node1.getDepth() - node2.getDepth());

	    priorityQueue.add(root); 		// start the queue at the root 

	    while (!priorityQueue.isEmpty()) { 		// while the priority queue is not empty
	        Node currentNode = priorityQueue.poll(); 		// takes the head out of the queue and puts it in the current node
	        states.add(currentNode.getState());

	        for (Node child : currentNode.getChildren()) { 		// add the children into the priority queue
	            priorityQueue.add(child);
	        }
	    }
	   
	    return states;
	}

	
	/**
	 * Returns the depth d of the search tree or -1 if the tree
	 * does not exist.
	 * 
	 * @return the 
	 */
	public int searchTreeDepth() {
		
		if (root == null) { 		// if the search tree does not exist return -1
	        return -1;
	    }

	    int depth = 0; 		// start the depth at 0
	    Node currentNode = root;

	    while (currentNode.getParent() != null) { 		// go back through he path using the 
	    												// parents and count each one along the route
	        depth++;
	        currentNode = currentNode.getParent();
	    }

	    return depth;
	}

	
	
	/**
	 * Returns to a pre-search state in which no path is known and
	 * no search tree exists.
	 */
	public void clearPath() {
		
		this.root = null;
		this.path = null;
		
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Node getRoot() {
		return root;
	}



}
