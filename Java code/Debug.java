/**
 * Class designed for debugging, which mainly has a 
 * method to display the board in the console/terminal
 * and also has some methods for testing.
 * 
 * @author Creierul si Cerebelii
 *
 */
public class Debug {

	/**
	 * Method that displays the actual state of the board
	 * in the console/terminal.
	 * @param game the game with its actual state.
	 */
	public static void displayBoard (Game game) {
		Bitboard bitboard = game.getBitboard();
		
		char[] board = new char[64];
		long[] whitePieces = bitboard.getWhitePieces();
		long[] blackPieces = bitboard.getBlackPieces();
		
		for(int i = 0; i < 64; i++) {
			board[i] = '-';
			
			if((whitePieces[0] & (1L<<i)) != 0)	board[i]='K';
			if((whitePieces[1] & (1L<<i)) != 0)	board[i]='Q';
			if((whitePieces[2] & (1L<<i)) != 0)	board[i]='R';
			if((whitePieces[3] & (1L<<i)) != 0)	board[i]='B';
			if((whitePieces[4] & (1L<<i)) != 0)	board[i]='N';
			if((whitePieces[5] & (1L<<i)) != 0)	board[i]='P';
			
			if((blackPieces[0] & (1L<<i)) != 0)	board[i]='k';
			if((blackPieces[1] & (1L<<i)) != 0)	board[i]='q';
			if((blackPieces[2] & (1L<<i)) != 0)	board[i]='r';
			if((blackPieces[3] & (1L<<i)) != 0)	board[i]='b';
			if((blackPieces[4] & (1L<<i)) != 0)	board[i]='n';
			if((blackPieces[5] & (1L<<i)) != 0)	board[i]='p';
		}
		
		System.out.println("#   A B C D E F G H");
		
		for(int i = 7; i >= 0; i--) {
			System.out.print("# ");
			System.out.print(i+1 + " ");
			
			for(int j = 0; j < 8; j++) {
				System.out.print(board[8*i + j] + " ");
			}
			
			System.out.println();
		}
	}	
	
	/**
	 * Method that tests valid moves for every type
	 * of piece in the chess game.
	 * @param game the game with its actual state.
	 */
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//	public static void testValidMoves(Game game) {
//		long[] move;
//
//		System.out.println("PAWN--------------------------------");
//		move = Move.convertMove("c2c4");
//		System.out.println(Pawn.isValidWhiteMove(move, 0, 0));
//		move = Move.convertMove("c2c3");
//		System.out.println(Pawn.isValidWhiteMove(move, 0, 0));
//		move = Move.convertMove("a7a5");
//		System.out.println(Pawn.isValidBlackMove(move, 0, 0));
//		move = Move.convertMove("e5e4");
//		System.out.println(Pawn.isValidBlackMove(move, 0, 0));
//		move = Move.convertMove("c3d3");
//
//		System.out.println(Pawn.isValidBlackMove(move, 0, 0));
//		System.out.println(Pawn.isValidWhiteMove(move, 0, 0));
//
//		System.out.println("BISHOP--------------------------------");
//		move = Move.convertMove("g2b7");
//		System.out.println(Bishop.isValidMove(move, 0));
//		move = Move.convertMove("g5c1");
//		System.out.println(Bishop.isValidMove(move, 0));
//
//		move = Move.convertMove("h1h4");
//		System.out.println(Bishop.isValidMove(move, 0));
//		move = Move.convertMove("h1h3");
//		System.out.println(Bishop.isValidMove(move, 0));
//
//		System.out.println("KING--------------------------------");
//		move = Move.convertMove("g1g2");
//		System.out.println(King.isValidMove(move));
//		move = Move.convertMove("g5h6");
//		System.out.println(King.isValidMove(move));
//		move = Move.convertMove("h8h6");
//		System.out.println(King.isValidMove(move));
//
//		System.out.println("KNIGHT----------------------------");
//		move = Move.convertMove("g1f3");
//		System.out.println(Knight.isValidMove(move));
//		move = Move.convertMove("h6f7");
//		System.out.println(Knight.isValidMove(move));
//		move = Move.convertMove("g6f5");
//		System.out.println(Knight.isValidMove(move));
//
//		System.out.println("ROOK---------------------------");
//		move = Move.convertMove("h1h2");
//		System.out.println(Rook.isValidMove(move, 0));
//		move = Move.convertMove("h4a4");
//		System.out.println(Rook.isValidMove(move, 0));
//		move = Move.convertMove("h1g7");
//		System.out.println(Rook.isValidMove(move, 0));
//
//		System.out.println("QUEEN-------------------------");
//		move = Move.convertMove("h1h2");
//		System.out.println(Queen.isValidMove(move, 0));
//		move = Move.convertMove("h4a4");
//		System.out.println(Queen.isValidMove(move, 0));
//		move = Move.convertMove("h1f3");
//		System.out.println(Queen.isValidMove(move, 0));
//		move = Move.convertMove("h1g3");
//		System.out.println(Queen.isValidMove(move, 0));
//	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
