package zeldaGame.main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import zeldaGame.entities.Entity;
import zeldaGame.entities.Player;
import zeldaGame.graphics.SpriteSheet;
import zeldaGame.world.World;

public class Game extends Canvas implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;

	//Janela Game
	public static JFrame frame;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	private final int SCALE = 4;
	//Dimensoes da Janela Game

	
	//Thread para rodar simultaneamente
	private Thread thread;
	/***/
	//Booleans
	private boolean isRunning;
	/***/
	//Buffereds
	private BufferedImage image;
	/***/
	
	//LISTA DE ENTIDADES
	public static Player player;
	public static List<Entity> entities;
	/***/
	
	//Sprites
	public static SpriteSheet spriteSheet;
	/***/
	
	//WORLD
	public static World world;
	/***/

	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public Game() {		
		addKeyListener(this);
		initFrame();
		entities = new ArrayList<Entity>();
		spriteSheet = new SpriteSheet("/Spritesheet.png");
		player = new Player(0, 0, 16, 16, spriteSheet.getSprite(32, 0, 16, 16));
		world = new World("/Map.png");
		entities.add(player);
	}
	
	private void initFrame() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		for(int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.update();
		}
	}
		
	
	public void render() {
		//Quantidade de buffer para otimizar a renderizaçao
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		/*Renderizaçao do Jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			entity.render(g);
		}
		
		
		g.dispose();
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
		
		
		/***/
		
	}

	@Override
	public void run() {
		//Ultimo tempo em nano segundos pela precisao
		long lastTime = System.nanoTime();
		// 60 frames por segundos(Rodar em 60 fps)
		double amountOfTick = 60.0;
		//1000000000 = 1 seg em nano segundos / pelos frames por segundo, sera o calculo pra ver
		//o momento correto de dar o update e renderizar o game
		double ns = 1000000000 / amountOfTick;
		double delta = 0;
		
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			//Tempo atual que foi rodado o game
			long now = System.nanoTime();
			//Tempo atual - tempo anterior / ns
			delta += (now - lastTime) / ns;
			//Tempo anterior passa ser o atual nesse Loop
			lastTime = now;
			// Se calculo der 1 seg ou mais atualiza o game e renderiza e aumenta um frame
			// e reseta o delta
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer += 1000;
			}
		}
		
		stop();
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			player.down = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A){
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S){
			player.down = false;
		}
		
	}
}
