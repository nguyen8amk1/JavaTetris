package level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import box.TextBox;
import box.DataBox;
import box.GridDataBox;
import box.TableDataBox;
import game.Common;

public class LevelOptionScene extends Scene {

	private ArrayList<DataBox> dataBoxes; 

	private ArrayList<TextBox> levelOptions;
	private GridDataBox levelGrid;
	private int levelGridRow = 2;
	private int levelGridCol = 5;
	private TextBox levelTextBox;
	private int currentLevelIndex = 0;

	
	TableDataBox table = new TableDataBox(200, 300, 5, 3, 100, 20);

	
	public LevelOptionScene() {
		super();
		levelOptions = new ArrayList<TextBox>();
		for(int i = 0; i < 10; i++) {
			levelOptions.add(new TextBox(Integer.toString(i), Common.fontSizeMid));
		}

		levelGrid = new GridDataBox(100, 100, levelGridRow, levelGridCol, levelOptions);
		levelGrid.showBorder(true);
		levelTextBox = new TextBox("LEVEL", levelGrid.getX(), levelGrid.getY() - Common.fontSizeBig - 10,  Common.fontSizeBig);
	
		// init table 
		ArrayList<TextBox> headers = new ArrayList<TextBox>();
		headers.add(new TextBox("", 20));
		headers.add(new TextBox("DITME", 20));
		headers.add(new TextBox("DITME", 20));

		ArrayList<TextBox> data = new ArrayList<TextBox>();
		data.add(new TextBox("VCL", 20));
		data.add(new TextBox("VCL", 20));
		data.add(new TextBox("FUCKING", 20));

		ArrayList<TextBox> newdata = new ArrayList<TextBox>();
		newdata.add(new TextBox("VAILON", 20));
		newdata.add(new TextBox("VAILON", 20));
		newdata.add(new TextBox("FACKINH", 20));

		table.addHeaderRow(headers);
		table.addDataRow(newdata);
		table.addDataRow(data);
		table.addDataRow(newdata);
		table.addDataRow(data);
		
		dataBoxes = new ArrayList<DataBox>();
		dataBoxes.add(levelGrid);
		dataBoxes.add(table);
		
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

		for(DataBox box: dataBoxes)
			box.render(g);

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
