package pong;

import java.awt.Color;
import java.awt.Graphics;

public class Player {
	
	//CONTROLADORES
	public boolean right, left;
	/***/
	
	//POSICAO DO PLAYER
	public int x, y, width, height;
	public int speed = 3;
	/***/
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height = 6;
	}
	
	public void update() {
		if(right) {
			x += speed;
		}
		else if(left) {
			x -= speed;
		}
		
		if(x + width > Game.WIDHT) {
			x = Game.WIDHT - width;
		}
		else if(x < 0) {
			x = 0;
		}
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}

}
