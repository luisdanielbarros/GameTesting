package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 6691247796639148462L;
	//The handler is given all the game objects and collisions to make them work out
	private Handler handler;
	//The interactionhandler is given interactions to read through and render
	private InteractionHandler interactionhandler;
	//The camera is only given images and the coordinates in which to render said images
	private static Camera camera;
	
	private MapHandler maphandler;
//	private HUD hud;
//	private Spawn spawner;
//	private Random r = new Random();
//	private Menu menu;
	//Global variables
	public static int ScreenWidth = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getWidth();
	public static int ScreenHeight = (int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize().getHeight();
	public static float TileWidth = ScreenWidth / 32;
	public static float TileHeight = ScreenHeight / 20;
	public static final String DirectoryPath = "F:/Portable Programs/Eclipse/EclipsePortable/Data/workspace/GameTesting/";
	Game()
	{
		handler = new Handler(this);
		interactionhandler = new InteractionHandler(this);
		camera = new Camera();
		maphandler = new MapHandler(handler, camera);
		maphandler.loadMap(1);
		handler.addObject(new Player(8, 38, this.handler));
//		handler.addObject(new WarpEventIntra(8, 34, 8, 20, WarpEventAnimationId.DefaultExternal));
//		handler.addObject(new NPCInteractable(8, 35, handler, NPCPath.ThreeXThreeAround, new Interaction(new InteractionSentence[] { new InteractionSentenceDefault("Hi!"), new InteractionSentenceYNQuestion("Feeling good today?", new InteractionSentenceDefault[] { new InteractionSentenceDefault("Nice!") }, new InteractionSentenceDefault[] { new InteractionSentenceDefault("Oh...") }) }, InteractionId.NPC )));
		this.addKeyListener(new KeyInput(this, handler));
//		hud = new HUD();
//		spawner = new Spawn(handler, hud);	
//		menu = new Menu(this, handler);
//		this.addMouseListener(new MouseInput(this, handler));
//		this.addMouseMotionListener(new MouseInput(this, handler));
		new Window(ScreenWidth, ScreenHeight, "Just testing.", this);
	}
	private Thread thread;
	private boolean running = false;
	public synchronized void start()
	{
		thread = new Thread (this);
		thread.start();
		running = true;
	}
	public synchronized void stop()
	{
		try
		{
			thread.join();
			running = false;
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	public void run()
	{
		//Easy explanation below, my brain hurts
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			//delta is equal to (the time that has passed since the last loop) / (the optimal time)
			delta += (now - lastTime) / ns;
			lastTime = now;
			//then if delta is bigger or equal to 1, it meants that the (time that has passed since the last loop) is reasonable
			//the higher the delta the faster the loop was (that's good), the lower the delta the slower the loop was (that's bad)
			while (delta >= 1)
			{
				tick();
				delta--;
			}
			if (running)
				render();
				frames++;
			//if a second has passed, it will execute the code in the brackets
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}	
	private void tick()
	{
		if (GameState == STATE.GameDefault)
		{
			handler.tick();
//			hud.tick();
//			spawner.tick();
		}
		else if (GameState == STATE.GameInteracting)
		{
			handler.tick();
//			hud.tick();
//			spawner.tick();
		}
		else if (GameState == STATE.Menu)
		{
//			menu.tick();
		}
		
	}
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, ScreenWidth, ScreenHeight);
		if (GameState == STATE.GameDefault)
		{
			camera.renderBottomLayer(g);
			handler.render(g);
			camera.renderTopLayer(g);
//			hud.render(g);
		}
		else if (GameState == STATE.GameInteracting)
		{
			camera.renderBottomLayer(g);
			handler.render(g);
			camera.renderTopLayer(g);
			interactionhandler.render(g);
//			hud.render(g);
		}
		else if (GameState == STATE.Menu)
		{
//			menu.render(g);
		}
		
		g.dispose();
		bs.show();
	}
	public static int clamp(int var, int min, int max)
	{
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}
	public static float clamp(float var, int min, int max)
	{
		if (var >= max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}
	public InteractionHandler getInteractionHandler()
	{
		return interactionhandler;
	}
	public static Camera getCamera()
	{
		return camera;
	}
	public static void main(String args[]){
		new Game();
	}
	public enum STATE {
		Menu,
		GameDefault,
		GameInteracting
	};
	public STATE GameState = STATE.GameDefault;
}
