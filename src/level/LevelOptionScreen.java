package level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

import game.Common;

public class LevelOptionScreen extends Scene {

	private float splashScreenTimeAccumulator;
	private float splashScreenDelay = 3;

	@Override
	public void initSceneAndNextScene() {
		scene = Scene.LEVEL_OPTION_SCREEN;
		nextScene = Scene.LEVEL0;
		
	}

	@Override
	public void update(float dt) {
		splashScreenTimeAccumulator += dt; 
		if(splashScreenTimeAccumulator >= splashScreenDelay ) {
			toNextScene();
			splashScreenTimeAccumulator = 0;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Common.gameWidth, Common.gameHeight);

		g.setFont(new Font("Serif", Font.BOLD, 50));
		g.setColor(Color.WHITE);
		g.drawString("LEVEL OPTION", 100, 100);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	protected void loadResources() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
