import java.awt.Color;
import java.awt.Graphics;

public class Tile {

	protected int x, y, size;
	protected TileState state;
	protected boolean newlyOozing;
	
	/**
	 * Constructor for the tile
	 * @param x
	 * @param y
	 * @param size
	 * @param state
	 */
	public Tile(int x, int y, int size, TileState state) {
		
		this.x = x;
		this.y = y;
		this.size = size;
		this.state = state;
		this.newlyOozing = false;
	}
	
	public TileState getState() {
		return state;
	}


	public void setState(TileState state) {
		this.state = state;
	}
	
	/**
	 * I honestly dont completely understand this part, got help in tutoring. But if the state is not part of the enum it throws an error
	 * @param stateString
	 */
	public void setState(String stateString) {
		if (stateString == null) {
			return;
		}
		
		try { 			// testing to see if the value is part of the enum
		       this.state = TileState.valueOf(stateString);
		       return;
		    } catch (IllegalArgumentException ex) {
		    	System.err.println("Could not set enum");
		    	return;
		  }
	}


	/**
	 * choosing the color of the next ooze
	 * @param g
	 * @param colors
	 */
	public void color(Graphics g, Color[] colors) {
		 
		switch (state) { 			// setting the drawing color
		case block:
			g.setColor(Color.black);
			break;
		case color1:
			g.setColor(colors[0]);
			break;
		case color2:
			g.setColor(colors[1]);
			break;
		case free:
			g.setColor(Color.white);
			break;
		case color3:
			g.setColor(colors[2]);
			break;
		case color4:
			g.setColor(colors[3]);
			break;
		case color5:
			g.setColor(colors[4]);
			break;
		default:
			g.setColor(Color.white);
			break;
		}
		g.fillRect(x, y, size, size); 			// set the tile to the color selected
		
	}
	
}
