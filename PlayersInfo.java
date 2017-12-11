import java.util.*;
import java.io.*;

/**
 * PlayersInfo is a serialized class that's responsible holding a list of the connected players.
 * It includes both a getter and setter methods.  <p>
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

public class PlayersInfo implements Serializable {

   ArrayList<String> players = new ArrayList<String>();
   
   /**
   * PlayersInfo default constructor
   */
   public PlayersInfo(){

   } 


   /**
   * setName method, adds a player to the arraylist
   *
   * @param name player name, new player that joined
   */
   
   public void setName(String name){
      players.add(name);
   }
   
   
   /**
   * getName method, gets the player at the specified index
   *
   * @param index player index that you want to access
   * @return the String value of the index that you specify
   */
   
   
   public String getName(int index){
      return players.get(index);
   }
   
   
   /**
   * getIndex method, gets the index of the player name specified
   *
   * @param playerName String value of the player's index you want to access
   * @return the int value of the specified index
   */
   
   public int getIndex (String playerName){
      return players.indexOf(playerName);
   }
   
} //End of class playerInfo 