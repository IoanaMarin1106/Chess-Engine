public final class Knight extends Piece {
	public static boolean isValidMove(long[] move) {
		long src = move[0], dest = move[1];

		if((src << 6L) == dest)		return true;
		if((src << 10L) == dest)	return true;
		if((src << 15L) == dest) 	return true;
		if((src << 17L) == dest)	return true;
		if((src >> 6L) == dest)		return true;
		if((src >> 10L) == dest)	return true;
		if((src >> 15L) == dest)	return true;
		if((src >> 17L) == dest)	return true;

		return false;
	}
}
