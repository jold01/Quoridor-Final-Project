import java.util.*;
import java.io.*;
/**
 * PlayerName is a serialized class that contains methods for setting the player name by index,
 * and retrieving the player name. <p>
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
public class PlayerName implements Serializable {
   /**
    * @attributes: String literal for player name, and an integer value for player index
    */     
   String name;
   int index;
   /**
    * Parameterized Constructor accepts a player name, and saves it as an attribute.
    * @params name_ - player name
    */     
   public PlayerName(String name_){
      name = name_;
   } //End of constructor
   /**
    * @method getPlayerName() - retrieves the player name 
    * @type accessor
    */    
   public String getPlayerName(){
      return name;
   }
   /**
    * @method setIndex() - sets the player's index
    * @type mutator
    */ 
   public void setIndex(int _index){
      this.index = _index;
   }
   /**
    * @method getIndex() - retrieves the player's index 
    * @type accessor
    */ 
   public int getIndex(){
      return index;
   }

} //End of class playerInfo 