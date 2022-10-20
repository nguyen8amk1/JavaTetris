package box;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class BoxOption extends Box {
	private String content;
	private int fontSize; 

	public BoxOption(String content, int x, int y, int fontSize) {
		super(x, y, content.length()*fontSize, fontSize);
		this.content = content;
		this.fontSize = fontSize;
	}

	public BoxOption(String content, int fontSize) {
		this(content, 0, 0, fontSize);
	}

	public void render(Graphics g) {
		g.setFont(new Font("Serif", Font.BOLD, fontSize));
		g.setColor(Color.WHITE);

		g.drawString(content, rect.x, rect.y);
	}
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
}
