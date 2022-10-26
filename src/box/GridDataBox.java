package box;

import java.awt.Rectangle;
import java.util.ArrayList;

public class GridDataBox extends DataBox {
	private int cols, rows; 
	// FIXME: calling initDataRect specifically in here is not something i love, but i can't think of anything else right now... 
	public GridDataBox(int x, int y, int rows, int cols, ArrayList<TextBox> data) {
		super(x, y, data);
		this.cols = cols; 
		this.rows = rows;
		initDataRect();
	}

	@Override
	protected void initDataRect() {
		int width = 0; 
		int height = 0;
		
		int nx = 0; 
		int ny = 0;
		width = cols*data.get(0).getWidth();
		height = rows*data.get(0).getHeight();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				int index = i*cols + j; 
				TextBox option = data.get(index);
				nx = rect.x + j * option.getWidth();
				ny = rect.y + i * option.getHeight();
				option.setPos(nx, ny);
			}
		}

		rect = new Rectangle(getX(), getY(), width, height);
	}

}
