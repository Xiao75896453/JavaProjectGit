package userinterface;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gameobject.Clouds;
import gameobject.EnemiesManager;
import gameobject.Land;
import gameobject.MainCharacter;
import util.Resource;

public class GameScreen extends JPanel implements Runnable, KeyListener {

	private static final int START_GAME_STATE = 0;
	private static final int GAME_PLAYING_STATE = 1;
	private static final int GAME_OVER_STATE = 2;
	private static final int CHANGEROLE = 3;
	private static final int CHANGEROLE1 = 4;
	// !!!!!!!!!!!!
	private static boolean isEAT = false;
	private static boolean dnsEV = false;
	private static int charDrawType = 0;
	private static String charType = "dns";
	// !!!!!!!!!!!!
	private GameInstruction gameInstruction;
	// private Land land;
	private MainCharacter mainCharacter;
	private EnemiesManager enemiesManager;
	private Clouds clouds;
	private Thread thread;

	private boolean isKeyPressed;

	private int gameState = START_GAME_STATE;

	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;

	private float dnsRunSpeed = 4;
	// !!!!!!!!!!!!!!!!
	private String diplayRunSpeed = String.valueOf(dnsRunSpeed);
	private int maxScore = 0;
	private int eatTime = 0;
	private boolean flicker = false;
	private JPanel background;
	private BufferedImage backgroundImage;
	private String[] backgroundPath = { "data/background1.png", "data/background2.png", "data/background3.png",
			"data/background4.png" };
	private String[] dnsImg = { "data/main-character1.png", "data/main-character2.png",
			"data/main-character3.png", "data/main-character4.png",
			"data/main-character5.png", "data/main-character6.png" };
	private String[] evDnsImg = { "data/evmain-character1.png", "data/evmain-character2.png",
			"data/evmain-character3.png", "data/evmain-character4.png",
			"data/evmain-character5.png", "data/evmain-character6.png" };	
	private String[] totImg = {"data/totoro1.png", "data/totoro2.png", "data/totoro6.png",
			"data/totoro4.png", "data/totoro5.png", "data/totoro5.png" };
	private String[] evTotImg = {"data/evtotoro1.png", "data/evtotoro2.png", "data/evtotoro6.png",
			"data/totoro4.png", "data/evtotoro5.png", "data/evtotoro5.png"};
	private AudioClip BGM;
	
