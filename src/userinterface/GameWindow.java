package userinterface;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class GameWindow extends JFrame {

	public static final int SCREEN_WIDTH = 600;
	private GameScreen gameScreen;

	public GameWindow() {
		super("JavaProject");
		setSize(SCREEN_WIDTH, 175);
		setLocation(400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		gameScreen = new GameScreen();
		addKeyListener(gameScreen);
		add(gameScreen);
	}

	public void startGame() {
		setVisible(true);
		gameScreen.startGame();
	}

	public static void main(String args[]) throws Exception {
		Clip clip = createClip(new File("data/BGM.wav"));
		(new GameWindow()).startGame();
		clip.start();
		Thread.sleep(3000);
	}

	public static Clip createClip(File path) {
		try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)) {
			AudioFormat af = ais.getFormat();
			DataLine.Info dataLine = new DataLine.Info(Clip.class, af);
			Clip c = (Clip) AudioSystem.getLine(dataLine);
			c.open(ais);
			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
