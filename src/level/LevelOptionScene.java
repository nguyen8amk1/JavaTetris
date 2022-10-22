package level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import box.TextBox;
import box.StatusBox;
import game.Common;

public class LevelOptionScene extends Scene {

	private ArrayList<TextBox> levelOptions;
	private StatusBox levelGrid;
	private int levelGridRow = 2;
	private int levelGridCol = 5;
	private TextBox levelTextBox;
	private StatusBox scoreTable;
	private int currentLevelIndex = 0;
	
	public LevelOptionScene() {
		super();
		levelOptions = new ArrayList<TextBox>();
		for(int i = 0; i < 10; i++) {
			levelOptions.add(new TextBox(Integer.toString(i), Common.fontSizeMid));
		}

		levelGrid = new StatusBox(levelOptions, 100, 100, StatusBox.GRID, levelGridRow, levelGridCol);
		levelGrid.showBorder(true);
		levelTextBox = new TextBox("LEVEL", levelGrid.getX(), levelGrid.getY() - Common.fontSizeBig - 10,  Common.fontSizeBig);
	}

	@Override
	public void initSceneAndNextScene() {
		scene = Scene.LEVEL_OPTION_SCREEN;
		nextScene = Scene.LEVEL0;
	}

	@Override
	public void update(float dt) {
	}

	@Override
	public void render(Graphics g) {
		drawSelectedCell(g); 
		levelGrid.render(g);
		levelTextBox.render(g);
	}

	private void drawSelectedCell(Graphics g) {
		TextBox option = levelOptions.get(currentLevelIndex);
		int x = option.getPos().get(0); 
		int y = option.getPos().get(1); 
		int w = option.getWidth();
		int h = option.getHeight();
		g.setColor(Color.RED);
		g.fillRect(x, y, w, h);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_ENTER: 
			toNextScene();
			break;
		case KeyEvent.VK_LEFT: 
			currentLevelIndex--;
			break;
		case KeyEvent.VK_RIGHT: 
			currentLevelIndex++;
			break;
		case KeyEvent.VK_UP: 
			currentLevelIndex -= levelGridCol;
			break;
		case KeyEvent.VK_DOWN: 
			currentLevelIndex += levelGridCol;
			break;
		}

		currentLevelIndex = Common.clamp(currentLevelIndex, 0, levelOptions.size() - 1);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	protected void loadResources() throws IOException {
		// TODO Auto-generated method stub
		
	}

}
