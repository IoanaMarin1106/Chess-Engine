import java.util.Scanner;

public class Debug {
	public static void main(String[] args) {
		Game game = new Game();

		long[] move;	
		
		System.out.println("PAWN--------------------------------");
		move = Move.convertMove("c2c4");
		System.out.println(Pawn.isValidWhiteMove(move, 0));
		move = Move.convertMove("c2c3");
		System.out.println(Pawn.isValidWhiteMove(move, 0));
		move = Move.convertMove("a7a5");
		System.out.println(Pawn.isValidBlackMove(move, 0));
		move = Move.convertMove("e5e4");
		System.out.println(Pawn.isValidBlackMove(move, 0));
		
		move = Move.convertMove("c3d3");
		System.out.println(Pawn.isValidBlackMove(move, 0));
		System.out.println(Pawn.isValidWhiteMove(move, 0));
		
		System.out.println("BISHOP--------------------------------");
		
		move = Move.convertMove("g2b7");
		System.out.println(Bishop.isValidMove(move, 0));
		move = Move.convertMove("g5c1");
		System.out.println(Bishop.isValidMove(move, 0));
		
		move = Move.convertMove("h1h4");
		System.out.println(Bishop.isValidMove(move, 0));
		move = Move.convertMove("h1h3");
		System.out.println(Bishop.isValidMove(move, 0));
		
		
		System.out.println("KING--------------------------------");
		
		move = Move.convertMove("g1g2");
		System.out.println(King.isValidMove(move));
		move = Move.convertMove("g5h6");
		System.out.println(King.isValidMove(move));
		move = Move.convertMove("h8h6");
		System.out.println(King.isValidMove(move));
	
		System.out.println("KNIGHT----------------------------");
		move = Move.convertMove("g1f3");
		System.out.println(Knight.isValidMove(move));
		move = Move.convertMove("h6f7");
		System.out.println(Knight.isValidMove(move));
		move = Move.convertMove("g6f5");
		System.out.println(Knight.isValidMove(move));
	
		System.out.println("ROOK---------------------------");
		move = Move.convertMove("h1h2");
		System.out.println(Rook.isValidMove(move, 0));
		move = Move.convertMove("h4a4");
		System.out.println(Rook.isValidMove(move, 0));
		move = Move.convertMove("h1g7");
		System.out.println(Rook.isValidMove(move, 0));
	
		System.out.println("QUEEN-------------------------");
		move = Move.convertMove("h1h2");
		System.out.println(Queen.isValidMove(move, 0));
		move = Move.convertMove("h4a4");
		System.out.println(Queen.isValidMove(move, 0));
		move = Move.convertMove("h1f3");
		System.out.println(Queen.isValidMove(move, 0));
		move = Move.convertMove("h1g3");
		System.out.println(Queen.isValidMove(move, 0));
	}
}
