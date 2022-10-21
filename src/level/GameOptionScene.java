package level;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import box.BoxOption;
import box.OptionDoublePointer;
import box.StatusBox;
import game.Common;
import music.MusicPlayer;

public class GameOptionScene extends Scene {

	public static final int MUSIC_A = 0;
	public static final int GAME_TYPE_A = 0;
	
	private int currentSong = 0;
	private int currentType = 0;

	private int fontSize = 20;

	// type box 
	private ArrayList<BoxOption> typeOptions = new ArrayList<BoxOption>();
	private StatusBox typeBox;

	// music box 
	private ArrayList<BoxOption> musicOptions = new ArrayList<BoxOption>();
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

	public GameOptionScene() {
		super();
		
		musicOptions.add(new BoxOption("MUSIC - 1", fontSize));
		musicOptions.add(new BoxOption("MUSIC - 2", fontSize));
		musicOptions.add(new BoxOption("MUSIC - 3", fontSize));
		musicOptions.add(new BoxOption("OFF", fontSize));

		typeOptions.add(new BoxOption("A-TYPE", fontSize));
		typeOptions.add(new BoxOption("B-TYPE", fontSize));
		

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
