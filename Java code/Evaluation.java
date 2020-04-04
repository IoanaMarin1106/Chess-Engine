
public class Evaluation {
	public static int materialValue(Bitboard board, Piece.Color playerColor) {
		long playerPieces, vsPieces;

		if(playerColor == Piece.Color.WHITE) {
			playerPieces = board.getAllPieces(board.whitePieces);
			vsPieces = board.getAllPieces(board.blackPieces);
		} else {
			playerPieces = board.getAllPieces(board.blackPieces);
			vsPieces = board.getAllPieces(board.whitePieces);
		}

		int playerScore = 0, vsScore = 0;

		while(playerPieces != 0) {
			playerScore += ((playerPieces & 1) != 0) ? 1 : 0;
			playerPieces = (playerPieces >>> 1);
		}

		while(vsPieces != 0) {
			vsScore += ((vsPieces & 1) != 0) ? 1 : 0;
			vsPieces = (vsPieces >>> 1);
		}

		return (playerScore - vsScore);
	}

	public static int evaluate(Bitboard board, Piece.Color playerColor) {
		return materialValue(board, playerColor);
	}
}
