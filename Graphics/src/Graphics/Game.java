package Graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;

	//Janela Game
	public static JFrame frame;
			
	//Dimensoes da Janela Game
	private final int WIDTH = 240;
	private final int HEIGHT = 160;
	private final int SCALE = 3;
	
	//Thread para rodar simultaneamente
	private Thread thread;
	
	private boolean isRunning;
	
	private BufferedImage image;
	private BufferedImage[] player;
	
	private SpriteSheet sprite;
	
	private int frames = 0;
	private int maxFrames = 13;
	private int curAnimation = 0;
	private int maxAnimation = 4;
	
	public Game() {
		sprite = new SpriteSheet("/Woodcutter_idle.png");
		player = new BufferedImage[4];
		player[0] = sprite.getSprite(0, 0, 48, 48);
		player[1] = sprite.getSprite(48, 0, 48, 48);
		player[2] = sprite.getSprite(96, 0, 48, 48);
		player[3] = sprite.getSprite(144, 0, 48, 48);
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	}
	
	private void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		
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
		frames++;
		if(frames >= maxFrames) {
			frames = 0;
			curAnimation++;
			if(curAnimation >= maxAnimation) {
				curAnimation = 0;
			}
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
		g.setColor(new Color(40,40,40));
		//Desenha um retangulo
		g.fillRect(0, 0, WIDTH, HEIGHT);
		/*
		//Seta a cor azul
		g.setColor(Color.green);
		//Desenha retangulo
		g.fillRect(20, 20, 80, 80);
		
		g.setColor(Color.blue);
		//Desenha um circulo
		g.fillOval(80, 50, 20, 20);
		
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("UNDERTALE", 25, 30);*/
		
		
		/*Renderizaçao do Jogo*/
		
		//Renderiza o Player
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(player[curAnimation], 0, 0, null);
		g2.setColor(new Color(0,0,0,100));
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		
		/***/
		g.dispose();
		
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
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
}
