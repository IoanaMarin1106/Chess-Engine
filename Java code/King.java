import java.util.ArrayList;

/**
 * This class stands for the King piece in the chess game.
 *
 * It has two static methods: one that checks if a move
 * is valid and one that generates all the moves possible.
 * 
 * @author Creierul si Cerebelii
 *
 */
public final class King extends Piece {
	/**
	 * Method which checks if a move is valid. This method
	 * returns true if the move is valid for the King piece, 
	 * for instance, it will return false if a player tries to 
	 * move two squares with the King.
	 * @param move the move to be checked.
	 * @return true for valid move, false otherwise.
	 */
	public static boolean isValidMove(long src, long dest) {		
		if(src << 1 == dest && (src & Bitboard.FILES[7]) == 0) return true;
		if(src >>> 1 == dest && (src & Bitboard.FILES[0]) == 0) return true;

		if((src & Bitboard.RANKS[0]) == 0) {
			if(src >>> 9 == dest && (src & Bitboard.FILES[7]) == 0) return true;
			if(src >>> 8 == dest) return true;
			if(src >>> 7 == dest && (src & Bitboard.FILES[0]) == 0) return true;
		}

		if((src & Bitboard.RANKS[7]) == 0) {
			if(src << 9 == dest && (src & Bitboard.FILES[0]) == 0) return true;
			if(src << 8 == dest) return true;
			if(src << 7 == dest && (src & Bitboard.FILES[7]) == 0) return true;
		}
		
		return false;
	}
	
	/**
	 * Methods which generates an ArrayList of possible moves
	 * for the King piece. It only generates valid moves.
	 * @param src the source of the move.
	 * @return an array with all moves possible for the King.
	 */
	// public static ArrayList<long[]> generateMoves(long src) {
	// 	ArrayList<long[]> moves = new ArrayList<long[]>();
		
	// 	moves.add(new long[] {src, (src << 9)});
	// 	moves.add(new long[] {src, (src << 8)});
	// 	moves.add(new long[] {src, (src << 7)});
	// 	moves.add(new long[] {src, (src >>> 9)});
	// 	moves.add(new long[] {src, (src >>> 8)});
	// 	moves.add(new long[] {src, (src >>> 7)});
	// 	moves.add(new long[] {src, (src << 1)});
	// 	moves.add(new long[] {src, (src >>> 1)});
		
	// 	return moves;
	// }

	public static ArrayList<long[]> generateMoves(
		Piece.Type type, Piece.Color color, Bitboard board
		) {

		ArrayList<long[]> moves = new ArrayList<long[]>();
		long src, attackerPieces;

		if(color == Piece.Color.WHITE) {
			src = board.whitePieces[0];
			attackerPieces = board.getAllPieces(board.whitePieces);
		} else {
			src = board.blackPieces[0];
			attackerPieces = board.getAllPieces(board.blackPieces);
		}

		long[] dest = {(src << 9), (src << 8), (src << 7), (src >>> 9), (src >>> 8),
					(src >>> 7), (src << 1), (src >>> 1)};

		for(int i = 0; i < dest.length; i++) {
			if((dest[i] & attackerPieces) == 0 && isValidMove(src, dest[i])) {
				moves.add(new long[] {src, dest[i]});
			}
		}

		return moves;
	}
}
