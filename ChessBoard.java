import java.util.Scanner;
import java.util.Vector;

public class ChessBoard {
	private static int size = 8;
	private PieceInterface[][] matrice;

	public ChessBoard() {
		matrice = new PieceInterface[size][size];
		boolean gWhite = false; // colore pedine
		for (int i = 0; i < 8; i++) {
			if (i > 3) // meta' scacchiera
				gWhite = true;
			for (int j = 0; j < 8; j++) {
				if (i == 1) // fila di pedoni neri
				{
					matrice[j][i] = new Pedone(j, i, gWhite, 'p');
				} else if (i == 6) // fila di pedoni bianchi
				{
					matrice[j][i] = new Pedone(j, i, gWhite, 'p');
				} else if (i == 0) // fila con pedine nere
				{
					if (j == 0 || j == 7) {
						matrice[j][i] = new Torre(j, i, gWhite, 't');
					}
					if (j == 1 || j == 6) {
						matrice[j][i] = new Cavallo(j, i, gWhite, 'c');
					}
					if (j == 2 || j == 5) {
						matrice[j][i] = new Alfiere(j, i, gWhite, 'a');
					}
					if (j == 3) {
						matrice[j][i] = new Regina(j, i, gWhite, 'd');
					}
					if (j == 4) {
						matrice[j][i] = new Re(j, i, gWhite, 'r');
					}
				} else if (i == 7) // fila con pedine bianche
				{
					if (j == 0 || j == 7) {
						matrice[j][i] = new Torre(j, i, gWhite, 't');
					}
					if (j == 1 || j == 6) {
						matrice[j][i] = new Cavallo(j, i, gWhite, 'c');
					}
					if (j == 2 || j == 5) {
						matrice[j][i] = new Alfiere(j, i, gWhite, 'a');
					}
					if (j == 3) {
						matrice[j][i] = new Regina(j, i, gWhite, 'd');
					}
					if (j == 4) {
						matrice[j][i] = new Re(j, i, gWhite, 'r');
					}
				} else {
					matrice[j][i] = null;
				}
			}
		}
	}

	boolean isEmpty(int x, int y) {
		return matrice[x][y] == null;
	}

	public PieceInterface[][] getMatrice() {
		return matrice;
	}

	public PieceInterface getPedina(int ROWS, int COLS) {
		return matrice[ROWS][COLS];
	}

	public void changePiece(int x, int y, PieceInterface p) {
		matrice[x][y] = p;
	}

	public void setPtr(int x, int y) {
		matrice[x][y] = null;
	}

	public void move(PieceInterface p, int j, int i) {
		// controlla che casella (j,i) sia valida per p
		if (!(p.checkPos(j, i, matrice)))
			throw new InvalidPosition();

		// memorizza eventuale pedina in (j,i)
		PieceInterface temp = matrice[j][i];

		// salva coordinate di partenza
		int x = p.getX();
		int y = p.getY();

		// provo a spostare la pedina
		p.setPos(j, i); // aggiorna coordinate della pedina
		matrice[j][i] = p; // aggiorna matrice
		matrice[x][y] = null; // libera cella di partenza

		if (p.getColor()) // se e' bianca
		{
			if (isScaccoBianco()) {
				matrice[x][y] = p;
				matrice[j][i] = temp;
				p.setPos(x, y);
				throw new InvalidMove();
			}
		} else // e' nera
		{
			if (isScaccoNero()) {
				matrice[x][y] = p;
				matrice[j][i] = temp;
				p.setPos(x, y);
				throw new InvalidMove();
			}
		}
		p.increaseCount();
	}

