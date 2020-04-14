import java.util.*;

public class Negamax {
	public static int negamax(
		Bitboard board, Piece.Color playerColor,
		int alpha, int beta, int depth) {


		if(depth == 0 || board.gameOver()) {
			// Debug.displayBoard(board);
			int eval = Evaluation.evaluate(board, playerColor);
			// System.out.println("# eval = " + eval);
			return eval;
		}

		ArrayList<long[]> moves = board.generateAllMoves(playerColor);
		Piece.Color vsColor = (playerColor == Piece.Color.WHITE) ?
							Piece.Color.BLACK : Piece.Color.WHITE;
		int max = -10000000;

		for(long[] move : moves) {
			Bitboard newState = (Bitboard)board.clone();
			newState.makeMove(move, playerColor, null);

			int score = -negamax(newState, vsColor, -beta, -alpha, depth - 1);

			if(score >= max) {
				max = score;
			}

			if(max >= alpha) {
				alpha = max;
			}

			if(alpha >= beta) {
				break;
			}
		}

		return max;
	}

	public static long[] negamaxHandler(Bitboard board, Piece.Color playerColor) {
		ArrayList<long[]> moves = board.generateAllMoves(playerColor);
		Piece.Color vsColor = (playerColor == Piece.Color.WHITE) ?
							Piece.Color.BLACK : Piece.Color.WHITE;

		ArrayList<long[]> bestMoves = new ArrayList<long[]>();
		int alpha = -10000000, beta = 10000000;
		int max = -1000000;

		for(long[] move : moves) {
			Bitboard newState = (Bitboard)board.clone();
			newState.makeMove(move, playerColor, null);

			// if(newState.isCheck(playerColor)) {
			// 	continue;
			// }

			int score = -negamax(newState, vsColor, -beta, -alpha, 4);

			if(score > max) {
				max = score;
				bestMoves.clear();
				bestMoves.add(move);
			} else if(score == max) {
				bestMoves.add(move);
			}

			if(max >= alpha) {
				alpha = score;
			}
		}

		long[] bestMove = null;
		int maxMove = -10000000;

		for(long[] move : bestMoves) {
			int score = Evaluation.evaluateMove(board, playerColor, move);

			if(score > maxMove) {
				maxMove = score;
				bestMove = move;
			}
		}

		System.out.println("# Am evaluat la: " + max);
		return bestMove;
	}
}
