/**
 * 
 */

/**
 * @author Nitin
 *
 */
public class Constants {
	public static final int GREEDY = 1;
	public static final int MINIMAX = 2;
	public static final int ALPHABETA = 3;
	public static final int COMPETITION = 4;
	
	public static final char[] COLUMN =  {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
	
	public static final int[][] COST_MATRIX = 
		{{99, -8, 8, 6, 6, 8, -8, 99},
		 {-8, -24, -4, -3, -3, -4, -24, -8},
		 {8, -4, 7, 4, 4, 7, -4, 8},
		 {6, -3, 4, 0, 0, 4, -3, 6},
		 {6, -3, 4, 0, 0, 4, -3, 6},
		 {8, -4, 7, 4, 4, 7, -4, 8},
		 {-8, -24, -4, -3, -3, -4, -24, -8},
		 {99, -8, 8, 6, 6, 8, -8, 99}};
}
