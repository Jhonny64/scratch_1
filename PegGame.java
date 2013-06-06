import java.util.Scanner;
import java.util.Random;

/**
 * 
 * @author Jon Blount
 * This is the Peg Game class, the main class in this project used to solve the 
 * the "triangle puzzle game." It's responsibility is to 
 * initialize the game, make the "children" and run the game to find a solution. 
 * It uses a depth-first search currently, as it proved to be the most efficient 
 * way of finding a solution path. 
  * It's currently only a command line program. May add a GUI later...maybe...
  * ...if I feel like it. 
 		run from the command line with an integer argument ranging from 0-14. 
		i.e java PegGame 0 will start with the peg on the "top" missing.  
 * Upon running the game, the user will
 * be presented with a pictoral representation of the triangle peg game where
 * '1's represent pegs, and '0's represent holes. Each step shows the board after
 * a legal move was made (it's up to the user/ viewer to figure out how it was made)
 * 
 *
 * for information about the peg game: see the link below:
 * http://www.officeplayground.com/Peg-Game-P1691.aspx
 * 
 * for video : http://www.youtube.com/watch?v=9zkaTS5Pb7o&feature=fvwrel
 *
 */
public class PegGame extends AbstractGame <PegState>{
	
    
	 Scanner keyIn;//scanner declared for reading in user input. 

	 int [][] moveMatrix = {
			{0,1,3},{0,2,5},{1,3,6},{1,4,8},{2,5,9},{2,4,7},{3,4,5},{3,1,0},{3,7,12},{3,6,10},
			{4,8,13},{4,7,11},{5,2,0},{5,9,14},{5,4,3},{5,8,12},{6,7,8},{6,3,1},{7,4,2},{7,8,9},
			{8,4,1},{8,7,6},{9,5,2},{9,8,7},{10,6,3},{10,11,12},{11,7,4},{11,12,13},{12,7,3},
			{12,8,5},{12,13,14},{12,11,10},{13,8,4},{13,12,11},{14,9,5},{14,13,12}
	};// This is the table representing all possible in the game. Luckily it's easy to 				   hardcode them...
	
	public int missingPeg;//  the variable representing the starting missing peg in the game.   
	public PegState scratchState;// an extra or "temporary" state. 
	
	
	/* @constructor
     * Pass to the super constructor the size of the expected amount of children to be 				generated
     * and the type of search that we want to do. (See AbstractGame.java)
     *
     */

	public PegGame(int emptyPegSpace){
		
		super(40000,2);
		missingPeg = emptyPegSpace;
		scratchState = new PegState();
		keyIn = new Scanner(System.in);

	}
	
    /*
	*Initialize the game. 
     */

	public void initializeGame(){
		
		int i = 0;
		PegState beginState = makeInitialState();

		beginState.positions[missingPeg] = 0; //set the missing peg 
		startGame(beginState);
	
	}

	/*
     *
	 * Adds children by checking to see if a move is legal.   
     *
	 */
	public void addChildren(){
		
		for(int i = 1;i < moveMatrix.length; i++){

            //copy the current state into the temporary state. 
			scratchState.copy(currentState);

            
			boolean a = moveIsLegal(i,scratchState);
				if (a){
					scratchState.setBoard(moveMatrix[i][0],0);
					scratchState.setBoard(moveMatrix[i][1],0);
					scratchState.setBoard(moveMatrix[i][2],1);
					if (addNewState(scratchState)){
						scratchState = new PegState();
					}				
			}

			}
				
		}
	
	/*
     *
     * This is a method used to output a picture and update it with each move.
     *
     */

	public void prettyPicture (PegState st){
		
		int moveOn = 0;
		PegState a = st;
		a.pshiddyPicture();
        System.out.println("\n****** press  0 and return to continue ****");
        moveOn = keyIn.nextInt();
		
	}
	                             
	
	public PegState makeInitialState(){
		
		return new PegState();
		
	}
	
	
	
	public boolean goalState(){
		
		return (currentState.isFinal(currentState.positions));

	}

/*
 *
 *This method checks to see if a move is legal. A move is legal if and only if there 
 *is a peg adjacent to the current peg that can be jumped, and there is an empty space
 *on the other side of that peg.  
 *
 */

	public boolean moveIsLegal(int x,PegState a){
		int firstNum = moveMatrix[x][0]; // peg to be moved.
		int secondNum = moveMatrix[x][1];// peg to be jumped.  
		int thirdNum = moveMatrix[x][2]; // position to be filled with moving peg.
		if (a.positions[firstNum] == 1){ // check first number of indexed row.If there's a peg there...
			
			if (a.positions[secondNum] == 1){// check to see if there's a peg available for jumping.
				
				if (a.positions[thirdNum ] == 0){// then finally check to see if there's an empty space after it. 
					
					return true;
				}
				
					
			}//2nd IF
		}//1st IF

		return false;
	}
	
	
	
	public static void main (String [] args){
		
		int theMissingOne;//used to hold the value that is to be used for the empty peg.  
		Random myRand = new Random();//declare a new random value. 

        //if the user types some arguments at the command line,
        //set the missing peg to be the peg that the user selected. 
        //0 is the top peg, 14 is the bottom right peg

		if (args.length > 0){
			theMissingOne = new Integer (args[0]);
		}

        //if the user did not enter in any arguments, let them know, 
        //and then choose a random integer between 0 and 14 as the empty peg. 
		else{
			System.out.println();
			System.out.println("You didn't enter any arguments,");
			System.out.println("so a random integer will be used. ");
			System.out.println("Next time enter an integer 0 - 14 to decide");
			System.out.println("which hole is left empty!!1");
			System.out.println();
			theMissingOne = myRand.nextInt(14);
			
		}


        //declare a new peg game object. 
		PegGame pegGame = new PegGame (theMissingOne);
		pegGame.initializeGame();

        //if there is a solution found to the problem
        //let the user know, and begin to print the solution. 

		if (pegGame.search()){
			
			System.out.println("Solution Found");
			pegGame.printSolution();
		}

        //if there is no solution to the problem, 
        //let the user know, and print the stats (children generated, etc)
        //for the recent run. Control will probably never reach this part. 
		else
		{
			System.out.println("No Solution found");
		}
			pegGame.printStats();
	}

	

	



}
