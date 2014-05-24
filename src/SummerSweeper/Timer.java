package SummerSweeper;

import javax.swing.JTextPane;

public class Timer extends JTextPane implements Runnable {
	private static final long serialVersionUID = 1L;

	private static long startTime = 0;

	public Timer() {
		super();
	}

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		while (SummerSweeper.getGameState() == SummerSweeper.PLAYING) {
			this.setText("Time: " + (System.currentTimeMillis() - startTime) / 1000 + "s");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}