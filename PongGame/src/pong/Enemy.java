package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	
	public double x, y;
	public int width, height;
	
	public Enemy(double x, double y) {
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 6;
	}
	
	public void update(){
		x += (Game.ball.x - x - 7) * 0.07;
		
		if(x + width > Game.WIDHT) {
			x = Game.WIDHT - width;
		}
		else if(x < 0) {
			x = 0;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(150,0,0));
		g.fillRect((int)x, (int)y, width, height);
	}

}
