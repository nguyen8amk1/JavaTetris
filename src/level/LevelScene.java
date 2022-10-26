package level;

import java.awt.Color;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import game.Common;

public class LevelScene extends Scene {
	private String tetrominos[];

	private int field[];
	private int fieldWidth = Common.fieldWidth;
	private int fieldHeight = Common.fieldHeight;
	private int rw = Common.rw;
	private int rh = Common.rh;

	// game logic
	private int currentPiece = 0;
	private int currentRotation = 0;
	private int currentX = fieldWidth / 2;
	private int currentY = 0;

	private boolean gameOver = false;
	private int nextPiece;
	private int nextPieceX, nextPieceY;
	private int currentScore = 0;

	private int topScore = 1000;
	
	private String gameType;
	private String level;
	private String linesParam = "LINES-000";

	// color constants
	private final Color WALL_COLOR = new Color(139, 137, 137);
	private final Color I_COLOR = new Color(255, 0, 255);
	private final Color J_COLOR = new Color(255, 69, 0);
	private final Color L_COLOR = new Color(0, 0, 255);
	private final Color O_COLOR = new Color(255, 255, 0);
	private final Color S_COLOR = new Color(0, 255, 0);
	private final Color T_COLOR = new Color(155, 48, 255);
	private final Color Z_COLOR = new Color(255, 0, 0);
	private final Color REMOVED_COLOR = new Color(255, 185, 15);

	private float downTimeAccumulator = 0;

	private ArrayList<Integer> removeLines;

	private float down = 0;
	private int fieldOffsetX = 11 * rw, fieldOffsetY = 5 * rh;

	// boxes
	private int fontSizeBig = 30;
	private int fontSizeMid = 25;
	private Font boxFont = new Font("Serif", Font.BOLD, fontSizeMid);

	private Rectangle nextBlockRect = new Rectangle(fieldOffsetX + fieldWidth * rw, fieldOffsetY + 7 * rh, 6 * rw,
			7 * rh);

	private Rectangle levelBoxRect = new Rectangle(fieldOffsetX + fieldWidth * rw, fieldOffsetY + 14 * rh, 7 * rw,
			3 * rh);

	private Rectangle statisticBoxRect = new Rectangle((fieldWidth - (8 + 1)) * rw, 7 * rh, 8 * rw, 16 * rh);

	private Rectangle topAndScoreBoxRect = new Rectangle(fieldWidth * rw + fieldOffsetX, nextBlockRect.y - 10 * rh,
			7 * rw, 9 * rh);

	private Rectangle linesBoxRect = new Rectangle(fieldOffsetX, fieldOffsetY - 3 * rh, fieldWidth * rw, 3 * rh);

	private Rectangle typeBoxRect = new Rectangle((fieldWidth - (6 + 2)) * rw, fieldOffsetY - 3 * rh, 6 * rw, 3 * rh);

	// statistic parameters
	private int[] pieceCount= new int[7];
	
	private float holdTimeTilDown = 0;

	public LevelScene(GameSceneManager gsm, String type, String level) {
		super(gsm);

		// init tetrominos
		tetrominos = new String[7];
		tetrominos[0] = "..X...X...X...X.";
		tetrominos[1] = ("..X..XX...X.....");
		tetrominos[2] = (".....XX..XX.....");
		tetrominos[3] = ("..X..XX..X......");
		tetrominos[4] = (".X...XX...X.....");
		tetrominos[5] = (".X...X...XX.....");
		tetrominos[6] = ("..X...X..XX.....");

		gameType = type; 
		this.level = level;

		// init field
		field = new int[fieldWidth * fieldHeight];
		for (int y = 0; y < fieldHeight; y++) {
			for (int x = 0; x < fieldWidth; x++) {
				boolean onBottomLeftRight = x == 0 || x == fieldWidth - 1 || y == fieldHeight - 1;
				int num = 0;
				if (onBottomLeftRight) {
					num = 9;
				}
				field[y * fieldWidth + x] = num;
			}
		}

		removeLines = new ArrayList<Integer>();
		nextPiece = (new Random()).nextInt(tetrominos.length);
		pieceCount[currentPiece]++;
	}

	public void update(float dt) {
		handlePieceDownAction(dt);
		removeLines(dt);
	}

