package SummerSweeper;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = 1L;
	public static final int TYPE_EMPTY = 0, TYPE_ONE = 1, TYPE_TWO = 2, TYPE_THREE = 3, TYPE_FOUR = 4, TYPE_FIVE = 5, TYPE_SIX = 6, TYPE_SEVEN = 7, TYPE_EIGHT = 8, TYPE_MINE = 9;

	private int type;
	private Point position;

	public Button(int x, int y) {
		super();
		
		this.setMinimumSize(new Dimension(40, 40));
		this.position = new Point(x, y);
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
		if (type == TYPE_MINE)
			this.setText("x");
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public Point getPosition() {
		return position;
	}

	public void setText(String text){
		super.setIcon(null);
		super.setText(text);
	}
	
	public void setIcon(ImageIcon icon){
		super.setText(null);
		super.setIcon(icon);
	}
}