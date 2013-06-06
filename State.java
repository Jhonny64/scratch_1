public interface State 
{
     public int hashCode();
     public boolean equals(Object anotherState);
     public void copy(State anotherState);
     public State clone();
} 
