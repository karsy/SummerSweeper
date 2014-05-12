package SummerSweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class SummerSweeper extends JFrame implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	//private static final int screenW = Toolkit.getDefaultToolkit().getScreenSize().width;
	private static final int screenH = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	private static SummerSweeper frame;
	private static Image flagImage;
	private static ImageIcon flagIcon;
	private static Dimension screen, oldScreen;
	private Button[][] field;
	private Board board;

	public SummerSweeper() {
		super();
		this.addMouseListener(this);
		
		field = new Button[10][10];
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				field[i][j] = new Button(i, j);
				field[i][j].addActionListener(this);
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
			flagImage = ImageIO.read(new File("res/ww.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		flagImage = flagImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		field[0][0].setIcon(new ImageIcon(flagImage));
	}

	public static void main(String[] args) {
		frame = new SummerSweeper();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("SummerSweeper");
		frame.setBounds(0, 0, screenH / 2, screenH / 2);
		frame.setVisible(true);
		
		screen = frame.getSize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Button) {
			Button source = (Button) e.getSource();
			System.out.println(source.getWidth() + ", " + source.getHeight());
			if (source.getType() != Button.TYPE_MINE) {
				source.setText(String.valueOf(source.getType()));
				source.setBackground(Color.WHITE);
			}
			if (source.getType() == Button.TYPE_EMPTY)
				source.setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		oldScreen = frame.getSize();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screen = frame.getSize();
		if (screen != oldScreen) {
			System.out.println("Resize");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}