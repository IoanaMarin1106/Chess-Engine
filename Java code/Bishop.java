import java.util.ArrayList;

/**
 * This class stands for the Bishop piece in the chess game.
 * 
 * It has three static methods: one that checks if the piece 
 * meets a collision by performing the given move, one that
 * checks if a move is valid and one that generates all the
 * moves possible.
 *
 * @author Creierul si Cerebelii
 */
public final class Bishop extends Piece {

	/**
 	 * Method which checks if a Bishop meets a collision by
 	 * performing the given move.
 	 * @param srcRank the line on which the Bishop is initially located.
 	 * @param srcFile the column on which the Bishop is initially located.
 	 * @param destRank the line on which the Bishop is after the move.
 	 * @param destFile the column on which the Bishop is after the move.
 	 * @param allPieces the current state of the board.
 	 * @return true in case the Bishop meets a collion, false otherwise.
 	 */
	public static boolean meetCollision(
		int srcRank, int srcFile,
		int destRank, int destFile,
		long allPieces
		) {
		
		if(srcRank < destRank) {
			if(srcFile < destFile) {
				for(int i = 1; i < (destRank - srcRank); i++) {
					long rank = Bitboard.RANKS[srcRank + i];
					long file = Bitboard.FILES[srcFile + i];

					if(((rank & file) & allPieces) != 0) {
						return true;
					}
				}
			} else {
				System.out.println("cszul asta");

				for(int i = 1; i < (destRank - srcRank); i++) {
					long rank = Bitboard.RANKS[srcRank + i];
					long file = Bitboard.FILES[srcFile - i];

					if(((rank & file) & allPieces) != 0) {
						return true;
					}
				}
			}
		} else {
			if(srcFile < destFile) {
				for(int i = 1; i < (srcRank - destRank); i++) {
					long rank = Bitboard.RANKS[srcRank - i];
					long file = Bitboard.FILES[srcFile + i];

					if(((rank & file) & allPieces) != 0) {
						return true;
					}
				}
			} else {
				for(int i = 1; i < (srcRank - destRank); i++) {
					long rank = Bitboard.RANKS[srcRank - i];
					long file = Bitboard.FILES[srcFile - i];

					if(((rank & file) & allPieces) != 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 *	Method which checks if a move is valid. This method returns
	 *	true if the move is valid for the Bishop piece, for instance,
	 *	the Bishop can only move diagonally.
	 *	@param move the move to be checked.
	 *	@param allPieces the current state of the board.
	 *	@return true for valid move, false otherwise. 
	 */
	public static boolean isValidMove(long[] move, long allPieces) {
		long src = move[0], dest = move[1];

		int srcRank = Bitboard.getRank(src), srcFile = Bitboard.getFile(src);
		int destRank = Bitboard.getRank(dest), destFile = Bitboard.getFile(dest);

		if(Math.abs(srcRank - destRank) == Math.abs(srcFile - destFile)) {
			if(!meetCollision(srcRank, srcFile, destRank, destFile, allPieces)) {
				return true;
			}
		}

		return false;
	}

	/**
	 *	Method which generates an ArrayList of possible moves for
	 *	the Bishop piece. It only generates valid moves.
	 *	@param pieces only the positions on which the Bishops are on the board.
	 *	@return an array with all moves possible for the Bishop.
	 */
	public static ArrayList<long[]> generateMoves(long pieces) {
		ArrayList<long[]> moves = new ArrayList<long[]>();

		while(pieces != 0) {
			long src = 1;

			while((src & pieces) == 0) {
				src = (src << 1);
			}

			pieces = (pieces & (~src));

			int srcRank = Bitboard.getRank(src), srcFile = Bitboard.getFile(src);

			int rank, file;

			rank = srcRank - 1;
			file = srcFile - 1;

			while(rank >= 0 && file >= 0) {
				moves.add(new long[] {src, (Bitboard.RANKS[rank] & Bitboard.FILES[file])});
				rank--;
				file--;
			}

			rank = srcRank - 1;
			file = srcFile + 1;

			while(rank >= 0 && file < 8) {
				moves.add(new long[] {src, (Bitboard.RANKS[rank] & Bitboard.FILES[file])});
				rank--;
				file++;
			}

			rank = srcRank + 1;
			file = srcFile - 1;

			while(rank < 8 && file >= 0) {
				moves.add(new long[] {src, (Bitboard.RANKS[rank] & Bitboard.FILES[file])});
				rank++;
				file--;
			}

			rank = srcRank + 1;
			file = srcFile + 1;

			while(rank < 8 && file < 8) {
				moves.add(new long[] {src, (Bitboard.RANKS[rank] & Bitboard.FILES[file])});
				rank++;
				file++;
			}
		}

		return moves;
	}
}

