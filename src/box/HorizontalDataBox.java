package box;

import java.awt.Rectangle;
import java.util.ArrayList;

public class HorizontalDataBox extends DataBox {

	public HorizontalDataBox(int x, int y, ArrayList<TextBox> data) {
		super(x, y, data);
	}

	@Override
	protected void initDataRect() {
		int width = 0; 
		int height = 0;
		
		int i = 0;
		int nx = 0; 
		int ny = rect.y;
		for(TextBox option: data) {
			nx = rect.x + i*(option.getWidth() + gap);
			width += option.getWidth() + gap;
			height = option.getHeight();
			option.setPos(nx, ny);
			i++;
		}

		rect = new Rectangle(getX(), getY(), width, height);
	}

}
