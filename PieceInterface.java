public interface PieceInterface {

	public boolean checkPos(int destX, int destY, PieceInterface[][] matrice);

	public int getX();

	public int getY();

	public void setPos(int x, int y);

	public boolean getColor();

	public void setName(char name);

	public char getName();

	public int getMoveCount();

	public void increaseCount();

	public boolean emptyBox(PieceInterface matrice[][], int x, int y);

	public boolean checkBoundaries(int j, int i);
}
