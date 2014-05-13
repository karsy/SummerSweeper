package SummerSweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = 1L;
	public static final int SIZE = 50;
	public static final int TYPE_EMPTY = 0, TYPE_ONE = 1, TYPE_TWO = 2, TYPE_THREE = 3, TYPE_FOUR = 4, TYPE_FIVE = 5, TYPE_SIX = 6, TYPE_SEVEN = 7, TYPE_EIGHT = 8, TYPE_MINE = 9;

	private int type;
	private boolean open, flagged;
	private Point position;

	public Button(int x, int y) {
		super();

		this.setMinimumSize(new Dimension(SIZE, SIZE));
		this.position = new Point(x, y);
		this.open = false;
		this.flagged = false;
		this.setVisible(true);
	}

	public ArrayList<Button> getNeightbours(Button[][] field) {
		ArrayList<Button> neighbours = new ArrayList<Button>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				try {
					neighbours.add(field[this.position.x + i][this.position.y + j]);
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}
		return neighbours;
	}

	public void onLeftClick(Board board, Button[][] field) {
		if (!this.flagged)
			this.setOpen(true);
		if (this.type != TYPE_MINE) {
			this.setBackground(Color.WHITE);
		}
		if (this.type == TYPE_EMPTY)
			board.revealTilesAround(this, field);
	}

	public void onRightClick(ImageIcon flag) {
		if (!this.open && !this.flagged) {
			this.setFlagged(true, flag);
		} else if (this.flagged){
			this.setFlagged(false, flag);
			this.setText("");
		}
	}

	public int getMinesAround(Button[][] field) {
		int mines = 0;
		ArrayList<Button> neighbours = this.getNeightbours(field);
		for (int i = 0; i < neighbours.size(); i++) {
			if (neighbours.get(i).getType() == TYPE_MINE)
				mines++;
		}
		return mines;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public Point getPosition() {
		return position;
	}

	public void setText(String text) {
		super.setIcon(null);
		super.setText(text);
	}

	public void setIcon(ImageIcon icon) {
		super.setText(null);
		super.setIcon(icon);
	}

	public void setOpen(boolean open) {
		if (open && this.type != TYPE_EMPTY)
			this.setText(this.type != TYPE_MINE ? String.valueOf(this.type) : "X");
		else if (open && this.type == TYPE_EMPTY)
			this.setVisible(false);
		this.open = open;
	}

	public boolean isOpen() {
		return open;
	}

	public void setFlagged(boolean flagged, ImageIcon flag) {
		if (!this.flagged)
			this.setIcon(flag);
		this.flagged = flagged;
	}

	public boolean isFlagged() {
		return flagged;
	}
}