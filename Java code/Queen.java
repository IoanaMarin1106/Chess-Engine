import java.util.ArrayList;

public final class Queen extends Piece {
	public static boolean isValidMove(long[] move, long allPieces) {
		return (Rook.isValidMove(move, allPieces) || Bishop.isValidMove(move, allPieces));
	}
	
	public static ArrayList<long[]> generateMoves(long src) {
		ArrayList<long[]> moves = new ArrayList<long[]>();
		
		moves.addAll(Rook.generateMoves(src));
		moves.addAll(Bishop.generateMoves(src));
		
		return moves;
	}
}
