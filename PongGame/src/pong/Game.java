package pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class Game extends Canvas implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	
	//TELA
	private JFrame telaGame;
	private BufferedImage layer = new BufferedImage(WIDHT, HEIGHT, BufferedImage.TYPE_INT_RGB);
	/***/
	
	//TAMANHO DA TELA
	public static final int WIDHT = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 4;
	/***/
	
	//PLAYER AND BALL
	public static Player player;
	public static Player player2;
	public static Enemy enemy;
	public static Ball ball;
	/***/
	
	//THREAD
	private Thread thread;
	/***/
	
	//ENQUANTO JOGO RODA
	private boolean isRunning;
	/***/
	
	//ESTILO DE GAME
	public static boolean isMultiplayer = false, inMenu = true, play;
	/***/
	
	
	//SCORE
	public static int scorePlayer, scorePlayer2, scoreEnemy;
	/***/
	public Game() {
		initComponents();
		start();
	}
	
	public void initComponents() {
		//COMPONENTES DA TELA
		this.setPreferredSize(new Dimension(WIDHT*SCALE, HEIGHT*SCALE));
		this.addKeyListener(this);
		telaGame = new JFrame("Pong");
		telaGame.setResizable(false);
		telaGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		telaGame.add(this);
		telaGame.pack(); 
		telaGame.setLocationRelativeTo(null);
		telaGame.setVisible(true);
		/***/
		//COMPONENTES PLAYER
		player = new Player(100,2);
		player2 = new Player(100, HEIGHT-10);
		enemy = new Enemy(100, HEIGHT-10);
		ball = new Ball(100, HEIGHT/2 - 1);
		/***/
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
		player.update();
		if(isMultiplayer) {
			player2.update();
		}else {
			enemy.update();
		}
		if(play) {
			ball.update();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = layer.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDHT, HEIGHT);
		
		//RENDER PLAYER AND ENEMY
		if(inMenu) {
			g.setColor(new Color(255,255,255));
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString("PONG", 88, 70);
			
			g.setColor(new Color(255,255,255));
			g.setFont(new Font("Arial", Font.BOLD, 10));
			g.drawString("1 - SinglePlayer   2 - Multiplayer", 40, 100);
		}else {
			g.setColor(new Color(255,255,255));
			g.setFont(new Font("Arial", Font.BOLD, 9));
			g.drawString(Integer.toString(scorePlayer), 5, 10);
			player.render(g);
			if(isMultiplayer) {
				g.setColor(new Color(255,255,255));
				g.setFont(new Font("Arial", Font.BOLD, 9));
				g.drawString(Integer.toString(scorePlayer2), 5, HEIGHT - 10);
				player2.render(g);
			}else {
				g.setColor(new Color(255,255,255));
				g.setFont(new Font("Arial", Font.BOLD, 9));
				g.drawString(Integer.toString(scoreEnemy), 5, HEIGHT - 10);
				enemy.render(g);
			}
			ball.render(g);
		}
		/***/
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDHT * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}
	
	@Override
	public void run() {
		//SISTEMA DE TICK
		long lastTime = System.nanoTime();
		double amountOfTick = 60.0;
		double ns = 1000000000 / amountOfTick;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		/***/
		
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				update();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
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
		if(e.getKeyCode()	 == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		if(inMenu) {
			if(e.getKeyCode() == KeyEvent.VK_1) {
				isMultiplayer = false;
				inMenu = false;
			}
			else if(e.getKeyCode() == KeyEvent.VK_2) {
				isMultiplayer = true;
				inMenu = false;
			}
		}else {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				play = true;
			}
		}
		if(isMultiplayer) {
			if(e.getKeyCode()	 == KeyEvent.VK_D) {
				player2.right = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				player2.left = true;
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()	 == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}	
		
		if(isMultiplayer) {
			if(e.getKeyCode() == KeyEvent.VK_D) {
				player2.right = false;
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				player2.left = false;
			}
		}
		
	}

}
