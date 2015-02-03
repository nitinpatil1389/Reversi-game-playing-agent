/**
 * 
 */


import java.util.LinkedList;

/**
 * @author Nitin
 *
 */
public class OutputValueObj {
	private LinkedList<String> traverseLog;
	private char[][] moveMatrix;
	
	public OutputValueObj(char[][] moveMatrix, LinkedList<String> traverseLog) {
		this.traverseLog = traverseLog;
		this.moveMatrix = moveMatrix;
	}

	/**
	 * @return the traverseLog
	 */
	public LinkedList<String> getTraverseLog() {
		return traverseLog;
	}

	/**
	 * @return the moveMatrix
	 */
	public char[][] getMoveMatrix() {
		return moveMatrix;
	}
}
