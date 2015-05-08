package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import GameStateClasses.Credits;
import GameStateClasses.GameOver;
import GameStateClasses.Menu;
import States.GameState;

public class Display extends Canvas implements Runnable, KeyListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//public int screenWidth = 2000, screenHeight = 2000;
	public int screenWidth, screenHeight;
	
	private boolean running;
	
	/**
	 * starts the application in the menu state
	 */
	public GameState state = GameState.Menu;
	
	private Thread thread;
	private Graphics2D g;
	private BufferStrategy bs;
	
	/**
	 * The area that calls all of the others classes into this main class.
	 * Controls the entire game
	 */
	private World world;
	private Camera camera;
	
	ImageLoader image = new ImageLoader();
	
	private Menu menu;
	private Credits credits;
	private GameOver gameOver;
	
	MouseInput mouse = new MouseInput();
	
	/**
	 * creates the ScreenSize class to help determine how big the scene will be
	 */
	public ScreenSize s = new ScreenSize();
	
	KeyEvent e;
	
	/**
	 * Constructor
	 */
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
		
		
		this.addMouseListener(mouse); // calls the keyboard input from the Controls class
		camera = new Camera();
		addKeyListener(this);
	}
	
	/**
	 * will change the state of the game from the game state to the menu state
	 * when the character has run out of lives
	 */
	public void menuStateAfterDeath() {
		if(world.getCharacter().getLives() < 0) {
			System.out.println("true");
			state = GameState.GameOver;
		}
	}
	
	// initializes everything
	public void init() {
		world = new World(image);
		menu = new Menu(mouse, state, image);
		credits = new Credits(image);
		gameOver = new GameOver(image);
		
		screenWidth = (int) (s.getScreenWidth());
		screenHeight = (int) (s.getScreenHeight());
	}
	
	// function to run the game
	public void run() {
		running = true;
		
		init();
		world.init();
		
		/**
		 * The original time step that was implemented using .nanoSeconds()
		 */
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
		
		/**
		 * current time step using .currentTimeMillis
		 */
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
				/**
				 * allows for changes in the state when a button is pressed
				 * Such as being able to change from the menu screen (menu state) to the actual game (game state)
				 */
				if(state == GameState.Menu) {
					//menu.update();
					if(mouse.clickX >= menu.play().x && mouse.clickX <= (menu.play().x + menu.play().width)
							&& mouse.clickY >= menu.play().y && mouse.clickY <= (menu.play().y + menu.play().height)) {
						state = GameState.Game;
						world.getCharacter().setLives(3);
					}
					
					else if(mouse.clickX >= menu.credits().x && mouse.clickX <= (menu.credits().x + menu.credits().width)
							&& mouse.clickY >= menu.credits().y && mouse.clickY <= (menu.credits().y + menu.credits().height)) {
						state = GameState.Credits;
						System.out.println(true);
					}
					
					else if(mouse.clickX >= menu.exit().x && mouse.clickX <= (menu.exit().x + menu.exit().width)
							&& mouse.clickY >= menu.exit().y && mouse.clickY <= (menu.exit().y + menu.exit().height)) {
						System.exit(0);
					}
				}
				
				else if(state == GameState.Credits) {
					if(mouse.clickX >= credits.returnToMenu().x && mouse.clickX <= (credits.returnToMenu().x + credits.returnToMenu().width)
							&& mouse.clickY >= credits.returnToMenu().y && mouse.clickY <= (credits.returnToMenu().y + credits.returnToMenu().height)) {
						state = GameState.Menu;
					}
				}
				
				else if(state == GameState.GameOver) {
					if(mouse.clickX >= gameOver.restartBut().x && mouse.clickX <= (gameOver.restartBut().x + gameOver.restartBut().width)
							&& mouse.clickY >= gameOver.restartBut().y && mouse.clickY <= (gameOver.restartBut().y + gameOver.restartBut().height)) {
						state = GameState.Menu;
						//world.musicExit();
					}
				}
				
				else if(state == GameState.Game) {
					world.update();
					camera.update(world.getCharacter(), screenWidth, screenHeight);
					menuStateAfterDeath();
					//world.musicStart();
				}
				
				t += dt;
				accumulator -= dt;
			}
			
			/**
			 * calculating the interpolation by dividing the left over time by the number of updates eac second
			 */
			double interpolation = accumulator / dt;
			
			//System.out.println("Interpolation " + interpolation);
			camera.interpolate(interpolation);
			world.getCharacter().interpolate(interpolation);
			
			render();
		}
	}
	
	/**
	 * renders the game
	 */
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
		
		if(state == GameState.Menu) {
			menu.render(g2d);
		}
		else if(state == GameState.Credits) {
			credits.render(g2d);
		}
		else if(state == GameState.GameOver) {
			gameOver.render(g2d);
		}
		else {
			/**
			 * Setting the entire background bigger than the width and height of
			 * the level itself. This is to prevent any visual glitches that
			 * occurs when something is not rendered properly
			 */
			g.fillRect(0, 0, world.getLevel().getMapWidth() * 150, world.getLevel().getMapHeight() * 150);

			// draws the world to the screen
			g2d.translate(camera.getCameraX(), camera.getCameraY());
			world.render(g);
			g2d.translate(-camera.getCameraX(), -camera.getCameraY());

			g.setColor(Color.white);
			g.setFont(new Font("Century Gothic", Font.PLAIN, 30));
			g.drawString("Lives: " + world.getCharacter().getLives(), 20, 30);
			g.drawString("Health: " + world.getCharacter().getHealth(), 20, 60);
		}
		
		g.dispose();
		bs.show();
	}
	
	///////////////////////////////////////////////////
	
	/**
	 * the 3 methods to see if the player has pressed a certain key or not
	 */
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
