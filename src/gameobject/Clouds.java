package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import userinterface.GameWindow;
import util.Resource;

public class Clouds {
	private List<ImageCloud> listCloud;
	private BufferedImage cloud;

	private MainCharacter mainCharacter;

	public Clouds(int width, MainCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		cloud = Resource.getResouceImage("data/fly1.png");
		listCloud = new ArrayList<ImageCloud>();

		ImageCloud imageCloud = new ImageCloud();
		imageCloud.posX = 0;
		imageCloud.posY = 30;
		listCloud.add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.posX = 200;
		imageCloud.posY = 40;
		listCloud.add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.posX = 400;
		imageCloud.posY = 50;
		listCloud.add(imageCloud);

//		imageCloud = new ImageCloud();
//		imageCloud.posX = 450;
//		imageCloud.posY = 20;
//		listCloud.add(imageCloud);

		imageCloud = new ImageCloud();
		imageCloud.posX = 600;
		imageCloud.posY = 60;
		listCloud.add(imageCloud);
	}

	public void update() {
		Iterator<ImageCloud> itr = listCloud.iterator();
		ImageCloud firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX() / 8;
		while (itr.hasNext()) {
			ImageCloud element = itr.next();
			element.posX -= mainCharacter.getSpeedX() / 8;
		}
		if (firstElement.posX < -cloud.getWidth()) {
			listCloud.remove(firstElement);
			firstElement.posX = GameWindow.SCREEN_WIDTH;
			listCloud.add(firstElement);
		}
	}

	public void draw(Graphics g) {
		for (ImageCloud imgLand : listCloud) {
			g.drawImage(cloud, (int) imgLand.posX, imgLand.posY, null);
		}
	}

	private class ImageCloud {
		float posX;
		int posY;
	}

	public void changeCloud(int type) {
		if (type == 1) {
			cloud = Resource.getResouceImage("data/fly1.png");
		} else if (type == 2) {
			cloud = Resource.getResouceImage("data/fly2.png");
		} else if (type == 3) {
			cloud = Resource.getResouceImage("data/fly3.png");
		} else if (type == 4) {
			cloud = Resource.getResouceImage("data/fly4.png");
		} else if (type == 5) {
			cloud = Resource.getResouceImage("data/fly5.png");
		}  else {
			cloud = Resource.getResouceImage("data/fly6.png");
		}
	}
}
