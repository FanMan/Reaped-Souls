package Game;

import javax.swing.JFrame;

public class Game {
	
	// the main class to start up the entire application
	public static void main(String[] args) {
		ScreenSize screen = new ScreenSize();
		
		JFrame f = new JFrame("Reaped Souls");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setBounds(100, 100, 1500, 1000);
		/**
		 * this is set to resize automatically on any screen monitor especially because one screen could have
		 * a different monitor resolution than another
		 */
		f.setSize((int) (screen.getScreenWidth() / 1.45), (int) (screen.getScreenHeight() / 1.45));
		//f.setBackground(Color.black);
		f.getContentPane().add(new Display());
		f.setVisible(true);
		f.setResizable(true);
		f.pack();
		
		
		// will keep this here just in case until testing has been done on other monitors
		//Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height / 2 - f.getSize().height / 2);
		
		// This is set to start the game application in the center of the monitor
		f.setLocation((int) (screen.getScreenWidth() / 2 - f.getSize().width / 2),
				(int) (screen.getScreenHeight() / 2 - f.getSize().height / 2));
		
	}
}
