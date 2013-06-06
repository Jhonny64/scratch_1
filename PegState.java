import java.util.*;
import java.lang.Math;
/**
 *
 * @author Jon Blount
 * This is the PegState class.  I chose to represent the peg-board simply as an array, since 
 * there are 15 holes capable of only two states. The indexes represent available
 * slots.  A '1' represents a peg occupying said slot.  A '0' represents the current
 * slot as empty. 
 *
 */

public class PegState implements State {
	
	int [] positions;
	
	public PegState(){
		
		positions = new int [15];
		for(int i= 0; i<15; i++){
			
			positions[i] = 1;
	
		}
	}
		
		public PegState(int [] k){
					
			for (int kay = 0; kay < 15; kay ++){
				
				positions[kay] = k[kay];
				
			}
			
		
	}

//A very, very simple hashing method to represent the different states.    
// Didn't really need a highly sophisticated one, this worked fine. 
	public int hashCode(){
		
		int sum = 0;
		for (int i = 0; i < positions.length; i++){
			
			sum += ((positions[i] * Math.pow(2,i))% 32768);
			// 1 or 0 * 2^(position index) mod by 2^15. 
			
		}
		return sum;
		
	}
	

	public void copy(State anotherState){
		
		PegState a = (PegState) anotherState;
	    for (int i = 0;  i < 15; i++)
	          positions[i] = a.positions[i];
	    
	}
	
	public State clone(){
		
		PegState a = new PegState();
        a.copy(this); 
        return a;
		
	}
	
	public int getBoard(int i){
		
		return positions[i];
		
	}
	
	public void setBoard(int i, int value){
		
		positions[i] = value;
		
	}
	
	
	/**
	 * void pshiddyPicture()
	 * prints a picture of the current state. 
	 * 
	 */
	
	public void pshiddyPicture(){
		
		
		
		System.out.println("------------------------");
		int k=0;
		int spaceCount = 5;
		System.out.println();
		for(int i = 0; i <6; i++){
		for (int d = spaceCount; d>0; d--){
				System.out.print(" ");	
		}
		for(int j = 0; j <i; j++){
		System.out.print(positions[k]);
		k = k + 1;
		System.out.print(" ");
		}
		System.out.println("");
		spaceCount -= 1;
		}
		System.out.println("--------------------");
		System.out.println("'1' represents a peg");
		System.out.println("'0' represents a hole");
		}
	
	/**
	 * 
	 * 
	 * @param s an array (representing a board) from the state being checked.
	 * @return returns true if the state is a goal state. 
	 * if there is one peg left, then it's a final state. 
	 */
	
	public boolean isFinal(int [] s){
		
		
		int sum = 0;
		for (int i = 0; i< s.length; i++){
			
			sum += s[i];
			
		}
		
		if (sum == 1){
			return true; 
		}
		
		else {
			
			return false;
		}
		
		
	}
		



}
