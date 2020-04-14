import java.util.ArrayList;

/**
 * This class stands for the Bishop piece in the chess game.
 * 
 * It has three static methods: one that checks if the piece 
 * meets a collision by performing the given move, one that
 * checks if a move is valid and one that generates all the
 * moves possible.
 *
 * @author Creierul si Cerebelii
 */
public final class Bishop extends Piece {

	public static long bishopAttacksMap(int tile, long blockers) {
		long attacks = 0;

		attacks |= Magic.raysNW[tile];
		if((Magic.raysNW[tile] & blockers) != 0) {
			int blockerTile = Long.numberOfTrailingZeros(Magic.raysNW[tile] & blockers);
			attacks &= ~Magic.raysNW[blockerTile];
		}

		attacks |= Magic.raysNE[tile];
		if((Magic.raysNE[tile] & blockers) != 0) {
			int blockerTile = Long.numberOfTrailingZeros(Magic.raysNE[tile] & blockers);
			attacks &= ~Magic.raysNE[blockerTile];
		}

		attacks |= Magic.raysSE[tile];
		if((Magic.raysSE[tile] & blockers) != 0) {
			int blockerTile = 63 - Long.numberOfLeadingZeros(Magic.raysSE[tile] & blockers);
			attacks &= ~Magic.raysSE[blockerTile];
		}

		attacks |= Magic.raysSW[tile];
		if((Magic.raysSW[tile] & blockers) != 0) {
			int blockerTile = 63 - Long.numberOfLeadingZeros(Magic.raysSW[tile] & blockers);
			attacks &= ~Magic.raysSW[blockerTile];
		}

		return attacks;
	}

	public static ArrayList<long[]> generateMoves(
		Piece.Type type, Piece.Color color, long bishops, long attackerPieces, long defenderPieces
		) {

		ArrayList<long[]> moves = new ArrayList<long[]>();

		long blockers = attackerPieces | defenderPieces;

		while(bishops != 0) {
			int tile = Long.numberOfTrailingZeros(bishops);
			long src = (1L << tile);
			bishops = bishops & (~(1L << tile));

			long attacks = (bishopAttacksMap(tile, blockers) & (~attackerPieces));

			while(attacks != 0) {
				long dest = (1L << Long.numberOfTrailingZeros(attacks));
				attacks = (attacks & (~dest));

				moves.add(new long[] {src, dest});
			}
		}

		return moves;
	}

	public static long generateMovesMap(Color color, long bishops, long attackerPieces, long defenderPieces) {
		long attacksMap = 0L;
		long blockers = attackerPieces | defenderPieces;

		while(bishops != 0) {
			int tile = Long.numberOfTrailingZeros(bishops);
			bishops = bishops & (~(1L << tile));

			long attacks = bishopAttacksMap(tile, blockers);

			attacksMap |= attacks;
		}

		attacksMap &= ~attackerPieces;

		return attacksMap;
	}
}

