package level;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Scene {
	// Scene  
	public static int SPLASH_SCREEN = 0;
	public static int START_SCREEN = 1;
	public static int GAME_OPTION_SCREEN = 2;
	public static int LEVEL_OPTION_SCREEN = 3;
	public static int LEVEL0 = 4; 
	public static int LEVEL1 = 5;  

	protected GameSceneManager gsm;

	public Scene(GameSceneManager gsm) {
		this.gsm = gsm;

		try {
			loadResources();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void update(float dt);
	public abstract void render(Graphics g);
	public abstract void keyPressed(KeyEvent e);
	public abstract void keyReleased(KeyEvent e);

	protected abstract void loadResources() throws IOException;

	protected abstract void toNextScene(); 
}
