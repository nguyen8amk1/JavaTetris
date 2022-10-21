package box;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

public abstract class Box {
	protected Rectangle rect;
	
	public Box(int x, int y, int width, int height) {
		initRect(x, y, width, height);
	}
	
	public Box(int x, int y) {
		this(x, y, 0, 0);
	}

	private void initRect(int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public abstract void render(Graphics g);

	public Vector<Integer> getPos() { 
		Vector<Integer> t = new Vector<Integer>();
		t.add(rect.x);
		t.add(rect.y);
		return t;
	}

	public void setPos(int x, int y) {
		rect.x = x; 
		rect.y = y;
	}

	public int getWidth() { return rect.width; }
	public int getHeight() { return rect.height; }
}
