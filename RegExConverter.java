// Pouya Rad
// Dr. VanDeGrift
// CS 357 FA17
// 12/04/2017
// Programming Project: DFA to RegEx Converter
// This class is an object that converts a DFA to a Regular Expression.

import java.util.*;

public class RegExConverter {

   public DFA initDFA;
   public DFA currDFA;
   public String regex;

   
   
   // Takes the given DFA and stores it as a field.
   public RegExConverter(DFA dfa) {
      this.initDFA = dfa;
      this.currDFA = this.initDFA;
      this.regex = generateRegEx();
      System.out.println("Regular Expression Generated from DFA:");
      System.out.println("" + this.regex);
   }
   
   public String generateRegEx() {
      String regEx = "";
      
      addNewStartAndAcceptStates();
      
      while(this.currDFA.statesList.size() > 2) {
         ripStates();
         this.currDFA.printDFA();
      }
      
      if(this.currDFA.statesList.size() == 2) {
         for(State s : this.currDFA.allTransitions.keySet()) {
            for(Transition t : this.currDFA.allTransitions.get(s)) {
               regEx += t.transSymbol;
            }
         }
      }
      
      return regEx;
   
   }
   
   public void addNewStartAndAcceptStates() {
      // Create the new Start State with the following attributes
      State newStart = new State("Start", 0, true, false);
      // Add the one transition it has on the empty string ($) to the original start state)
      newStart.addTransition(new Transition(this.currDFA.startState.name, "$"));
      // Update the current DFA StartState field to not have the original start state be the start state
      this.currDFA.startState.isStartState = false;
      // Set the old start state's boolean value to false within the list of states
      this.currDFA.statesList.get(this.currDFA.statesList.indexOf(this.currDFA.startState)).isStartState = false;
      // Add the new start state to the current DFA list of states
      this.currDFA.addState(newStart);
      // Assign DFA start state field to the newly created start state
      this.currDFA.startState = newStart;
       
      // Create the new accept state with the following attributes
      State newAccept = new State("Accept", this.initDFA.numStates + 1, false, true);
      // Go through the list of accept states and update them all so that they are no longer
      // accept states and add a new transition to the new accept state.
      this.currDFA.acceptStates.clear();
      // Same as above loop, but in the list of all states in the DFA.
      for(State s : this.currDFA.statesList) {
         if(s.isAcceptState) {
            s.isAcceptState = false;
            s.addTransition(new Transition(newAccept.name, "$"));
         }
      }
      // Add the new accept state to the list of states and list of accept states
      newAccept.transitions.clear();
      this.currDFA.addState(newAccept);
      this.currDFA.acceptStates.add(newAccept);
      
      this.currDFA.printDFA();
   }
   
   public void ripStates() {
   
      DFA thisDFA = this.currDFA.deepCopy();
      // Go through the list of states in the current DFA
      for(int i = 0; i < thisDFA.statesList.size(); i++) {
      
         // Grab a state from the list
         State rippedState = thisDFA.statesList.get(i);
         
         // Check to make sure that the grabbed state is not the Start or Accept state
         if(!rippedState.name.equals("Start") && !rippedState.name.equals("Accept")) {
            // List for the transitions of the state that we are about to rip.
            ArrayList<Transition> rippedStateTrans = new ArrayList<Transition>();
            
            // Go through all keys (states) in the Transition List for the DFA
            for(State s : thisDFA.allTransitions.keySet()) {
               
               // If the Transition List state's name is equal to the state we want to rip
               if(s.name.equals(rippedState.name)) {
                  // save the transitions for the state that we are ripping
                  rippedStateTrans = thisDFA.allTransitions.get(s);
                  // remove that state and its transitions from the DFA
                  thisDFA.allTransitions.remove(s);
                  // Break out of this loop because we removed the state we are ripping from the
                  // DFA's data structures
                  break;
               }
               
            }
            
            
            
            // Loop through the DFA's transitions again, this time with the ripped state removed
            for(State s : thisDFA.allTransitions.keySet()) {
               
               ArrayList<Transition> newTransitions = new ArrayList<Transition>();
               // Go through the list of transitions for the current state in the DFA to see if the 
               // newly ripped state is used in the transition, then fix.
               for(Transition t : thisDFA.allTransitions.get(s)) {
                  
                  if(t.destinationStateName.equals(rippedState.name)) {
                     ArrayList<String> symbols = new ArrayList<String>();
                     
                     String newDestName = "";
                     String newTransSymb = "";
                     
                     for(Transition t2 : rippedStateTrans) {
                        
                        if(t2.destinationStateName.equals(rippedState.name)) {
                           newTransSymb = star(newTransSymb + (t2.transSymbol));
                        } else {
                           
                        }
                        
                        newDestName = t2.destinationStateName;
                        newTransSymb += (t2.transSymbol + t.transSymbol);
                        
                        Transition newTrans = new Transition(newDestName, newTransSymb);
                        newTransitions.add(newTrans);
                                                
                     }
                     
                     
                  } else {
                     newTransitions.add(t);
                  }
                  ArrayList<Transition> dupes = new ArrayList<Transition>();
                  
                  for(Transition t2 : newTransitions) {
                     for(int j = 0; j < newTransitions.size(); j++) {
                        if(t2.destinationStateName.equals(newTransitions.get(j))) {
                           String newStr = union(t2.transSymbol, newTransitions.get(j).transSymbol);
                           String replacedName = t2.destinationStateName;
                           newTransitions.remove(t2);
                           newTransitions.remove(j);
                           newTransitions.add(new Transition(replacedName, newStr));
                        }
                     }
                  }
                  this.currDFA.allTransitions.put(s, newTransitions);
               } 
               
            }  
            
            // remove the state that was just chosen to be ripped from the DFA's list of states
            // (this is still the state index that we are currently on for the main for loop)
            thisDFA.statesList.remove(i);    
                        
         }
         
         this.currDFA = thisDFA;
      }
      
   }
   
   
   
   public String parenthize(String s) {
      return "(" + s + ")";
   }
   
   public String union(String s1, String s2) {
      String s3 = "" + s1 + " U " + s2;
      s3 = parenthize(s3);
      return s3;
   }
   
   public String star(String s) {
      String s1 = parenthize(s);
      s1 += "*";
      return s1;
   }
   public String concat(String s1, String s2) {
      String s3 = "" + s1 + s2;
      s3 = parenthize(s3);
      return s3;
   }
   
   public void printRegex() {
      System.out.println("Current Expression: " + this.regex);
   }
}