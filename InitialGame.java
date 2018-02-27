import java.util.*;
import java.io.*;

/**
 * InitialGame is a serialized class that contains a list of player names that were entered before 
 * the game begins, and contains methods to update and access the list, and to retrieve the total
 * number of players in the game. <p>
 * 
 * Group# 06 <p>
 * ISTE 121
 * 
 * @author Catherine Poggioli 
 * @author John Hill
 * @author Jack Old
 * @author David Luong
 *
 *
 * @version 2017-11-30
 */

public class InitialGame implements Serializable {

   int playerAmount; //number of players 
   ArrayList<String> pNames = new ArrayList<String>();
   
   /**
   * InitialGame Default Constructor
   */
   public InitialGame(){
      
   } 
   
   /**
    * setArray() method - sets the list of player names, and its size
    *
    * @param _pNames ArrayList of the player names
    */
   public void setArray(ArrayList<String> _pNames){
      this.pNames = _pNames;
      this.playerAmount = _pNames.size();
   }
   
   /**
    * getArray() method - retrieves the list of player names
    * 
    * @return ArrayList of playerNames
    */   
   public ArrayList<String> getArray(){
      return pNames;
   }
   
   /**
   * getPlayerAmount() method - retrieves the number of players
   * 
   * @return integer value of the number of players 
   */     
   public int getPlayerAmount(){
      return playerAmount;
   }

} //End of class InitialGame
