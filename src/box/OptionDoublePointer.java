package box;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class OptionDoublePointer {
	private int x, y;
	private int gap;
	private int pointerWidth, pointerHeight;
	private int[] leftTriXs;
	private int[] rightTriYs;
	private int[] leftTriYs;
	private int[] rightTriXs;

	public OptionDoublePointer(int x, int y, int gap, int pointerWidth, int pointerHeight) {
		super();
		this.x = x; 
		this.y = y;
		this.gap = gap;
		this.pointerWidth = pointerWidth;
		this.pointerHeight = pointerHeight;
	}
	
	public void update() {
		int muOffsetX = x; 
		int muOffsetY = y; 
		leftTriXs = new int[] {0 + muOffsetX, pointerWidth + muOffsetX, 0 + muOffsetX};
		leftTriYs = new int[] {0 + muOffsetY, pointerHeight/2 + muOffsetY, pointerHeight + muOffsetY};
		rightTriXs = new int[] {leftTriXs[0] + gap + 2*pointerWidth, leftTriXs[1] + gap, leftTriXs[2] + gap + 2*pointerWidth};
		rightTriYs = leftTriYs;
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillPolygon(leftTriXs, leftTriYs, 3);
		g.fillPolygon(rightTriXs, rightTriYs, 3);
	}
	
	public Vector<Integer> getPos() {
		return new Vector<Integer>(x, y);
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getPointerWidth() {
		return pointerWidth;
	}

	public void setPointerWidth(int pointerWidth) {
		this.pointerWidth = pointerWidth;
	}

	public int getPointerHeight() {
		return pointerHeight;
	}

	public void setPointerHeight(int pointerHeight) {
		this.pointerHeight = pointerHeight;
	}
	
}
