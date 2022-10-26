package level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Common;

public class SplashScene extends Scene {
	private BufferedImage image;
	private float splashScreenTimeAccumulator;
	private float splashScreenDelay = 3; 

	public SplashScene(GameSceneManager gsm) {
		super(gsm);
	}

	@Override
	public void update(float dt) {
		splashScreenTimeAccumulator += dt; 
		if(splashScreenTimeAccumulator >= splashScreenDelay) {
			toNextScene();
			splashScreenTimeAccumulator = 0;
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, 0, 0, Common.gameWidth, Common.gameHeight, 0, 0, image.getWidth(null), image.getHeight(null), null);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	protected void loadResources() throws IOException {
		image = Common.loadImage("./assets/images/tetris_splash_screen.jpeg");
	}

	@Override
	protected void toNextScene() {
		gsm.pushScene(new StartScene(gsm));
	}

}
