package box;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

// FIXME: still have little issue in the 
public class TableDataBox extends DataBox {
	private ArrayList<TextBox> headers;

	private int rows, cols;
	private int cellWidth, cellHeight;
	private int currentDataRow = 0;

	public TableDataBox(int x, int y, int rows, int cols, int cellWidth, int cellHeight) {
		super(x, y, null);
		setWidth(cols * cellWidth);
		setHeight(rows*cellHeight);

		headers = new ArrayList<TextBox>();
		data = new ArrayList<TextBox>();

		this.rows = rows;
		this.cols = cols;

		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
	}

	public void render(Graphics g) {
		super.render(g);

		// show headers
		for (int i = 0; i < cols; i++) {
			TextBox header = headers.get(i);
			drawCell(g, header);
		}

		// show data
		for (int i = 0; i < rows - 1; i++) {
			for (int j = 0; j < cols; j++) {
				TextBox d = data.get(i * cols + j);
				d.setRect(new Rectangle(rect.x + j * cellWidth, rect.y + (i + 1) * cellHeight, cellWidth, cellHeight));
				drawCell(g, d);
			}
		}
	}

	private void drawCell(Graphics g, TextBox cell) {
		g.setColor(Color.CYAN);
		int x = cell.getX();
		int y = cell.getY();
		g.drawRect(x, y, cellWidth, cellHeight);
		cell.render(g);
	}

	public void addDataRow(ArrayList<TextBox> rowData) {
		data.addAll(rowData);
		initDataRect();
		increaseTableHeight(rowData.get(0).getFontSize());
	}

	protected void initDataRect() {
		for (int j = 0; j < cols; j++) {
			TextBox d = data.get(currentDataRow * cols + j);
			int x = rect.x + j * cellWidth;
			int y = rect.y + (currentDataRow) * cellHeight;

			d.setPos(x, y);
		}
		currentDataRow++;
	}

	public void addHeaderRow(ArrayList<TextBox> headers) {
		this.headers = headers;
		initHeadersRect();
	}

	private void initHeadersRect() {
		for (int i = 0; i < cols; i++) {
			TextBox header = headers.get(i);
			int x = rect.x + i * cellWidth;
			int y = rect.y;

			header.setPos(x, y);
		}
	}

	private void increaseTableHeight(int amount) {
		rect.height += amount;
	}
}
