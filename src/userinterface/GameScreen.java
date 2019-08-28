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

import gameobject.Airplanes;
import gameobject.Airplanes;
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
	private Airplanes airplanes;
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
	
	String background1FilePath = "data/background1.png"; // 背景圖1路徑
	String background2FilePath = "data/background2.png"; // 背景圖2路徑
	String background3FilePath = "data/background3.png"; // 背景圖3路徑
	String background4FilePath = "data/background4.png"; // 背景圖4路徑

	String lightCharacter1Dns = "data/evmain-character1.png"; // 發光 恐龍 1
	String lightCharacter2Dns = "data/evmain-character2.png"; // 發光 恐龍 2
	String lightCharacter3Dns = "data/evmain-character3.png"; // 發光 恐龍 3
	String lightCharacter4Dns = "data/evmain-character4.png"; // 發光 恐龍 4
	String lightCharacter5Dns = "data/evmain-character5.png"; // 發光 恐龍 5
	String lightCharacter6Dns = "data/evmain-character6.png"; // 發光 恐龍 6

	String lightCharacter1Tot = "data/evtotoro1.png"; // 發光 豆豆龍 1
	String lightCharacter2Tot = "data/evtotoro2.png"; // 發光 豆豆龍 2
	String lightCharacter6Tot = "data/evtotoro6.png"; // 發光 豆豆龍 6
	String lightCharacter4Tot = "data/totoro4.png"; // 發光 豆豆龍 4
	String lightCharacter5Tot = "data/evtotoro5.png"; // 發光 豆豆龍 5

	String Character1Dns = "data/main-character1.png"; // 恐龍 1
	String Character2Dns = "data/main-character2.png"; // 恐龍 2
	String Character3Dns = "data/main-character3.png"; // 恐龍 3
	String Character4Dns = "data/main-character4.png"; // 恐龍 4
	String Character5Dns = "data/main-character5.png"; // 恐龍 5
	String Character6Dns = "data/main-character6.png"; // 恐龍 6

	String Character1Tot = "data/totoro1.png"; // 豆豆龍 1
	String Character2Tot = "data/totoro2.png"; // 豆豆龍 2
	String Character4Tot = "data/totoro4.png"; // 豆豆龍 4
	String Character5Tot = "data/totoro5.png"; // 豆豆龍 5
	String Character6Tot = "data/totoro6.png"; // 豆豆龍 6

	private String[] backgroundPath = { background1FilePath, background2FilePath, background3FilePath,
			background4FilePath };
	private AudioClip BGM;

	// !!!!!!!!!!!!!!!!
	public GameScreen() {
		mainCharacter = new MainCharacter();
		// land = new Land(GameWindow.SCREEN_WIDTH, mainCharacter);
		mainCharacter.setSpeedX(dnsRunSpeed);
		replayButtonImage = Resource.getResouceImage("data/replay_button.png");
		gameOverButtonImage = Resource.getResouceImage("data/gameover_text.png");
		enemiesManager = new EnemiesManager(mainCharacter);
		airplanes = new Airplanes(GameWindow.SCREEN_WIDTH, mainCharacter);
		gameInstruction = new GameInstruction();

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
			airplanes.update();
			// land.update();
			mainCharacter.update();
			enemiesManager.update();
			// !!!!!!!!!!
			if (enemiesManager.isCollision() == "DEATH" && isEAT == false) {
				mainCharacter.playDeadSound();
				gameState = GAME_OVER_STATE;
				mainCharacter.dead(true);
			} else if (enemiesManager.isCollision() == "EAT") {
				mainCharacter.playEatFoodSound();
				isEAT = true;
				charDrawType = 1;
			}

			if (mainCharacter.score > maxScore) {
				maxScore = mainCharacter.score;
			}
			if (mainCharacter.score % 100 == 80) {
				airplanes.changeAirplane((mainCharacter.score / 160) % 6);
				try {
					backgroundImage = ImageIO.read(new File(backgroundPath[(mainCharacter.score / 100) % 4]));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (isEAT == true) {
				if (dnsEV == false) {
					if (charType == "dns") {
						mainCharacter.Role(lightCharacter1Dns, lightCharacter2Dns, lightCharacter3Dns,
								lightCharacter4Dns, lightCharacter5Dns, lightCharacter6Dns);
					} else if (charType == "tot") {
						mainCharacter.Role(lightCharacter1Tot, lightCharacter2Tot, lightCharacter6Tot,
								lightCharacter4Tot, lightCharacter5Tot, lightCharacter5Tot);
					}
					dnsEV = true;
				}
				if (eatTime > 600 && eatTime < 1000) {
					if ((eatTime % 60) == 0) {
						if ((eatTime / 60) % 2 == 0) {
							if (charType == "dns") {
								mainCharacter.Role(lightCharacter1Dns, lightCharacter2Dns, lightCharacter3Dns,
										lightCharacter4Dns, lightCharacter5Dns, lightCharacter6Dns);
							} else if (charType == "tot") {
								mainCharacter.Role(lightCharacter1Tot, lightCharacter2Tot, lightCharacter6Tot,
										lightCharacter4Tot, lightCharacter5Tot, lightCharacter5Tot);
							}
							charDrawType = 1;
						} else {
							if (charType == "dns") {
								mainCharacter.Role(Character1Dns, Character2Dns, Character3Dns, Character4Dns,
										Character5Dns, Character6Dns);
							} else if (charType == "tot") {
								mainCharacter.Role(Character1Tot, Character2Tot, Character6Tot, Character4Tot,
										Character5Tot, Character5Tot);
							}
							charDrawType = 0;
						}
					}
				}
				if (eatTime < 1000) {
					eatTime += 1;
				} else {
					eatTime = 0;
					if (charType == "dns") {
						mainCharacter.Role(Character1Dns, Character2Dns, Character3Dns, Character4Dns, Character5Dns,
								Character6Dns);
					} else if (charType == "tot") {
						mainCharacter.Role(Character1Tot, Character2Tot, Character6Tot, Character4Tot, Character5Tot,
								Character5Tot);
					}
					charDrawType = 0;
					isEAT = false;
					dnsEV = false;
				}
			}
			// !!!!!!!!!!
		}
	}

	public void paint(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, this); // see javadoc for more info on the parameters
//		g.setColor(Color.decode("#f7f7f7"));
//		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {

		case CHANGEROLE: /// LAN
			mainCharacter.Role("data/totoro1.png", "data/totoro2.png", "data/totoro6.png", "data/totoro4.png",
					"data/totoro5.png", "data/totoro5.png");
			mainCharacter.draw(g, charDrawType);
			gameState = START_GAME_STATE;
			break;
		case CHANGEROLE1: /// LAN
			mainCharacter.Role(Character1Dns, Character2Dns, Character3Dns, Character4Dns, Character5Dns,
					Character6Dns);
			mainCharacter.draw(g, charDrawType);
			gameState = START_GAME_STATE;
			break;
		case START_GAME_STATE:
			mainCharacter.draw(g, charDrawType);
			break;
		case GAME_PLAYING_STATE:

		case GAME_OVER_STATE:
			airplanes.draw(g);
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
				if (e.getKeyCode() == KeyEvent.VK_Z) { // LAN
					charType = "tot";
					gameState = CHANGEROLE;
				}
				if (e.getKeyCode() == KeyEvent.VK_X) { // LAN
					charType = "dns";
					gameState = CHANGEROLE1;
				}
				if (e.getKeyCode() == KeyEvent.VK_LEFT) // LAI
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
}
