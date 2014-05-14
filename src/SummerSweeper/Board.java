package SummerSweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

public class Board {
	public static final int EASY = 0, MEDIUM = 1, HARD = 2, CUSTOM = 3;

	private static GridLayout containerLayout;
	private JPanel container;
	private static Random random = new Random();

	private int difficulty;

	public Board(JPanel parent, Button[][] field) {
		containerLayout = new GridLayout(field.length, field[0].length);
		container = new JPanel();
		container.setLayout(containerLayout);
		container.setPreferredSize(new Dimension(field[0].length * 50, field.length * 50));
		container.setMinimumSize(new Dimension(field[0].length * 40, field.length * 40));
		containerLayout.preferredLayoutSize(container);

		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[y].length; x++) {
				container.add(field[y][x]);
			}
		}

		container.setBackground(Color.WHITE);
		container.setVisible(true);
		parent.add(container, BorderLayout.CENTER);
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
			int x = random.nextInt(field[0].length);
			int y = random.nextInt(field.length);
			if (field[y][x].getType() != Button.TYPE_MINE) {
				field[y][x].setType(Button.TYPE_MINE);
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
				if (neighbours.get(i).isOpen() || neighbours.get(i).isFlagged())
					continue;
				neighbours.get(i).setOpen(true);
				if (neighbours.get(i).getType() == Button.TYPE_EMPTY)
					stack.add(neighbours.get(i));
			}
			currentButton = stack.remove(stack.size() - 1);
		}
	}
}