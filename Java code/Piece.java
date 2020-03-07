public abstract class Piece {
	enum PieceType {
		KING(0),
		QUEEN(1),
		ROOK(2),
		BISHOP(3),
		KNIGHT(4),
		PAWN(5);

		private int index;

		PieceType(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

	enum PieceColor {
		WHITE,
		BLACK
	}
}
