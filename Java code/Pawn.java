import java.util.*;

/**
 * This class stands for the Pawn piece in the chess game.
 * 
 * It has three static methods: one that checks if a move
 * is valid for white color(this means that it can only move
 * to the top of board), one that checks if a move is valid for
 * black color(this means that it cand only move to the bottom of
 * the board) and one that generates all the moves possible.
 *
 *	@author Creierul si Cerebelii
 *
 */
public final class Pawn extends Piece {

	/**
	 * Method which checks if a move is valid for white color, this
	 * means that it can only move to the top of the board.
	 * Except on its first move, a pawn may only move one square 
	 * forward at a time. Pawns can never move backwards. Although 
	 * a pawn moves straight forward it captures by talking one 
	 * square diagonally forward.
	 * @param move the move to be checked.
	 * @param blackPieces the current state of the black pieces positions.
	 * @param allPieces the current state of the board.
	 * @return true for valid move, false otherwise. 
	 */
	public static boolean isValidWhiteMove(long[] move, long blackPieces, long allPieces) {
		long src = move[0], dest = move[1];

		/**
		* The Pawn is on attack.
		*/
		if((dest & blackPieces) != 0) {
			if((src << 7L) == dest && (src & Bitboard.FILES[7]) == 0) {
				return true;
			}

			if((src << 9L) == dest && (src & Bitboard.FILES[0]) == 0) {
				return true;
			}
		} else {
			/**
			* The Pawn is pushing forward.
			*/
			if(dest == (src << 8L) && (src & Bitboard.RANKS[7]) == 0) {
				return true;
			} else if(dest == (src << 16L) && (src >= (1L << 8L) && src < (1L << 16L))) {
				if(((src << 8L) & allPieces) == 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Method which checks if a move is valid for black color, this
	 * means that it can only move to the bottom of the board.
	 * Except on its first move, a pawn may only move one square 
	 * forward at a time. Pawns can never move backwards. Although 
	 * a pawn moves straight forward it captures by talking one 
	 * square diagonally forward.
	 * @param move the move to be checked.
	 * @param whitePieces the current state of the white pieces positions.
	 * @param allPieces the current state of the board.
	 * @return true for valid move, false otherwise. 
	 */
	public static boolean isValidBlackMove(long[] move, long whitePieces, long allPieces) {
		long src = move[0], dest = move[1];

		/**
		* The Pawn is on attack.
		*/
		if((dest & whitePieces) != 0) {
			if((src >> 7L) == dest && (src & Bitboard.FILES[0]) == 0) {
				return true;
			}

			if((src >> 9L) == dest && (src & Bitboard.FILES[7]) == 0) {
				return true;
			}
		} else {
			/**
			* The Pawn is pushing forward.
			*/
			if(dest == (src >> 8L) && (src & Bitboard.RANKS[0]) == 0) {
				return true;
			} else if(dest == (src >> 16L) && (src >= (1L << 48L) && src < (1L << 56L))) {
				if(((src >> 8L) & allPieces) == 0) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 *	Method which generates an ArrayList of possible moves for
	 *	the Pawn piece. It only generates valid moves.
	 *	@param pieces only the positions on which the Pawns are on the board.
	 *	@return an array with all moves possible for the Pawns.
	 */
	public static ArrayList<long[]> generateMoves(long pieces) {
		ArrayList<long[]> moves = new ArrayList<long[]>();

		while(pieces != 0) {
			long src = 1;

			while((src & pieces) == 0) {
				src = (src << 1);
			}

			pieces = (pieces & (~src));

			moves.add(new long[] {src, (src >> 8)});
			moves.add(new long[] {src, (src >> 16)});
			moves.add(new long[] {src, (src >> 7)});	
			moves.add(new long[] {src, (src >> 9)});	
			moves.add(new long[] {src, (src << 8)});
			moves.add(new long[] {src, (src << 16)});
			moves.add(new long[] {src, (src << 7)});
			moves.add(new long[] {src, (src << 9)});
		}

		return moves;
	}
}

