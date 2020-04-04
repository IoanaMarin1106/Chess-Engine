import java.util.*;

/**
 * This class stands for the Rook piece in the chess game.
 * 
 * It has four static methods: one that checks if the piece
 * meets a collision by moving to the column it is on, one
 * that checks if the piece meets a collision by moving to
 * the line it is on, one that checks if a move is valid 
 * and one that generates all the moves possible.
 *
 * @author Creierul si Cerebelii
 *
 */
public final class Rook extends Piece {

	/**
	 * Method which checks if a Rook meets a collision
	 * by moving to the column it is on.
	 *
	 * This method returns false if the Rook will not meet
	 * a collision by moving to the column it is on and it 
	 * returns true otherwise.
	 *
	 * @param file the column on which the Rook can be moved.
	 * @param srcRank the line on which the Rook is initially located.
	 * @param destRank the line on which the Rook is after the move.
	 * @param allPieces the current state of the board.
	 * @return true in case the Rook meets a collion, false otherwise.
	 */
	public static boolean meetCollisionForColumns(
		long file,
		int srcRank, int destRank,
		long allPieces
		) {

		if(srcRank < destRank) {
			for(int i = srcRank + 1; i < destRank; i++) {
				if(((Bitboard.RANKS[i] & file) & allPieces) != 0) {
					return true;
				}
			}
		} else {
			for(int i = destRank + 1; i < srcRank; i++) {
				if(((Bitboard.RANKS[i] & file) & allPieces) != 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Method which checks if a Rook meets a collision
	 * by moving to the line it is on.
	 *
	 * This method returns false if the Rook will not meet
	 * a collision by moving to the line it is on and it 
	 * returns true otherwise.
	 *
	 * @param rank the line on which the Rook can be moved.
	 * @param srcFile the column on which the Rook is initially located.
	 * @param destFile the column on which the Rook is after the move.
	 * @param allPieces the current state of the board.
	 * @return true in case the Rook meets a collion, false otherwise.
	 */
	public static boolean meetCollisionForLines(
		long rank,
		int srcFile, int destFile,
		long allPieces
		) {

		if(srcFile < destFile) {
			for(int i = srcFile + 1; i < destFile; i++) {
				if(((Bitboard.FILES[i] & rank) & allPieces) != 0) {
					return true;
				}
			}
		} else {
			for(int i = destFile + 1; i < srcFile; i++) {
				if(((Bitboard.FILES[i] & rank) & allPieces) != 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Method which checks if a move is valid. This method returns
	 * true if the move is valid for the Rook piece, for instance,
	 * the Rook can only move straight.
	 * @param move the move to be checked.
	 * @param allPieces the current state of the board.
	 * @return true for valid move, false otherwise.	
	 */
	public static boolean isValidMove(
		long[] move, 
		long allPieces
		) {

		long src = move[0], dest = move[1];

		int srcRank = Bitboard.getRank(src), srcFile = Bitboard.getFile(src);
		int destRank = Bitboard.getRank(dest), destFile = Bitboard.getFile(dest);

		if (srcFile == destFile) {
			long file = Bitboard.FILES[srcFile];

			if (meetCollisionForColumns(file, srcRank, destRank, allPieces) == false) {
				return true;
			}
		}

		if(srcRank == destRank) {
			long rank = Bitboard.RANKS[srcRank];

			if (meetCollisionForLines(rank, srcFile, destFile, allPieces) == false) {
				return true;
			}
		}

		return false;
	}


	/**
	 * Method which generates an ArrayList of possible moves for
	 * the Rook piece. It only generates valid moves.
	 * @param pieces only the positions on which the Rooks are on the board.
	 * @return an array with all moves possible for the Rook.
	 */
	// public static ArrayList<long[]> generateMoves(long pieces) {
	// 	ArrayList<long[]> moves = new ArrayList<long[]>();

	// 	while(pieces != 0) {
	// 		long src = 1;

	// 		while((src & pieces) == 0) {
	// 			src = (src << 1);
	// 		}

	// 		pieces = (pieces & (~src));

	// 		int rookRank = Bitboard.getRank(src),
	// 			rookFile = Bitboard.getFile(src);

	// 		for(int i = 1; i <= rookRank; i++) {
	// 			moves.add(new long[] {src, (src >>> (8 * i))});
	// 		}

	// 		for (int i = 1; i <= (7 - rookRank); i++) {
	// 			moves.add(new long[] {src, (src << (8 * i))});
	// 		}

	// 		for (int i = 1; i <= rookFile; i++) {
	// 			moves.add(new long[] {src, (src >>> i)});
	// 		}

	// 		for (int i = 1; i <= (7 - rookFile); i++) {
	// 			moves.add(new long[] {src, (src << i)});
	// 		}
	// 	}

	// 	return moves;
	// }

	public static ArrayList<long[]> generateMoves(Type type,
												Color color,
												Bitboard board
												) {

		ArrayList<long[]> moves = new ArrayList<long[]>();
		long pieces, attackerPieces;
        long defenderPieces;

		if(color == Piece.Color.WHITE) {
            pieces = board.whitePieces[2];
            attackerPieces = board.getAllPieces(board.whitePieces);
            defenderPieces = board.getAllPieces(board.blackPieces);
        } else {
            pieces = board.blackPieces[2];
            attackerPieces = board.getAllPieces(board.blackPieces);
            defenderPieces = board.getAllPieces(board.whitePieces);
        }

        long allPieces = defenderPieces | attackerPieces;

        while (pieces != 0) {
        	long src = 1;

        	while ((src & pieces) == 0) {
        		src = (src << 1);
        	}

        	pieces = (pieces & (~src));

			int rookRank = Bitboard.getRank(src),
				rookFile = Bitboard.getFile(src);

			for (int i = 1; i <= rookRank; i++) {
				if (((src >>> (8 * i)) & attackerPieces) != 0) {

					if (isValidMove(new long[] {src, (src >>> (8 * i))}, allPieces)) {
						moves.add(new long[] {src, (src >>> (8 * i))});
					}
				}
			}

			for (int i = 1; i <= (7 - rookRank); i++) {
				if (((src << (8 * i)) & attackerPieces) != 0) {

					if (isValidMove(new long[] {src, (src << (8 * i))}, allPieces)) {
						moves.add(new long[] {src, (src << (8 * i))});
					}
				}
			}


			for (int i = 1; i <= rookFile; i++) {
				if (((src >>> i) & attackerPieces) != 0) {

					if (isValidMove(new long[] {src, (src >>> i)}, allPieces)) {
						moves.add(new long[] {src, (src >>> i)});
					}
				}
			}

			for (int i = 1; i <= (7 - rookFile); i++) {
				if (((src << i) & attackerPieces) != 0) {
				
					if (isValidMove(new long[] {src, (src << i)}, allPieces)) {
						moves.add(new long[] {src, (src << i)});
					}
				}
			}
        }

		return moves;
	}
}

