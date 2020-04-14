
public class Magic {
	public static long[] raysN = new long[64];
	public static long[] raysE = new long[64];
	public static long[] raysS = new long[64];
	public static long[] raysW = new long[64];
	public static long[] raysNE = new long[64];
	public static long[] raysNW = new long[64];
	public static long[] raysSE = new long[64];
	public static long[] raysSW = new long[64];

	public static final long diagPr = 0x8040201008040201L;
	public static final long diagSc = 0x102040810204080L;

	public static void generateRays() {
		long[] RANKS = Bitboard.RANKS;
		long[] FILES = Bitboard.FILES;

		// raysE and raysW
		for(int i = 0; i < 8; i++) {
			long rank = RANKS[i];

			raysE[i * 8] = rank & (~FILES[7]);
			raysW[i * 8 + 7] = rank & (~FILES[0]);

			for(int j = 1; j < 8; j++) {
				raysE[i * 8 + j] = ((raysE[i * 8 + j - 1] & (~FILES[0])) << 1);
				raysW[i * 8 + 7 - j] = ((raysW[i * 8 + 7 - j + 1] & (~FILES[7])) >>> 1);
			}
		}

		// for(int i = 0; i < 64; i++) {
		// 	System.out.println("Rays W de " + i);
		// 	Debug.displayMagicRays(raysW[i]);
		// }

		// raysS and raysN
		for(int i = 0; i < 8; i++) {
			long file = FILES[7 - i];

			raysN[i] = file & (~RANKS[0]);
			raysS[56 + i] = file & (~RANKS[7]);

			for(int j = 1; j < 8; j++) {
				raysN[i + 8 * j] = ((raysN[i + 8 * (j - 1)] & (~RANKS[7])) << 8);
				raysS[i + 8 * (7 - j)] = ((raysS[i + 8 * (7 - j + 1)] & (~RANKS[0])) >>> 8);
			}
		}

		// for(int i = 0; i < 64; i++) {
		// 	System.out.println("Rays S de " + i);
		// 	Debug.displayMagicRays(raysS[i]);
		// }

		// raysNE and raysSW
		for(int i = 0; i < 8; i++) {
			if(i == 0) {
				raysNE[i * 8] = diagPr & (~RANKS[0]);
				raysSW[63] = diagPr & (~RANKS[7]);
			} else {
				raysNE[i * 8] = ((raysNE[(i - 1) * 8] & (~RANKS[7])) << 8);
				raysSW[56 + (7 - i)] = ((raysSW[56 + (7 - i + 1)] & (~FILES[7])) >>> 1);
			}

			for(int j = 1; j < 8; j++) {
				raysNE[i * 8 + j] = ((raysNE[i * 8 + j - 1] & (~FILES[0])) << 1);
				raysSW[(7 - j) * 8 + (7 - i)] = ((raysSW[(7 - j + 1) * 8 + (7 - i)] & (~RANKS[0])) >>> 8);
			}
		}

		// for(int i = 0; i < 64; i++) {
		// 	System.out.println("Rays SW de " + i);
		// 	Debug.displayMagicRays(raysSW[i]);
		// }

		// raysNW and raysSE
		for(int i = 0; i < 8; i++) {
			if(i == 0) {
				raysNW[7 - i] = diagSc & (~RANKS[0]);
				raysSE[56] = diagSc & (~RANKS[7]);
			} else {
				raysNW[7 - i] = ((raysNW[7 - i + 1] & (~FILES[7])) >>> 1);
				raysSE[56 + i] = ((raysSE[56 + i - 1] & (~FILES[0])) << 1);
			}

			for(int j = 1; j < 8; j++) {
				raysNW[8 * j + 7 - i] = ((raysNW[8 * (j - 1) + 7 - i] & (~RANKS[7])) << 8);
				raysSE[8 * (7 - j) + i] = ((raysSE[8 * (7 - j + 1) + i] & (~RANKS[0])) >>> 8);
			}
		}

		// for(int i = 0; i < 64; i++) {
		// 	System.out.println("Rays SE de " + i);
		// 	Debug.displayMagicRays(raysSE[i]);
		// }
	}
}
