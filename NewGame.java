import java.util.*;
import java.io.*;

/**
 *  This is an object used to tell the players to create a new game
 * group# 01 
 * course ISTE 121
 * instructor Michael Floeser
 
 * @author Catherine Poggioli 
 * @author John Hill
 * @author Jack Old
 * @author David Luong
 *
 *
 * @version 2017-11-30
 */

public class NewGame implements Serializable {
    
   String clientName;
   int index;
   
   /**
    * Stores the player name
    *
    * @param _clientName - saves the name of the person who made the client game request
    */     
   public NewGame(String _clientName, int _index){
      this.clientName = _clientName;
      this.index = _index;
      
   } //End of method
   public String getName(){
      return clientName;
   }
   public int getIndex(){
      return index;
   }
   
} //End of class playerInfo 