	public void render(Graphics g) {
		drawBackground(g);
		drawField(g);
		drawCurrentPiece(g);
		drawNextPiece(g);

		// draw boxes
		drawNextBlockBox(g);
		drawLevelBox(g);
		drawStatisticsBox(g);
		drawTopAndScoreBox(g);
		drawTypeBox(g);
		drawLinesBox(g);
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT) {
			if (doesPieceFit(currentPiece, currentRotation, currentX + 1, currentY)) {
				currentX++;
			}
		} else if (key == KeyEvent.VK_LEFT) {
			if (doesPieceFit(currentPiece, currentRotation, currentX - 1, currentY)) {
				currentX--;
			}
		}

		if (key == KeyEvent.VK_UP) {
			if (doesPieceFit(currentPiece, currentRotation + 1, currentX, currentY)) {
				currentRotation++;
			}
		} else if (key == KeyEvent.VK_DOWN) {
			if (down < 1)
				down += .3;
			if ((int) down >= 1) {
				if (doesPieceFit(currentPiece, currentRotation, currentX, currentY + (int) down)) {
					currentY += (int) down;
					holdTimeTilDown += .4f;
				}
				down = 0;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_DOWN)
			holdTimeTilDown = 0;
	}


	private void updateScore(float dt) {
		System.out.println(holdTimeTilDown);
		if(holdTimeTilDown > 0.0f) {
			System.out.println("larger than 0");
			if(holdTimeTilDown >= 1.0f) {
				currentScore+= 10;
			} else{
				currentScore+= 5;
			}
			holdTimeTilDown = 0;
		} 
	}

	private void removeLines(float dt) {
		if (!removeLines.isEmpty()) {
			for (int lineY : removeLines)
				for (int x = 1; x < fieldWidth - 1; x++) {
					for (int y = lineY; y > 0; y--)
						field[y * fieldWidth + x] = field[(y - 1) * fieldWidth + x];
					field[x] = 0;
				}
			removeLines.clear();
		}
	}

	private void handlePieceDownAction(float dt) {
		downTimeAccumulator += dt;
		float downTimeDelay = 0.8f;
		if (downTimeAccumulator >= downTimeDelay) {
			if (doesPieceFit(currentPiece, currentRotation, currentX, currentY + 1))
				currentY++;
			else {

				addPieceToField();

				checkLines();

				chooseNextPiece();

				pieceCount[currentPiece]++;

				updateScore(dt);

				gameOver = isGameOver();
				System.out.println(gameOver);
			}
			downTimeAccumulator = 0;
		}
	}

	private boolean isGameOver() {
		// if next piece not fit (means every is up on top) -> gameOver
		return !doesPieceFit(currentPiece, currentRotation, currentX, currentY);
	}

	private void chooseNextPiece() {
		currentX = fieldWidth / 2;
		currentY = 0;
		currentRotation = 0;
		currentPiece = nextPiece;
		nextPiece = (new Random()).nextInt(tetrominos.length);
	}

	private void checkLines() {
		for (int y = 0; y < 4; y++) {
			if (currentY + y < fieldHeight - 1) { // boundary check
				boolean thereIsALine = true;
				for (int x = 1; x < fieldWidth - 1; x++) { // not include the walls
					thereIsALine &= field[(currentY + y) * fieldWidth + x] != 0;
				}

				if (thereIsALine) {
					// remove the line
					for (int x = 1; x < fieldWidth - 1; x++)
						field[(currentY + y) * fieldWidth + x] = 8;

					removeLines.add(currentY + y);
				}
			}
		}

	}

