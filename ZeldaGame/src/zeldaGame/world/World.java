package zeldaGame.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import zeldaGame.entities.Enemy;
import zeldaGame.entities.Entity;
import zeldaGame.entities.LifePack;
import zeldaGame.entities.ManaPack;
import zeldaGame.entities.Weapon;
import zeldaGame.main.Game;

public class World {
	
	private Tile[] tiles;
	private static int WIDTH, HEIGHT;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * WIDTH)];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000) {
						//CHAO
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					} else if(pixelAtual == 0xFFF7FFF7) {
						//PAREDE
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
					} else if(pixelAtual == 0XFF0000FF) {
						//PLAYER
						Game.player.setX(xx*16);
						Game.player.setY(yy * 16);
					} else if(pixelAtual == 0xFFFF0010){
						//ENEMY
						Game.entities.add(new Enemy(xx * 16, yy * 16 , 16, 16, Entity.ENEMY_EN));
					} else if(pixelAtual == 0xFFF2FF00) {
						//WEAPON
						Game.entities.add(new Weapon(xx*16, yy*16, 16, 16, Entity.WEAPON_EN));
					} else if(pixelAtual == 0xFF00FF26) {
						//LIFE
						Game.entities.add(new LifePack(xx * 16, yy * 16, 16, 16, Entity.LIFE_EN));
					} else if(pixelAtual == 0xFF00C3FF) {
						//MANA
						Game.entities.add(new ManaPack(xx * 16, yy * 16, 16, 16, Entity.MANA_EN));
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		int xStart = Camera.x/16;
		int yStart = Camera.y/16;
		
		int xFinal = xStart + (Game.WIDTH/16);
		int yFinal = yStart + (Game.HEIGHT/16);
		
		for(int xx = xStart; xx <= xFinal; xx++) {
			for(int yy = yStart; yy < yFinal; yy++) {
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
		
	}

}
