package zeldaGame.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import zeldaGame.main.Game;
import zeldaGame.world.Camera;

public class Entity {
	
	public  double x;
	public  double y;
	public  int width;
	public  int height;
	
	private BufferedImage sprite;
	public static BufferedImage LIFE_EN = Game.spriteSheet.getSprite(96, 0, 16, 16);
	public static BufferedImage WEAPON_EN = Game.spriteSheet.getSprite(112, 0, 16, 16);
	public static BufferedImage MANA_EN = Game.spriteSheet.getSprite(96, 16, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spriteSheet.getSprite(32, 64, 16, 16);
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	public int getX() {
		return (int)x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void update() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
