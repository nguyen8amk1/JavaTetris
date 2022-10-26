package level;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import box.TextBox;
import box.VerticalDataBox;
import box.DataBox;
import box.HorizontalDataBox;
import box.OptionDoublePointer;
import box.TableDataBox;
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
	private DataBox typeBox;

	// music box 
	private ArrayList<TextBox> musicOptions = new ArrayList<TextBox>();
	private DataBox musicBox;  
	
	// type pointers parameters 
	private Rectangle currentTypeRect;
	private String currentTypeStr;
	private OptionDoublePointer typePointer;

	// music pointer parameters
	private Rectangle currentMusicRect;
	private String currentMusicStr;
	private OptionDoublePointer musicPointer; 


	private MusicPlayer musicPlayer = new MusicPlayer(null);

	private ArrayList<DataBox> dataBoxes;
	private ArrayList<OptionDoublePointer> pointers; 

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

		musicBox = new VerticalDataBox(200, 350, musicOptions);
		musicBox.setGap(20);
		musicBox.showBorder(true);

		musicPointer = new OptionDoublePointer(currentMusicRect.x - fontSize, currentMusicRect.y, musicOptions.get(currentSong).getWidth(), fontSize, fontSize);

		currentTypeRect = typeOptions.get(currentType).getRect();
		currentTypeStr = typeOptions.get(currentType).getContent();

		typeBox = new HorizontalDataBox(100, 100, typeOptions);
		typeBox.showBorder(true);
		typeBox.setGap(50);
		typePointer = new OptionDoublePointer(currentTypeRect.x - fontSize, currentTypeRect.y, typeOptions.get(currentSong).getWidth(), fontSize, fontSize);

		
		dataBoxes = new ArrayList<DataBox>();
		pointers = new ArrayList<OptionDoublePointer>();
		
		dataBoxes.add(musicBox);
		dataBoxes.add(typeBox);

		pointers.add(musicPointer);
		pointers.add(typePointer);


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
		for(DataBox box: dataBoxes) {
			box.render(g);
		}

		for(OptionDoublePointer pointer: pointers) {
			pointer.render(g);
		}
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
