package gameobject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import util.Animation;

public class Pterodactyl extends Enemy {
	
	public static final int Y_LAND = 100;
	
	private int posX;
	private int width;
	private int height;
	
	private Animation image;
//	private MainCharacter mainCharacter;
	
	private Rectangle rectBound;
	
	public Pterodactyl( int posX, int width, int height, Animation pterodactyl) {
		this.posX = posX;
		this.width = width;
		this.height = height;
		this.image = pterodactyl;
//		this.mainCharacter = mainCharacter;
		rectBound = new Rectangle();
	}
	
	public void update() {
//		posX -= mainCharacter.getSpeedX();
		posX -= MainCharacter.getSpeedX();
	}
	
	public void draw(Graphics g) {
		g.drawImage(image.getFrame(), posX, Y_LAND - image.getFrame().getHeight(), null);
//		g.setColor(Color.red);
//		Rectangle bound = getBound();
//		g.drawRect(bound.x, bound.y, bound.width, bound.height);
	}
	
	public Rectangle getBound() {
		rectBound = new Rectangle();
		rectBound.x = (int) posX + (image.getFrame().getWidth() - width)/2;
		rectBound.y = Y_LAND - image.getFrame().getHeight() + (image.getFrame().getHeight() - height)/2;
		rectBound.width = width;
		rectBound.height = height;
		return rectBound;
	}

	@Override
	public boolean isOutOfScreen() {
		if(posX < -image.getFrame().getWidth()) {
			return true;
		}
		return false;
	}
}
