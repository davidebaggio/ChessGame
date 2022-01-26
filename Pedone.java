
public class Pedone extends Piece implements PieceInterface {

	public Pedone(int x, int y, boolean color, char name) {
		super(x, y, color, name);
	}

	@Override
	public boolean checkPos(int destX, int destY, PieceInterface[][] matrice) {
		if (getColor()) {
			if (destX == getX() && (destY == getY() - 1)) // se avanti di 1
			{
				if (!emptyBox(matrice, destX, destY)) // se c'e' una pedina non puo'
					return false;
				return true;
			}
			if ((getMoveCount() == 0) && destX == getX() && destY == getY() - 2) // se prima mossa e avanti di 2
			{
				if (!emptyBox(matrice, destX, destY) || !emptyBox(matrice, destX, getY() - 1))
					return false; // se c'e' una pedina nella prima o seconda cella non puo'
				return true;
			}
			if ((destY == getY() - 1 && (destX == getX() - 1 || destX == getX() + 1))) // se nelle due celle diagonali
			{
				if (!emptyBox(matrice, destX, destY) && !(matrice[destX][destY].getColor())) // solo se c'e' una pedina
																								// nera
					return true;
				return false;
			}
		} else {
			if (destX == getX() && (destY == getY() + 1)) // se avanti di 1
			{
				if (!emptyBox(matrice, destX, destY)) // se c'e' una pedina non puo'
					return false;
				return true;
			}
			if ((getMoveCount() == 0) && destX == getX() && destY == getY() + 2) // se prima mossa e avanti di 2
			{
				if (!emptyBox(matrice, destX, destY) || !emptyBox(matrice, destX, getY() + 1))
					return false; // se c'e' una pedina nella prima o seconda cella non puo'
				return true;
			}
			if ((destY == getY() + 1 && (destX == getX() - 1 || destX == getX() + 1))) // se nelle due celle diagonali
			{
				if (!emptyBox(matrice, destX, destY) && matrice[destX][destY].getColor()) // solo se c'e' una pedina
																							// bianca
					return true;
				return false;
			}
		}
		return false;
	}
}
