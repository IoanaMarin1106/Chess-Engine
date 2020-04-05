import java.util.*;

public class Negamax {
	public static int negamax(
		Bitboard board, Piece.Color playerColor,
		int alpha, int beta, int depth) {


		if(depth == 0 || board.gameOver()) {
			Debug.displayBoard(board);
			int eval = Evaluation.evaluate(board, playerColor);
			System.out.println("# eval = " + eval);
			return eval;
		}

		ArrayList<long[]> moves = board.generateAllMoves(playerColor);
		Piece.Color vsColor = (playerColor == Piece.Color.WHITE) ?
							Piece.Color.BLACK : Piece.Color.WHITE;
		int max = -10000000;

		for(long[] move : moves) {
			Bitboard newState = (Bitboard)board.clone();

			newState.makeMove(move, playerColor);

			// if(newState.isCheck(playerColor)) {
			// 	continue;
			// }

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

		long[] bestMove = null;
		// int alpha = -10000000, beta = 10000000;
		int max = -10000000;

		for(long[] move : moves) {
			Bitboard newState = (Bitboard)board.clone();
			newState.makeMove(move, playerColor);

			// if(newState.isCheck(playerColor)) {
			// 	continue;
			// }

			int score = -negamax(newState, vsColor, -10000000, 10000000, 2);

			if(score >= max) {
				max = score;
				bestMove = move;
			}

			// if(max >= alpha) {
			// 	alpha = score;
			// }
		}

		System.out.println("# Am evaluat la: " + max);
		return bestMove;
	}
}
