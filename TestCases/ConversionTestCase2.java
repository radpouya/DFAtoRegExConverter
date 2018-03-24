// Pouya Rad
// Dr. VanDeGrift
// CS 357 FA17
// 12/04/2017
// Programming Project: DFA to RegEx Converter.
// This is a second test case for the programming project.

import java.util.*;
import java.io.*;

public class ConversionTestCase2 {

   public static void main(String[] args) {
   
      ArrayList<State> states = new ArrayList<State>();
   
      State q0 = new State("q0", 1, true, false);
      q0.transitions.add(new Transition("q0", "0"));
      q0.transitions.add(new Transition("q1", "1"));
      states.add(q0);
      
      State q1 = new State("q1", 2, false, true);
      q1.transitions.add(new Transition("q1", "0"));
      q1.transitions.add(new Transition("q0", "1"));
      states.add(q1);
      
      Set<String> alpha = new TreeSet<String>();
      alpha.add("0");
      alpha.add("1");
      
      DFA dfa = new DFA(states, alpha);
      dfa.printDFA();
      
      
      RegExConverter rec = new RegExConverter(dfa);
      
      
   }

}