package path.test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import path.agent.PathAgent;
import path.gui.MainFrame;
import path.level.Level;
import java.awt.Point;

class PathAgentTest {
	
	private PathAgent pathAgent;
	
	@BeforeEach
	public void build() {
		/*
		 * First we build up the level (a container of obstacle zones.  Our agent is not
		 * allowed in these zones.
		 */
		Level theLevel = Level.builder().size(1000, 1000)
				
				.addZone(new Rectangle(0,0,20,300))
				.addZone(new Rectangle(300,0,20,100))
				.addZone(new Rectangle(310,260,20,40))
				
				.addZone(new Rectangle(0,300,120,100))
				.addZone(new Rectangle(230,300,100,100))
				.addZone(new Rectangle(230,400,200,20))
				.addZone(new Rectangle(230,420,50,280))  
				.addZone(new Rectangle(130,500,100,200))  
				
				
				.addZone(new Rectangle(600,0,400,50))
				.addZone(new Rectangle(600,50,20,50))
				
				.addZone(new Rectangle(400,100,100,200))
				
				.addZone(new Rectangle(600,200,20,100))
				.addZone(new Rectangle(520,380,80,20))   
				
				.addZone(new Rectangle(600,300,200,100))
				.addZone(new Rectangle(800,250,30,200))
				
				.addZone(new Rectangle(300,750,150,50))
				.addZone(new Rectangle(900,450,100,550))
				.addZone(new Rectangle(600,750,200,50))
				.addZone(new Rectangle(800,650,30,150))
				.build();
		
		
		/*
		 * Now display our level and interact with the operator. 
		 */
		MainFrame frame = new MainFrame(theLevel);
		frame.setVisible(true);
	}

	@Test
	void testSearchTreeDepth() {
		Point start = new Point(10,10); 	// set the start and end easily for testing
		Point goal = new Point(12,12);
		pathAgent.setStart(start);
		pathAgent.setGoal(goal); 
		
		List<Point> path = pathAgent.findPath(); // populate the search tree
		
		int expecDepth = 4; // only 4 points difference between the two, so they should be equal
		int actDepth = pathAgent.searchTreeDepth();
		
		assertEquals(expecDepth, actDepth); // make sure they equal
	}
	
	@Test
	void testSearchTreeStates() {
		Point start = new Point(10,10); // set the start and end easily for testing
		Point goal = new Point(12,12);
		pathAgent.setStart(start);
		pathAgent.setGoal(goal); 
		
		List<Point> path = pathAgent.findPath(); // populate the search tree
		
		List<Point> states = pathAgent.searchTreeStates(); // gets the list of states
		
		assertNotNull(states); // must contain at least the start and goal
		assertTrue(states.contains(pathAgent.getStart()));
		assertTrue(states.contains(pathAgent.getGoal()));
		
		// should add more for better testing
	}
	
	// add more tests for better coverage

}
