import javax.swing.JFrame;

/**
 * starter method for the ooze
 * @author jdyla
 *
 */
public class Starter {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Ooze");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		OozePanel mainPanel = new OozePanel();
		frame.getContentPane().add(mainPanel);
		frame.pack();

	}

}
