public final class Knight extends Piece {
	public static boolean isValidMove(long[] move) {
		long src = move[0], dest = move[1];

		if((src << 6) == dest)	return true;
		if((src << 10) == dest)	return true;
		if((src << 15) == dest) return true;
		if((src << 17) == dest)	return true;
		if((src >> 6) == dest)	return true;
		if((src >> 10) == dest)	return true;
		if((src >> 15) == dest)	return true;
		if((src >> 17) == dest)	return true;

		return false;
	}
}
