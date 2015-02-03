import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 * 
 */

/**
 * @author Nitin
 *
 */
public class agent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Integer taskNo;
		final char player;
		final Integer cutOffDepth;
		final char[][] stateMatrix = new char[8][8];
		char ch;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("input.txt")));
			taskNo = Integer.parseInt(br.readLine());
			player=br.readLine().charAt(0);
			cutOffDepth = Integer.parseInt(br.readLine());
			for (int i = 0; i < 8; i++){
				for (int j = 0; j < 8; j++){
					do{
						ch =(char)br.read();
					}while(ch!='*' && ch!='O' && ch!='X');
					stateMatrix[i][j] = ch;
				}
			}
			br.close();
			
			/*System.out.println("The input is as follows:");
			System.out.println(taskNo);
			System.out.println(player);
			System.out.println(cutOffDepth);
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++)
					System.out.print(stateMatrix[i][j]);
				System.out.println();
			}
			System.out.println("-----------------End of input----------------------");*/
			
			PrintWriter writer = new PrintWriter("output.txt");
			OutputValueObj output = null;
			
			switch(taskNo){
			case Constants.GREEDY: output = MiniMax.adversarialSearchAlgorithms(taskNo, player, 1, stateMatrix); 
					//AdversarialSearchAlgorithms.adversarialSearchAlgorithms(taskNo, player, 1, stateMatrix);
					break;
			case Constants.MINIMAX: output = MiniMax.adversarialSearchAlgorithms(taskNo, player, cutOffDepth, stateMatrix);
					//AdversarialSearchAlgorithms.adversarialSearchAlgorithms(taskNo, player, cutOffDepth, stateMatrix);
					break;
			case Constants.ALPHABETA: output = AlphaBeta.adversarialSearchAlgorithms(taskNo, player, cutOffDepth, stateMatrix); 
					//AdversarialSearchAlgorithms.adversarialSearchAlgorithms(taskNo, player, cutOffDepth, stateMatrix);
					break;
			/*case Constants.COMPETITION: output = AdversarialSearchAlgorithms.adversarialSearchAlgorithms(taskNo, player, cutOffDepth, stateMatrix);
					break;*/
			default: writer.print("Error in Input.");
			}
			
			if(output!=null){
				char[][] moveMatrix = output.getMoveMatrix();
				for(int i=0;i<8;i++){
					for(int j=0;j<8;j++)
						writer.print(moveMatrix[i][j]);
					if(i<7)		writer.println();
				}
				
				if(taskNo==Constants.MINIMAX || taskNo==Constants.ALPHABETA){
					writer.println();
					LinkedList<String> traverseLog = output.getTraverseLog();
					for(int i=0; i<traverseLog.size(); i++){
						writer.print(traverseLog.get(i));
						if(i<traverseLog.size()-1)		writer.println();
					}
				}
			}
			
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print("Error in Input.");
			return;
		}
	}
}
