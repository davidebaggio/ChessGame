
public class Re extends Piece implements PieceInterface {

	public Re(int x, int y, boolean color, char name) {
		super(x, y, color, name);
	}

	@Override
	public boolean checkPos(int destX, int destY, PieceInterface[][] matrice) {
		if (destX == getX() && (destY == getY() - 1 || destY == getY() + 1)) {
			if (!emptyBox(matrice, destX, destY) && (matrice[destX][destY].getColor() == getColor()))
				return false;
			return true;
		}
		if (destX == getX() - 1 && (destY == getY() || destY == getY() - 1 || destY == getY() + 1)) {
			if (!emptyBox(matrice, destX, destY) && (matrice[destX][destY].getColor() == getColor()))
				return false;
			return true;
		}
		if (destX == getX() + 1 && (destY == getY() || destY == getY() - 1 || destY == getY() + 1)) {
			if (!emptyBox(matrice, destX, destY) && (matrice[destX][destY].getColor() == getColor()))
				return false;
			return true;
		}
		return false;
	}

}
