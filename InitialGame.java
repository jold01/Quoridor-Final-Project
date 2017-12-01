import java.util.*;
import java.io.*;

/**
 * InitialGame is a serialized class that contains a list of player names that were entered before 
 * the game begins, and contains methods to update and access the list, and to retrieve the total
 * number of players in the game. <p>
 *
 * @author Catherine Poggioli 
 * @author John Hill
 * @author Jack Old
 * @author David Luong
 *
 * @group# 06 
 * @course ISTE 121
 * @instructor Michael Floeser
 *
 * @version 2017-11-30
 */

public class InitialGame implements Serializable {
   /**
    * @attributes: the number of players, and a list of player names
    */
   int playerAmount;
   ArrayList<String> pNames = new ArrayList<String>();
   
   /**
    * Default Constructor
    */
   public InitialGame(){
      
   } //End of default constructor
   
   /**
    * @method setArray() - sets the list of player names, and its size
    * @type mutator
    */
   public void setArray(ArrayList<String> _pNames){
      this.pNames = _pNames;
      this.playerAmount = _pNames.size();
   }
   
   /**
    * @method getArray() - retrieves the list of player names
    * @type accessor
    */   
   public ArrayList<String> getArray(){
      return pNames;
   }
   
   /**
    * @method getPlayerAmount() - retrieves the number of players (the list size)
    * @type accessor
    */   
   public int getPlayerAmount(){
      return playerAmount;
   }

} //End of class InitialGame
