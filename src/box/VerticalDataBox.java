package box;

import java.awt.Rectangle;
import java.util.ArrayList;

public class VerticalDataBox extends DataBox {
	private int gap = 0; 

	public VerticalDataBox(int x, int y, ArrayList<TextBox> data) {
		super(x, y, data);
	}

	protected void initDataRect() {
		int width = 0; 
		int height = 0;
		
		int ny = 0;
		int nx = rect.x; 
		for(int i = 0; i < data.size(); i++) {
			TextBox option = data.get(i);

			int bWidth = option.getWidth();
			int bHeight = option.getHeight();
			ny = rect.y + i*(bHeight + gap);
			if(width < bWidth) {
				width = bWidth;
			}

			height += bHeight + gap;
			option.setPos(nx, ny);
		}

		rect = new Rectangle(getX(), getY(), width, height);
	}
}
