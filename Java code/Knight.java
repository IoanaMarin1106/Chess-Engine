import java.util.ArrayList;

/**
 * This class stands for the Knight piece in the chess game.
 *
 * It has two static methods: one that checks if a move is valid and one that
 * generates all the moves possible.
 * 
 * @author Creierul si Cerebelii
 *
 */
public final class Knight extends Piece {

	public static ArrayList<long[]> generateMoves(
		Type type, Color color, long knights, long attackerPieces, long defenderPieces
		) {

		ArrayList<long[]> moves = new ArrayList<long[]>();

		long[] RANKS = Bitboard.RANKS;
		long[] FILES = Bitboard.FILES;

		while(knights != 0) {
			long src = (1L << Long.numberOfTrailingZeros(knights));
			knights &= (~src);

			long east = ((src & (~FILES[0])) << 1);

			if(east != 0) {
				long upEast = (((east & (~RANKS[7]) & (~RANKS[6])) << 16) & (~attackerPieces));

				if(upEast != 0) {
					moves.add(new long[] {src, upEast});
				}

				long downEast = (((east & (~RANKS[0]) & (~RANKS[1])) >>> 16) & (~attackerPieces));

				if(downEast != 0) {
					moves.add(new long[] {src, downEast});
				}

				east = ((east & (~FILES[0])) << 1);

				if(east != 0) {
					upEast = (((east & (~RANKS[7])) << 8) & (~attackerPieces));

					if(upEast != 0) {
						moves.add(new long[] {src, upEast});
					}

					downEast = (((east & (~RANKS[0])) >>> 8) & (~attackerPieces));

					if(downEast != 0) {
						moves.add(new long[] {src, downEast});
					}
				}
			}

			long west = ((src & (~FILES[7])) >>> 1);

			if(west != 0) {
				long upWest = (((west & (~RANKS[7]) & (~RANKS[6])) << 16) & (~attackerPieces));

				if(upWest != 0) {
					moves.add(new long[] {src, upWest});
				}

				long downWest = (((west & (~RANKS[0]) & (~RANKS[1])) >>> 16) & (~attackerPieces));

				if(downWest != 0) {
					moves.add(new long[] {src, downWest});
				}

				west = ((west & (~FILES[7])) >>> 1);

				if(west != 0) {
					upWest = (((west & (~RANKS[7])) << 8) & (~attackerPieces));

					if(upWest != 0) {
						moves.add(new long[] {src, upWest});
					}

					downWest = (((west & (~RANKS[0])) >>> 8) & (~attackerPieces));

					if(downWest != 0) {
						moves.add(new long[] {src, downWest});
					}
				}
			}
		}

		return moves;
	}

	public static long generateMovesMap(Color color, long knights, long attackerPieces, long defenderPieces) {
		long attacksMap = 0L;

		long[] RANKS = Bitboard.RANKS;
		long[] FILES = Bitboard.FILES;

		long attacks = 0L;

		long east = ((knights & (~FILES[0])) << 1);
		long west = ((knights & (~FILES[7])) >>> 1);

		attacks |= (((east | west) & (~RANKS[7]) & (~RANKS[6])) << 16);
		attacks |= (((east | west) & (~RANKS[0]) & (~RANKS[1])) >>> 16);

		east = ((east & (~FILES[0])) << 1);
		west = ((west & (~FILES[7])) >>> 1);

		attacks |= (((east | west) & (~RANKS[7])) << 8);
		attacks |= (((east | west) & (~RANKS[0])) >>> 8);

		attacksMap |= attacks;

		attacksMap &= ~attackerPieces;

		return attacksMap;
	}

}
