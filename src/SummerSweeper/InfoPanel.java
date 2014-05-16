package SummerSweeper;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class InfoPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static Timer timer;
	private static JButton optionButton;
	private static JTextPane mines;
	private static Options options;
	
	private static SummerSweeper game;

	public InfoPanel(SummerSweeper sweeper) {
		super();

		// Save reference to main class
		game = sweeper;
		
		optionButton = new JButton("Options");
		optionButton.setFocusable(false);
		optionButton.addActionListener(this);
		this.add(optionButton);

		timer = new Timer();
		timer.setFocusable(false);
		timer.setPreferredSize(new Dimension(75, 17));
		timer.setText("Time: 0s");
		this.add(timer);

		mines = new JTextPane();
		mines.setFocusable(false);
		mines.setText("Mines: 10");
		this.add(mines);
		
		options = new Options(sweeper);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		options.setVisible(true, game.getField(), game.getBoard());
	}

	public Timer getTimer() {
		return timer;
	}
	
	public JTextPane getMinesPane() {
		return mines;
	}
}