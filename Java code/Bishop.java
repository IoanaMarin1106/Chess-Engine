public final class Bishop extends Piece {
	public static boolean isValidMove(long[] move) {
		long src = move[0], dest = move[1];
		
		for(int i = 1; i <= 8; i++) {
			if(src << ((8*i) + i) == dest || src >> ((8*i) + i) == dest ||
				src << ((8*i) - i) == dest || src >> ((8*i) - i) == dest ) {
				return true;
			}	
			if(src << ((8*i) + i) < (1 << 64) && src >> (8*i) + i > 0) {
				return true;
			}
		}
		
		return false;
	}
}
