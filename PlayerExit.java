import java.util.*;
import java.net.*;
import java.io.*;
/**
 * PlayerExit is a serialized class that's responsible for keeping track of players 
 * who exit the game. <p>
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
public class PlayerExit implements Serializable{
   
   boolean turn;
   int index;
   /**
    * PlayerExit default constructor
    */ 
	public PlayerExit(){
	}
   /**
   * @param _turn - takes in string showing if it is turn or not
   */
   public void setTurn(boolean _turn, int _index){
      this.turn = _turn;
      this.index = _index;
   }
   /**
   * @return turn - used to show if it is player's turn who disconnected
   */
   public boolean getTurn(){
      return turn;
   }
   /**
   * @return index - used to show index of player who disconnects
   */
   public int getIndex(){
      return index;
   }

}
