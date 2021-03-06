package SummerSweeper;

import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = 1L;
	public static final int TYPE_EMPTY = 0, TYPE_MINE = 9;

	private int type;
	private boolean open, flagged;
	private Point position;

	public Button(int x, int y) {
		super();

		this.setMargin(new Insets(-10, -10, -10, -10));
		this.position = new Point(x, y);
		this.open = false;
		this.flagged = false;
		this.setVisible(true);
	}

	public ArrayList<Button> getNeighbours(Button[][] field) {
		ArrayList<Button> neighbours = new ArrayList<Button>();
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				if (i == 0 && j == 0)
					continue;
				try {
					neighbours.add(field[this.position.y + i][this.position.x + j]);
				} catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
			}
		}
		return neighbours;
	}

	public void onLeftClick(Board board, Button[][] field) {
		if (!this.flagged)
			this.setOpen(true);
		if (this.type == TYPE_EMPTY)
			board.revealTilesAround(this, field);
	}

	public void onRightClick(ImageIcon flag, Board board, InfoPanel infoPanel, Button[][] field) {
		if (!this.open && !this.flagged && board.getMinesLeft() != 0) {
			this.setFlagged(true, flag);
			board.decreaseMines(infoPanel);
		} else if (this.flagged) {
			this.setFlagged(false, flag);
			this.setText("");
			board.increaseMines(infoPanel);
		} else if (this.open) {
			board.revealTilesAround(this, field);
		}
	}

	public int getMinesAround(Button[][] field) {
		int mines = 0;
		ArrayList<Button> neighbours = this.getNeighbours(field);
		for (Button neighbour : neighbours) {
			if (neighbour.getType() == TYPE_MINE)
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

	public void setText(String text) {
		super.setIcon(null);
		super.setText(text);
	}

	public void setIcon(ImageIcon icon) {
		super.setText(null);
		super.setIcon(icon);
	}

	public void setOpen(boolean open) {
		if (open && this.type != TYPE_EMPTY) {
			this.setContentAreaFilled(false);
			this.setBorderPainted(false);
			this.setText(this.type != TYPE_MINE ? String.valueOf(this.type) : "X");
		} else if (open)
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