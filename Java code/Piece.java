import java.util.*;

/**
 * This class stands for a piece in the chess game.
 *
 * It has an enum to highlight the types of pieces on
 * board, another enum that contains the two colors of
 * the pieces in a chess game, a static method that returns
 * the type of a piece according to its index, a static 
 * method that checks if a move is valid and a static method 
 * that generates all the moves possible.
 * 
 * @author Creierul si Cerebelii
 */
public abstract class Piece {

	/**
	 * This enum contains all types of pieces correlated at
	 * the same time with their index in the piece vector of 
	 * the Bitboard class(eg. whitePieces, blackPieces).
	 */
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

	/**
	 * This enum contains the two types of colors of the
	 * present pieces on a chess board.
	 */
	enum Color {
		WHITE,
		BLACK
	}

	/**
	 * This static method returns the type of the piece
	 * whose index is given as a parameter.
	 * @param index the index in the pieces vector.
	 * @return piece type.
	 */
	public static Type getType(int index) {
		if(index == 0)	return Type.KING;
		if(index == 1)	return Type.QUEEN;
		if(index == 2)	return Type.ROOK;
		if(index == 3)	return Type.BISHOP;
		if(index == 4)	return Type.KNIGHT;
		if(index == 5)	return Type.PAWN;

		return null;
	}

	public static int getTypeIndex(char type) {
		switch(type) {
			case 'k':
				return 0;
			case 'q':
				return 1;
			case 'r':
				return 2;
			case 'b':
				return 3;
			case 'n':
				return 4;
			case 'p':
				return 5;
			default:
				return 1;
		}
	}

	public static ArrayList<long[]> generateMoves(
		Type type, Color color,
		long pieces, long attackerPieces, long defenderPieces
		) {

		switch(type) {
			case KING:
				return King.generateMoves(type, color, pieces, attackerPieces, defenderPieces);
			case QUEEN:
				return Queen.generateMoves(type, color, pieces, attackerPieces, defenderPieces);
			case ROOK:
				return Rook.generateMoves(type, color, pieces, attackerPieces, defenderPieces);
			case BISHOP:
				return Bishop.generateMoves(type, color, pieces, attackerPieces, defenderPieces);
			case KNIGHT:
				return Knight.generateMoves(type, color, pieces, attackerPieces, defenderPieces);
			case PAWN:
				return Pawn.generateMoves(type, color, pieces, attackerPieces, defenderPieces);
			default:
				return null;
		}
	}
}