	private void addPieceToField() {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (tetrominos[currentPiece].charAt(rotate(x, y, currentRotation)) != '.') {
					int index = (currentX + x) + (currentY + y) * fieldWidth;
					field[index] = currentPiece + 1;
				}
			}
		}
	}

	private void drawLinesBox(Graphics g) {
		int fontSize = boxFont.getSize();
		int strX = linesBoxRect.x + 20;
		int strY = linesBoxRect.y + fontSize;
		g.setFont(boxFont);
		g.setColor(Color.WHITE);
		g.drawString(linesParam, strX, strY);

		g.setColor(Color.CYAN);
		g.drawRect(linesBoxRect.x, linesBoxRect.y, linesBoxRect.width, linesBoxRect.height);
	}

	private void drawTypeBox(Graphics g) {
		int fontSize = boxFont.getSize();
		int strX = typeBoxRect.x + 20;
		int strY = typeBoxRect.y + fontSize;
		g.setFont(boxFont);
		g.setColor(Color.WHITE);
		g.drawString(gameType, strX, strY);

		g.setColor(Color.CYAN);
		g.drawRect(typeBoxRect.x, typeBoxRect.y, typeBoxRect.width, typeBoxRect.height);
	}

	private void drawTopAndScoreBox(Graphics g) {
		int gap = 20;
		int fontSize = boxFont.getSize();
		int topStrX = topAndScoreBoxRect.x + 20;
		int topStrY = topAndScoreBoxRect.y + fontSize + gap;
		int topScoreX = topStrX;
		int topScoreY = topStrY + fontSize;
		int scoreStrX = topStrX;
		int scoreStrY = topScoreY + fontSize + gap;
		int scoreX = scoreStrX;
		int scoreY = scoreStrY + fontSize;
		g.setFont(boxFont);
		g.setColor(Color.WHITE);

		g.drawString("TOP", topStrX, topStrY);
		g.drawString("0" + topScore, topScoreX, topScoreY);
		g.drawString("SCORE", scoreStrX, scoreStrY);
		g.drawString("00000" + currentScore, scoreX, scoreY);

		g.setColor(Color.CYAN);
		g.drawRect(topAndScoreBoxRect.x, topAndScoreBoxRect.y, topAndScoreBoxRect.width, topAndScoreBoxRect.height);
	}

	private void drawStatisticsBox(Graphics g) {

		int fontSize = boxFont.getSize();
		int strX = statisticBoxRect.x + 20;
		int strY = statisticBoxRect.y + fontSize;
		g.setFont(boxFont);
		g.setColor(Color.WHITE);
		g.drawString("STATISTIC", strX, strY);

		g.setColor(Color.CYAN);
		g.drawRect(statisticBoxRect.x, statisticBoxRect.y, statisticBoxRect.width, statisticBoxRect.height);

		// draw shape 
		int shapeX = statisticBoxRect.x + (int)(.1*statisticBoxRect.width);
		int shapeY = statisticBoxRect.y + (int)(fontSize*1.5);
		int smallRw = 10;
		int smallRh = 10;
		int gap = 4*smallRh;
		for(int piece = 0; piece < tetrominos.length; piece++) {
			drawPiecePixelWise(piece, 1, shapeX, shapeY, g, smallRw, smallRh);
			shapeY+= gap;
		}
		
		// draw numbers
		int numStartX = shapeX + 7*smallRw;
		int numStartY = statisticBoxRect.y + (int)(fontSize*2.8);
		g.setFont(boxFont);
		g.setColor(Color.WHITE);

		for(int i = 0; i < pieceCount.length; i++ ) {
			g.drawString("00" + pieceCount[i], numStartX, numStartY + i*gap);
		}
	}

	private void drawBackground(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(0, 0, Common.gameWidth, Common.gameHeight);
	}

	private void drawLevelBox(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawRect(levelBoxRect.x, levelBoxRect.y, levelBoxRect.width, levelBoxRect.height);

		int fontSize = boxFont.getSize();
		int levelStrX = levelBoxRect.x + 0 + fontSize;
		int levelStrY = levelBoxRect.y + 0 + fontSize;
		int levelNumX = levelStrX + 0 + fontSize;
		int levelNumY = levelStrY + 0 + fontSize;

		g.setFont(boxFont);
		g.setColor(Color.WHITE);
		g.drawString("LEVEL", levelStrX, levelStrY);
		g.drawString("00" + level, levelNumX, levelNumY);
	}

	private void drawNextBlockBox(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawRect(nextBlockRect.x, nextBlockRect.y, nextBlockRect.width, nextBlockRect.height);
		int fontSize = boxFont.getSize();
		int strX = nextBlockRect.x + 0 + 15;
		int strY = nextBlockRect.y + 0 + fontSize;

		g.setFont(boxFont);
		g.setColor(Color.WHITE);
		g.drawString("NEXT", strX, strY);
	}

	private void drawNextPiece(Graphics g) {
		nextPieceX = nextBlockRect.x + 25;
		nextPieceY = nextBlockRect.y + 50;

		drawPiecePixelWise(nextPiece, 0, nextPieceX, nextPieceY, g);
	}

	private void drawCurrentPiece(Graphics g) {
		drawPieceOnField(currentPiece, currentRotation, currentX, currentY, g);
	}

	private void drawPieceOnField(int piece, int rotation, int nx, int ny, Graphics g) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				boolean isValid = tetrominos[piece].charAt(rotate(x, y, rotation)) != '.';
				if (isValid) {
					int rx = (nx + x) * rw + fieldOffsetX;
					int ry = (ny + y) * rh + fieldOffsetY;
					g.setColor(pickColorForPieces(piece));
					// draw bordered box
					g.fillRect(rx, ry, rw, rh);
					g.setColor(Color.black);
					g.drawRect(rx, ry, rw, rh);
				}
			}
		}
	}


	private void drawPiecePixelWise(int piece, int rotation, int nx, int ny, Graphics g, int sizeX, int sizeY) {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if (tetrominos[piece].charAt(rotate(x, y, rotation)) != '.') {
					int rx = (nx + x * sizeX);
					int ry = (ny + y * sizeY);
					g.setColor(pickColorForPieces(piece));

					// draw bordered box
					g.fillRect(rx, ry, sizeX, sizeY);
					g.setColor(Color.black);
					g.drawRect(rx, ry, sizeX, sizeY);
				}
			}
		}
	}

	private void drawPiecePixelWise(int piece, int rotation, int nx, int ny, Graphics g) {
		drawPiecePixelWise(piece, rotation, nx, ny, g, rw, rh);
	}

	private void drawField(Graphics g) {
		for (int y = 0; y < fieldHeight; y++) {
			for (int x = 0; x < fieldWidth; x++) {
				int num = field[y * fieldWidth + x];
				int rx = x * rw + fieldOffsetX;
				int ry = y * rh + fieldOffsetY;

				if (num != 0) {
					if (num == 9) {
						g.setColor(WALL_COLOR);
					} else if (num == 1)
						g.setColor(I_COLOR);
					else if (num == 2)
						g.setColor(J_COLOR);
					else if (num == 3)
						g.setColor(L_COLOR);
					else if (num == 4)
						g.setColor(O_COLOR);
					else if (num == 5)
						g.setColor(S_COLOR);
					else if (num == 6)
						g.setColor(T_COLOR);
					else if (num == 7)
						g.setColor(Z_COLOR);
					else if (num == 8)
						g.setColor(REMOVED_COLOR);

					// draw bordered box
					g.fillRect(rx, ry, rw, rh);
					g.setColor(Color.black);
					g.drawRect(rx, ry, rw, rh);
				}
			}
		}
	}

	private int rotate(int px, int py, int r) {
		int pi = 0;
		switch (r %= 4) {
		case 0:
			pi = py * 4 + px;
			break;
		case 1:
			pi = 12 + py - (px * 4);
			break;
		case 2:
			pi = 15 - (py * 4) - px;
			break;
		case 3:
			pi = 3 - py + (px * 4);
			break;
		}
		return pi;
	}

	private boolean doesPieceFit(int piece, int rotation, int nPosX, int nPosY) {
		for (int py = 0; py < 4; py++) {
			for (int px = 0; px < 4; px++) {
				int pi = rotate(px, py, rotation);
				int fi = (nPosX + px) + (nPosY + py) * fieldWidth;
				boolean inBoundX = nPosX + px >= 0 && nPosX + px < fieldWidth;
				boolean inBoundY = nPosY + py >= 0 && nPosY + py < fieldHeight;

				if (inBoundX && inBoundY) {
					// do check
					char currentTetrominoChar = tetrominos[piece].charAt(pi);
					int fieldNum = field[fi];
					boolean hit = currentTetrominoChar != '.' && fieldNum != 0;
					if (hit)
						return false;
				}
			}
		}
		return true;
	}

	private Color pickColorForPieces(int index) {
		Color color;
		switch (index) {
		case 0:
			color = (I_COLOR);
			break;
		case 1:
			color = (J_COLOR);
			break;
		case 2:
			color = (L_COLOR);
			break;
		case 3:
			color = (O_COLOR);
			break;
		case 4:
			color = (S_COLOR);
			break;
		case 5:
			color = (T_COLOR);
			break;
		case 6:
			color = (Z_COLOR);
			break;
		default: 
			color = Color.magenta;
			break;
		}
		return color;
	}

	@Override
	protected void loadResources() throws IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void toNextScene() {
	}
	
}
