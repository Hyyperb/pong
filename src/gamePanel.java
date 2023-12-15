import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class gamePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6350159535293799269L;
	static final int SCREEN_WIDTH = 840;
	static final int SCREEN_HEIGHT = 610;
	static final int PONG_SPAWN_FRAME = 50;
	static final int padding = 30;

	
	
	static final int pongSize = 30;
	static final int batHeight = SCREEN_HEIGHT / 4;
	static final int batWidth = SCREEN_WIDTH / 32;
	static final int batSpeed = 6;
	static final int pongSpeed = 3;
	static final int scoreTextSize = 45;
	
	boolean running = false;
	
	int redX = padding;
	int redY = SCREEN_HEIGHT / 2;
	int redVelocity = 0;

	int blueX = SCREEN_WIDTH - padding;
	int blueY = SCREEN_HEIGHT / 2;
	int blueVelocity = 0;

	int redPoints = 0;
	int bluePoints = 0;

	int pongX = SCREEN_WIDTH / 4 - pongSize / 2;
	int pongY = 0;

	int pongVelocityX = pongSpeed;
	int pongVelocityY = pongSpeed;

	final int DELAY = 6;
	Timer timer;

	Random random;

	gamePanel() {
		random = new Random();
		pongY = random.nextInt((int) (SCREEN_HEIGHT - PONG_SPAWN_FRAME) + PONG_SPAWN_FRAME);
		if (pongVelocityY == 0) {
			if (pongY > SCREEN_HEIGHT / 2 + pongSize) {
				pongVelocityY = -pongSpeed;
			} else {
				pongVelocityY = pongSpeed;
			}
		}
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.DARK_GRAY);
		this.setFocusable(true);
		this.addKeyListener(new myKeyAdapter());
		startGame();
	}

	void startGame() {
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();

	}

	void restart() {
		redX = padding;
		redY = SCREEN_HEIGHT / 2;
		redVelocity = 0;

		blueX = SCREEN_WIDTH - padding;
		blueY = SCREEN_HEIGHT / 2;
		blueVelocity = 0;

		pongX = SCREEN_WIDTH / 4 - pongSize / 2;
		pongY = 0;

		pongVelocityX = pongSpeed;
		pongVelocityY = pongSpeed;

		spawnPong();

	}

	void spawnPong() {
		pongY = random.nextInt((int) (SCREEN_HEIGHT - PONG_SPAWN_FRAME * 2)) + PONG_SPAWN_FRAME;
		if (pongVelocityY == 0) {
			if (pongY > SCREEN_HEIGHT / 2 + pongSize) {
				pongVelocityY = -pongSpeed;
			} else {
				pongVelocityY = pongSpeed;
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(pongX, pongY, pongSize, pongSize);
		g.setColor(Color.red);
		g.fillRect(redX - batWidth / 2, redY - batHeight / 2, batWidth, batHeight);
		g.setColor(Color.blue);
		g.fillRect(blueX - batWidth / 2, blueY - batHeight / 2, batWidth, batHeight);
		g.setColor(Color.white);
		g.setFont(new Font("Roman New Times", Font.PLAIN, scoreTextSize));
		g.drawString(String.valueOf(redPoints), SCREEN_WIDTH / 4, scoreTextSize);
		g.drawString(String.valueOf(bluePoints), SCREEN_WIDTH / 4 * 3, scoreTextSize);
	}

	void pongMove() {
		pongX += pongVelocityX;
		pongY += pongVelocityY;
	}

	void batMove() {
		if (redY > batHeight / 2 && redVelocity < 0) {
			redY += redVelocity * batSpeed;
		}
		if (redY < SCREEN_HEIGHT - batHeight / 2 && redVelocity > 0) {
			redY += redVelocity * batSpeed;
		}

		blueY = pongY;
		/*
		 * if (blueY>batHeight/2 && blueVelocity < 0) { blueY += blueVelocity *
		 * pongSpeed; } if (blueY<SCREEN_HEIGHT-batHeight/2 && blueVelocity > 0) { blueY
		 * += blueVelocity * pongSpeed; }
		 */
	}

	void batLerp() {
		redVelocity *= 0.5f;
		blueVelocity *= 0.5f; 
	}

	void checkCollision() {
		if ((pongX > blueX - pongSize - batWidth / 2 && pongX < blueX - pongSize + batWidth / 2)
				&& (pongY > blueY - batHeight / 2 && pongY < blueY + batHeight / 2)) {
			pongVelocityX *= -1;
		}
		if (pongX < redX + batWidth / 2 && (pongY > redY - batHeight / 2 && pongY < redY + batHeight / 2)) {
			pongVelocityX *= -1;
		}
		if (pongY < 0) {
			pongVelocityY *= -1;
		}
		if (pongY > SCREEN_HEIGHT - pongSize) {
			pongVelocityY *= -1;
		}
		if (pongX < -pongSize) {
			bluePoints += 1;
			restart();
		}
		if (pongX > SCREEN_WIDTH) {
			redPoints += 1;
			restart();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			checkCollision();
			batMove();
			batLerp();
			pongMove();
		}
		repaint();

	}

	public class myKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				redVelocity = -1;
				break;
			case KeyEvent.VK_S:
				redVelocity = 1;
				break;
			case KeyEvent.VK_UP:
				blueVelocity = -1;
				break;
			case KeyEvent.VK_DOWN:
				blueVelocity = 1;
				break;
			}
		}
	}
}
