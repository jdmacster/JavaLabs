import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class OozePanel extends JPanel implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	private Tile[][] tiles;			// generate the array of tiles
	private Color[] colors = new Color[5];			// generate the array of colors
	
	/** 
	 * The panel in which the ooze will be created and interacted with
	 */
	public OozePanel() {
		Random r = new Random();			// used to randomly generate color
		
		int size = 10; 			// size of tile
		tiles = new Tile[50][50]; 			// size of grid
		for (int i = 0; i < 50; i++) { 			// iterate through each tile
			for (int j = 0; j < 50; j++) {
				switch (r.nextInt(6)) {
				case 0: 			// when the int is 0
					tiles[i][j] = new Tile((size+1)*i+10, (size+1)*j+10, size, TileState.block); 			// new tile with a state block
					break;
				default:
					tiles[i][j] = new Tile((size+1)*i+10, (size+1)*j+10, size, TileState.free); 			// otherwise, it is an open block
				}
			}
		}
		for (int i = 0; i < colors.length; i++) { 			// tile colors
			int red = r.nextInt(255); 			//random values for the colors
			int green = r.nextInt(255);
			int blue = r.nextInt(255);
			colors[i] = new Color(red, green, blue); 			// array of colors
		}
		this.setPreferredSize(new Dimension(tiles.length*(size+1)+20,tiles.length*(size+1)+20)); 			// size of the panel
		this.setBackground(Color.black); 			// background color
		
		this.addMouseListener(this); 			// mouse listener
		
		Timer t = new Timer(30, this); 			// time to start
		t.start();
	}
	
	/**
	 * the paint method to color the tiles in the panel
	 */
	@Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < 50; i++) { 			// paint the squares
			for (int j = 0; j < 50; j++) {
				tiles[i][j].color(g, colors); 			// calls color function
				
			}
        }
        
    }
	
	/**
	 * Iteration to ensure the ooze properly spreads and doesnt go over blocks
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Random r = new Random(); 			// chance to spread - random
		for (int i = 0; i < 50; i++) { 			// for each tile
			for (int j = 0; j < 50; j++) {
				if (tiles[i][j].newlyOozing) { 			// see if it has oozed
					tiles[i][j].newlyOozing = false; 			// set to no longer new
					
					int k = (int)(((r.nextInt(3))/2)+2.5); 			// how many tiles could be oozed
					while (k > 0) {
						switch (r.nextInt(4)) {
						case 0:
							if (i > 0 && tiles[i-1][j].state != TileState.block && tiles[i-1][j].state != tiles[i][j].state) { 			// make sure not already taken
								tiles[i-1][j].state = tiles[i][j].state; 			// tile to the left is now same color
								tiles[i-1][j].newlyOozing = true; 			 // set to oozed
							}
							break;
						case 1:
							if (j > 0 && tiles[i][j-1].state != TileState.block && tiles[i][j-1].state != tiles[i][j].state) { 			// make sure not already taken
								tiles[i][j-1].state = tiles[i][j].state; 			// tile above is now same color
								tiles[i][j-1].newlyOozing = true; 			// set to oozed
							}
							break;
						case 2:
							if (i < 49 && tiles[i+1][j].state != TileState.block && tiles[i+1][j].state != tiles[i][j].state) { 			// make sure not already taken
								tiles[i+1][j].state = tiles[i][j].state; 			// right tile is now same color
								tiles[i+1][j].newlyOozing = true; 			// set to oozed
							}
							break;
						case 3:
							if (j < 49 && tiles[i][j+1].state != TileState.block && tiles[i][j+1].state != tiles[i][j].state) { 			// make sure not already taken
								tiles[i][j+1].state = tiles[i][j].state; 			// tile below is now same color
								tiles[i][j+1].newlyOozing = true; 			// set to oozed
							}
							break;
						default:
							break;
						}
						k--; 			// remove one tile
					} 

				}
				
			}
        }
		
		this.repaint();
		
	}

	/**
	 * sets color of the tile clicked on
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX(); 			// location of mouse click
		int y = e.getY(); 
		
		int i = (x-10)/11; 			// location of tile
		int j = (y-10)/11;
		if (tiles[i][j].state == TileState.block) { 			// return if block
			return;
		}
		tiles[i][j].newlyOozing = true; 			// set as oozed
		
		Random r = new Random(); 			// to decide color
		
		switch (r.nextInt(5)) {
		case 0:
			if (tiles[i][j].state != TileState.color1) {
				tiles[i][j].setState(TileState.color1);
				break;
			}
		case 1:
			if (tiles[i][j].state != TileState.color2) {
				tiles[i][j].setState(TileState.color2);
				break;
			}
		case 2:
			if (tiles[i][j].state != TileState.color3) {
				tiles[i][j].setState(TileState.color3);
				break;
			}
		case 3:
			if (tiles[i][j].state != TileState.color4) {
				tiles[i][j].setState(TileState.color4);
				break;
			}
		case 4:
			if (tiles[i][j].state != TileState.color5) {
				tiles[i][j].setState(TileState.color5);
				break;
			}
		default: 
			tiles[i][j].setState(TileState.color1);
		}
		this.repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
