package SummerSweeper;

import java.awt.BorderLayout;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SummerSweeper extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	private static SummerSweeper frame;
	private static JPanel container;
	private static BorderLayout containerLayout = new BorderLayout();
	private static InfoPanel infoPanel;
	private static Image flagImage;
	private static ImageIcon flagIcon;
	private static Button[][] field;
	private static Board board;

	public SummerSweeper(String title) {
		super(title);

		container = new JPanel();
		container.setLayout(containerLayout);
		container.setBounds(0, 0, this.getWidth(), this.getHeight());
		container.setVisible(true);
		this.add(container);

		infoPanel = new InfoPanel();
		infoPanel.setBounds(0, 0, this.getWidth(), 50);
		infoPanel.setVisible(true);
		infoPanel.setBackground(Color.WHITE);
		container.add(infoPanel, BorderLayout.NORTH);

		initBoard(Board.EASY);

		try {
			flagImage = ImageIO.read(new File("res/flag.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		flagImage = flagImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		flagIcon = new ImageIcon(flagImage);
	}

	private void initBoard(int difficulty) {
		if (difficulty == Board.EASY)
			field = new Button[9][9];
		else if (difficulty == Board.MEDIUM)
			field = new Button[16][16];
		else if (difficulty == Board.HARD)
			field = new Button[16][30];

		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[y].length; x++) {
				field[y][x] = new Button(x, y);
				field[y][x].addActionListener(this);
				field[y][x].addMouseListener(this);
				field[y][x].setFocusable(false);
			}
		}

		board = new Board(container, field);
		board.setDifficulty(difficulty);
		board.addMines(field);

		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[y].length; x++) {
				if (field[y][x].getType() != Button.TYPE_MINE)
					field[y][x].setType(field[y][x].getMinesAround(field));
			}
		}
	}

	public static void main(String[] args) {
		frame = new SummerSweeper("SummerSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Button) {
			Button source = (Button) e.getSource();
			source.onLeftClick(board, field);
			if (source.getType() == Button.TYPE_MINE) {
				lost();
			} else if (getWin()) {
				if (JOptionPane.showConfirmDialog(new JOptionPane("You won!"), "You won! Restart?") == JOptionPane.YES_OPTION) {
					restart();
				} else {
					System.exit(0);
				}
			}
		}
	}

	private void lost() {
		if (JOptionPane.showConfirmDialog(new JOptionPane("You lost!"), "You lost! Restart?") == JOptionPane.YES_OPTION) {
			restart();
		} else {
			System.exit(0);
		}
	}

	private void restart() {
		board.getContainer().removeAll();
		container.remove(board.getContainer());
		initBoard(Board.HARD);
		frame.pack();
	}

	private boolean getWin() {
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[y].length; x++) {
				if (!field[y][x].isOpen() && field[y][x].getType() != Button.TYPE_MINE)
					return false;
			}
		}
		return true;
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