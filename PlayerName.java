import java.util.*;
import java.io.*;
/**
 * PlayerName is a serialized class that contains methods for setting the player name by index,
 * and retrieving the player name. <p>
 *
 * Group# 06 <p>
 * ISTE 121
 *
 *
 * @author Catherine Poggioli 
 * @author John Hill
 * @author Jack Old
 * @author David Luong
 *
 *
 * @version 2017-11-30
 */
public class PlayerName implements Serializable {
       
   String name; 
   int index;
   
   /**
    * Parameterized Constructor accepts a player name, and saves it as an attribute.
    * @param name_  player name
    */     
   public PlayerName(String name_){
      name = name_;
   } 
   
   
   /**
    * getPlayerName method - retrieves the player name 
    * 
    * @return the string name of the player
    */    
   public String getPlayerName(){
      return name;
   }
   
   
   /**
    * setIndex method - sets the player's index
    * 
    * @param _index sets the index of the player
    */ 
   public void setIndex(int _index){
      this.index = _index;
   }
   
   
   /**
    * getIndex - retrieves the player's index 
    *
    * @return player index
    */ 
   public int getIndex(){
      return index;
   }

} //End of class playerInfo 