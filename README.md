### Reversi-game-playing-agent

####Description: Determines the next move for a player in the Reversi game using the Greedy, Minimax, and Alpha-Beta pruning algorithms with positional weight evaluation functions

Constants.COST_MATRIX gives the positional weights used in the evaluation function

####Input:
input.txt - describes the current state of the game.
<task#> Greedy = 1, MiniMax = 2, Alpha-beta = 3, Competition =4
<your player: X or O>
<cutting off depth >
<current state as follows:>
*: blank cell
X: Black player
O: White Player
Example:
2
X
2
********
********
********
***OX***
***XO***
********
********
********

####Output:
Greedy algorithm output format:
<next state>
Example:
********
********
***X****
***XX***
***XO***
********
********
********

Minimax algorithm output format:
<next state>
<traverse log>
Example:
********
********
***X****
***XX***
***XO***
********
********
********
Node,Depth,Value
root,0,-Infinity
d3,1,Infinity
c3,2,-3
.
.
.
.
e6,1,-3
root,0,-3

“Node”: is the node name which refers to the move that is made by the agent. For example, the player X places a piece at the position “d3”. The node name is d3 and has depth 1. Then, the player O places a piece at the position “c3” and has depth 2. 
There are two special node names: “root” and “pass”. “root” is the name for the root node. “pass” is the name for the special move “pass”. Agent can make the pass move only when it cannot make any valid move.
“Depth”: is the depth of the node. The root node has depth zero.
“Value”: is the value of the node. The value is initialized to “-Infinity” for the max node (agent) and “Infinity” for the min (agent’s opponent) node. The value will be updated when its children return the value to the node. The value of leaf nodes is the evaluated value, for example, c3,2,-3.0

The program should output the Alpha-Beta algorithm in the format:
<next state>
<traverse log>
Example:
********
********
***X****
***XX***
***XO***
********
********
********
Node,Depth,Value,Alpha,Beta
root,0,-Infinity,-Infinity,Infinity
d3,1,Infinity,-Infinity,Infinity
.
.
.
.
.
e6,1,-3,-3,-3
root,0,-3,-3,Infinity
Note: The Alpha-Beta traverse log has 5 columns - node, depth, alpha, beta. The description is same with the Minimax log, plus alpha and beta values in the Alpha-Beta traversal.