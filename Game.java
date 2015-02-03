/**
 * 
 */

/**
 * @author Nitin
 *
 */
public class Game {
	private int taskNo;
	private char player;
	private char oppPlayer;
	private int[][] costMatrix;
	
	public Game(int taskNo, char player, char oppPlayer){
		this.taskNo = taskNo;
		this.player = player;
		this.oppPlayer = oppPlayer;
		this.costMatrix = Constants.COST_MATRIX;
	}

	/**
	 * @return the taskNo
	 */
	public int getTaskNo() {
		return taskNo;
	}

	/**
	 * @return the player
	 */
	public char getPlayer() {
		return player;
	}

	/**
	 * @return the oppPlayer
	 */
	public char getOppPlayer() {
		return oppPlayer;
	}

	/**
	 * @return the costMatrix
	 */
	public int[][] getCostMatrix() {
		return costMatrix;
	}
}
