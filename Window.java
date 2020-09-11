package main;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas{
	private static final long serialVersionUID = -6357664197674207224L;
	public int ScreenWidth;
	public int ScreenHeight;
	Window(int width, int height, String title, Game game)
	{
		JFrame frame = new JFrame(title);
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(0, 0));
		frame.setSize(width, height);
//		frame.setExtendedState(frame.getExtendedState() | frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
		this.requestFocus();
	}
}
