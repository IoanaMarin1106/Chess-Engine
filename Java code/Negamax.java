import java.util.*;

public class Negamax {
	public static int negamax(
		Bitboard board, Piece.Color playerColor,
		int alpha, int beta, int depth) {


		if(depth == 0 || board.gameOver()) {
			return Evaluation.evaluate(board, playerColor);
		}

		ArrayList<long[]> moves = board.generateAllMoves(playerColor);
		Piece.Color vsColor = (playerColor == Piece.Color.WHITE) ?
							Piece.Color.BLACK : Piece.Color.WHITE;

		for(long[] move : moves) {
			Bitboard newState = (Bitboard)board.clone();

			newState.makeMove(move, playerColor);

			if(newState.isCheck(playerColor)) {
				continue;
			}

			int score = -negamax(newState, vsColor, -beta, -alpha, depth - 1);

			if(score >= alpha) {
				alpha = score;
			}

			if(alpha >= beta) {
				break;
			}
		}

		return alpha;
	}

	public static long[] negamaxHandler(Bitboard board, Piece.Color playerColor) {
		ArrayList<long[]> moves = board.generateAllMoves(playerColor);
		Piece.Color vsColor = (playerColor == Piece.Color.WHITE) ?
							Piece.Color.BLACK : Piece.Color.WHITE;

		long[] bestMove = null;
		int alpha = Integer.MIN_VALUE, beta = Integer.MAX_VALUE;

		for(long[] move : moves) {
			Bitboard newState = (Bitboard)board.clone();
			newState.makeMove(move, playerColor);

			if(newState.isCheck(playerColor)) {
				continue;
			}

			int score = -negamax(newState, vsColor, -beta, -alpha, 5);

			if(score >= alpha) {
				alpha = score;
				bestMove = move;
			}
		}

		return bestMove;
	}
}
