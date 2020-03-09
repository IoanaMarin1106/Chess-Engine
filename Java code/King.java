import java.util.ArrayList;

public final class King extends Piece {
	public static boolean isValidMove(long[] move) {
		long src = move[0], dest = move[1];
		
		if(src << 9 == dest && (src & Bitboard.FILES[0]) == 0) return true;
		if(src << 8 == dest) return true;
		if(src << 7 == dest && (src & Bitboard.FILES[7]) == 0) return true;
		if(src >> 9 == dest && (src & Bitboard.FILES[7]) == 0) return true;
		if(src >> 8 == dest) return true;
		if(src >> 7 == dest && (src & Bitboard.FILES[0]) == 0) return true;
		if(src << 1 == dest) return true;
		if(src >> 1 == dest) return true;
		
		return false;
	}
	
	public static ArrayList<long[]> generateMoves(long src) {
		ArrayList<long[]> moves = new ArrayList<long[]>();
		
		moves.add(new long[] {src, (src << 9)});
		moves.add(new long[] {src, (src << 8)});
		moves.add(new long[] {src, (src << 7)});
		moves.add(new long[] {src, (src >> 9)});
		moves.add(new long[] {src, (src >> 8)});
		moves.add(new long[] {src, (src >> 7)});
		moves.add(new long[] {src, (src << 1)});
		moves.add(new long[] {src, (src >> 1)});
		
		return moves;
	}
}
