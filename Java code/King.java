public final class King extends Piece {
	public static boolean isValidMove(long[] move) {
		long src = move[0], dest = move[1];
		
		if(src << 9L == dest) return true;
		if(src << 8L == dest) return true;
		if(src << 7L == dest) return true;
		if(src >> 9L == dest) return true;
		if(src >> 8L == dest) return true;
		if(src >> 7L == dest) return true;
		
		return false;
	}
}
