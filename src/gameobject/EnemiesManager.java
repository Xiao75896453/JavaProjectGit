package gameobject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.Animation;
import util.Resource;

public class EnemiesManager {

	private BufferedImage cactus1;
	private BufferedImage cactus2;
	private Animation pterodactyl;
	// !!!!!!!!!!!!!!!!!!!
	private BufferedImage food;
	// !!!!!!!!!!!!!!!!!!!
	private Random rand;

	private List<Enemy> enemies;
	private MainCharacter mainCharacter;

	public EnemiesManager(MainCharacter mainCharacter) {
		rand = new Random();
		cactus1 = Resource.getResouceImage("data/cactus1.png");
		cactus2 = Resource.getResouceImage("data/cactus2.png");
		pterodactyl = new Animation(90);
		pterodactyl.addFrame(Resource.getResouceImage("data/pterodactyl1.png"));
		pterodactyl.addFrame(Resource.getResouceImage("data/pterodactyl2.png"));
		// !!!!!!!!!!!!!!!!!!!
		food = Resource.getResouceImage("data/food.png");
		// !!!!!!!!!!!!!!!!!!!
		enemies = new ArrayList<Enemy>();
		this.mainCharacter = mainCharacter;
		enemies.add(createEnemy());
	}

	public void update() {
		pterodactyl.updateFrame();
		for (Enemy e : enemies) {
			e.update();
		}
		Enemy enemy = enemies.get(0);
		if (enemy.isOutOfScreen()) {
			mainCharacter.upScore();
			enemies.clear();
			enemies.add(createEnemy());
		}
	}

	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			e.draw(g);
		}
	}

	// !!!!!!!!!!!!!!!!
	private Enemy createEnemy() {
		// if (enemyType = getRandom)
		int type = rand.nextInt(4);
		if (type == 0) {
			return new Cactus(mainCharacter, 800, cactus1.getWidth() - 10, cactus1.getHeight() - 10, cactus1);
		} else if (type == 1) {
			return new Cactus(mainCharacter, 800, cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2);
		} else if (type == 2) {
			return new Pterodactyl(mainCharacter, 800, pterodactyl.getFrame().getWidth() - 10,
					pterodactyl.getFrame().getHeight() - 10, pterodactyl);
		} else {
			return new Food(mainCharacter, 800, food.getWidth() - 10, food.getHeight() - 10, food);
		}
//		 else {
//			 return new Pterodactyl(mainCharacter, 800, pterodactyl.getFrame().getWidth() - 10,pterodactyl.getFrame().getHeight() - 10, pterodactyl);
//			}

	}

	// !!!!!!!!!!!!!!!!
	public String isCollision() {
		for (Enemy e : enemies) {
			if (mainCharacter.getBound().intersects(e.getBound())) {
				if (e.getClass().equals(Food.class)) {
					return "EAT";
				} else {
					return "DEATH";
				}
			}
		}
		return "RUN";
	}

	public void reset() {
		enemies.clear();
		enemies.add(createEnemy());
	}

}
