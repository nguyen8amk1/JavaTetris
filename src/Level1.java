import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Level1 {
	private String tetrominos[];

	private int field[];
	private int fieldWidth = 12; 
	private int fieldHeight = 18;
	private int rw = 20;
	private int rh = 20;

	
	// game logic 
	private int currentPiece = 0;
	private int currentRotation = 0;
	private int currentX = fieldWidth/2;
	private int currentY = 0;

	private boolean gameOver = false;
	
	// color constants 
    private final Color WALL_COLOR = new Color (139,137,137);
    private final Color I_COLOR = new Color(255, 0, 255);
    private final Color J_COLOR = new Color(255,69,0);
    private final Color L_COLOR = new Color(0, 0, 255);
    private final Color O_COLOR = new Color(255, 255, 0);
    private final Color S_COLOR = new Color(0, 255, 0);
    private final Color T_COLOR = new Color(155,48,255);
    private final Color Z_COLOR = new Color(255, 0, 0);
    private final Color REMOVED_COLOR = new Color(255,185,15);

	private float downTimeAccumulator = 0;

	private ArrayList<Integer> removeLines;

	private float down = 0;
	
	public Level1() {
		
		// init tetrominos 
		tetrominos = new String[7];
		tetrominos[0] = "..X...X...X...X.";
		tetrominos[1] = ("..X..XX...X.....");
		tetrominos[2] = (".....XX..XX.....");
		tetrominos[3] = ("..X..XX..X......");
		tetrominos[4] = (".X...XX...X.....");
		tetrominos[5] = (".X...X...XX.....");
		tetrominos[6] = ("..X...X..XX.....");

		// init field 
		field = new int[fieldWidth*fieldHeight];
		for(int y = 0; y < fieldHeight; y++) {
		    for(int x = 0; x < fieldWidth; x++) {
		        boolean onBottomLeftRight = x == 0 || x == fieldWidth - 1 || y == fieldHeight - 1;
		        int num = 0;
		        if(onBottomLeftRight) {
		            num = 9;
		        }
		        field[y*fieldWidth + x] = num;
		    }
		}
		removeLines = new ArrayList<Integer>();
	}

	public void update(float dt) {
		handlePieceDownAction(dt);
		removeLines();
	}

	private void removeLines() {
	    if(!removeLines.isEmpty()) {
	        for (int lineY : removeLines) 
	            for (int x  = 1; x < fieldWidth - 1; x++) {
	                for (int y = lineY; y > 0; y--)
	                    field[y * fieldWidth + x] = field[(y - 1)*fieldWidth + x];
	                field[x] = 0;
	            }
			removeLines.clear();
	    }
	}

	private void handlePieceDownAction(float dt) {
	    downTimeAccumulator += dt;
	    float downTimeDelay = 1;
		if(downTimeAccumulator >= downTimeDelay) {
			if(doesPieceFit(currentPiece, currentRotation, currentX, currentY + 1)) 
				currentY++;
			else {
				
				addPieceToField();

				checkLines();

				chooseNextPiece();

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
		currentX = fieldWidth/2;
		currentY = 0;
		currentRotation = 0;
		currentPiece = (new Random()).nextInt(tetrominos.length);
	}

	private void checkLines() {
		for (int y = 0; y < 4; y++) {
			if(currentY + y < fieldHeight - 1) { // boundary check 
				boolean thereIsALine = true;
				for(int x  = 1; x <  fieldWidth - 1; x++) { // not include the walls 
					thereIsALine &= field[(currentY + y) * fieldWidth + x] != 0;
				}

				if(thereIsALine) {
					// remove the line
					for(int x = 1; x < fieldWidth - 1; x++) 
						field[(currentY + y) * fieldWidth + x] = 8;

					removeLines.add(currentY + y);
				}
			}
		}

	}

	private void addPieceToField() {
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				if(tetrominos[currentPiece].charAt(rotate(x, y, currentRotation)) != '.') {
					int index = (currentX + x) + (currentY + y) * fieldWidth;
					field[index] = currentPiece + 1;
				}
			}
		}
	}

	public void render(Graphics g) {
		drawField(g);
		drawCurrentPiece(g);
	}

	private void drawCurrentPiece(Graphics g) {
	    // Draw current piece
	    for(int y = 0; y < 4; y++) {  
	        for(int x = 0; x < 4; x++) { 
	            boolean isValid = tetrominos[currentPiece].charAt(rotate(x, y, currentRotation)) != '.';
	            if(isValid) {
	                int rx = (currentX + x) * rw;
	                int ry = (currentY + y) * rh;
	                switch(currentPiece) {
						case 0: 
							g.setColor(I_COLOR);
							break;
						case 1: 
							g.setColor(J_COLOR);
							break;
						case 2: 
							g.setColor(L_COLOR);
							break;
						case 3: 
							g.setColor(O_COLOR);
							break;
						case 4: 
							g.setColor(S_COLOR);
							break;
						case 5: 
							g.setColor(T_COLOR);
							break;
						case 6: 
							g.setColor(Z_COLOR);
							break;
	                }
					// draw bordered box
					g.fillRect(rx, ry, rw, rh);
					g.setColor(Color.black);
					g.drawRect(rx, ry, rw, rh);
	            }
	        }
	    }
	}

	private void drawField(Graphics g) {
		for(int y = 0; y < fieldHeight; y++) {
		    for(int x = 0; x < fieldWidth; x++) {
				int num = field[y * fieldWidth + x];
				int rx = x * rw;
				int ry = y * rh;

				if(num != 0) {
					if(num == 9) {
						g.setColor( WALL_COLOR);
					}
					else if(num == 1) 
						g.setColor( I_COLOR);
					else if(num == 2) 
						g.setColor( J_COLOR);
					else if(num == 3) 
						g.setColor( L_COLOR);
					else if(num == 4)
						g.setColor( O_COLOR);
					else if(num == 5) 
						g.setColor( S_COLOR);
					else if(num == 6) 
						g.setColor( T_COLOR);
					else if(num == 7) 
						g.setColor( Z_COLOR);
					else if(num == 8) 
						g.setColor( REMOVED_COLOR);

					// draw bordered box
					g.fillRect(rx, ry, rw, rh);
					g.setColor(Color.black);
					g.drawRect(rx, ry, rw, rh);
				}
		    }
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_RIGHT) {
			if(doesPieceFit(currentPiece, currentRotation, currentX + 1, currentY)) {
				currentX++;
			}
		} else if(key == KeyEvent.VK_LEFT) {
			if(doesPieceFit(currentPiece, currentRotation, currentX - 1, currentY)) {
				currentX--;
			}
		}

		if(key == KeyEvent.VK_UP) {
			if(doesPieceFit(currentPiece, currentRotation+1, currentX, currentY)) {
				currentRotation++;
			}
		} else if(key == KeyEvent.VK_DOWN) {
			if(down < 1)
				down += .3;
			if((int)down >= 1) {
				if(doesPieceFit(currentPiece, currentRotation, currentX, currentY + (int)down))
					currentY += (int)down;
				down = 0;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}

	private int rotate(int px, int py, int r) {
		int pi = 0;
		switch(r %= 4) {
		
			case 0:
				pi = py * 4 + px;
				break;
			case 1:
				pi =  12 + py - (px * 4);
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
		for(int py = 0; py < 4; py++) {
			for (int px = 0; px <  4; px++) {
				int pi = rotate(px, py, rotation);
				int fi = (nPosX + px) + (nPosY+py) * fieldWidth;
				boolean inBoundX = nPosX + px >= 0 && nPosX + px < fieldWidth;
				boolean inBoundY = nPosY + py >= 0 && nPosY + py < fieldHeight; 

				if(inBoundX && inBoundY) {
					// do check 
					char currentTetrominoChar = tetrominos[piece].charAt(pi); 
					int fieldNum = field[fi];
					boolean hit = currentTetrominoChar != '.' && fieldNum != 0;
					if(hit)
						return false;
				}
			}
		}
		return true;
	}
}
