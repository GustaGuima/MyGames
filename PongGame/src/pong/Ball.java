package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {

	public double x, y;
	public int width, height;
	
	public double dx, dy;
	public double speed = 3.5;
	
	public Ball(double x, double y) {
		this.x = x;
		this.y = y;
		this.width = 3;
		this.height = 3;
		
		
		int angle = new Random().nextInt(120-45) + 45;
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
	public void update(){
		if(x + (dx * speed) + width >= Game.WIDHT) {
			dx *= -1;
		}
		else if(x + (dx * speed) + width < 0) {
			dx *= -1;
		}
		
		if(y >= Game.HEIGHT) {
			//PONTO DO JOGADOR
			Game.scorePlayer++;
			x = 100;
			y = (Game.HEIGHT/2) - 1;
			int angle = new Random().nextInt(120-45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
		}
		else if(y < 0) {
			//PONTO INIMIGO OU PLAYER 2
			if(Game.isMultiplayer) {
				Game.scorePlayer2++;
				x = 100;
				y = (Game.HEIGHT/2) - 1;
				int angle = new Random().nextInt(120-45) + 45;
				dx = Math.cos(Math.toRadians(angle));
				dy = Math.sin(Math.toRadians(angle));
			}else {
				Game.scoreEnemy++;
				x = 100;
				y = (Game.HEIGHT/2) - 1;
				int angle = new Random().nextInt(120-45) + 45;
				dx = Math.cos(Math.toRadians(angle));
				dy = Math.sin(Math.toRadians(angle));
			}
		}
		
		Rectangle boundsBall = new Rectangle((int)(x + (dx * speed)), (int)(y + (dy * speed)), width, height);
		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		Rectangle boundsPlayer2 = new Rectangle(Game.player2.x, Game.player2.y, Game.player2.width, Game.player2.height);
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width, Game.enemy.height);
		
		if(boundsBall.intersects(boundsPlayer)) {
			int angle = new Random().nextInt(120-45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy < 0) {
				dy *= -1;
			}
		}else if(boundsBall.intersects(boundsPlayer2)) {
			int angle = new Random().nextInt(120-45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy > 0) {
				dy *= -1;
			}
		}else if(boundsBall.intersects(boundsEnemy)) {
			int angle = new Random().nextInt(120-45) + 45;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if(dy > 0) {
				dy *= -1;
			}
		}
		
		x += dx * speed;
		y += dy * speed;
	}
	
	public void render(Graphics g) {
		g.setColor(new Color(255,255,255));
		g.fillRect((int)x, (int)y, width, height);
	}
	
}
