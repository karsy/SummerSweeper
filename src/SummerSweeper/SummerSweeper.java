package SummerSweeper;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SummerSweeper extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private static SummerSweeper frame;
	private static Image flagImage;
	private static ImageIcon flagIcon;
	private static Button[][] field;
	private static Board board;

	public SummerSweeper() {
		super();
		
		field = new Button[9][9];
		System.out.println(field);
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				field[x][y] = new Button(x, y);
				field[x][y].addActionListener(this);
				field[x][y].addMouseListener(this);
				field[x][y].setFocusable(false);
			}
		}

		board = new Board(this, field);
		board.setDifficulty(Board.EASY);
		board.addMines(field);
		board.getContainer().setBackground(Color.WHITE);

		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j].getType() != Button.TYPE_MINE)
					field[i][j].setType(field[i][j].getMinesAround(field));
			}
		}

		try {
			flagImage = ImageIO.read(new File("res/flag.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		flagImage = flagImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		flagIcon = new ImageIcon(flagImage);
	}

	public static void main(String[] args) {
		frame = new SummerSweeper();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("SummerSweeper");
		frame.setBounds(0, 0, field[0].length * Button.SIZE, field.length * Button.SIZE);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Button) {
			Button source = (Button) e.getSource();
			source.onLeftClick(board, field);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Button source = (Button) e.getSource();
			source.onRightClick(flagIcon);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}