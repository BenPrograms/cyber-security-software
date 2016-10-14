package ben.mellogame.source;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
public class Game {
	private static GraphicsDevice vc;
	
	public static void main(String[] args){
		JFrame window = new JFrame("</START>");
		
		GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = e.getDefaultScreenDevice();
		
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setContentPane(new GamePanel());
		window.setResizable(true);
		window.setUndecorated(true);
		window.pack();
		window.setVisible(true);
		vc.setFullScreenWindow(window);
	}
}