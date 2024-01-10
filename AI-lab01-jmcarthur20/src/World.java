
/**
 * an instance of this class is the world in which the vbot will be released into to clean. It is a randomly sized grid between 1 and 10 columns and rows. the grid will be full of 0s and 1s, 
 * where 1 = dirt and 0 = clean. The vbot agent is represented by the letter 'A' and it shows its movement around the world
 * @author jdyla
 *
 */
public class World {
	int[][] world;
	int agentRow;	// current agents row
	int agentColumn; 	// current agent column
    
    public World(int[][] world, int agentRow, int agentColumn) { 	// constructor for the world with the dimensions and starting location of the vbot
        this.world = world; 	// instantiates the values
        this.agentRow = agentRow;
        this.agentColumn = agentColumn;
    }
    
    public int currentTileState(int x, int y) { 	// keeps track of the cleanliness of the tiles. returns either 1 for dirty or 0 for clean
        if (x < 0 || x >= world.length || y < 0 || y >= world[0].length) { 		// if the value is not 0 or 1, throw an error
            return -1;
        }
        return world[x][y]; 		
    }
    
    public void cleanTile(int x, int y) { 		// function to clean the tile when the agent is on it
        if (currentTileState(x, y) == 1) {
            world[x][y] = 0;
        }
    }
    
    public void moveAgent(int x, int y) { 		// moves the agent by setting the values of its row and column
    	agentRow = x;
    	agentColumn = y;
    }
    
    public void paint() {		// method to build the world, filling it with the agent and 0s and 1s
    	for (int i = 0; i < world.length; i++) {
    		for (int j = 0; j < world[0].length; j++) {
    			if (i == agentRow && j == agentColumn) { 		// i understand that this is broken because it only shows the agent when its location matches the for loops i and j variable, but i 
    															// wasnt sure how else to do it
    				System.out.print("A ");		// A for agent
    				}
    			else if (world[i][j] == -1){		// if the error from currentTileState is thrown, put an X in this tile to show it is broken
    				System.out.print("X ");
    			}
    			else {
    				System.out.print(world[i][j] + " ");
    				
    				
    			}
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
}
