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

	private static int mines = 10, maxMines = 10;
	private static Random random = new Random();
	
	private Dimension size;
	private JPanel container = new JPanel();
	private int difficulty;

	public Board(JPanel parent, Button[][] field) {
		size = new Dimension(field[0].length, field.length);
		
		GridLayout containerLayout = new GridLayout(field.length, field[0].length);
        container.setLayout(containerLayout);
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

	public void addMines(Button[][] field, int amountToAdd) {
		int placedMines = 0;
		
		if (field.length * field[0].length < amountToAdd) {
			amountToAdd = field.length * field[0].length - 1;
		}
		
		mines = amountToAdd;
		maxMines = amountToAdd;
		
		while (placedMines < amountToAdd) {
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
		ArrayList<Button> neighbours;
		stack.add(currentButton);

		while (stack.size() > 0) {
			neighbours = currentButton.getNeighbours(field);
			for (Button button : neighbours) {
				if (button.isOpen() || button.isFlagged())
					continue;
				button.setOpen(true);
				if (button.getType() == Button.TYPE_EMPTY)
					stack.add(button);
				if (button.getType() == Button.TYPE_MINE)
					SummerSweeper.lost();
			}
			currentButton = stack.remove(stack.size() - 1);
		}
	}

	public void decreaseMines(InfoPanel infoPanel) {
		mines--;
		infoPanel.getMinesPane().setText("Mines: " + mines);
	}

	public void increaseMines(InfoPanel infoPanel) {
		mines++;
		infoPanel.getMinesPane().setText("Mines: " + mines);
	}

	public int getMinesLeft() {
		return mines;
	}
	
	public int getMaxAmountOfMines() {
		return maxMines;
	}
	
	public Dimension getSize() {
		return size;
	}
}