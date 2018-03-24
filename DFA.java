// Pouya Rad
// Dr. VanDeGrift
// CS 357 FA17
// 12/04/2017
// Programming Project: DFA to RegEx Converter
// This class is a DFA object, comprised of State objects, which also include Transition objects.
// It stores a completed DFA. 

import java.util.*;

public class DFA {
   
   // List of all State objects.
   public ArrayList<State> statesList;
   public int numStates;
   
   // An instance of the Start State for the DFA
   public State startState;
   
   // A list of all states that are accepting states
   public ArrayList<State> acceptStates;
   
   // DFA's alphabet; no duplicates allowed.
   public Set<String> alphabet;
   // All of the DFA's transitions. Key = Origin State; Value = ArrayList of Transition Objects
   // for that State.
   public Map<State, ArrayList<Transition>> allTransitions;
   
   
   // Constructor for DFA object.
   // Accepts a single arguments: an ArrayList which contains all of the State objects in the DFA
   public DFA(ArrayList<State> states, Set<String> alphabet) {
      this.statesList = states;
      this.numStates = this.statesList.size();
      
      this.startState = null;
      this.acceptStates = new ArrayList<State>();
      for(int i = 0; i < numStates; i++) {
         State currState = statesList.get(i);
         if(currState.isStartState) {
            if(startState != null) {
               throw new IllegalStateException("DFA has more than 1 Start State!");
            }
            this.startState = currState;
         }
         if(currState.isAcceptState) {
            this.acceptStates.add(currState);
         }
      } 
      
      this.alphabet = alphabet;
            
      this.allTransitions = new HashMap<State, ArrayList<Transition>>();
      for(State s : statesList) {
         this.allTransitions.put(s, s.transitions);
      }
      
      // Check to make sure DFA has the proper format.
      if(this.statesList.isEmpty()) {
         throw new IllegalStateException("DFA has no States!");
      } else if(this.startState == null) {
         throw new IllegalStateException("DFA has no Start State!");
      } else if(this.acceptStates.isEmpty()) {
         throw new IllegalStateException("DFA has no Accept States!");
      } else if(this.alphabet.isEmpty()) {
         throw new IllegalStateException("DFA has no Alphabet!");
      } else if(this.allTransitions.isEmpty()) {
         throw new IllegalStateException("DFA has no Transitions!");
      }
      
   }
   
   // Add a new state to the DFA.
   public void addState(String name, int id, boolean isStart, boolean isAccept) {
      State s = new State(name, id, isStart, isAccept);
      this.statesList.add(s);
   }
   
   // Add a new state to the DFA.
   public void addState(State s) {
      this.statesList.add(s);
   }
   
   public State getState(String name) {
      for(State s : this.statesList) {
         if(s.name.equals(name)) {
            return s;
         }
      }
      return null;
   }
   
   // Add a new transition to a state in the DFA.
   public void addTransition(String originName, String destName, String symbol) {
      State originState = getState(originName);
      ArrayList<Transition> trans = this.allTransitions.get(originState);      
      Transition t = new Transition(destName, symbol);
      originState.addTransition(t);
      this.allTransitions.remove(originState);
      this.allTransitions.put(originState, trans);
   }
   
   public void addToAlphabet(String symbol) {
      this.alphabet.add(symbol);
   }
   
   public DFA deepCopy() {
      ArrayList<State> dfaStates = new ArrayList<State>();
      for(State s : this.statesList) {
         dfaStates.add(s);
      }
      Set<String> alpha = new TreeSet<String>();
      for(String s : this.alphabet) {
         alpha.add(s);
      }
      DFA newDFA = new DFA(dfaStates, alpha);
      return newDFA;
   }
   
   public void printDFA() {
      System.out.println();
      System.out.println("+------------------------------------------+");
      for(State state : statesList) {
         state.printState();
         System.out.println();
      }
      System.out.println("+------------------------------------------+");
      System.out.println();
   }
   
   

}