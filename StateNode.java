import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Nitin
 *
 */
public class StateNode {
	private String move;
	private StateNode parent;
	private LinkedList<StateNode> children;
	private int depth;//, alpha, beta;
	private char[][] stateMatrix = new char[8][8];
	
	public StateNode(String move, StateNode parent, int depth, char[][] stateMatrix) {
		// TODO Auto-generated constructor stub
		this.move = move;
		this.parent = parent;
		this.depth = depth;
		this.stateMatrix = stateMatrix;
	}

	/**
	 * @return the nodeName
	 */
	public String getMove() {
		return move;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setMove(String move) {
		this.move = move;
	}

	/**
	 * @return the parent
	 */
	public StateNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(StateNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public LinkedList<StateNode> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(LinkedList<StateNode> children) {
		this.children = children;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return the stateMatrix
	 */
	public char[][] getStateMatrix() {
		return stateMatrix;
	}

	/**
	 * @param stateMatrix the stateMatrix to set
	 */
	public void setStateMatrix(char[][] stateMatrix) {
		this.stateMatrix = stateMatrix;
	}
	
	/**
	 * @return the stateMatrix at (i,j) = X/O/*
	 */
	public char getLocationValue(int i, int j) {
		return stateMatrix[i][j];
	}
}
