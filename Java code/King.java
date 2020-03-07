public final class King extends Piece {
	public static boolean isValidMove(long[] move) {
		long src = move[0], dest = move[1];
		
		if(src << 8 == dest || src << 9 == dest || src << 7 == dest ||
			src >> 8 == dest || src >> 9 == dest || src >> 7 == dest ||
			src << 1 == dest || src >> 1 == dest) {
			return true;
		}		

		return false;
	}
}
