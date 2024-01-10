import java.util.Random;

public class Starter {

	public static void main(String[] args) {
		
		Random rand = new Random();
		int[][] nums = new int[rand.nextInt(2,10)][rand.nextInt(2,10)];		// random to set the dimensions of the world
		
		for (int i = 0; i < nums.length; i++) {
			for (int j = 0; j < nums[0].length; j++) {
				nums[i][j] = rand.nextInt(2);		// should be all 0's and 1's
			}
		}

		int agentStartX = 0; 		// start the agent at 0,0 in the world
		int agentStartY = 0;
		
		int moveCount = 0; 		// int to track the total moves the vbot makes
		
	    World world = new World(nums, agentStartX, agentStartY);
	    Agent agent = new Agent();
	        
	    for (int i = 0; i < nums.length; i++) {		// for each tile in the world
	        for (int j = 0; j < nums[0].length; j++) {
	            Actions act = agent.chooseAction(world, i, j);		// agent must choose an action
	                
	            if (act == Actions.SUCK) {		// changes the world based upon the agents chosen action
	                world.cleanTile(i, j);
	            }
	            else if (act == Actions.UP) {
	            	world.moveAgent(i - 1, j);
	            }
	            else if (act == Actions.DOWN) {
	            	world.moveAgent(i + 1, j);
	            }
	            else if (act == Actions.LEFT) {
	            	world.moveAgent(i, j - 1);
	            }
	            else if (act == Actions.RIGHT) {
	            	world.moveAgent(i, j + 1);
	            }
	            
	            moveCount++;
	            
	            System.out.println("Agent chose action: " + act);
	            System.out.println("Current move count: " + moveCount);
	            System.out.println("Current world:");
	            world.paint(); 		// once the agent has altered the world, re paint it
	            System.out.println("-------------");
	        }
	    }
    }
}
