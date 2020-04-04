
public class Evaluation {
	
	public final static int[] pieceValue = {200, 9, 5, 3, 3, 1};

	public static int materialValue(Bitboard board, Piece.Color playerColor) {
		int[] playerPieces, vsPieces;

		if(playerColor == Piece.Color.WHITE) {
			playerPieces = board.remainingWhitePieces;
			vsPieces = board.remainingBlackPieces;
		} else {
			playerPieces = board.remainingBlackPieces;
			vsPieces = board.remainingWhitePieces;
		}

		int playerScore = 0, vsScore = 0;

		for(int i = 0; i < 6; i++) {
			playerScore += (pieceValue[i] * playerPieces[i]);
			vsScore += (pieceValue[i] * vsPieces[i]);
		}		

		return (playerScore - vsScore);
	}

	public static int evaluate(Bitboard board, Piece.Color playerColor) {
		return materialValue(board, playerColor);
	}
}
