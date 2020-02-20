package zeldaGame.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import zeldaGame.main.Game;
import zeldaGame.world.Camera;
import zeldaGame.world.World;

public class Player extends Entity{
	
	public boolean right, left, up, down, isMoved;
	public int right_dir = 1, left_dir = 2, up_dir = 3, down_dir = 4;
	public int dir = down_dir;
	public int speed = 1;
	
	
	private int frames = 0, maxFrames = 5, animation = 0, maxAnimation = 3;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		
		downPlayer[0] = Game.spriteSheet.getSprite(32, 0, 16, 16);
		downPlayer[1] = Game.spriteSheet.getSprite(48, 0, 16, 16);
		downPlayer[2] = Game.spriteSheet.getSprite(64, 0, 16, 16);
		downPlayer[3] = Game.spriteSheet.getSprite(80, 0, 16, 16);
		
		upPlayer[0] = Game.spriteSheet.getSprite(32, 16, 16, 16);
		upPlayer[1] = Game.spriteSheet.getSprite(48, 16, 16, 16);
		upPlayer[2] = Game.spriteSheet.getSprite(64, 16, 16, 16);
		upPlayer[3] = Game.spriteSheet.getSprite(80, 16, 16, 16);
		
		leftPlayer[0] = Game.spriteSheet.getSprite(32, 32, 16, 16);
		leftPlayer[1] = Game.spriteSheet.getSprite(48, 32, 16, 16);
		leftPlayer[2] = Game.spriteSheet.getSprite(64, 32, 16, 16);
		leftPlayer[3] = Game.spriteSheet.getSprite(80, 32, 16, 16);
		
		rightPlayer[0] = Game.spriteSheet.getSprite(32, 48, 16, 16);
		rightPlayer[1] = Game.spriteSheet.getSprite(48, 48, 16, 16);
		rightPlayer[2] = Game.spriteSheet.getSprite(64, 48, 16, 16);
		rightPlayer[3] = Game.spriteSheet.getSprite(80, 48, 16, 16);
	}
	
	public void update() {
		isMoved = false;
			
		if(right && World.isFree(this.getX() + speed, this.getY())) {
			isMoved = true;
			dir = right_dir;
			x += speed;
		}else if(left && World.isFree(this.getX() - speed, this.getY())) {
			isMoved = true;
			dir = left_dir;
			x -= speed;
		}
		
		if(up && World.isFree(this.getX(), this.getY() - speed)) {
			isMoved = true;
			dir = up_dir;
			y -= speed;
		}else if(down && World.isFree(this.getX(), this.getY() + speed)) {
			isMoved = true;
			dir = down_dir;
			y += speed;
		}
		
		if(isMoved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				animation++;
				if(animation > maxAnimation) {
					animation = 0;
				}
			}
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
		
	}
	

	public void render(Graphics g) {
		if(dir == down_dir) {
			g.drawImage(downPlayer[animation], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} 
		else if(dir == up_dir) {
			g.drawImage(upPlayer[animation], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} 
		else if(dir == right_dir) {
			g.drawImage(rightPlayer[animation], this.getX() - Camera.x, this.getY() - Camera.y, null);
		} 
		else if(dir == left_dir) {
			g.drawImage(leftPlayer[animation], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

	}

}
