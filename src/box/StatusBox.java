package box;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class StatusBox extends Box {
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL= 1;
	public static final int GRID = 2; 
	public static final int TABLE = 3; 

	private ArrayList<BoxOption> options;
	private int direction = HORIZONTAL;
	private int gap = 0;
	private int x, y; 
	private int row; 
	private int col;
	private boolean showBorder = false; 

	public StatusBox(ArrayList<BoxOption> options, int x, int y, int direction, int row, int col) {
		super(x, y);
		this.options = options;
		this.direction = direction;
		this.x = x; 
		this.y = y;
		this.row = row;
		this.col = col; 
		initRect(x, y);
	}

	public StatusBox(ArrayList<BoxOption> options, int x, int y, int direction) {
		this(options, x, y, direction, 0, 0);
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
		
		// TODO: refactor this part to use dependency injection in here?? 
		switch(direction) {
		case VERTICAL: {
			int ny = 0;
			int nx = rect.x; 
			for(int i = 0; i < options.size(); i++) {
				BoxOption option = options.get(i);

				int bWidth = option.getWidth();
				int bHeight = option.getHeight();

				ny = rect.y + i*bHeight;
				if(width < bWidth) {
					width = bWidth;
				}

				height += bHeight;
				option.setPos(nx, ny);
			}
			break;
		}

		case HORIZONTAL: {
			int i = 0;
			int nx = 0; 
			int ny = rect.y;
			for(BoxOption option: options) {
				int fontSize = option.getFontSize();
				nx = rect.x + i*(option.getContent().length()*fontSize + gap);
				width += option.getContent().length()*fontSize + gap;
				height = option.getHeight();
				option.setPos(nx, ny);
				i++;
			}
			break;
		}

		case GRID: {
			int nx = 0; 
			int ny = 0;
			width = col*options.get(0).getWidth();
			height = row*options.get(0).getHeight();
			for(int i = 0; i < row; i++) {
				for(int j = 0; j < col; j++) {
					int index = i*col + j; 
					BoxOption option = options.get(index);
					nx = rect.x + j * option.getWidth();
					ny = rect.y + i * option.getHeight();
					option.setPos(nx, ny);
				}
			}

			break;
		}

		case TABLE: {
			break;
		}

		}

		rect = new Rectangle(x, y, width, height);
	}

	public void showBorder(boolean show) {
		showBorder = show;
	}

	public void render(Graphics g) {
		for(BoxOption option : options) {
			if(showBorder) {
				Rectangle rect = option.getRect();
				g.setColor(Color.CYAN);
				g.drawRect(rect.x, rect.y, rect.width, rect.height);
			}
			option.render(g);
		}
		if(showBorder) {
			g.setColor(Color.GREEN);
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
}
