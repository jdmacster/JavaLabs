import java.util.Random;

/**
 * an instance of this class is the vbot agent that scours the world we made searching for dirt to suck up. if it finds dirt, it cleans it. If the tile it is on isnt dirty, it randomly moves in one
 * of the 4 directions.
 * @author jdyla
 *
 */
public class Agent { 
	
	public Actions chooseAction(World world, int x, int y) { 	// keep it simple stupid. class that selects the action the vbot takes
		
        if (world.currentTileState(x, y) == 1) { 		// if the tile is dirty, suck it clean
            return Actions.SUCK;
        } 
        else {		// if not dirty, then randomly move. the bot could get stuck sucking in place if the code is broken. hasnt happened yet
            Random rand = new Random();
            int move = rand.nextInt(4);
            
            switch (move) {
                case 0:
                    return Actions.UP;
                case 1:
                    return Actions.DOWN;
                case 2:
                    return Actions.LEFT;
                case 3:
                    return Actions.RIGHT;
                default:
                	return Actions.SUCK;
            }
        }
    }
}
