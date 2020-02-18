package zeldaGame.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import zeldaGame.main.Game;
import zeldaGame.world.Camera;

public class Enemy extends Entity{

	public static BufferedImage[] ENEMY_EN;
	private int frames = 0, maxFrames = 10, animation = 0, maxAnimation = 3;
	
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		ENEMY_EN = new BufferedImage[4];
		
		ENEMY_EN[0] = Game.spriteSheet.getSprite(32, 64, 16, 16);
		ENEMY_EN[1] = Game.spriteSheet.getSprite(48, 64, 16, 16);
		ENEMY_EN[2] = Game.spriteSheet.getSprite(64, 64, 16, 16);
		ENEMY_EN[3] = Game.spriteSheet.getSprite(80, 64, 16, 16);
	}
	

	public void update() {
		frames++;
		if(frames >= maxFrames) {
			frames = 0;
			animation++;
			if(animation > maxAnimation) {
				animation = 0;
			}
		}
		
		
		
	}
	
	public void render(Graphics g) {
		g.drawImage(ENEMY_EN[animation], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}

}
