// Pouya Rad
// Dr. VanDeGrift
// CS 357 FA17
// 12/04/2017
// Programming Project: DFA to RegEx Converter
// This class is a Transition object that a State object can possess. 

public class Transition {

   public String destinationStateName;
   public String transSymbol;
   
   // Constructor for Transition object. 
   // Accepts two Strings as arguments: 
   //    The destination state's name.
   //    The symbol that the transition is made on (from the state that this
   //    transition belongs to, to the destination state).
   public Transition(String destinationStateName, String transSymbol) {
      this.destinationStateName = destinationStateName;
      this.transSymbol = transSymbol;
   }

}