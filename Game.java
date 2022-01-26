import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class Game {

	public String randomCommand() {
		Random rand = new Random();
		char c1;
		String commandPC = "";
		for (int i = 0; i < 5; i++) {
			if (i == 0 || i == 3)
				c1 = (char) (rand.nextInt(8) + 65);
			else
				c1 = (char) (rand.nextInt(8) + 49);
			if (i != 2)
				commandPC += c1;
			else
				commandPC += " ";
		}
		return commandPC;
	}

	public boolean isValid(String cmd) {
		if (cmd.length() != 5 || (int) cmd.charAt(0) < 65 || (int) cmd.charAt(0) > 72 || (int) cmd.charAt(1) < 49
				|| (int) cmd.charAt(1) > 57 ||
				(int) cmd.charAt(3) < 65 || (int) cmd.charAt(3) > 72 || (int) cmd.charAt(4) < 49
				|| (int) cmd.charAt(4) > 57 || cmd.charAt(2) != ' ') {
			System.out.println("Errore nella scrittura del comando");
			return false;
		} else
			return true;
	}

	public boolean computeCommand(ChessBoard sca, String cmd, boolean col, boolean err) {
		if (!isValid(cmd))
			return false;
		int xi = ((int) cmd.charAt(0)) - 65; // -65 per ascii table A
		int yi = 8 - (((int) cmd.charAt(1)) - 48); // -49 per ascii table 0
		int xf = ((int) cmd.charAt(3)) - 65; // -65 per ascii table A
		int yf = 8 - (((int) cmd.charAt(4)) - 48); // -49 per ascii table 0

		PieceInterface temp = sca.getPedina(xi, yi);
		if (temp == null) {
			if (err)
				System.out.println("Non stai spostando alcuna pedina");
			return false;
		}
		if (temp.getColor() != col) {
			if (err)
				System.out.println("Stai spostando la pedina dell'altro colore.");
			return false;
		}
		try {
			// cattura en passant
			if (canEnPassant(sca, temp, xf, yf)) {
				sca.enPassant(temp, xf, yf);
			}
			// comando per arrocco (si indicano la posizione del re e della torre che si
			// vogliono spostare con l'arrocco)
			// le precondizioni vengono verificate anche nella funzione
			else if (xi == 4 && yi == 0 && yf == 0 && (xf == 0 || xf == 7)) // arrocco nero
			{
				sca.arrocco(xi, yi, xf, yf);
				System.out.println("Arrocco");
			} else if (xi == 4 && yi == 7 && yf == 7 && (xf == 0 || xf == 7)) // arrocco bianco
			{
				sca.arrocco(xi, yi, xf, yf);
				System.out.println("Arrocco");
			} else {
				sca.move(temp, xf, yf);
				// promozione
				if ((temp.getName() == 'p' && temp.getY() == 0) || (temp.getName() == 'P' && temp.getY() == 7)) {
					sca.promozione(temp, col, err);
				}
			}
		} catch (InvalidPosition e) {
			if (err)
				System.out.println("Mossa non consentita.");
			return false;
		} catch (InvalidMove e) {
			if (err)
				System.out.println("Mossa non consentita: giocatore sotto scacco.");
			return false;
		}

		return true;
	}

	public boolean canEnPassant(ChessBoard sca, PieceInterface p, int xf, int yf) {
		// controllo se destinazione e' occupata da un pedone
		PieceInterface target;
		if (!sca.isEmpty(xf, yf)) {
			target = sca.getPedina(xf, yf);
			// controlla che target sia un pedone
			if (target.getName() != 'p' || target.getName() != 'P')
				return false;
		} else {
			return false;
		}
		// controlla se destinazione e' occupata da un pedone
		if (p.getName() != 'p' || p.getName() != 'P')
			return false;

		if (p.getName() == 'p') // pedone bianco
		{
			if (p.getY() != 3)
				return false;
		} else // pedone nero
		{
			if (p.getY() != 4)
				return false;
		}

		// controlla se target ha eseguito solo un movimento (che e' di due caselle)
		if (target.getMoveCount() != 1)
			return false;

		// controlla se destinazione e' di fianco al pedone (unica da verificare)
		if (yf == p.getY() && (xf == p.getX() - 1 || xf == p.getX() + 1))
			return true;

		return false;
	}

	public void startPC() {
		System.out.println(
				"\nGiocatore Nero: Pedine con carattere maiuscolo;\nGiocatore Bianco: Pedine con carattere minuscolo.");
		ChessBoard board = new ChessBoard();
		System.out.println("Scacchiera iniziale: ");
		Main.printChessBoard(board);
		Random p = new Random();
		int pr = p.nextInt(2);
		boolean player;
		if (pr == 1)
			player = true;
		else
			player = false;

		Vector<String> cmd = new Vector<String>();

		Scanner cin = new Scanner(System.in);

		while (true) {
			// --------------------------- turno del bianco
			// ---------------------------------
			if (board.isScaccoBianco()) {
				System.out.println("Giocatore Bianco sotto scacco");
				// outputFile << "Giocatore sotto scacco\n";
			}
			if (board.isPatta(cmd) == 1) {
				System.out.println("Patta: Bianco in stallo");
				break;
			}

			System.out.println("Giocatore Bianco: ");
			String commandW;

			// Inserimento del comando attraverso std::istream affinchè non sia valido.
			if (player) {
				do {
					commandW = cin.nextLine();
					/* Main.clrscr(); */
					commandW = commandW.toUpperCase();
				} while (!computeCommand(board, commandW, player, player));
			} else {
				do {
					commandW = randomCommand();
					/* Main.clrscr(); */
					commandW = commandW.toUpperCase();
				} while (!computeCommand(board, commandW, !player, player));
				System.out.println(commandW);
			}
			cmd.add(commandW);

			// stampa scacchiera
			Main.printChessBoard(board);

			// controlla se la partita e'finita
			if (board.isScaccoMattoNero()) {
				System.out.println("Nero sotto scacco matto");
				break;
			}
			if (board.isPatta(cmd) == 3) {
				System.out.println("Patta: Mosse ripetute");
				break;
			}

			// --------------------------- turno del nero ---------------------------------
			// controlla se computer e' sotto scacco
			if (board.isScaccoNero()) {
				System.out.println("Nero sotto scacco");
				// outputFile << "Computer sotto scacco\n";
			}
			if (board.isPatta(cmd) == 2) {
				System.out.println("Patta: Nero in stallo");
				break;
			}

			System.out.println("Giocatore Nero: ");
			String commandB = "";

			// Estrazione casuale del comando da parte del PC affinchè non sia valido.
			if (!player) {
				do {
					commandB = cin.nextLine();
					/* Main.clrscr(); */
					commandB = commandB.toUpperCase();
				} while (!computeCommand(board, commandB, player, !player));
			} else {
				do {
					commandB = randomCommand();
					/* Main.clrscr(); */
					commandB = commandB.toUpperCase();
				} while (!computeCommand(board, commandB, !player, !player));
				System.out.println(commandB);
			}
			// stampa comando
			cmd.add(commandB);

			// stampa scacchiera
			Main.printChessBoard(board);

			// controlla se la partita e'finita
			if (board.isScaccoMattoBianco()) {
				System.out.println("Bianco sotto scacco matto");
				break;
			}
			if (board.isPatta(cmd) == 3) {
				System.out.println("Patta: Mosse ripetute");
				break;
			}
			// mosse--;
		}
		cin.close();
	}

	public void startCC() {
		// PC vs PC ha un massimo di mosse
		int mosseMax = 200;
		System.out.println("\nNumero massimo di mosse: " + mosseMax + " per giocatore");

		ChessBoard board = new ChessBoard();
		System.out.println("Scacchiera iniziale: ");
		Main.printChessBoard(board);

		// Apertura di file di log dove verranno salvate tutte le mosse.

		Vector<String> cmd = new Vector<String>();
		while (mosseMax > 0) {
			System.out.println("Mossa " + (51 - mosseMax) + "\n");
			if (board.isScaccoBianco()) {
				System.out.println("PC1 sotto scacco");
			}
			if (board.isPatta(cmd) == 1) {
				System.out.println("Patta: PC1 in stallo");
				break;
			}

			System.out.println("computer 1 (bianco): ");
			String commandPC1 = "";

			// Estrazione casuale del comando da parte del PC affinchè non sia valido.
			do {
				commandPC1 = randomCommand();
				/* Main.clrscr(); */
			} while (!computeCommand(board, commandPC1, true, false));

			// stampa il comando
			cmd.add(commandPC1);
			System.out.println(commandPC1);

			// stampa scacchiera
			Main.printChessBoard(board);

			// controlla se la partita e'finita
			if (board.isScaccoMattoNero()) {
				System.out.println("Computer 2 sotto scacco matto");
				break;
			}
			if (board.isPatta(cmd) == 3) {
				System.out.println("Patta: Mosse ripetute");
				break;
			}

			/*
			 * try {
			 * TimeUnit.SECONDS.sleep(1);
			 * } catch (InterruptedException e) {
			 * // TODO Auto-generated catch block
			 * e.printStackTrace();
			 * }
			 */

			// --------------------------- turno del nero ---------------------------------

			if (board.isScaccoNero()) {
				System.out.println("PC2 sotto scacco");
				// outputFile << "PC2 sotto scacco\n";
			}
			if (board.isPatta(cmd) == 2) {
				System.out.println("Patta: PC2 in stallo");
				break;
			}

			System.out.println("computer 2 (nero): ");
			String commandPC2 = "";

			// Estrazione casuale del comando da parte del PC affinchè non sia valido.
			do {
				commandPC2 = randomCommand();
				/* Main.clrscr(); */
			} while (!computeCommand(board, commandPC2, false, false));

			// stampa comando
			cmd.add(commandPC2);
			System.out.println(commandPC2);

			// stampa scacchiera
			Main.printChessBoard(board);

			// controlla se la partita e'finita
			if (board.isScaccoMattoBianco()) {
				System.out.println("Computer 1 sotto scacco matto");
				break;
			}
			if (board.isPatta(cmd) == 3) {
				System.out.println("Patta: Mosse ripetute");
				break;
			}

			/*
			 * try {
			 * TimeUnit.SECONDS.sleep(1);
			 * } catch (InterruptedException e) {
			 * // TODO Auto-generated catch block
			 * e.printStackTrace();
			 * }
			 */

			mosseMax--;
		}
		if (mosseMax == 0) {
			System.out.println("Raggiunto limite di mosse");
		}
	}

}
