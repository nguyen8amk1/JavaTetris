package box;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class StatusBox extends Box {
	public static int HORIZONTAL = 0;
	public static int VERTICAL= 1;

	private ArrayList<BoxOption> options;
	private int direction = HORIZONTAL;
	private int gap = 0;
	private int x, y; 

	public StatusBox(ArrayList<BoxOption> options, int x, int y, int direction) {
		super(x, y);
		this.options = options;
		this.direction = direction;
		this.x = x; 
		this.y = y;
		initRect(x, y);
	}

	public StatusBox(ArrayList<BoxOption> options, int x, int y) {
		this(options, x, y, VERTICAL);
	}

	public void setGap(int gap) {
		this.gap = gap;
		initRect(x, y);
	}

	public int getGap() {
		return gap;
	}

	private void initRect(int x, int y) {
		int width = 0; 
		int height = 0;
		int nx = rect.x; 
		int ny = rect.y;
		int i = 0;
		for(BoxOption option: options) {
			int bWidth = option.getWidth();
			int bHeight = option.getHeight();
			int fontSize = option.getFontSize();
			if(direction == VERTICAL) {
				ny += bHeight + gap;
				if(width < bWidth) {
					width = bWidth;
				}
				height += bHeight + gap;
			} else { // horizontal 
				nx += i*option.getContent().length()*fontSize + gap;
				width += i*option.getContent().length()*fontSize + gap;
			}
			i++;
			option.setPos(nx, ny);
		}
		rect = new Rectangle(x, y, width, height);
	}

	public void render(Graphics g) {
		for(BoxOption option : options) {
			option.render(g);
		}
	}
}