	// !!!!!!!!!!!!!!!!
	public GameScreen() {
		mainCharacter = new MainCharacter();
		// land = new Land(GameWindow.SCREEN_WIDTH, mainCharacter);
		mainCharacter.setSpeedX(dnsRunSpeed);
		replayButtonImage = Resource.getResouceImage("data/replay_button.png");
		gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
		enemiesManager = new EnemiesManager(mainCharacter);
		clouds = new Clouds(GameWindow.SCREEN_WIDTH, mainCharacter);
		gameInstruction = new GameInstruction();
		gameInstruction.setVisible(true);
		
		
		try {
			backgroundImage = ImageIO.read(new File(backgroundPath[0]));
			BGM = Applet.newAudioClip(new URL("file", "", "data/BGM.wav"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
		BGM.play();
	}

	public void gameUpdate() {
		if (gameState == GAME_PLAYING_STATE) {
			clouds.update();
			mainCharacter.update();
			enemiesManager.update();

			isCollision();
			
			updateMaxScore();
			
			changeBackground();
			
			if (isEAT == true) {
				evolution();
				flicker();
				calEatTime();
			}
		}
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this); // see javadoc for more info on the parameters
//		g.setColor(Color.decode("#f7f7f7"));
//		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {

		case CHANGEROLE: /// LAN
			mainCharacter.Role(totImg);
			mainCharacter.draw(g, charDrawType);
			gameState = START_GAME_STATE;
			break;
		case CHANGEROLE1: /// LAN
			mainCharacter.Role(dnsImg);
			mainCharacter.draw(g, charDrawType);
			gameState = START_GAME_STATE;
			break;
		case START_GAME_STATE:
			mainCharacter.draw(g, charDrawType);
			break;
		case GAME_PLAYING_STATE:

		case GAME_OVER_STATE:
			clouds.draw(g);
			// land.draw(g);
			enemiesManager.draw(g);
			mainCharacter.draw(g, charDrawType);
			g.setColor(Color.BLACK);
			// !!!!!!!!!!!!!!!!
			diplayRunSpeed = String.valueOf(dnsRunSpeed);
			diplayRunSpeed = diplayRunSpeed.substring(0, diplayRunSpeed.indexOf(".") + 2);
			g.drawString("SPEED " + diplayRunSpeed + " km/hr", 50, 20);
			g.drawString("HI " + maxScore, 400, 20);
			// !!!!!!!!!!!!!!!!
			g.drawString("" + mainCharacter.score, 500, 20);
			if (gameState == GAME_OVER_STATE) {
				g.drawImage(gameOverButtonImage, 200, 30, null);
				g.drawImage(replayButtonImage, 283, 50, null);
			}
			break;
		}

	}

	@Override
	public void run() {

		int fps = 100;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = 0;
		long elapsed;

		int msSleep;
		int nanoSleep;

		long endProcessGame;
		long lag = 0;

		while (true) {
			gameUpdate();
			repaint();
			endProcessGame = System.nanoTime();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int) (elapsed / 1000000);
			nanoSleep = (int) (elapsed % 1000000);

			if (msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			// !!!!!!!!
			if (gameState == GAME_PLAYING_STATE && dnsRunSpeed < 10) {
				dnsRunSpeed += 0.001;
				mainCharacter.setSpeedX(dnsRunSpeed);
			}

			// !!!!!!!!
			try {
				Thread.sleep(msSleep, nanoSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastTime = System.nanoTime();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyPressed) {
			isKeyPressed = true;
			switch (gameState) {
			case START_GAME_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = GAME_PLAYING_STATE;
				}
				if (e.getKeyCode() == KeyEvent.VK_Z) { /// LAN
					charType = "tot";
					gameState = CHANGEROLE;
				}
				if (e.getKeyCode() == KeyEvent.VK_X) { /// LAN
					charType = "dns";
					gameState = CHANGEROLE1;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) // LAI ������V�� �}�һ���
				{

					gameInstruction.setVisible(true);
				}
				break;
			case GAME_PLAYING_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					mainCharacter.jump();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					mainCharacter.down(true);
				}
				break;
			case GAME_OVER_STATE:
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameState = GAME_PLAYING_STATE;
					resetGame();
				}
				if (e.getKeyCode() == KeyEvent.VK_Z) { /// LAN
					charType = "tot";
					gameState = CHANGEROLE;
				}
				if (e.getKeyCode() == KeyEvent.VK_X) { /// LAN
					charType = "dns";
					gameState = CHANGEROLE1;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) // LAI ������V�� �}�һ���
				{
					gameInstruction.setVisible(true);
				}
				break;

			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
		if (gameState == GAME_PLAYING_STATE) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				mainCharacter.down(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void resetGame() {
		enemiesManager.reset();
		mainCharacter.dead(false);
		mainCharacter.reset();
		// !!!!!!!!
		dnsRunSpeed = 4;
		mainCharacter.score = 0;
		// !!!!!!!!
	}
	private void isCollision() {
		if (enemiesManager.isCollision() == "DEATH" && isEAT == false) {
			mainCharacter.playDeadSound();
			gameState = GAME_OVER_STATE;
			mainCharacter.dead(true);
		}
		else if (enemiesManager.isCollision() == "EAT") {
			mainCharacter.playEatFoodSound();
			isEAT = true;
			charDrawType = 1;				
		}
	}
	private void updateMaxScore() {
		if (mainCharacter.score > maxScore) {
			maxScore = mainCharacter.score;
		}
	}
	private void changeBackground() {
		if (mainCharacter.score % 100 == 80) {
			clouds.changeCloud((mainCharacter.score / 160) % 6);
			try {
				backgroundImage = ImageIO.read(new File(backgroundPath[(mainCharacter.score / 100) % 4]));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void evolution(){
		if (dnsEV == false) {
			if (charType == "dns") {
				mainCharacter.Role(evDnsImg);
			} else if (charType == "tot") {
				mainCharacter.Role(evTotImg);
			}
			dnsEV = true;
		}
	}
	private void flicker() {
		if (eatTime > 600 && eatTime < 1000) {
			if ((eatTime % 60) == 0) {
				if ((eatTime / 60) % 2 == 0) {
					if (charType == "dns") {
						mainCharacter.Role(evDnsImg);
					} else if (charType == "tot") {
						mainCharacter.Role(evTotImg);
					}
					charDrawType = 1;
				} else {
					if (charType == "dns") {
						mainCharacter.Role(dnsImg);
					} else if (charType == "tot") {
						mainCharacter.Role(totImg);
					}
					charDrawType = 0;
				}
			}
		}
	}
	private void calEatTime() {
		if (eatTime > 600 && eatTime < 1000) {
			if ((eatTime % 60) == 0) {
				if ((eatTime / 60) % 2 == 0) {
					if (charType == "dns") {
						mainCharacter.Role(evDnsImg);
					} else if (charType == "tot") {
						mainCharacter.Role(evTotImg);
					}
					charDrawType = 1;
				} else {
					if (charType == "dns") {
						mainCharacter.Role(dnsImg);
					} else if (charType == "tot") {
						mainCharacter.Role(totImg);
					}
					charDrawType = 0;
				}
			}
		}
	}

}
