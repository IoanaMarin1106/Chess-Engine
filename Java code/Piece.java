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

	/**
	 * Method which checks if a move is valid for a piece 
	 * depending on its type and color.
	 * @param type piece type.
	 * @param color piece color.
	 * @param move the move to be checked.
	 * @param defenderPieces attacker pieces.
	 * @param attackerPieces defender pieces.
	 * @return true for valid move, false otherwise. 
	 */
	// public static boolean isValidMove(
	// 	Type type, Color color,
	// 	long[] move,
	// 	long defenderPieces, long attackerPieces
	// 	) {

	// 	switch(type) {
	// 		case KING:
	// 			return King.isValidMove(move);
	// 		case QUEEN:
	// 			return Queen.isValidMove(move, (defenderPieces| attackerPieces));
	// 		case ROOK:
	// 			return Rook.isValidMove(move, (defenderPieces | attackerPieces));
	// 		case BISHOP:
	// 			return Bishop.isValidMove(move, (defenderPieces | attackerPieces));
	// 		case KNIGHT:
	// 			return Knight.isValidMove(move);
	// 		case PAWN:
	// 			if(color == Piece.Color.WHITE) {
	// 				return Pawn.isValidWhiteMove(move, attackerPieces,
	// 										(defenderPieces | attackerPieces));
	// 			} else {
	// 				return Pawn.isValidBlackMove(move, attackerPieces,
	// 										(defenderPieces | attackerPieces));
	// 			}
	// 		default:
	// 			return false;
	// 	}
	// }

	/**
	 * Method which generates an ArrayList of possible moves for 
	 * a piece depending on its type.
	 * @param type piece type.
	 * @param pieces only the positions on which this piece is on the board.
	 * @return an array with all moves possible for the piece.
	 */
	// public static ArrayList<long[]> generateMoves(Type type, long pieces) {
	// 	switch(type) {
	// 		case KING:
	// 			return King.generateMoves(pieces);
	// 		case QUEEN:
	// 			return Queen.generateMoves(pieces);
	// 		case ROOK:
	// 			return Rook.generateMoves(pieces);
	// 		case BISHOP:
	// 			return Bishop.generateMoves(pieces);
	// 		case KNIGHT:
	// 			return Knight.generateMoves(pieces);
	// 		case PAWN:
	// 			return Pawn.generateMoves(pieces);
	// 		default:
	// 			return null;
	// 	}
	// }

	public static ArrayList<long[]> generateMoves(
		Type type, Color color,
		Bitboard board
		) {

		switch(type) {
			case KING:
				return King.generateMoves(type, color, board);
			case QUEEN:
				return Queen.generateMoves(type, color, board);
			case ROOK:
				return Rook.generateMoves(type, color, board, false);
			case BISHOP:
				return Bishop.generateMoves(type, color, board, false);
			case KNIGHT:
				return Knight.generateMoves(type, color, board);
			case PAWN:
				return Pawn.generateMoves(type, color, board);
			default:
				return null;
		}
	}
}

