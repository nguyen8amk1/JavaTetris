package game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import level.Level;

class Game implements Runnable {

	public int width, height, fps = 60;
	private String title;
	public Display display;

	public Thread thread;
	public boolean running = false;
	private BufferStrategy bs;
	private Graphics g;

	private Level level1; 
	
	public Game(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	private void init() {
		display = new Display(width, height, title);
		display.addKeyListener(new KeyInput(this));

		level1 = new Level(); 
	}

	private void tick(float dt) {
		level1.update(dt);
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
		level1.render(g);
    
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
		level1.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		level1.keyReleased(e);
	}
}
