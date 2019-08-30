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

//	private MainCharacter mainCharacter;

	public Clouds(int width) {
//		this.mainCharacter = mainCharacter;
		cloud = Resource.getResouceImage("data/fly1.png");
		listCloud = new ArrayList<ImageCloud>();

		ImageCloud imageCloud = new ImageCloud();
		makeCloud(imageCloud,0, 30);
		makeCloud(imageCloud,200, 40);
		makeCloud(imageCloud,400, 50);
		makeCloud(imageCloud,600, 60);
//		imageCloud = new ImageCloud();
	}

	public void update() {
		Iterator<ImageCloud> itr = listCloud.iterator();
		ImageCloud firstElement = itr.next();
//		firstElement.posX -= mainCharacter.getSpeedX() / 8;
		firstElement.posX -= MainCharacter.getSpeedX() / 8;
		while (itr.hasNext()) {
			ImageCloud element = itr.next();
//			element.posX -= mainCharacter.getSpeedX() / 8;
			element.posX -= MainCharacter.getSpeedX() / 8;
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
	
	public void makeCloud(ImageCloud imageCloud,int PosX, int PosY ) {
		imageCloud = new ImageCloud();
		imageCloud.posX = PosX;
		imageCloud.posY = PosY;
		listCloud.add(imageCloud);

	}
	}

