package gameobject;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import util.Animation;
import util.Resource;

public class MainCharacter {

	public static final int LAND_POSY = 80;
	public static final float GRAVITY = 0.4f;

	private static final int NORMAL_RUN = 0;
	private static final int JUMPING = 1;
	private static final int DOWN_RUN = 2;
	private static final int DEATH = 3;
	private static final int EAT = 4;

	private float posY;
	private float posX;
	private float speedX;
	private float speedY;
	private Rectangle rectBound;

	public int score = 0;

	private int state = NORMAL_RUN;

	private Animation normalRunAnim;
	private BufferedImage jumping;
	private Animation downRunAnim;
	private BufferedImage deathImage;

	private AudioClip jumpSound;
	private AudioClip deadSound;
	private AudioClip scoreUpSound;
	private AudioClip eatFoodSound;

	public MainCharacter() {
		posX = 50;
		posY = LAND_POSY;
		rectBound = new Rectangle();
		normalRunAnim = new Animation(90);
		normalRunAnim.addFrame(Resource.getResouceImage("data/main-character1.png"));
		normalRunAnim.addFrame(Resource.getResouceImage("data/main-character2.png"));
		jumping = Resource.getResouceImage("data/main-character3.png");
		downRunAnim = new Animation(90);
		downRunAnim.addFrame(Resource.getResouceImage("data/main-character5.png"));
		downRunAnim.addFrame(Resource.getResouceImage("data/main-character6.png"));
		deathImage = Resource.getResouceImage("data/main-character4.png");

		try {
			jumpSound = Applet.newAudioClip(new URL("file", "", "data/jump.wav"));
			deadSound = Applet.newAudioClip(new URL("file", "", "data/dead.wav"));
			scoreUpSound = Applet.newAudioClip(new URL("file", "", "data/scoreup.wav"));
			eatFoodSound = Applet.newAudioClip(new URL("file", "", "data/eatFood.wav"));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float dnsRunSpeed) {
		this.speedX = dnsRunSpeed;
	}

	public void draw(Graphics g, int drawType) {
		if (drawType == 0) {
			switch (state) {
			case NORMAL_RUN:
				g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
				break;
			case JUMPING:
				g.drawImage(jumping, (int) posX, (int) posY, null);
				break;
			case DOWN_RUN:
				g.drawImage(downRunAnim.getFrame(), (int) posX, (int) (posY + 20), null);
				break;
			case DEATH:
				g.drawImage(deathImage, (int) posX, (int) posY, null);
				break;
			}
		} else {
			switch (state) {
			case NORMAL_RUN:
				g.drawImage(normalRunAnim.getFrame(), (int) posX - 24, (int) posY - 24, null);
				break;
			case JUMPING:
				g.drawImage(jumping, (int) posX - 24, (int) posY - 24, null);
				break;
			case DOWN_RUN:
				g.drawImage(downRunAnim.getFrame(), (int) posX - 24, (int) (posY + 20 - 24), null);
				break;
			case DEATH:
				g.drawImage(deathImage, (int) posX, (int) posY, null);
				break;
			}
//			Rectangle bound = getBound();
//			g.setColor(Color.RED);
//			g.drawRect(bound.x, bound.y, bound.width, bound.height);
		}
//		Rectangle bound = getBound();
//		g.setColor(Color.RED);
//		g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}

	public void drawEV(Graphics g) {

	}

	public void update() {
		normalRunAnim.updateFrame();
		downRunAnim.updateFrame();
		if (posY >= LAND_POSY) {
			posY = LAND_POSY;
			if (state != DOWN_RUN) {
				state = NORMAL_RUN;
			}
		} else {
			speedY += GRAVITY;
			posY += speedY;
		}
	}

	public void jump() {
		if (posY >= LAND_POSY) {
			if (jumpSound != null) {
				jumpSound.play();
			}
			speedY = -7.5f;
			posY += speedY;
			state = JUMPING;
		}
	}

	public void down(boolean isDown) {
		if (state == JUMPING) {
			return;
		}
		if (isDown) {
			state = DOWN_RUN;
		} else {
			state = NORMAL_RUN;
		}
	}

	public Rectangle getBound() {
		rectBound = new Rectangle();
		if (state == DOWN_RUN) {
			rectBound.x = (int) posX + 5;
			rectBound.y = (int) posY + 20;
			rectBound.width = downRunAnim.getFrame().getWidth() - 10;
			rectBound.height = downRunAnim.getFrame().getHeight();
		} else {
			rectBound.x = (int) posX + 5;
			rectBound.y = (int) posY;
			rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
			rectBound.height = normalRunAnim.getFrame().getHeight();
		}
		return rectBound;
	}

	public void dead(boolean isDeath) {
		if (isDeath) {
			state = DEATH;
		} else {
			state = NORMAL_RUN;
		}
	}

	public void reset() {
		posY = LAND_POSY;
	}

	public void playDeadSound() {
		deadSound.play();
	}
	public void playEatFoodSound() {
		eatFoodSound.play();
	}

	public void upScore() {
		score += 20;
		if (score % 100 == 0) {
			scoreUpSound.play();
		}
	}

	public void Role(String img1, String img2, String img3, String img4, String img5, String img6) {
		normalRunAnim = new Animation(90);
		normalRunAnim.addFrame(Resource.getResouceImage(img1));
		normalRunAnim.addFrame(Resource.getResouceImage(img2));
		jumping = Resource.getResouceImage(img3);
		downRunAnim = new Animation(90);
		downRunAnim.addFrame(Resource.getResouceImage(img6));
		downRunAnim.addFrame(Resource.getResouceImage(img5));
		deathImage = Resource.getResouceImage(img4);
	}

	

}