	public boolean isScaccoNero() {
		// inizializzazione a indici non validi
		int reNeroX = -1, reNeroY = -1;

		// trova posizione re
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!isEmpty(j, i)) {
					if (getPedina(j, i).getName() == 'R') // re nero
					{
						reNeroX = j;
						reNeroY = i;
					}
				}
			}
		}

		// controlla se le altre pedine potrebbero muoversi nella casella del re (e
		// quindi catturarlo)
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!isEmpty(j, i)) {
					PieceInterface p = getPedina(j, i);

					if (p.getColor()) // se c'e' una pedina ed e' bianca
					{
						if (p.checkPos(reNeroX, reNeroY, matrice)) // scacco al nero
							return true;
					}
				}
			}
		}
		return false; // non scacco
	}

	/**
	 * @brief controlla se il giocatore bianco uno e' sotto scacco
	 * @return true se bianco e' sotto scacco
	 * @return false se bianco non e' sotto scacco
	 */
	boolean isScaccoBianco() {
		// inizializzazione a indici non validi
		int reBiancoX = -1, reBiancoY = -1;

		// trova posizione re
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!isEmpty(j, i)) {
					if (getPedina(j, i).getName() == 'r') // re bianco
					{
						reBiancoX = j;
						reBiancoY = i;
					}
				}
			}
		}

		// controlla se le altre pedine potrebbero muoversi nella casella del re (e
		// quindi catturarlo)
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!isEmpty(j, i)) {
					PieceInterface p = getPedina(j, i);

					if (!p.getColor()) // se c'e' una pedina ed e' nera
					{
						if (p.checkPos(reBiancoX, reBiancoY, matrice)) // scacco al bianco
							return true;
					}
				}
			}
		}
		return false; // non scacco
	}

	public void setPedina(PieceInterface p) {
		matrice[p.getX()][p.getY()] = p;
	}

	public void promozione(PieceInterface p, boolean player, boolean pl) {
		Scanner cin = new Scanner(System.in);
		if (p.getColor() == player && pl) // colore del giocatore e partita PC
		{
			System.out.println("\nPromozione del Pedone del Giocatore");
			System.out.println("Inserire la pedina con il quale vogliamo promuovere:");
			char name;
			do {
				name = (char) cin.nextInt();
			} while (name != 't' && name != 'c' && name != 'a' && name != 'd' && name != 'r');
			cin.close();
			int x = p.getX();
			int y = p.getY();
			PieceInterface temp;
			if (name == 't')
				temp = new Torre(x, y, player, name);
			if (name == 'c')
				temp = new Cavallo(x, y, player, name);
			if (name == 'a')
				temp = new Alfiere(x, y, player, name);
			if (name == 'd')
				temp = new Regina(x, y, player, name);
			if (name == 'r')
				temp = new Re(x, y, player, name);
			else
				return;
			changePiece(x, y, temp);
		} else if (p.getColor() == player) {
			System.out.println("\nPromozione del Pedone del Computer");
			int x = p.getX();
			int y = p.getY();
			PieceInterface temp = new Regina(x, y, player, 'd');
			changePiece(x, y, temp);
		} else {
			System.out.println("\nPromozione del Pedone del Computer");
			int x = p.getX();
			int y = p.getY();
			PieceInterface temp = new Regina(x, y, !player, 'd');
			changePiece(x, y, temp);
		}
	}

	public void enPassant(PieceInterface p, int xf, int yf) {
		int xi = p.getX();
		int yi = p.getY();
		p.setPos(xf, yf);
		matrice[xf][yf] = p;
		matrice[xi][yi] = null;
	}

	public void arrocco(int xi, int yi, int xf, int yf) {
		if (xi == 4 && yi == 0 && yf == 0) // arrocco del nero
		{
			if (xf == 0) // arrocco lungo
			{
				// verifica condizioni
				if (isScaccoNero()) // arrocco non si puo' fare se re e' sotto scacco
				{
					throw new InvalidPosition();
				}
				if (isEmpty(xi, yi) || isEmpty(xf, yf)) // se una delle due caselle e' vuota
				{
					throw new InvalidPosition();
				}
				PieceInterface re = getPedina(xi, yi);
				PieceInterface torre = getPedina(xf, yf);
				if (re.getMoveCount() != 0 || torre.getMoveCount() != 0) {
					throw new InvalidPosition();
				}
				if (!isEmpty(1, 0) || !isEmpty(2, 0) || !isEmpty(3, 0)) {
					throw new InvalidPosition();
				}
				// esegue arrocco
				move(re, 3, 0); // move controlla anche se re va sotto scacco (lancia InvalidPosition)
				move(re, 2, 0);
				torre.setPos(3, 0);
				setPedina(torre);
				setPtr(0, 0);
				return;
			} else if (xf == 7) // arrocco corto
			{
				// verifica condizioni
				if (isScaccoNero()) // arrocco non si puo' fare se re e' sotto scacco
				{
					throw new InvalidPosition();
				}
				if (isEmpty(xi, yi) || isEmpty(xf, yf)) // se una delle due caselle e' vuota
				{
					throw new InvalidPosition();
				}
				PieceInterface re = getPedina(xi, yi);
				PieceInterface torre = getPedina(xf, yf);
				if (re.getMoveCount() != 0 || torre.getMoveCount() != 0) {
					throw new InvalidPosition();
				}
				if (!isEmpty(5, 0) || !isEmpty(6, 0)) {
					throw new InvalidPosition();
				}
				// esegue arrocco
				move(re, 5, 0); // move controlla anche se re va sotto scacco (lancia InvalidPosition)
				move(re, 6, 0);
				torre.setPos(5, 0);
				setPedina(torre);
				setPtr(7, 0);
				return;
			} else // altre xf non valide
			{
				throw new InvalidPosition();
			}
		} else if (xi == 4 && yi == 7 && yf == 7) // arrocco del bianco
		{
			if (xf == 0) // arrocco lungo
			{
				// verifica condizioni
				if (isScaccoBianco()) // arrocco non si puo' fare se re e' sotto scacco
				{
					throw new InvalidPosition();
				}
				if (isEmpty(xi, yi) || isEmpty(xf, yf)) // se una delle due caselle e' vuota
				{
					throw new InvalidPosition();
				}
				PieceInterface re = getPedina(xi, yi);
				PieceInterface torre = getPedina(xf, yf);
				if (re.getMoveCount() != 0 || torre.getMoveCount() != 0) {
					throw new InvalidPosition();
				}
				if (!isEmpty(1, 7) || !isEmpty(2, 7) || !isEmpty(3, 7)) {
					throw new InvalidPosition();
				}
				// esegue arrocco
				move(re, 3, 7); // move controlla anche se re va sotto scacco (lancia InvalidPosition)
				move(re, 2, 7);
				torre.setPos(3, 7);
				setPedina(torre);
				setPtr(0, 7);
				return;
			} else if (xf == 7) // arrocco corto
			{
				// verifica condizioni
				if (isScaccoBianco()) // arrocco non si puo' fare se re e' sotto scacco
				{
					throw new InvalidPosition();
				}
				if (isEmpty(xi, yi) || isEmpty(xf, yf)) // se una delle due caselle e' vuota
				{
					throw new InvalidPosition();
				}
				PieceInterface re = getPedina(xi, yi);
				PieceInterface torre = getPedina(xf, yf);
				if (re.getMoveCount() != 0 || torre.getMoveCount() != 0) {
					throw new InvalidPosition();
				}
				if (!isEmpty(5, 7) || !isEmpty(6, 7)) {
					throw new InvalidPosition();
				}
				// esegue arrocco
				move(re, 5, 7); // move controlla anche se re va sotto scacco (lancia InvalidPosition)
				move(re, 6, 7);
				torre.setPos(5, 7);
				setPedina(torre);
				setPtr(7, 7);
				return;
			} else {
				throw new InvalidPosition();
			}
		} else // tutti i casi non validi
		{
			throw new InvalidPosition();
		}
	}

	public boolean isScaccoMattoNero() {
		if (isScaccoNero() && !neroHasValidPositions())
			return true;
		return false;
	}

	boolean isScaccoMattoBianco() {
		if (isScaccoBianco() && !biancoHasValidPositions())
			return true;
		return false;
	}

	int isPatta(Vector<String> cmd) {
		// patta per assenza di posizioni valide
		if (!isScaccoNero() && !neroHasValidPositions()) // se nero non e' scacco ma non ha pos valide
		{
			return 2; // nero in stallo
		}
		if (!isScaccoBianco() && !biancoHasValidPositions()) // se bianco non e' scacco ma non ha pos valide
		{
			return 1; // bianco in stallo
		}
		// patta per mosse ripetute
		try {
			for (int i = 0; i < cmd.size(); i++) // verifica se tre mosse sono ripetute
			{
				String s1 = cmd.elementAt(i);
				String s2 = cmd.elementAt(i + 1);

				if (s1 == cmd.elementAt(i + 4) && s1 == cmd.elementAt(i + 8) && s2 == cmd.elementAt(i + 5)
						&& s2 == cmd.elementAt(i + 9))
					return 3; // giocatori 1 e 2 in stallo
			}
		} catch (ArrayIndexOutOfBoundsException e) // se indice non valido non puo' essere patta (fine del vector)
		{
			return 0;
		}
		return 0;
	}

	public boolean neroHasValidPositions() {
		boolean neroHasPositions = false;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!isEmpty(j, i)) // c'e'una pedina in (j,i)
				{
					PieceInterface p = matrice[j][i];

					if (!p.getColor()) // se la pedina e' nera
					{
						// prova tutti i movimenti della pedina esaminata e vede se e' scacco
						for (int y = 0; y < 8; y++) {
							for (int x = 0; x < 8; x++) {
								// se la posizione e'valida per lo spostamento
								if (p.checkPos(x, y, matrice)) {
									PieceInterface temp = matrice[x][y];

									// se c'e' il re bianco non puo'provare a spostarsi(NON si puo' mangiare il re)
									if (temp != null && temp.getName() == 'r') {
									} else // non c'e' re bianco
									{
										// prova movimento (NON si puo fare move() perche mangerebbe le pedine)
										matrice[x][y] = p;
										matrice[j][i] = null;
										if (!isScaccoNero()) // se non e' scacco allora il nero ha almeno una posizione
																// valida
										{
											neroHasPositions = true;
										}
										// torna indietro
										matrice[j][i] = matrice[x][y];
										matrice[x][y] = temp;
									}
								}
							}
						}
					}
				}
			}
		}
		return neroHasPositions;
	}

	public boolean biancoHasValidPositions() {
		boolean biancoHasPositions = false;
		// esamina ogni pedina
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (!isEmpty(j, i)) {
					PieceInterface p = matrice[j][i];
					if (p.getColor()) // se la pedina e' bianca
					{
						// prova tutti i movimenti della pedina esaminata e vede se e' scacco
						for (int y = 0; y < 8; y++) {
							for (int x = 0; x < 8; x++) {
								// se la posizione e'valida per lo spostamento
								if (p.checkPos(x, y, matrice)) {
									PieceInterface temp = matrice[x][y];

									// se c'e' il re nero non puo'provare a spostarsi(NON si puo' mangiare il re)
									if (temp != null && temp.getName() == 'R') {
									} else // se non c'e' il re nero
									{
										// prova movimento (NON si puo fare move() perche mangerebbe le pedine)
										matrice[x][y] = p;
										matrice[j][i] = null;
										if (!isScaccoBianco()) // se non e' scacco allora il bianco ha almeno una
																// posizione valida
										{
											biancoHasPositions = true;
										}
										// torna indietro
										matrice[j][i] = matrice[x][y];
										matrice[x][y] = temp;
									}
								}
							}
						}
					}
				}
			}
		}
		return biancoHasPositions;
	}

}