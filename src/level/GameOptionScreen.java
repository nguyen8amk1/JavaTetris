package level;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.concurrent.ConcurrentNavigableMap;

import game.Common;
import music.MusicPlayer;

public class GameOptionScreen extends Scene {

	public static final int MUSIC_A = 0;
	public static final int GAME_TYPE_A = 0;
	
	private int currentSong = 0;
	private int currentType = 0;

	private int fontSize = 20;

	// type parameters 
	private String atype = "A-TYPE";
	private Rectangle atypeRect = new Rectangle(100, 100, atype.length() * fontSize, fontSize);
	private String btype = "B-TYPE";
	private Rectangle btypeRect = new Rectangle(300, 100, atype.length() * fontSize, fontSize);

	// music patemeters
	private String musicType = "MUSIC TYPE";
	private String music1 = "MUSIC - 1";
	private String music2 = "MUSIC - 2";
	private String music3 = "MUSIC - 3";
	private String musicOff = "OFF";

	private Rectangle music1Rect = new Rectangle(200, 350, music1.length() * fontSize, fontSize);
	private Rectangle music2Rect = new Rectangle(200, 400, music2.length() * fontSize, fontSize);
	private Rectangle music3Rect = new Rectangle(200, 450, music3.length() * fontSize, fontSize);
	private Rectangle musicOffRect = new Rectangle(200, 500, musicOff.length() * fontSize, fontSize);

	// type pointers parameters 
	private Rectangle currentTypeRect = atypeRect;
	private String currentTypeStr = atype;

	private int triWidth = fontSize;
	private int triHeight = fontSize;
	private int lTriOffsetX = currentTypeRect.x - triWidth;
	private int lTriOffsetY = currentTypeRect.y - triHeight;
	private int rTriOffsetX = currentTypeStr.length()*fontSize;

	private int[] leftTriXs = {0 + lTriOffsetX, triWidth + lTriOffsetX, 0 + lTriOffsetX};
	private int[] leftTriYs = {0 + lTriOffsetY, triHeight/2 + lTriOffsetY, triHeight + lTriOffsetY};
	private int[] rightTriXs = {leftTriXs[0] + rTriOffsetX, leftTriXs[1] + rTriOffsetX - 2*triWidth, leftTriXs[2] + rTriOffsetX};
	private int[] rightTriYs = leftTriYs;

	// music pointer parameters
	private Rectangle currentMusicRect = atypeRect;
	private String currentMusicStr = music1;
	private int mutriWidth = fontSize;
	private int mutriHeight = fontSize;
	private int mulTriOffsetX = currentMusicRect.x - triWidth;
	private int mulTriOffsetY = currentMusicRect.y - triHeight;
	private int murTriOffsetX = currentMusicStr.length()*fontSize;

	private int[] muleftTriXs = {0 + mulTriOffsetX, mutriWidth + mulTriOffsetX, 0 + mulTriOffsetX};
	private int[] muleftTriYs = {0 + mulTriOffsetY, mutriHeight/2 + mulTriOffsetY, mutriHeight + mulTriOffsetY};
	private int[] murightTriXs = {muleftTriXs[0] + murTriOffsetX, muleftTriXs[1] + murTriOffsetX - 2*mutriWidth, muleftTriXs[2] + murTriOffsetX};
	private int[] murightTriYs = muleftTriYs;
	
	
	private MusicPlayer musicPlayer = new MusicPlayer(null);
	

	@Override
	public void initSceneAndNextScene() {
		scene = Scene.GAME_OPTION_SCREEN;
		nextScene = Scene.LEVEL_OPTION_SCREEN;
	}

	@Override
	public void update(float dt) {

		updatePointersPos();

		musicPlayer.play();
	}

	private void updatePointersPos() {
		lTriOffsetX = currentTypeRect.x - triWidth;
		lTriOffsetY = currentTypeRect.y - triHeight;
		rTriOffsetX = currentTypeStr.length()*fontSize;

		leftTriXs = new int[] {0 + lTriOffsetX, triWidth + lTriOffsetX, 0 + lTriOffsetX};
		leftTriYs = new int[] {0 + lTriOffsetY, triHeight/2 + lTriOffsetY, triHeight + lTriOffsetY};
		rightTriXs = new int[] {leftTriXs[0] + rTriOffsetX, leftTriXs[1] + rTriOffsetX - 2*triWidth, leftTriXs[2] + rTriOffsetX};
		rightTriYs = leftTriYs;

		
		// music 
		mulTriOffsetX = currentMusicRect.x - triWidth;
		mulTriOffsetY = currentMusicRect.y - triHeight;
		murTriOffsetX = currentMusicStr.length()*fontSize;

		muleftTriXs = new int[] {0 + mulTriOffsetX, mutriWidth + mulTriOffsetX, 0 + mulTriOffsetX};
		muleftTriYs = new int[] {0 + mulTriOffsetY, mutriHeight/2 + mulTriOffsetY, mutriHeight + mulTriOffsetY};
		murightTriXs = new int[] {muleftTriXs[0] + murTriOffsetX, muleftTriXs[1] + murTriOffsetX - 2*mutriWidth, muleftTriXs[2] + murTriOffsetX};
		murightTriYs = muleftTriYs;
	}

	@Override
	public void render(Graphics g) {
		drawType(g);
		drawMusic(g);
	}

	private void drawMusic(Graphics g) {
		drawMusicPointer(g);

		g.setFont(new Font("Serif", Font.BOLD, fontSize));
		g.setColor(Color.WHITE);

		g.drawString(music1, music1Rect.x, music1Rect.y);
		g.drawString(music2, music2Rect.x, music2Rect.y);
		g.drawString(music3, music3Rect.x, music3Rect.y);
		g.drawString(musicOff, musicOffRect.x, musicOffRect.y);
	}

	private void drawType(Graphics g) {
		drawTypePointer(g);

		g.setFont(new Font("Serif", Font.BOLD, fontSize));
		g.setColor(Color.WHITE);

		g.drawString(atype, atypeRect.x, atypeRect.y);
		g.drawString(btype, btypeRect.x, btypeRect.y);
	}

	private void drawTypePointer(Graphics g) {
		if(currentType == 0) {
			currentTypeRect = atypeRect;
			currentTypeStr = atype;
		} else {
			currentTypeRect = btypeRect;
			currentTypeStr = btype;
		}

		// draw double triangles 
		g.setColor(Color.WHITE);
		g.fillPolygon(leftTriXs, leftTriYs, 3);
		g.fillPolygon(rightTriXs, rightTriYs, 3);
	}

	private void drawMusicPointer(Graphics g) {
		switch(currentSong) {
		case 0:
			currentMusicRect = music1Rect;
			currentMusicStr = music1;
			break;
		case 1:
			currentMusicRect = music2Rect;
			currentMusicStr = music2;
			break;
		case 2:
			currentMusicRect = music3Rect;
			currentMusicStr = music3;
			break;
		case 3:
			currentMusicRect = musicOffRect;
			currentMusicStr = musicOff;
			break;
		} 

		// draw double triangles 
		g.setColor(Color.WHITE);
		g.fillPolygon(muleftTriXs, muleftTriYs, 3);
		g.fillPolygon(murightTriXs, murightTriYs, 3);
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

		// clamping 
		if(currentSong >= 3) { currentSong = 3; }
		else if(currentSong <= 0) currentSong = 0;

		if(currentType >= 1) { currentType = 1; }
		else if(currentType <= 0) currentType = 0;
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	protected void loadImage() throws IOException {
		
	}
}
