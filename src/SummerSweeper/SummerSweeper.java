package SummerSweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class SummerSweeper extends JFrame implements ActionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	public static final int PLAYING = 0, PAUSED = 1, READY = 2;

	private static SummerSweeper game;
	private static JPanel container;
	private static BorderLayout containerLayout = new BorderLayout();
	private static InfoPanel infoPanel;
	private static Image flagImage;
	private static ImageIcon flagIcon;
	private static Button[][] field;
	private static Board board;
	private static int gameState = READY;

	public SummerSweeper(String title) {
		super(title);

		container = new JPanel();
		container.setLayout(containerLayout);
		container.setLocation(0, 0);
		container.setVisible(true);
		this.add(container);

		infoPanel = new InfoPanel(this);
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

	private void initBoard(int width, int height, int mines, int difficulty) {
		field = new Button[height][width];
		
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
		board.addMines(field, mines);
		
		for (int y = 0; y < field.length; y++) {
			for (int x = 0; x < field[y].length; x++) {
				if (field[y][x].getType() != Button.TYPE_MINE) {
					field[y][x].setType(field[y][x].getMinesAround(field));
				}
			}
		}

		infoPanel.getMinesPane().setText("Mines: " + board.getMinesLeft());
		gameState = READY;

	}
	
	private void initBoard(int difficulty) {
		if (difficulty == Board.EASY)
			initBoard(9, 9, 10, difficulty);
		else if (difficulty == Board.MEDIUM)
			initBoard(16, 16, 40, difficulty);
		else if (difficulty == Board.HARD)
			initBoard(30, 16, 99, difficulty);
		else if (difficulty == Board.CUSTOM)
			initBoard(field[0].length, field.length, board.getMaxAmountOfMines(), difficulty);
	}

	public static void main(String[] args) {
		game = new SummerSweeper("SummerSweeper");
		game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		game.setPreferredSize(new Dimension(field[0].length * 50, field.length * 50 + infoPanel.getHeight()));
		game.setMinimumSize(new Dimension(field[0].length * 40, field.length * 40 + infoPanel.getHeight()));
		game.setVisible(true);
		game.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Button) {
			if (gameState == READY) {
				gameState = PLAYING;
				new Thread(infoPanel.getTimer()).start();
			}
			
			Button source = (Button) e.getSource();
			source.onLeftClick(board, field);
			
			if (source.getType() == Button.TYPE_MINE) {
				lost();
			} else if (getWin()) {
				gameState = PAUSED;
				for (int y = 0; y < field.length; y++) {
					for (int x = 0; x < field[y].length; x++) {
						if (field[y][x].getType() == Button.TYPE_MINE) {
							field[y][x].setIcon(flagIcon);
						}
					}
				}
				infoPanel.getMinesPane().setText("Mines: 0");
				if (JOptionPane.showConfirmDialog(new JOptionPane("You won!"), "You won! Restart?") == JOptionPane.YES_OPTION) {
					restart(board.getSize().width, board.getSize().height, board.getMaxAmountOfMines(), board.getDifficulty());
				} else {
					System.exit(0);
				}
			}
		}
	}

	public static void lost() {
		gameState = PAUSED;
		if (JOptionPane.showConfirmDialog(new JOptionPane("You lost!"), "You lost! Restart?") == JOptionPane.YES_OPTION) {
			game.restart(board.getSize().width, board.getSize().height, board.getMaxAmountOfMines(), board.getDifficulty());
		} else {
			System.exit(0);
		}
	}

	public static void reshapeBoard(int width, int height, int mines) {
		game.restart(width, height, mines, Board.CUSTOM);
	}
	
	private void restart(int width, int height, int mines, int difficulty) {
		board.getContainer().removeAll();
		container.remove(board.getContainer());
		initBoard(width, height, mines, difficulty);
		infoPanel.getTimer().setText("Time: 0s");
		game.setPreferredSize(new Dimension(field[0].length * 50, field.length * 50 + infoPanel.getHeight()));
		game.setMinimumSize(new Dimension(field[0].length * 40, field.length * 40 + infoPanel.getHeight()));
		game.pack();
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

	public Button[][] getField() {
		return field;
	}
	public Board getBoard() {
		return board;
	}
	
	public static int getGameState() {
		return gameState;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			Button source = (Button) e.getSource();
			source.onRightClick(flagIcon, board, infoPanel, field);
			if (getWin()) {
				gameState = PAUSED;
				for (int y = 0; y < field.length; y++) {
					for (int x = 0; x < field[y].length; x++) {
						if (field[y][x].getType() == Button.TYPE_MINE) {
							field[y][x].setIcon(flagIcon);
						}
					}
				}
				infoPanel.getMinesPane().setText("Mines: 0");
				if (JOptionPane.showConfirmDialog(new JOptionPane("You won!"), "You won! Restart?") == JOptionPane.YES_OPTION) {
					restart(board.getSize().width, board.getSize().height, board.getMaxAmountOfMines(), board.getDifficulty());
				} else {
					System.exit(0);
				}
			}
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