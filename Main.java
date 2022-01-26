
public class Main {
	public static void main(String[] args) {

		Game game = new Game();

		if (args.length < 1) {
			System.out.print(
					"[Error] Not enough arguments. Type:\n\t pc: to play vs computer.\n\t cc: to watch a match computer vs computer.\n");
			return;
		} else if (args[0].equals("pc") && args[0].equals("cc")) {
			System.out.print(
					"[Error] Incorrect arguments. Type: \n\t pc: to play vs computer.\n\t cc: to watch a match computer vs computer.\n");
			return;
		} else if (args[0].equals("pc")) {
			System.out.print("Partita giocatore (Bianco) vs computer (Nero)");
			System.out.print(
					"Per giocare inserire la posizione di cella iniziale e finale separati da uno spazio. Es: A2 C3");

			game.startPC();

		} else {
			System.out.print("Partita computer vs computer");

			game.startCC();

		}

	}

	public static void printChessBoard(ChessBoard board) {
		for (int i = 0; i < 8; i++) {
			System.out.print(8 - i + "  ");
			for (int j = 0; j < 8; j++) {
				printPiece(board.getMatrice()[j][i]);
			}
			System.out.print("\n");
		}
		System.out.print("\n   ABCDEFGH\n\n");
	}

	public static void printPiece(PieceInterface piece) {
		if (piece == null)
			System.out.print(" ");
		else
			System.out.print(piece.getName());
	}
}