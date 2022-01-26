
public class Piece {

	private int x, y, moveCount;
	private boolean color;
	private char name;

	public Piece(int x, int y, boolean color, char name) {
		setPos(x, y);
		this.color = color;
		setName(name);
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean getColor() {
		return this.color;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getMoveCount() {
		return this.moveCount;
	}

	public char getName() {
		return this.name;
	}

	public void setName(char name) {
		if (!this.color)
			this.name = Character.toUpperCase(name);
		else
			this.name = name;
	}

	public void increaseCount() {
		this.moveCount++;
	}

	public boolean emptyBox(PieceInterface matrice[][], int x, int y) {
		return matrice[x][y] == null;
	}

	public boolean checkBoundaries(int j, int i) {
		return (i >= 0 && i <= 7 && j >= 0 && j <= 7);
	}
}
