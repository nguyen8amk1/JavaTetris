package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import level.GameOptionScreen;
import level.Level;
import level.LevelOptionScreen;
import level.Scene;
import level.SplashScreen;
import level.StartScreen;

class Game implements Runnable {

	public int width, height, fps = 60;
	private String title;
	public Display display;

	public Thread thread;
	public boolean running = false;
	private BufferStrategy bs;
	private Graphics g;

	private ArrayList<Scene> scenes;
	private int currentScene = 0;

	private Scene level1; 
	private Scene splashScreen;
	private Scene startScreen;
	private Scene gameOptionScreen;
	private Scene levelOptionScreen;
	
	public Game(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	private void init() {
		display = new Display(width, height, title);
		display.addKeyListener(new KeyInput(this));

		// init scenes 
		scenes = new ArrayList<Scene>();

		splashScreen = new SplashScreen();
		startScreen = new StartScreen();
		gameOptionScreen = new GameOptionScreen();
		levelOptionScreen = new LevelOptionScreen();
		level1 = new Level(); 

		scenes.add(splashScreen);
		scenes.add(startScreen);
		scenes.add(gameOptionScreen);
		scenes.add(levelOptionScreen);
		scenes.add(level1);
	}

	private void tick(float dt) {
		currentScene = scenes.get(currentScene).scene();
		scenes.get(currentScene).update(dt);
	}

	private void render() {
		bs = display.canvas.getBufferStrategy();
		if(bs == null) {
			display.canvas.createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
		g.clearRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

    //draw here
		scenes.get(currentScene).render(g);
    
		bs.show();
		g.dispose();
	}
	
	public void run() {
		init();

		double tickDuration = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / tickDuration;
			lastTime = now;

			if(delta >= 1) {
				tick(1.0f/fps);
				render();
				delta--;
			}
		}

		stop();
	}

	public synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void keyPressed(KeyEvent e) {
		scenes.get(currentScene).keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		scenes.get(currentScene).keyReleased(e);
	}
}
