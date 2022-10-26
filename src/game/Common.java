package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import level.GameOptionScene;

public class Common {
	public static final int rw = 20;  
	public static final int rh = 20;
	public static final int fieldWidth = 12;
	public static final int fieldHeight = 18;
	public static final int gameWidth = 32*rw;
	public static final int gameHeight = 30*rh;

	public static final int fontSizeBig = 30;
	public static final int fontSizeMid = 25;

	public static BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}

	public static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}

	public static void drawString(String content) {
		
	}
}
