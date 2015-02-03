import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Nitin
 *
 */
public class MiniMax {
	private static LinkedList<String> traverseLog = new LinkedList<String>();

	public static OutputValueObj adversarialSearchAlgorithms(Integer taskNo, char player, Integer cutOffDepth, char[][] stateMatrix) {
		
		//find the opponent player character representation X or O
		final char oppPlayer=oppositionPlayer(player, stateMatrix);
		
		//game storing the task number, player (X or O) and opponent player (O or X) 
		final Game game = new Game(taskNo, player, oppPlayer);
		
		//create traverse log
		traverseLog.add("Node,Depth,Value");
		
		//get the child nodes of root
		LinkedList<StateNode> childrenNodes =  Operators(game, new StateNode("root", null, 0, stateMatrix), cutOffDepth, player, oppPlayer);
		
		//check if game is over before checking next move
		if(childrenNodes==null){

			//if greedy
			if(taskNo==Constants.GREEDY)
				return new OutputValueObj(stateMatrix, null);
			
			//if minimax
			int evalValue = Eval(game, stateMatrix);
		
			//traverseLog.add("root,0,-Infinity");
			traverseLog.add("root,0,"+evalValue);
			
			return new OutputValueObj(stateMatrix, traverseLog);
		}

		//store utility values of each move
		int[] values = new int[childrenNodes.size()];
		
		//store output move
		char[][] moveMatrix = null;
		
		int value = Integer.MIN_VALUE;
		
		//find the values of each move played by min
		for(int op=0; op<childrenNodes.size(); op++){ 
			StateNode curChildNode = childrenNodes.get(op); 
			
			traverseLog.add(curChildNode.getParent().getMove()+","+curChildNode.getParent().getDepth()+","+((value==Integer.MIN_VALUE)?"-Infinity":value));
			
			values[op] = minValue(game, curChildNode, cutOffDepth);
			
			if(value<values[op]){
				value=values[op];
				moveMatrix=curChildNode.getStateMatrix();
			}
		}
		
		traverseLog.add("root"+","+"0"+","+((value==Integer.MIN_VALUE)?"-Infinity":value));

		//if greedy
		if(taskNo==Constants.GREEDY)
			return new OutputValueObj(moveMatrix, null);
		
		//if minimax
		return new OutputValueObj(moveMatrix, traverseLog);
	}

	//find the opponent player's character
	private static char oppositionPlayer(char player, char[][] stateMatrix) {
		for(int i=0; i<8; i++)
			for(int j=0; j<8; j++)
				if(stateMatrix[i][j]!=player && stateMatrix[i][j]!='*')
					return stateMatrix[i][j];
		return (player=='X')?'O':'X';
	}

	private static int minValue(final Game game, StateNode state, int cutOffDepth) {
		//determine the children nodes, returns null if cutOff depth is reached
		LinkedList<StateNode> childrenStateNodes =  Operators(game, state, cutOffDepth, game.getOppPlayer(), game.getPlayer());
		
		//cutOffTest(state, game, cutOffDepth)
		if(childrenStateNodes==null){
			//get the evaluation function value
			int evalValue = Eval(game, state.getStateMatrix());
			
			//traverse log
			traverseLog.add(state.getMove()+","+state.getDepth()+","+evalValue);
			
			//return the value
			return evalValue;
		}
		
		int value=Integer.MAX_VALUE;
		
		for(int op=0; op<childrenStateNodes.size(); op++){
			StateNode curChildNode = childrenStateNodes.get(op);
			
			//traverse log
			traverseLog.add(state.getMove()+","+state.getDepth()+","+((value==Integer.MAX_VALUE)?"Infinity":value));
			
			//beta - min(beta, maxValue());
			value = Math.min(value, maxValue(game, curChildNode, cutOffDepth));
		}
		
		//traverse log
		traverseLog.add(state.getMove()+","+state.getDepth()+","+((value==Integer.MAX_VALUE)?"Infinity":value));
		return value;
	}

	public static int maxValue(final Game game, StateNode state, int cutOffDepth){
		//determine the children nodes, returns null if cutOff depth is reached
		LinkedList<StateNode> childrenStateNodes =  Operators(game, state, cutOffDepth, game.getPlayer(), game.getOppPlayer());
		
		//cutOffTest(state, game, cutOffDepth)
		if(childrenStateNodes==null){
			//get the evaluation function value
			int evalValue = Eval(game, state.getStateMatrix());
			
			//traverse log
			traverseLog.add(state.getMove()+","+state.getDepth()+","+evalValue);
			
			//return the value
			return evalValue;
		}
		
		int value=Integer.MIN_VALUE;
		
		for(int op=0; op<childrenStateNodes.size(); op++){
			StateNode curChildNode = childrenStateNodes.get(op);
			
			//traverse log
			traverseLog.add(state.getMove()+","+state.getDepth()+","+((value==Integer.MIN_VALUE)?"-Infinity":value));
			
			//aplha = max(alpah, minValue())
			value = Math.max(value, minValue(game, curChildNode, cutOffDepth));
		}
		
		//traverse log
		traverseLog.add(state.getMove()+","+state.getDepth()+","+((value==Integer.MIN_VALUE)?"-Infinity":value));
		return value;
	}

