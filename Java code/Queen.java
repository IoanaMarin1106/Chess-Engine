public final class Queen extends Piece {
	public static boolean isValidMove(long[] move) {
		return (Rook.isValidMove(move) || Bishop.isValidMove(move));
	}
}
