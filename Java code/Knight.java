import java.util.ArrayList;

/**
 * This class stands for the Knight piece in the chess game.
 *
 * It has two static methods: one that checks if a move is valid and one that
 * generates all the moves possible.
 * 
 * @author Creierul si Cerebelii
 *
 */
public final class Knight extends Piece {
	/**
	 * Method which checks if a move is valid. This method returns true if the move
	 * is valid for the Knight piece, for instance, it will return false if a player
	 * tries to move in a straight line with the Knight.
	 * 
	 * @param move the move to be checked.
	 * @return true for valid move, false otherwise.
	 */
	public static boolean isValidMove(long src, long dest) {
		if ((src & Bitboard.FILES[7]) == 0) {
			if ((src << 17) == dest && (src & Bitboard.RANKS[6]) == 0 && (src & Bitboard.RANKS[7]) == 0)
				return true;
			if ((src >>> 15) == dest && (src & Bitboard.RANKS[0]) == 0 && (src & Bitboard.RANKS[1]) == 0)
				return true;
		}

		if ((src & Bitboard.FILES[0]) == 0) {
			if ((src << 15) == dest && (src & Bitboard.RANKS[6]) == 0 && (src & Bitboard.RANKS[7]) == 0)
				return true;
			if ((src >>> 17) == dest && (src & Bitboard.RANKS[0]) == 0 && (src & Bitboard.RANKS[1]) == 0)
				return true;
		}

		if ((src & Bitboard.RANKS[0]) == 0) {
			if ((src >>> 6) == dest && (src & Bitboard.FILES[1]) == 0 && (src & Bitboard.FILES[0]) == 0)
				return true;
			if ((src >>> 10) == dest && (src & Bitboard.FILES[7]) == 0 && (src & Bitboard.FILES[6]) == 0)
				return true;
		}

		if ((src & Bitboard.RANKS[7]) == 0) {
			if ((src << 10) == dest && (src & Bitboard.FILES[1]) == 0 && (src & Bitboard.FILES[0]) == 0)
				return true;
			if ((src << 6) == dest && (src & Bitboard.FILES[7]) == 0 && (src & Bitboard.FILES[6]) == 0)
				return true;
		}

		return false;
	}

	/**
	 * Methods which generates an ArrayList of possible moves for the Knight piece.
	 * It only generates valid moves.
	 * 
	 * @param src the source of the move.
	 * @return an array with all moves possible for the Knight.
	 */
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public static ArrayList<long[]> generateMoves(long pieces) {
//		ArrayList<long[]> moves = new ArrayList<long[]>();
//
//		while (pieces != 0) {
//			long src = 1;
//
//			while ((src & pieces) == 0) {
//				src = (src << 1);
//			}
//
//			pieces = (pieces & (~src));
//
//			moves.add(new long[] { src, (src << 6) });
//			moves.add(new long[] { src, (src << 10) });
//			moves.add(new long[] { src, (src << 15) });
//			moves.add(new long[] { src, (src << 17) });
//			moves.add(new long[] { src, (src >>> 6) });
//			moves.add(new long[] { src, (src >>> 10) });
//			moves.add(new long[] { src, (src >>> 15) });
//			moves.add(new long[] { src, (src >>> 17) });
//		}
//
//		return moves;
//	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static ArrayList<long[]> generateMoves(Type type, Color color, Bitboard board) {
		ArrayList<long[]> moves = new ArrayList<long[]>();

		long src, pieces, attackerPieces;

		if (color == Piece.Color.WHITE) {
			pieces= board.whitePieces[4];
			attackerPieces = board.getAllPieces(board.whitePieces);
		} else {
			pieces= board.blackPieces[4];
			attackerPieces = board.getAllPieces(board.blackPieces);
		}

		while (pieces != 0) {
			src = 1;

			while ((src & pieces) == 0) {
				src = (src << 1);
			}

			pieces = (pieces & (~src));

			long dest[] = {(src << 6), (src << 10), (src << 15), (src << 17), (src >>> 6), (src >>> 10), (src >>> 15),
					(src >>> 17) };

			for (int i = 0; i < dest.length; i++) {
				if ((dest[i] & attackerPieces) == 0 && isValidMove(src, dest[i])) {
					moves.add(new long[] { src, dest[i] });
				}
			}
		}

		return moves;
	}

}
