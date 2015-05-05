package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

class Display extends Canvas implements Runnable, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//public int screenWidth = 2000, screenHeight = 2000;
	public int screenWidth, screenHeight;
	
	private boolean running;
	
	private Thread thread;
	private Graphics2D g;
	private BufferStrategy bs;
	
	/**
	 * The area that calls all of the others classes into this main class.
	 * Controls the entire game
	 */
	private World world;
	private Character character;
	private Enemy enemy;
	private LevelReader level;
	private Camera camera;
	//private Character character;
	public ScreenSize s = new ScreenSize();
	
	KeyEvent e;
	
	public Display() {
		super();
		setFocusable(true);
		this.requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		
		// calls the keyboard input from the Controls class
		//addFocusListener(this);
		camera = new Camera();
		addKeyListener(this);
		//this.addKeyListener(c);
	}
	
	
	
	// initializes everything
	public void init() {
		world = new World();
		
		screenWidth = (int) (s.getScreenWidth());
		screenHeight = (int) (s.getScreenHeight());
		/*level = new LevelReader(1);
		world = new World(level);
		enemy = new Enemy(level);
		character = new Character(level, enemy);
		//camera = new Camera();
		 * 
		 */
	}
	
	// function to run the game
	public void run() {
		running = true;
		
		init();
		world.init();
		
		/*
		double fps = 60;
		double startTime = System.nanoTime();
		double accumulator = 0.0; // accumulates the left over time
		double delta = 0; // the change in time
		//double time = 1000000000 / fps;
		double time = 1000000000 / fps;
		
		while(running) {
			double currentTime = System.nanoTime();
			delta += (currentTime - startTime) / time;
			startTime = currentTime;
			
			//accumulator += delta;
			
			//while(accumulator >= time) {
			//	accumulator -= time;
				
			while(delta >= 1) {
				world.update();
				
				camera.update(world.getCharacter(), screenWidth, screenHeight);
				//camera.update(character, screenWidth);
				delta--;
			}
			
			//character.interpolate(delta/time);
			//System.out.println(delta);
			render();
		}*/
		
		double t = 0.0;
		double dt = 1.0 / 60.0;
		double currentTime = System.currentTimeMillis() * 0.001;
		double accumulator = 0.0;
		double newTime;
		double frameTime;
		
		while(running)
		{
			newTime = System.currentTimeMillis() * 0.001;
			frameTime = newTime - currentTime;
			
			if(frameTime > (dt * 25))
				frameTime = dt * 25;
			
			currentTime = newTime;
			
			accumulator += frameTime;
			
			//world.getCharacter().attackSystem(frameTime);
			
			while(accumulator >= dt)
			{	
				world.update();
				camera.update(world.getCharacter(), screenWidth, screenHeight);
				t += dt;
				accumulator -= dt;
			}
			
			double interpolation = accumulator / dt;
			
			//System.out.println("Interpolation " + interpolation);
			camera.interpolate(interpolation);
			world.getCharacter().interpolate(interpolation);
			
			render();
		}
	}
	
	// renders the game
	public void render() {
		bs = getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		g = (Graphics2D) bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//g.setBackground(Color.black);
		g.setColor(Color.black);
		//g.fillRect(0, 0, screenWidth, screenHeight);
		
		/**
		 * Setting the entire background bigger than the width and height of the level itself.
		 * This is to prevent any visual glitches that occurs when something is not rendered properly
		 */
		g.fillRect(0, 0, world.getLevel().getMapWidth() * 150, world.getLevel().getMapHeight() * 150);
		
		
		
		// draws the world to the screen
		g2d.translate(camera.getCameraX(), camera.getCameraY());
		world.render(g);
		g2d.translate(-camera.getCameraX(), -camera.getCameraY());
		
		g.setColor(Color.white);
		g.setFont(new Font("Century Gothic", Font.PLAIN, 20));
		g.drawString("Lives: " + world.getCharacter().getLives(), 20, 20);
		
		
		g.dispose();
		bs.show();
	}
	
	///////////////////////////////////////////////////
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		switch(keyCode) {
		case KeyEvent.VK_D :
			//character.moveRight(true);
			world.getCharacter().moveRight(true);
			break;
		case KeyEvent.VK_A :
			//character.moveLeft(true);
			world.getCharacter().moveLeft(true);
			break;
		case KeyEvent.VK_W :
			//character.jump(true);
			world.getCharacter().jump(true);
			break;
		case KeyEvent.VK_SPACE :
			world.getCharacter().attack(true);
			break;
		case KeyEvent.VK_SHIFT :
			world.getCharacter().run(true);
			break;
		case KeyEvent.VK_ESCAPE :
			System.exit(0);
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		switch(keyCode) {
		case KeyEvent.VK_D :
			//character.moveRight(false);
			world.getCharacter().moveRight(false);
			break;
		case KeyEvent.VK_A :
			world.getCharacter().moveLeft(false);
			//character.moveLeft(false);
			break;
		case KeyEvent.VK_W :
			//character.jump(false);
			world.getCharacter().jump(false);
			break;

		case KeyEvent.VK_SHIFT :
			world.getCharacter().run(false);
			break;
		case KeyEvent.VK_SPACE :
			world.getCharacter().attack(false);
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	////////////////////////////////////////////////////
}

public class Game {
	
	// the main class to start up the entire application
	public static void main(String[] args) {
		ScreenSize screen = new ScreenSize();
		
		JFrame f = new JFrame("Reaped Souls");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setBounds(100, 100, 1500, 1000);
		/**
		 * this is set to resize automatically on any screen monitor especially because one screen could have
		 * a different monitor resolution than another
		 */
		f.setSize((int) (screen.getScreenWidth() / 1.45), (int) (screen.getScreenHeight() / 1.45));
		//f.setBackground(Color.black);
		f.getContentPane().add(new Display());
		f.setVisible(true);
		f.setResizable(false);
		f.pack();
		
		// will keep this here just in case until testing has been done on other monitors
		//Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		//f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height / 2 - f.getSize().height / 2);
		
		// This is set to start the game application in the center of the monitor
		f.setLocation((int) (screen.getScreenWidth() / 2 - f.getSize().width / 2),
				(int) (screen.getScreenHeight() / 2 - f.getSize().height / 2));
		
	}
}
