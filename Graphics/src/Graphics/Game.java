package Graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;

	//Janela Game
	public static JFrame frame;
			
	//Dimensoes da Janela Game
	private final int WIDTH = 240;
	private final int HEIGHT = 160;
	private final int SCALE = 5;
	
	//Thread para rodar simultaneamente
	private Thread thread;
	
	private boolean isRunning;
	
	private BufferedImage image;
	private BufferedImage[] player;
	private BufferedImage[] playerMovement;
	private BufferedImage[] playerObjective;
	
	private SpriteSheet sprite;
	private SpriteSheet spriteMovement;
	private SpriteSheet spriteObjective;
	
	private int frames = 0;
	private int maxFrames = 7;
	
	private int framesSec = 0;
	private int maxFramesSec = 50;
	
	private int curAnimation = 0;
	private int maxAnimation = 4;
	
	private int objAnimation = 0;
	private int maxObjAnimation = 4;
	
	private int animationWalk = 0;
	private int maxAnimationWalk = 6;
	
	
	private int x = 0, y = 0;
	private boolean right, left, down, up, isWalking, gameOver = false;
	private int speed = 3;
	
	private Rectangle rec;
	private Rectangle playerPos;
	
	private Integer score = 0;
	private Integer timer = 5;
	
	private int randomX = 30, randomY = 30;
	
	private boolean startTimer = false;
	
	Random random = new Random();
	
	public Game() {
		rec = new Rectangle();
		playerPos = new Rectangle();
		
		sprite = new SpriteSheet("/Woodcutter_idle.png");
		spriteMovement = new SpriteSheet("/Woodcutter_walk.png");
		spriteObjective = new SpriteSheet("/GraveRobber_idle.png");
		player = new BufferedImage[4];
		playerMovement = new BufferedImage[6];
		playerObjective = new BufferedImage[4];
		player[0] = sprite.getSprite(0, 0, 48, 48);
		player[1] = sprite.getSprite(48, 0, 48, 48);
		player[2] = sprite.getSprite(96, 0, 48, 48);
		player[3] = sprite.getSprite(144, 0, 48, 48);
		
		playerObjective[0] = spriteObjective.getSprite(0, 0, 48, 48);
		playerObjective[1] = spriteObjective.getSprite(48, 0, 48, 48);
		playerObjective[2] = spriteObjective.getSprite(96, 0, 48, 48);
		playerObjective[3] = spriteObjective.getSprite(144, 0, 48, 48);
		
		
		playerMovement[0] = spriteMovement.getSprite(0, 0, 48, 48);
		playerMovement[1] = spriteMovement.getSprite(48, 0, 48, 48);
		playerMovement[2] = spriteMovement.getSprite(96, 0, 48, 48);
		playerMovement[3] = spriteMovement.getSprite(144, 0, 48, 48);
		playerMovement[4] = spriteMovement.getSprite(192, 0, 48, 48);
		playerMovement[5] = spriteMovement.getSprite(240, 0, 48, 48);
		
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
		framesSec++;
		frames++;
		if(frames >= maxFrames) {
			frames = 0;
			objAnimation++;
			if(objAnimation >= maxObjAnimation) {
				objAnimation = 0;
			}
			curAnimation++;
			if(curAnimation >= maxAnimation) {
				curAnimation = 0;
			}
		
			
			if(right) {
				x+=speed;
			}
			else if(left) {
				x-=speed;
			}
			if(up) {
				y-=speed;
			}
			else if(down) {
				y+=speed;
			}
			if(isWalking) {
				animationWalk++;
				if(animationWalk >= maxAnimationWalk) {
					animationWalk = 0;
				}
			}
			
			if(startTimer) {
				if(framesSec >= maxFramesSec) {
					framesSec = 0;
					timer--;
				}
			}
			
			if(rec.intersects(playerPos)) {
				timer = 5;
				score++;
				randomX = random.nextInt(160);
				randomY = random.nextInt(120);
			}
			System.out.println(x + "-" + y + "Random: "+ randomX +"-"+randomY);
			
			if(timer == 0) {
				timer = 5;
				startTimer = false;
				gameOver = true;
			}
			
		}
		
		
		this.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_D) {
					right = false;
					isWalking = false;
				} if(e.getKeyCode() == KeyEvent.VK_A) {
					left = false;
					isWalking = false;
				}if(e.getKeyCode() == KeyEvent.VK_S) {
					down = false;
					isWalking = false;
				}if(e.getKeyCode() == KeyEvent.VK_W) {
					up = false;
					isWalking = false;
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_D) {
					right = true;
					isWalking = true;
					startTimer = true;
				} if(e.getKeyCode() == KeyEvent.VK_A) {
					left = true;
					isWalking = true;
					startTimer = true;
				}if(e.getKeyCode() == KeyEvent.VK_S) {
					down = true;
					isWalking = true;
					startTimer = true;
				}if(e.getKeyCode() == KeyEvent.VK_W) {
					up = true;
					isWalking = true;
					startTimer = true;
				}if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					gameOver = false;
				}
			}
		});
		
	}
	
	public void render() {
		//Quantidade de buffer para otimizar a renderiza�ao
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
		*/
		
		
		/*Renderiza�ao do Jogo*/
		Graphics2D g2 = (Graphics2D) g;
		
		//Renderiza o Player
		
		
		
		if(gameOver) {
			g2.setColor(new Color(255,255,255));
			g2.setFont(new Font("Arial", Font.BOLD, 25));
			g2.drawString("Game Over", 50, 50);
			
			g2.setColor(new Color(255,255,255));
			g2.setFont(new Font("Arial", Font.BOLD, 15));
			g2.drawString("Score: " + score, WIDTH/2, 70);
			
			
			g2.setColor(new Color(255,255,255));
			g2.setFont(new Font("Arial", Font.BOLD, 13));
			g2.drawString("Press Enter to Player Again", WIDTH/7, 125);
		} else {
			rec.setBounds(randomX, randomY, 5,5);
			playerPos.setBounds(x,y,5,5);
			g2.drawImage(playerObjective[objAnimation], randomX, randomY, null);
			g2.drawImage((isWalking)? playerMovement[animationWalk] : player[curAnimation],(x > WIDTH)? x = -WIDTH : x, (y > HEIGHT)? y = -HEIGHT : y, null);
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH, HEIGHT);
			
			g2.setColor(new Color(255,255,255));
			g2.setFont(new Font("Arial", Font.BOLD, 15));
			g2.drawString(Integer.toString(score), WIDTH/2, 20);
			
			g2.setColor(new Color(255,255,255));
			g2.setFont(new Font("Arial", Font.BOLD, 10));
			g2.drawString("Timer:", 187, 18);
		
			g2.setColor(new Color(255,255,255));
			g2.setFont(new Font("Arial", Font.BOLD, 15));
			g2.drawString(Integer.toString(timer), 222, 20);
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
}
