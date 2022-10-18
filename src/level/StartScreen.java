package level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Common;

public class StartScreen extends Scene {
	private BufferedImage image; 

	public StartScreen() {
		super();

	}
	
	protected void loadImage() throws IOException {
		image = ImageIO.read(new File("./assets/images/tetris_start_screen.jpeg"));
	}

	@Override
	public void initSceneAndNextScene() {
		scene = Scene.START_SCREEN;
		nextScene = Scene.GAME_OPTION_SCREEN;
	}

	@Override
	public void update(float dt) {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(image, 0, 0, Common.gameWidth, Common.gameHeight, 0, 0, image.getWidth(null), image.getHeight(null), null);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_ENTER: 
			toNextScene();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
