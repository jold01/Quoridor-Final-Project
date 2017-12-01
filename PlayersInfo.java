import java.util.*;
import java.io.*;

/**
 * PlayersInfo is a serialized class that contains methods for setting and retrieving the player 
 * name by index, and retrieving the player index. <p>
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

public class PlayersInfo implements Serializable {

   //A list of active players in the game
   ArrayList<String> players = new ArrayList<String>();
   
   /**
    * PlayersInfo default constructor
    */
   public PlayersInfo(){

   } //End of default constructor
   
   /**
    * @method setName() - sets the player name, and adds it to the array list
    * @type mutator
    */   
   public void setName(String name){
      players.add(name);
   } 
   
   /**
    * @method getName() - retrieves the player name from the array list by index   
    */   
   public String getName(int index){
      return players.get(index);
   }
   
   /**
    * @method getIndex() - retrieves the player index from the array list
    */      
   public int getIndex (String playerName){
      return players.indexOf(playerName);
   }
   
} //End of class playerInfo 