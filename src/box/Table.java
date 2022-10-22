package box;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Table extends Box {
	private ArrayList<TextBox> headers; 
	private ArrayList<ArrayList<TextBox>> data; 

	private int rows, cols;
	private int cellWidth, cellHeight; 

	public Table(int x, int y, int rows, int cols, int cellWidth, int cellHeight) {
		super(x, y, cols*cellWidth, rows*cellHeight);

		headers = new ArrayList<TextBox>();
		data = new ArrayList<ArrayList<TextBox>>();
		this.rows = rows; 
		this.cols = cols;

		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
	}

	public void render(Graphics g) {
		// show headers 
		for(int i = 0; i < cols; i++) {
			TextBox header = headers.get(i);
			int x = rect.x + i*cellWidth; 
			int y = rect.y;

			// TODO: this has code smells - d.setRect() but TextBox setRect is not used 
			header.setPos(x, y);
			drawCell(g, x, y, header);
		}

		// show data
		for(int i = 1; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				TextBox d = data.get(i-1).get(j);
				int x = rect.x + j*cellWidth; 
				int y = rect.y + i*cellHeight; 
				// TODO: this has code smells - d.setRect() but TextBox setRect is not used 
				d.setPos(x, y);
				drawCell(g, x, y, d);
			}
		}
	}

	private void drawCell(Graphics g, int x, int y, TextBox header) {
		g.setColor(Color.CYAN);
		g.drawRect(x, y, cellWidth, cellHeight);
		header.render(g);
	}

	public void addDataRow(ArrayList<TextBox> rowData) {
		data.add(rowData);
		increaseTableHeight(rowData.get(0).getFontSize());
	}

	public void addHeaderRow(ArrayList<TextBox> headers) {
		this.headers = headers; 
	}

	private void increaseTableHeight(int amount) {
		rect.height += amount; 
	}
}
