package level;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import box.TextBox;
import box.OptionDoublePointer;
import box.StatusBox;
import box.Table;
import game.Common;
import music.MusicPlayer;

public class GameOptionScene extends Scene {

	public static final int MUSIC_A = 0;
	public static final int GAME_TYPE_A = 0;
	
	private int currentSong = 0;
	private int currentType = 0;

	private int fontSize = 20;

	// type box 
	private ArrayList<TextBox> typeOptions = new ArrayList<TextBox>();
	private StatusBox typeBox;

	// music box 
	private ArrayList<TextBox> musicOptions = new ArrayList<TextBox>();
	private StatusBox musicBox;  
	
	// type pointers parameters 
	private Rectangle currentTypeRect;
	private String currentTypeStr;
	private OptionDoublePointer typePointer;

	// music pointer parameters
	private Rectangle currentMusicRect;
	private String currentMusicStr;
	private OptionDoublePointer musicPointer; 


	private MusicPlayer musicPlayer = new MusicPlayer(null);

	// TEST 
	Table table = new Table(0, 0, 4, 3, 100, 20); 

	public GameOptionScene() {
		super();
		
		musicOptions.add(new TextBox("MUSIC-1", fontSize));
		musicOptions.add(new TextBox("MUSIC-2", fontSize));
		musicOptions.add(new TextBox("MUSIC-3", fontSize));
		musicOptions.add(new TextBox("OFF", fontSize));

		typeOptions.add(new TextBox("A-TYPE", fontSize));
		typeOptions.add(new TextBox("B-TYPE", fontSize));
		

		currentMusicRect = musicOptions.get(currentSong).getRect();
		currentMusicStr = musicOptions.get(currentSong).getContent();

		musicBox = new StatusBox(musicOptions, 200, 350);
		musicBox.setGap(10);
		musicBox.showBorder(true);

		musicPointer = new OptionDoublePointer(currentMusicRect.x - fontSize, currentMusicRect.y, musicOptions.get(currentSong).getWidth(), fontSize, fontSize);

		currentTypeRect = typeOptions.get(currentType).getRect();
		currentTypeStr = typeOptions.get(currentType).getContent();

		typeBox = new StatusBox(typeOptions, 100, 100, StatusBox.HORIZONTAL);
		typeBox.showBorder(true);
		typeBox.setGap(50);
		typePointer = new OptionDoublePointer(currentTypeRect.x - fontSize, currentTypeRect.y, typeOptions.get(currentSong).getWidth(), fontSize, fontSize);

		
		
		// test
		ArrayList<TextBox> headers = new ArrayList<TextBox>();
		headers.add(new TextBox("FUCKING", 20));
		headers.add(new TextBox("DITME", 20));
		headers.add(new TextBox("DITME", 20));

		ArrayList<TextBox> data = new ArrayList<TextBox>();
		data.add(new TextBox("VCL", 20));
		data.add(new TextBox("VCL", 20));
		data.add(new TextBox("FUCKING", 20));

		table.addHeaderRow(headers);
		table.addDataRow(data);
		table.addDataRow(data);
		table.addDataRow(data);
	}

	@Override
	public void initSceneAndNextScene() {
		scene = Scene.GAME_OPTION_SCREEN;
		nextScene = Scene.LEVEL_OPTION_SCREEN;
	}

	@Override
	public void update(float dt) {
		currentMusicRect = musicOptions.get(currentSong).getRect();
		currentMusicStr = musicOptions.get(currentSong).getContent();
		
		musicPointer.setPos(currentMusicRect.x - fontSize, currentMusicRect.y);
		musicPointer.update();

		
		
		currentTypeRect = typeOptions.get(currentType).getRect();
		currentTypeStr = typeOptions.get(currentType).getContent();

		typePointer.setPos(currentTypeRect.x - fontSize, currentTypeRect.y);
		typePointer.update();

		musicPlayer.play();
	}

	@Override
	public void render(Graphics g) {
		typeBox.render(g);
		typePointer.render(g);

		musicBox.render(g);
		musicPointer.render(g);

		table.render(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_ENTER: 
			toNextScene();
			break;
		case KeyEvent.VK_LEFT: 
			currentType--; 
			break;
		case KeyEvent.VK_RIGHT: 
			currentType++; 
			break;
		case KeyEvent.VK_UP: 
			--currentSong;
			break;
		case KeyEvent.VK_DOWN: 
			++currentSong;
			break;
		}

		currentSong = Common.clamp(currentSong, 0, 3);
		currentType = Common.clamp(currentType, 0, 1);
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	protected void loadResources() throws IOException {
		
	}
}
