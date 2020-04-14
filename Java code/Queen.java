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
		Piece.Type type, Piece.Color color, long queen, long attackerPieces, long defenderPieces
		) {

		ArrayList<long[]> moves = Rook.generateMoves(type, color, queen, attackerPieces, defenderPieces);
		moves.addAll(Bishop.generateMoves(type, color, queen, attackerPieces, defenderPieces));
		
		return moves;
	}

	public static long generateMovesMap(Color color, long queen, long attackerPieces, long defenderPieces) {
		return (Bishop.generateMovesMap(color, queen, attackerPieces, defenderPieces) | 
			Rook.generateMovesMap(color, queen, attackerPieces, defenderPieces));
	}
}
