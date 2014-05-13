package SummerSweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board {
	public static final int EASY = 0, MEDIUM = 1, HARD = 2, CUSTOM = 3;

	private static GridLayout containerLayout;
	private JPanel container = new JPanel();
	private static Random random = new Random();

	private int difficulty;

	public Board(JFrame frame, Button[][] field) {
		containerLayout = new GridLayout(field.length, field[0].length);
		container.setBounds(0, 0, Button.SIZE * field[0].length, Button.SIZE * field.length);
		container.setMaximumSize(new Dimension(Button.SIZE * field.length, Button.SIZE * field[0].length));
		container.setLayout(containerLayout);
		containerLayout.preferredLayoutSize(container);

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
				placedMines++;
			}
		}
	}

	public JPanel getContainer() {
		return container;
	}

	public void revealTilesAround(Button source, Button[][] field) {
		Button currentButton = source;
		ArrayList<Button> stack = new ArrayList<Button>();
		ArrayList<Button> neighbours = new ArrayList<Button>();
		stack.add(currentButton);

		while (stack.size() > 0) {
			neighbours = currentButton.getNeightbours(field);
			for (int i = 0; i < neighbours.size(); i++) {
				if (neighbours.get(i).isOpen())
					continue;
				neighbours.get(i).setOpen(true);
				if (neighbours.get(i).getType() == Button.TYPE_EMPTY)
					stack.add(neighbours.get(i));
			}
			currentButton = stack.remove(stack.size() - 1);
		}
	}
}