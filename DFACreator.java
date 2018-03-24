// Pouya Rad
// Dr. VanDeGrift
// CS 357 FA17
// 12/04/2017
// Programming Project: DFA to RegEx Converter
// This class creates a DFA, either manually through user input or automatically via an input file.

import java.util.*;
import java.io.*;

public class DFACreator {

   public Scanner s;
   public boolean isManual;
   public DFA myDFA;
   
   public DFACreator() throws FileNotFoundException {
      this.s = new Scanner(System.in);
      isManual = introMessage();
      System.out.println("Please prepare to enter the data to create your DFA.");
      if(isManual) {
         myDFA = manuallyCreateDFA();
      } else {
         myDFA = automaticallyCreateDFA();
      }

   }
   

   public boolean introMessage() {
      System.out.println("Do you wish to manually enter the DFA data or use a file input? ");
      System.out.print("Please type \"Manual\" or \"Auto\". ");
      String response = s.nextLine();
      response = response.toLowerCase();
      
      if(response.startsWith("m")) {
         return true;
      } else {
         return false;
      }
   }
   
   public DFA manuallyCreateDFA() {      
      System.out.println("Please enter the alphabet of your DFA, with each symbol " + 
                         "seperated with only commas, and no spaces.");
      String alph = s.nextLine();                   
      String[] alpha = alph.split(",");
      Set<String> alphabet = new TreeSet<String>();
      for(int i = 0; i < alpha.length; i++) {
         alphabet.add(alpha[i]);
      }
      
      System.out.print("How many states does your DFA have? ");
      int numStates = s.nextInt();
      s.nextLine();
      
      ArrayList<State> states = new ArrayList<State>();
      for(int i = 1; i <= numStates; i++) {
         State state = createState(i, alphabet);
         states.add(state);
      }
      
      DFA dfa = new DFA(states, alphabet);
      
      return dfa;
   }
   
   public State createState(int stateNum, Set<String> alphabet) {
      System.out.print("What is the name of state #" + stateNum + "? ");
      String stateName = s.nextLine();
      
      int stateID = stateNum;
      
      boolean isStartBool = false;
      System.out.print("Is this the start state? [Y/N] ");
      String isStart = s.nextLine();
      isStart = isStart.toLowerCase();
      if(isStart.startsWith("y")) {
         isStartBool = true;
      }
       
      boolean isAcceptBool = false;
      System.out.print("Is this an accept state? [Y/N] ");
      String isAccept = s.nextLine();
      isAccept = isAccept.toLowerCase();
      if(isAccept.startsWith("y")) {
         isAcceptBool = true;
      }
      
      State state = new State(stateName, stateNum, isStartBool, isAcceptBool);
      
      for(String symb : alphabet) {
         System.out.print("What is the destination state name for transition symbol \"" + 
                          "" + symb + "\"? ");
         String destState = s.nextLine();
         state.addTransition(new Transition(destState, symb));
      }
      
      return state;
      
   }
   
   public DFA automaticallyCreateDFA() throws FileNotFoundException {
      DFA dfa = null;
      
      System.out.print("What is the .txt file name for the DFA data? ");
      String fileName = s.nextLine();
      Scanner input = new Scanner(new File(fileName));
      input.useDelimiter(",");
      
      ArrayList<String> stateNames = new ArrayList<String>();
      String startState = "";
      ArrayList<String> acceptStates = new ArrayList<String>();
      ArrayList<String> alphabet = new ArrayList<String>();
      Map<String, Map<String, String>> transitions = new TreeMap<String, Map<String, String>>();
      
      String label = "";
      String data = "";
      while(input.hasNextLine()) {
         label = input.nextLine();
         if(label.equalsIgnoreCase("Transitions:")) {
            break;
         }
         data = input.nextLine();
         Scanner lineRead = new Scanner(data);
         while(lineRead.hasNext()) {
            String element = lineRead.next();
            if(label.equalsIgnoreCase("States:")) {
               stateNames.add(element);
            } else if(label.equalsIgnoreCase("Start State:")) {
               startState += element;
            } else if(label.equalsIgnoreCase("Accept States:")) {
               acceptStates.add(element);
            } else if(label.equalsIgnoreCase("Alphabet:")) {
               alphabet.add(element);
            }
         }
         if(label.equalsIgnoreCase("Transitions:")) {
            while(input.hasNextLine()) {
               String trans = input.nextLine();
               String[] elements = trans.split(",");
               String origin = elements[0];
               String dest = elements[1];
               String symb = elements[2];
               Map<String, String> transi = new TreeMap<String, String>();
               transi.put(dest, symb);
               transitions.put(origin, transi);
            }
         }
         
      }
      return dfa;
   }
   
   public DFA getDFA() {
      return myDFA;
   }
  

}