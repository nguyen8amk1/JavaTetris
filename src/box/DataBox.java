package box;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public abstract class DataBox extends Box {
	protected ArrayList<TextBox> data; 
	private boolean showBorder = false;
	protected int gap = 0;

	public DataBox(int x, int y, ArrayList<TextBox> data) {
		super(x, y);
		this.data= data; 
		initDataRect();
	}


	public void setGap(int gap) {
		this.gap = gap;
		initDataRect();
	}

	public int getGap() {
		return gap;
	}

	protected abstract void initDataRect();

	public void showBorder(boolean show) {
		showBorder = show;
	}

	public void render(Graphics g) {
		for(TextBox option : data) {
			if(showBorder) {
				Rectangle optionRect = option.getRect();
				g.setColor(Color.CYAN);
				g.drawRect(optionRect.x, optionRect.y, optionRect.width, optionRect.height);
			}
			option.render(g);
		}
		if(showBorder) {
			g.setColor(Color.GREEN);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
}