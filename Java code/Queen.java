import java.util.ArrayList;

/**
 * This class stands for the Queen piece in the chess game.
 *
 * It has two static methods: one that checks if a move
 * is valid, and one that generates all the moves possible.
 * 
 * @author Creierul si Cerebelii
 *
 */
public final class Queen extends Piece {
	
	public static ArrayList<long[]> generateMoves(
		Piece.Type type, Piece.Color color, Bitboard board
		) {

		ArrayList<long[]> moves = Rook.generateMoves(type, color, board, true);
		moves.addAll(Bishop.generateMoves(type, color, board, true));
		
		return moves;
	}
}
