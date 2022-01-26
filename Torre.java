
public class Torre extends Piece implements PieceInterface {

	public Torre(int x, int y, boolean color, char name) {
		super(x, y, color, name);
	}

	@Override
	public boolean checkPos(int destX, int destY, PieceInterface[][] matrice) {
		for (int n = 1; n < 8; n++) // sinistra
		{
			if (destY != getY()) // se riga diversa non puo' essere a sx o dx
				break;
			if (!checkBoundaries(getX() - n, getY())) // controlla se va fuori da scacchiera
				break;
			if (!emptyBox(matrice, getX() - n, destY) && (matrice[getX() - n][destY].getColor() == getColor()))
				break; // se c'e' pedina del suo stesso colore non puo' andare oltre
			if (!emptyBox(matrice, getX() - n, destY) && (matrice[getX() - n][destY].getColor() != getColor())) {
				if (destX == getX() - n)
					return true;
				break;
			}
			if (destX == getX() - n) // se non trova nessuna pedina
				return true;
		}
		for (int n = 1; n < 8; n++) // destra
		{
			if (destY != getY())
				break;
			if (!checkBoundaries(getX() + n, getY()))
				break;
			if (!emptyBox(matrice, getX() + n, destY) && (matrice[getX() + n][destY].getColor() == getColor()))
				break;
			if (!emptyBox(matrice, getX() + n, destY) && (matrice[getX() + n][destY].getColor() != getColor())) {
				if (destX == getX() + n)
					return true;
				break;
			}
			if (destX == getX() + n)
				return true;
		}
		for (int n = 1; n < 8; n++) // su
		{
			if (destX != getX()) // se colonna diversa non puo' essere su o giu
				break;
			if (!checkBoundaries(getX(), getY() - n))
				break;
			if (!emptyBox(matrice, destX, getY() - n) && (matrice[destX][getY() - n].getColor() == getColor()))
				break;
			if (!emptyBox(matrice, destX, getY() - n) && (matrice[destX][getY() - n].getColor() != getColor())) {
				if (destY == getY() - n)
					return true;
				break;
			}
			if (destY == getY() - n)
				return true;
		}
		for (int n = 1; n < 8; n++) // giu
		{
			if (destX != getX())
				break;
			if (!checkBoundaries(getX(), getY() + n))
				break;
			if (!emptyBox(matrice, destX, getY() + n) && (matrice[destX][getY() + n].getColor() == getColor()))
				break;
			if (!emptyBox(matrice, destX, getY() + n) && (matrice[destX][getY() + n].getColor() != getColor())) {
				if (destY == getY() + n)
					return true;
				break;
			}
			if (destY == getY() + n)
				return true;
		}
		return false;
	}

}
