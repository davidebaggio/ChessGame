
public class Cavallo extends Piece implements PieceInterface {

	public Cavallo(int x, int y, boolean color, char name) {
		super(x, y, color, name);
	}

	@Override
	public boolean checkPos(int destX, int destY, PieceInterface[][] matrice) {
		if (!emptyBox(matrice, destX, destY) && (matrice[destX][destY].getColor() == getColor()))
			return false;
		if (destX == getX() - 1 && (destY == getY() - 2 || destY == getY() + 2))
			return true;
		if (destX == getX() - 2 && (destY == getY() - 1 || destY == getY() + 1))
			return true;
		if (destX == getX() + 1 && (destY == getY() - 2 || destY == getY() + 2))
			return true;
		if (destX == getX() + 2 && (destY == getY() - 1 || destY == getY() + 1))
			return true;
		return false;
	}

}