	private static int Eval(Game game, char[][] stateMatrix) {
		//find the evaluation function value
		int value = 0;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				if(stateMatrix[i][j]=='*')	
					continue;
				else if(stateMatrix[i][j]==game.getPlayer())
					value += game.getCostMatrix()[i][j];
				else
					value -= game.getCostMatrix()[i][j];
			}
		return value;
	}

	private static LinkedList<StateNode> Operators(Game game, StateNode parentState, int cutOffDepth, char player, char oppPlayer) {
		if(parentState.getDepth()==cutOffDepth)
			return null;

		LinkedList<StateNode> childrenStateNodes = new LinkedList<StateNode>();

		//check if there is a valid move for player
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				if(parentState.getLocationValue(i, j)=='*'){
					if(valid(i, j, parentState.getStateMatrix(), player, oppPlayer))
						childrenStateNodes.add(new StateNode(move(i,j), parentState, parentState.getDepth()+1, createChildStateMatrix(player, oppPlayer, i, j, parentState.getStateMatrix())));
				}
			}
		if (childrenStateNodes.size() > 0)
			return childrenStateNodes;
		
		//check if pass SHOULD NOT be passed
		boolean noFreeLocOnBoard=true;
		boolean allPlayersCoinsOnBoard=true;
		boolean allOppPlayersCoinsOnBoard=true;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++){
				if(parentState.getLocationValue(i, j)=='*' && noFreeLocOnBoard)//"*" found so false
						noFreeLocOnBoard=false;
				else if(parentState.getLocationValue(i, j)==oppPlayer && allPlayersCoinsOnBoard)//oppPlayer piece found so false
					allPlayersCoinsOnBoard=false;
				else if(parentState.getLocationValue(i, j)==player && allOppPlayersCoinsOnBoard)//player piece found so false
					allOppPlayersCoinsOnBoard=false;
			}
		//if no location available on board or only one player's pieces on the board 
		if(noFreeLocOnBoard || allPlayersCoinsOnBoard || allOppPlayersCoinsOnBoard)
			return null;
		
		//if no, check if the player's previous move was "pass" and parent's move was "pass", if yes then return null
		if(parentState.getMove()=="pass" && parentState.getParent().getMove()=="pass")
			return null;
		/*boolean flag=false;
		for(int i=0;i<8;i++){
			if(flag)	break;
			for(int j=0;j<8;j++){
				if(parentState.getLocationValue(i, j)=='*'){
					if(valid(i, j, parentState.getStateMatrix(), oppPlayer, player)){
						childrenStateNodes.add(new StateNode("pass", parentState, parentState.getDepth()+1, parentState.getStateMatrix()));
						flag = true;
						break;
					}
				}
			}
		}*/
		
		//else return "pass"
		childrenStateNodes.add(new StateNode("pass", parentState, parentState.getDepth()+1, parentState.getStateMatrix()));
		return childrenStateNodes;
	}

	private static boolean valid(int i, int j, final char[][] stateMatrix, char player, char oppPlayer) {
		//up
		if(i-1>0 && stateMatrix[i-1][j]==oppPlayer){
			for(int x=2; i-x>=0; x++){
				if(stateMatrix[i-x][j]==oppPlayer)
					continue;
				else if(stateMatrix[i-x][j]==player)
					return true;
				else
					break;
			}
		}
		
		//down
		if(i+1<7 && stateMatrix[i+1][j]==oppPlayer){
			for(int x=2; i+x<=7; x++){
				if(stateMatrix[i+x][j]==oppPlayer)
					continue;
				else if(stateMatrix[i+x][j]==player)
					return true;
				else
					break;
			}
		}
		//left
		if(j-1>0 && stateMatrix[i][j-1]==oppPlayer){
			for(int x=2; j-x>=0; x++){
				if(stateMatrix[i][j-x]==oppPlayer)
					continue;
				else if(stateMatrix[i][j-x]==player)
					return true;
				else
					break;
			}
		}
		
		//right
		if(j+1<7 && stateMatrix[i][j+1]==oppPlayer){
			for(int x=2; j+x<=7; x++){
				if(stateMatrix[i][j+x]==oppPlayer)
					continue;
				else if(stateMatrix[i][j+x]==player)
					return true;
				else
					break;
			}
		}
		
		//up-left
		if(i-1>0 && j-1>0 && stateMatrix[i-1][j-1]==oppPlayer){
			for(int x=2; i-x>=0 && j-x>=0; x++){
				if(stateMatrix[i-x][j-x]==oppPlayer)
					continue;
				else if(stateMatrix[i-x][j-x]==player)
					return true;
				else
					break;
			}
		}
		
		//down-right
		if(i+1<7 && j+1<7 && stateMatrix[i+1][j+1]==oppPlayer){
			for(int x=2; i+x<=7 && j+x<=7; x++){
				if(stateMatrix[i+x][j+x]==oppPlayer)
					continue;
				else if(stateMatrix[i+x][j+x]==player)
					return true;
				else
					break;
			}
		}
		
		//up-right
		if(i-1>0 && j+1<7 && stateMatrix[i-1][j+1]==oppPlayer){
			for(int x=2; i-x>=0 && j+x<=7; x++){
				if(stateMatrix[i-x][j+x]==oppPlayer)
					continue;
				else if(stateMatrix[i-x][j+x]==player)
					return true;
				else
					break;
			}
		}
		
		//down-left
		if(i+1<7 && j-1>0 && stateMatrix[i+1][j-1]==oppPlayer){
			for(int x=2; i+x<=7 && j-x>=0; x++){
				if(stateMatrix[i+x][j-x]==oppPlayer)
					continue;
				else if(stateMatrix[i+x][j-x]==player)
					return true;
				else
					break;
			}
		}
		return false;
	}

	private static char[][] createChildStateMatrix(char player, char oppPlayer, int i, int j, final char[][] stateMatrix) {
		char[][] newStateMatrix = new char[8][8];
		for(int a=0; a<8; a++)
			for(int b=0; b<8; b++)
				newStateMatrix[a][b] = stateMatrix[a][b];
		newStateMatrix[i][j] = player;

		//above new position
		if(i-1>0 && newStateMatrix[i-1][j]==oppPlayer){
			for(int x=2; i-x>=0; x++){
				if(newStateMatrix[i-x][j]==oppPlayer)
					continue;
				else if(newStateMatrix[i-x][j]==player){
					for(int y=i-x+1; y<i; y++){
						newStateMatrix[y][j] = player;
					}
					break;
				}
				else
					break;
			}
		}
		
		//below new position
		if(i+1<7 && newStateMatrix[i+1][j]==oppPlayer){
			for(int x=2; i+x<=7; x++){
				if(newStateMatrix[i+x][j]==oppPlayer)
					continue;
				else if(newStateMatrix[i+x][j]==player){
					for(int y=i+x-1; y>i; y--){
						newStateMatrix[y][j] = player;
					}
					break;
				}
				else
					break;					
			}
		}
		
		//left of new position
		if(j-1>0 && newStateMatrix[i][j-1]==oppPlayer){
			for(int x=2; j-x>=0; x++){
				if(newStateMatrix[i][j-x]==oppPlayer)
					continue;
				else if(newStateMatrix[i][j-x]==player){
					for(int y=j-x+1; y<j; y++){
						newStateMatrix[i][y] = player;
					}
					break;
				}
				else
					break;
			}
		}
		
		//right of new position
		if(j+1<7 && newStateMatrix[i][j+1]==oppPlayer){
			for(int x=2; j+x<=7; x++){
				if(newStateMatrix[i][j+x]==oppPlayer)
					continue;
				else if(newStateMatrix[i][j+x]==player){
					for(int y=j+x-1; y>j; y--){
						newStateMatrix[i][y] = player;
					}
					break;
				}
				else
					break;
			}
		}
		
		//left-top of new position
		if(i-1>0 && j-1>0 && newStateMatrix[i-1][j-1]==oppPlayer){
			for(int x=2; i-x>=0 && j-x>=0; x++){
				if(newStateMatrix[i-x][j-x]==oppPlayer)
					continue;
				else if(newStateMatrix[i-x][j-x]==player){
					for(int y=i-x+1, z=j-x+1; y<i && z<j; y++, z++){
						newStateMatrix[y][z] = player;
					}
					break;
				}
				else
					break;
			}
		}
		
		//right-bottom of new position
		if(i+1<7 && j+1<7 && newStateMatrix[i+1][j+1]==oppPlayer){
			for(int x=2; i+x<=7 && j+x<=7; x++){
				if(newStateMatrix[i+x][j+x]==oppPlayer)
					continue;
				else if(newStateMatrix[i+x][j+x]==player){
					for(int y=i+x-1, z=j+x-1; y>i && z>j; y--, z--){
						newStateMatrix[y][z] = player;
					}
					break;
				}
				else
					break;
			}
		}
		
		//right-top of new position
		if(i-1>0 && j+1<7 && newStateMatrix[i-1][j+1]==oppPlayer){
			for(int x=2; i-x>=0 && j+x<=7; x++){
				if(newStateMatrix[i-x][j+x]==oppPlayer)
					continue;
				else if(newStateMatrix[i-x][j+x]==player){
					for(int y=i-x+1, z=j+x-1; y<i && z>j; y++, z--){
						newStateMatrix[y][z] = player;
					}
					break;
				}
				else
					break;
			}
		}
		
		//left-bottom of new position
		if(i+1<7 && j-1>0 && newStateMatrix[i+1][j-1]==oppPlayer){
			for(int x=2; i+x<=7 && j-x>=0; x++){
				if(newStateMatrix[i+x][j-x]==oppPlayer)
					continue;
				else if(newStateMatrix[i+x][j-x]==player){
					for(int y=i+x-1, z=j-x+1; y>i && z<j; y--, z++){
						newStateMatrix[y][z] = player;
					}
					break;
				}
				else
					break;
			}
		}
		
		return newStateMatrix;
	}

	//generate move string
	private static String move(int i, int j) {
		return Constants.COLUMN[j]+Integer.toString(i+1);
	}

}
