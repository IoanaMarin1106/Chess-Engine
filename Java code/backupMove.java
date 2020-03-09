public final class Move {

	private static int charToNumber(char c) {
		return c - 'a' + 1;
	}

	public static boolean isMove(String move) {
		/* Nu cred ca exista miscare cu lungime mai mica de 4.
		 * Eg: e4e5. Nu cred ca poti sa ai mai putin. */
		if (move == null || move.length() < 4) {
			return false;
		}
		/* Aici e posibil sa modificam, ca s-ar putea sa fie miscari
		 * care sa nu aiba numericele asa. */
		if (Character.isDigit(move.charAt(1)) == false ||
			Character.isDigit(move.charAt(3)) == false) {
			return false;
		}	
		/* La fel si aici, dar momentan e ok. */
		if (Character.isLetter(move.charAt(0)) == false ||
			Character.isLetter(move.charAt(2)) == false) {
			return false;
		}
		/* Si sa verificam si ca literele alea sunt intre a si h,
		 * ca altfel clar nu sunt miscari. */
		if (move.charAt(0) < 'a' || move.charAt(0) > 'h' ||
			move.charAt(2) < 'a' || move.charAt(2) > 'h') {
			return false;
		}

		return true;
	}

	public static long[] convertMove(String move) {
		long[] convertedMove = new long[2];
		
		int initialX = charToNumber(move.charAt(0));
		int initialY = Character.getNumericValue(move.charAt(1));
		int finalX   = charToNumber(move.charAt(2));
		int finalY   = Character.getNumericValue(move.charAt(3));
		
		long initialPosition = 1L << (8L * (initialY - 1));
		initialPosition = initialPosition << (initialX - 1);
		
		/* Delete comment for debugging.
		System.out.println("#DEBUG for convert move: -------------");
		System.out.println("#The move: " + move);
		System.out.println("#Initial X: " + initialX);
		System.out.println("#Initial Y: " + initialY);
		System.out.println("#Final X: " + finalX);
		System.out.println("#Final Y: " + finalY);
		System.out.print(("#Initial Position: "));
		System.out.println(8 * (initialY - 1) + (initialX - 1));
		System.out.print(("#Final Position: "));
		System.out.println(8 * (finalY - 1) + (finalX - 1));
		System.out.println("#-------------------------------------");
		*/
		
		long finalPosition = 1L << (8L * (finalY - 1));
		finalPosition = finalPosition << (finalX - 1);
		
		convertedMove[0] = initialPosition;
		convertedMove[1] = finalPosition;

		return convertedMove;
	}	

	public static void unsetPosition(long[] attacker, int index, long pos) {
		attacker[index] = attacker[index] & (~pos);
	}

	public static long getPositionMask(long position, long[] masks) {
		for (int i = 0 ; i < masks.length; i++) {
			if ((position & masks[i]) != 0) {
				return masks[i];
			}
		}
		return 0L;
	}

	public static String getFileString(long filePosition) {

		if (filePosition == 0x8080808080808080L) {
			return "a";
		} else if (filePosition == 0x4040404040404040L) {
			return "b";
		} else if (filePosition == 0x2020202020202020L) {
			return "c";
		} else if (filePosition == 0x1010101010101010L) {
			return "d";
		} else if( filePosition == 0x0808080808080808L) {
			return "e";
		} else if (filePosition == 0x0404040404040404L) {
			return "f";
		} else if (filePosition == 0x0202020202020202L) {
			return "g";
		} else if (filePosition == 0x0101010101010101L) {
			return "h";
		}
		return null;
	}

	public static String getRankString(long rankPosition) {

		if (rankPosition == 0x00000000000000FFL) {
			return "1";
		} else if (rankPosition == 0x000000000000FF00L) {
			return "2";
		} else if (rankPosition == 0x0000000000FF0000L) {
			return "3";
		} else if (rankPosition == 0x000000FF00000000L) {
			return "4";
		} else if( rankPosition == 0x0000FF0000000000L) {
			return "5";
		} else if (rankPosition == 0x0000FF0000000000L) {
			return "6";
		} else if (rankPosition == 0x00FF000000000000L) {
			return "7";
		} else if (rankPosition == 0xFF00000000000000L) {
			return "8";
		}
		return null;
	}


	/* long mask1 = a7, long mask2 = a6 */
	public static String convertPositions(long[] positions) {
		long currentPosition = positions[0];
		long nextPosition = positions[1];

		long initialFileToBeConvert = getPositionMask(currentPosition, Bitboard.FILES); //a
		long initialRankToBeConvert = getPositionMask(currentPosition, Bitboard.RANKS);//7

		long lastFileToBeConvert = getPositionMask(nextPosition, Bitboard.FILES);//a
		long lastRankToBeConvert = getPositionMask(nextPosition, Bitboard.RANKS);//6

		String move = "";

		move = move.concat(getFileString(initialFileToBeConvert));
		move = move.concat(getRankString(initialRankToBeConvert));
		move = move.concat(getFileString(lastFileToBeConvert));
		move = move.concat(getRankString(lastRankToBeConvert));

		return move;
	}

	public static void setPosition(long[] attacker, int index, long[] attacked, long pos) {
		attacker[index] = attacker[index] | pos;

		for(int i = 0; i < attacked.length; i++) {
			if((attacked[i] & pos) != 0) {
				attacked[i] = attacked[i] & (~pos);
			}
		}
	}
}
