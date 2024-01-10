package path.agent;

import java.awt.Point;
import java.util.List;
import java.util.Map;

import path.agent.heuristic.Heuristic;
import path.level.Level;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.ArrayList;

public class AStarAgent extends PathAgent {
	
	private Heuristic huey;
	
	public AStarAgent(Level level) {
		super(level);
	}
	
	public void setHeuristic(Heuristic heuristic) {
        this.huey = heuristic;
    }

    /**
     * finds the path from the start point to the goal, all the while keeping track of the fringe and the visited states
     */
    @Override
    public List<Point> findPath() {
    	huey = new Heuristic(goal);
    	
        if (start == null || goal == null) {
            return null; // No start or goal state
        }

        PriorityQueue<Node> fringe = new PriorityQueue<>(); // nodes yet to visit
        Map<Point, Node> visitedNodes = new HashMap<>(); // visited node
        

        Node startNode = new Node(start, null, null);
        root = startNode;
        startNode.setCost(0);
        startNode.setFval(huey.h(start));

        fringe.add(startNode);

        while (!fringe.isEmpty()) { 
            Node currentNode = fringe.poll(); // remove the current node from the fringe

            if (currentNode.getState().equals(goal)) {  // if the current node is the goal, return the path
                path = pathFromNode(currentNode);
                return path;
            }

            List<Node> neighbors = expandNeighbors(currentNode); // run the expand neighbors class

            for (Node neighbor : neighbors) {
                if (visitedNodes.containsValue(neighbor)) { // if the node has already been visited
                    continue; // skip it
                }

                double tentativeCost = currentNode.getCost() + currentNode.getState().distance(neighbor.getState()); // the cost is the current nodes cost plus the distance

                if (!fringe.contains(neighbor) || tentativeCost < neighbor.getCost()) { // if the fringe doesn't contain the neighbor or the cost is greater
                	currentNode.getChildren().add(neighbor);
                    neighbor.setParent(currentNode); // set all the values of the neighbor to the current to move there
                    neighbor.setCost(tentativeCost);
                    neighbor.setFval(currentNode.getCost() + huey.h(neighbor.getState()));

                    visitedNodes.put(neighbor.getState(), neighbor);

                    if (!fringe.contains(neighbor)) {
                        fringe.add(neighbor);
                    }
                }
            }
        }

        return null; // only if there is no path
    }

    /**
     * expands upon a neighbor of the current node to see if any of the neighbors two states away are valid
     * @param node
     * @return
     */
    private List<Node> expandNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>(); // make a list of all the neighbors
        for (Action action : Action.values()) { // for all the possible actions
            Point nextState = applyAction(node.getState(), action); // apply them using the applyAction method
            if (level.isValid(nextState)) { // if the next state is valid, add the neighbor
                neighbors.add(new Node(nextState, null, action));
            }
        }
        return neighbors;
    }

    private Point applyAction(Point point, Action action) {
        int x = point.x;
        int y = point.y;

        switch (action) {
            case N:
                y -= 10;
                break;
            case S:
                y += 10;
                break;
            case E:
                x += 10;
                break;
            case W:
                x -= 10;
                break;
        }

        return new Point(x, y);
    }
    
    public String toString() {
		return "A* Agent";
	}

}
