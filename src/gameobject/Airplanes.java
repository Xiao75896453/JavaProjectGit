package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import userinterface.GameWindow;
import util.Resource;

public class Airplanes {
	private List<ImageAirplane> listAirplane;
	private BufferedImage airplane;
	private MainCharacter mainCharacter;
	
	String airplane1 = "data/fly1.png";  // 飛機 1
	String airplane2 = "data/fly2.png";  // 飛機 2
	String airplane3 = "data/fly3.png";  // 飛機 3
	String airplane4 = "data/fly4.png";  // 飛機 4
	String airplane5 = "data/fly5.png";  // 飛機 5
	String airplane6 = "data/fly6.png";  // 飛機 6

	public Airplanes(int width, MainCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		airplane = Resource.getResouceImage(airplane1);
		listAirplane = new ArrayList<ImageAirplane>();

		ImageAirplane imageAirplane = new ImageAirplane();
		imageAirplane.posX = 0;
		imageAirplane.posY = 30;
		listAirplane.add(imageAirplane);

		imageAirplane = new ImageAirplane();
		imageAirplane.posX = 200;
		imageAirplane.posY = 40;
		listAirplane.add(imageAirplane);

		imageAirplane = new ImageAirplane();
		imageAirplane.posX = 400;
		imageAirplane.posY = 50;
		listAirplane.add(imageAirplane);

		imageAirplane = new ImageAirplane();
		imageAirplane.posX = 600;
		imageAirplane.posY = 60;
		listAirplane.add(imageAirplane);
	}

	public void update() {
		Iterator<ImageAirplane> itr = listAirplane.iterator();
		ImageAirplane firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX() / 8;
		while (itr.hasNext()) {
			ImageAirplane element = itr.next();
			element.posX -= mainCharacter.getSpeedX() / 8;
		}
		if (firstElement.posX < -airplane.getWidth()) {
			listAirplane.remove(firstElement);
			firstElement.posX = GameWindow.SCREEN_WIDTH;
			listAirplane.add(firstElement);
		}
	}

	public void draw(Graphics g) {
		for (ImageAirplane imgLand : listAirplane) {
			g.drawImage(airplane, (int) imgLand.posX, imgLand.posY, null);
		}
	}

	private class ImageAirplane {
		float posX;
		int posY;
	}

	public void changeAirplane(int type) {
		if (type == 1) {
			airplane = Resource.getResouceImage(airplane1);
		} else if (type == 2) {
			airplane = Resource.getResouceImage(airplane2);
		} else if (type == 3) {
			airplane = Resource.getResouceImage(airplane3);
		} else if (type == 4) {
			airplane = Resource.getResouceImage(airplane4);
		} else if (type == 5) {
			airplane = Resource.getResouceImage(airplane5);
		}  else {
			airplane = Resource.getResouceImage(airplane6);
		}
	}
}
