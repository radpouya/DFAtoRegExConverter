// Pouya Rad
// Dr. VanDeGrift
// CS 357 FA17
// 12/04/2017
// Programming Project: DFA to RegEx Converter.
// This class is a State object used to create a DFA.

import java.util.*;

public class State {

   public String name;
   public int stateID;
   
   public boolean isStartState;
   public boolean isAcceptState;
   
   public ArrayList<Transition> transitions;

   // Constructor for State object.
   // Accepts 4 arguments:
   //    String name - Name of the state being created
   //    int id - Numerical id representing a state
   //    boolean isStartState - Indicates if the state is the start state
   //    boolean isAcceptState - Indicates if the state is an accept state
   public State(String name, int id, boolean isStartState, boolean isAcceptState) {
      this.name = name;
      this.stateID = id;
      
      this.isStartState = isStartState;
      this.isAcceptState = isAcceptState;
      
      // Used to store all of the transitions for the state, as Transition objects.
      this.transitions = new ArrayList<Transition>();
   }
   
   // Adds a new Transition object to the State's list of transitions.
   // Accepts 2 arguments:
   //    String destName - The name of the destination state
   //    String transSymbol - The symbol that the transition is made on
   public void addTransition(String destName, String transSymbol) {
      this.transitions.add(new Transition(destName, transSymbol));
   }
   
   // Adds a new Transition object to the State's list of transitions.
   // Accepts a Transition object as an argument to simply add it to the list of transitions.
   public void addTransition(Transition t) {
      transitions.add(t);
   }
  
   
   // Prints the attributes of this state. 
   public void printState() {
      System.out.println("Name: " + this.name);
      System.out.println("ID: " + this.stateID);
      
      System.out.println("Start State?: " + this.isStartState);
      System.out.println("Accept State?: " + this.isAcceptState);
      
      System.out.println("Transitions (Origin,Destination,Symbol): ");
      if(this.transitions.isEmpty()) {
         System.out.println("    None.");
      } else {
         for(Transition t : this.transitions) {
            System.out.print("   (" + this.name + "," + t.destinationStateName 
                             + "," + t.transSymbol + ") ; ");
         }
      }   
      System.out.println();
   }
   
   public boolean compareTo(State s) {
      if(this.name.equals(s.name)) {
         return true;
      } else {
         return false;
      }
   }
  

}