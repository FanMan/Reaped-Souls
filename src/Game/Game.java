package Game;

import java.awt.Canvas;
import java.awt.Color;
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

	public int screenWidth = 2000, screenHeight = 2000;
	
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
		
		level = new LevelReader(1);
		world = new World(level);
		enemy = new Enemy(level);
		character = new Character(level, enemy);
		//camera = new Camera();
	}
	
	// function to run the game
	public void run() {
		running = true;
		
		init();
		
		double fps = 60;
		double startTime = System.nanoTime();
		double accumulator = 0.0; // accumulates the left over time
		double delta; // the change in time
		double time = 1000000000 / fps;
		
		while(running) {
			double currentTime = System.nanoTime();
			delta = currentTime - startTime;
			startTime = currentTime;
			
			accumulator += delta;
			
			while(accumulator >= time) {
				accumulator -= time;
				//world.update();
				character.update();
				enemy.update();
				camera.update(character, screenWidth);
			}
			
			//character.interpolate(accumulator/time);
			//System.out.println(delta);
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
		g.fillRect(0, 0, screenWidth, screenHeight);
		
		// draws the world to the screen
		g2d.translate(camera.getCameraX(), camera.getCameraY());
		world.render(g);
		enemy.render(g);
		character.render(g);
		g2d.translate(-camera.getCameraX(), -camera.getCameraY());
		
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
			character.moveRight(true);
			break;
		case KeyEvent.VK_A :
			character.moveLeft(true);
			break;
		case KeyEvent.VK_W :
			character.jump(true);
			break;
		case KeyEvent.VK_S :
			character.dodge(true);
			break;
		case KeyEvent.VK_ESCAPE :
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		
		switch(keyCode) {
		case KeyEvent.VK_D :
			character.moveRight(false);
			break;
		case KeyEvent.VK_A :
			character.moveLeft(false);
			break;
		case KeyEvent.VK_W :
			character.jump(false);
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
	
	public static void main(String[] args) {
		//Display d = new Display();
		
		JFrame f = new JFrame("Reaped Souls");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(100, 100, 1500, 1000);
		//f.setBackground(Color.black);
		f.getContentPane().add(new Display());
		f.setVisible(true);
		f.setResizable(false);
		f.pack();
		
		// starts the game application in the center of the monitor
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		f.setLocation(dim.width / 2 - f.getSize().width / 2,
				dim.height / 2 - f.getSize().height / 2);
	}
}
