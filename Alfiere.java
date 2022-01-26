
public class Alfiere extends Piece implements PieceInterface {

	public Alfiere(int x, int y, boolean color, char name) {
		super(x, y, color, name);
	}

	@Override
	public boolean checkPos(int destX, int destY, PieceInterface[][] matrice) {
		for (int n = 1; n < 8; n++) // diagonale nord-ovest
		{
			if (!checkBoundaries(getX() - n, getY() - n))
				break;
			if (!emptyBox(matrice, getX() - n, getY() - n) && matrice[getX() - n][getY() - n].getColor() == getColor())
				break; // se c'e' una pedina ed e' del suo colore non va oltre
			if (!emptyBox(matrice, getX() - n, getY() - n)
					&& matrice[getX() - n][getY() - n].getColor() != getColor()) {
				if (destX == getX() - n && destY == getY() - n)
					return true;
				break;
			}
			if (destX == getX() - n && destY == getY() - n)
				return true;
		}
		for (int n = 1; n < 8; n++) // diagonale sud-ovest
		{
			if (!checkBoundaries(getX() - n, getY() + n))
				break;
			if (!emptyBox(matrice, getX() - n, getY() + n)
					&& (matrice[getX() - n][getY() + n].getColor() == getColor()))
				break;
			if (!emptyBox(matrice, getX() - n, getY() + n)
					&& matrice[getX() - n][getY() + n].getColor() != getColor()) {
				if (destX == getX() - n && destY == getY() + n)
					return true;
				break;
			}
			if (destX == getX() - n && destY == getY() + n)
				return true;
		}
		for (int n = 1; n < 8; n++) // diagonale sud-est
		{
			if (!checkBoundaries(getX() + n, getY() + n))
				break;
			if (!emptyBox(matrice, getX() + n, getY() + n)
					&& (matrice[getX() + n][getY() + n].getColor() == getColor()))
				break;
			if (!emptyBox(matrice, getX() + n, getY() + n)
					&& matrice[getX() + n][getY() + n].getColor() != getColor()) {
				if (destX == getX() + n && destY == getY() + n)
					return true;
				break;
			}
			if (destX == getX() + n && destY == getY() + n)
				return true;
		}
		for (int n = 1; n < 8; n++) // diagonale nord-est
		{
			if (!checkBoundaries(getX() + n, getY() - n))
				break;
			if (!emptyBox(matrice, getX() + n, getY() - n)
					&& (matrice[getX() + n][getY() - n].getColor() == getColor()))
				break;
			if (!emptyBox(matrice, getX() + n, getY() - n)
					&& matrice[getX() + n][getY() - n].getColor() != getColor()) {
				if (destX == getX() + n && destY == getY() - n)
					return true;
				break;
			}
			if (destX == getX() + n && destY == getY() - n)
				return true;
		}
		return false;
	}

}
