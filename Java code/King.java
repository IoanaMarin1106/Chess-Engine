public final class King extends Piece {
	public static boolean isValidMove(long[] move) {
		long src = move[0], dest = move[1];
		
		if(src << 9 == dest) return true;
		if(src << 8 == dest) return true;
		if(src << 7 == dest) return true;
		if(src >> 9 == dest) return true;
		if(src >> 8 == dest) return true;
		if(src >> 7 == dest) return true;
		
		return false;
	}
}
