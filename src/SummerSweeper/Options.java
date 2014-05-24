package SummerSweeper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Options extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private static final Dimension cellDimension = new Dimension(100, 30);
	private static final String[] difficulties = { "Easy", "Medium", "Hard", "Custom" };

	private static JPanel container;
	private static JComboBox<String> difficulty;
	private static JTextField xSize, ySize, mineField;
	private static JLabel xLabel, yLabel, mineLabel, diffLabel;
	private static JButton submit;

	public Options(JFrame mainFrame) {
		super("Options");

		this.setAlwaysOnTop(true);
		this.setLocation(mainFrame.getX(), mainFrame.getY());
		this.setLayout(new BorderLayout());

		container = new JPanel();
		container.setLayout(new GridLayout(4, 2));
		container.setVisible(true);
		this.add(container, BorderLayout.CENTER);

		diffLabel = new JLabel("Difficulty:");
		diffLabel.setHorizontalAlignment(SwingConstants.CENTER);
		diffLabel.setPreferredSize(cellDimension);
		diffLabel.setMinimumSize(cellDimension);
		diffLabel.setVisible(true);
		container.add(diffLabel);

		difficulty = new JComboBox<String>(difficulties);
		difficulty.setSelectedIndex(0);
		difficulty.addActionListener(new ChangeDiff());
		difficulty.setEditable(false);
		difficulty.setVisible(true);
		container.add(difficulty);

		xLabel = new JLabel("X (Max 30):");
		xLabel.setHorizontalAlignment(SwingConstants.CENTER);
		xLabel.setPreferredSize(cellDimension);
		xLabel.setMinimumSize(cellDimension);
		xLabel.setVisible(true);
		container.add(xLabel);

		xSize = new JTextField();
		xSize.setPreferredSize(cellDimension);
		xSize.setMinimumSize(cellDimension);
		xSize.addKeyListener(this);
		xSize.setVisible(true);
		container.add(xSize);

		yLabel = new JLabel("Y (Max 20):");
		yLabel.setHorizontalAlignment(SwingConstants.CENTER);
		yLabel.setPreferredSize(cellDimension);
		yLabel.setMinimumSize(cellDimension);
		yLabel.setVisible(true);
		container.add(yLabel);

		ySize = new JTextField();
		ySize.setPreferredSize(cellDimension);
		ySize.setMinimumSize(cellDimension);
		ySize.addKeyListener(this);
		ySize.setVisible(true);
		container.add(ySize);

		mineLabel = new JLabel("Mines (Max 99):");
		mineLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mineLabel.setPreferredSize(cellDimension);
		mineLabel.setMinimumSize(cellDimension);
		mineLabel.setVisible(true);
		container.add(mineLabel);

		mineField = new JTextField();
		mineField.setPreferredSize(cellDimension);
		mineField.setMinimumSize(cellDimension);
		mineField.addKeyListener(this);
		mineField.setVisible(true);
		container.add(mineField);

		submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(200, 30));
		submit.addActionListener(this);
		submit.setVisible(true);
		this.add(submit, BorderLayout.SOUTH);

		this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int x = 10, y = 10, mines = 10;

		try {
			x = Integer.parseInt(xSize.getText());
			y = Integer.parseInt(ySize.getText());
			mines = Integer.parseInt(mineField.getText());
		} catch (NumberFormatException ex) {
		}

		x = x < 1 ? 1 : x;
		x = x > 30 ? 30 : x;
		y = y < 1 ? 1 : y;
		y = y > 20 ? 20 : y;
		mines = mines < 0 ? 1 : mines;
		mines = mines > 99 ? 99 : mines;
		
		SummerSweeper.reshapeBoard(x, y, mines);
		this.setVisible(false);
	}

	public void setVisible(boolean visible, Button[][] field, Board board) {
		if (visible) {
			xSize.setText(String.valueOf(field[0].length));
			ySize.setText(String.valueOf(field.length));
			mineField.setText(String.valueOf(board.getMinesLeft()));
		}

		super.setVisible(visible);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		difficulty.setSelectedIndex(3);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	// For the difficulty dropdown
	private class ChangeDiff implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String choice = difficulty.getItemAt(difficulty.getSelectedIndex());
			
			if (choice == "Easy") {
				xSize.setText("9");
				ySize.setText("9");
				mineField.setText("10");
			} else if (choice == "Medium") {
				xSize.setText("16");
				ySize.setText("16");
				mineField.setText("40");
			} else if (choice == "Hard") {
				xSize.setText("30");
				ySize.setText("16");
				mineField.setText("99");
			}
		}
	}
}