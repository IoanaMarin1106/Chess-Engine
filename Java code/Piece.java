import java.util.*;

public abstract class Piece {
	enum Type {
		KING(0),
		QUEEN(1),
		ROOK(2),
		BISHOP(3),
		KNIGHT(4),
		PAWN(5);

		public int index;

		Type(int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	}

	enum Color {
		WHITE,
		BLACK
	}

	public static Type getType(int index) {
		if(index == 0)	return Type.KING;
		if(index == 1)	return Type.QUEEN;
		if(index == 2)	return Type.ROOK;
		if(index == 3)	return Type.BISHOP;
		if(index == 4)	return Type.KNIGHT;
		if(index == 5)	return Type.PAWN;

		return null;
	}

	public static boolean isValidMove(
		Type type, Color color,
		long[] move,
		long color1Pieces, long color2Pieces
		) {

		switch(type) {
			case KING:
				return King.isValidMove(move);
			case QUEEN:
				return Queen.isValidMove(move, (color1Pieces | color2Pieces));
			case ROOK:
				return Rook.isValidMove(move, (color1Pieces | color2Pieces));
			case BISHOP:
				return Bishop.isValidMove(move, (color1Pieces | color2Pieces));
			case KNIGHT:
				return Knight.isValidMove(move);
			case PAWN:
				if(color == Piece.Color.WHITE) {
					return Pawn.isValidWhiteMove(move, color2Pieces,
											(color1Pieces | color2Pieces));
				} else {
					return Pawn.isValidBlackMove(move, color2Pieces,
											(color1Pieces | color2Pieces));
				}
			default:
				return false;
		}
	}

	public static ArrayList<long[]> generateMoves(Type type, long pieces) {
		switch(type) {
			case KING:
				return King.generateMoves(pieces);
			case QUEEN:
				return Queen.generateMoves(pieces);
			case ROOK:
				return Rook.generateMoves(pieces);
			case BISHOP:
				return Bishop.generateMoves(pieces);
			case KNIGHT:
				return Knight.generateMoves(pieces);
			case PAWN:
				return Pawn.generateMoves(pieces);
			default:
				return null;
		}
	}
}
