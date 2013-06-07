import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*
 * @author Jon Blount
 * This is a template class used to hold the main search algorithms and data 
 * structures for similar puzzle games (with a defined start state and end state). 
 *
 */

public abstract class AbstractGame<StateType extends State>
{
    public static final int BFS = 1;//for a breadth first search
    public static final int DFS = 2;//for a depth first search
    private HashMap<StateType, StateType> htable;
    protected StateType currentState; 
    private int searchMethod;
    private Queue<StateType> open;
    int childrenAdded;
    int children;
    int pathLength;


    public AbstractGame (int size, int searchType)
    {
       if (size > 0)
       {
          htable = new HashMap<StateType,StateType>(size);
          searchMethod = searchType;
          open = new LinkedList<StateType>();
          childrenAdded = 0;
          children = 0;
          pathLength = 0;
       }
    }


    public void startGame(StateType startState)
    {
       currentState = startState;
       startGame();
    }


    public void startGame()
    {
       if (currentState == null)
          currentState = makeInitialState();
       open.add(currentState);
       htable.put(currentState, null);
    }

    public void printSolution()
    {
       printInReverse(currentState);
    }

   private void printInReverse(StateType someState)
   {
      StateType newState;

      if (someState != null)
      {
         pathLength++;
         newState = htable.get(someState);
         printInReverse(newState);
         prettyPicture(someState);
      }
   } 

/*
 * This search method returns true if 
 * a goal state is found. 
 *
 */

   public boolean search()
   {

       while(!open.isEmpty())
       {
          currentState = open.remove();
          if (goalState())
          {
            return true;
          }
          addChildren();
       }
       return false;
    }

/*
 *@addNewSTate
 */

   public boolean addNewState(StateType st)
   {
        children++;
        if (!htable.containsKey(st))
        {
           htable.put(st, currentState);
           if (searchMethod == DFS)
              ((LinkedList)open).push(st);
           else
              open.add(st);
           childrenAdded++;
           return true;
        }
        return false;
   }
/*
 *
 *For debugging purposes
 *
 */
   public void printStats()
   {
        System.out.println ("\n\n\n");
        System.out.println ("Path contained " + pathLength + " steps.");
        System.out.println ("Children generated " + children);
        System.out.println ("Unique children generated " + childrenAdded);
   }

//abstract methods to override 
   abstract public void addChildren();
 
   abstract public void prettyPicture(StateType st);

   abstract public boolean goalState();

   abstract public StateType makeInitialState();
}


