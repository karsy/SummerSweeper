package SummerSweeper;

import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board {
	public static final int EASY = 0, MEDIUM = 1, HARD = 2;

	private static GridLayout easyLayout = new GridLayout(10, 10);
	private JPanel container = new JPanel();
	private static Random random = new Random();

	private int difficulty;

	public Board(JFrame frame, Button[][] field) {
		container.setLayout(easyLayout);
		container.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		easyLayout.preferredLayoutSize(container);
		
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				container.add(field[i][j]);
			}
		}
		container.setVisible(true);
		frame.add(container);
		frame.pack();
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void addMines(Button[][] field) {
		int mines = 0, placedMines = 0;
		if (difficulty == EASY)
			mines = 10;
		else if (difficulty == MEDIUM)
			mines = 40;
		else if (difficulty == HARD)
			mines = 99;

		while (placedMines < mines) {
			int x = random.nextInt(field.length);
			int y = random.nextInt(field[0].length);
			if (field[x][y].getType() != Button.TYPE_MINE) {
				field[x][y].setType(Button.TYPE_MINE);
				System.out.println(x + ", " + y);
				placedMines++;
			}
		}
	}
	
	public JPanel getContainer(){
		return container;
	}
}