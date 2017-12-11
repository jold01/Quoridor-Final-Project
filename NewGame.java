import java.util.*;
import java.io.*;

/**
 * NewGame Class, used in order to allow for the creation of a new game
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

public class NewGame implements Serializable {
    
   String clientName;
   int index;
   
   /**
   * NewGame parameterized constructor 
   *
   * @param _clientName - saves the name of the person who made the client game request
   * @param _index - saves the index of the person who made the client game request  
   */     
   public NewGame(String _clientName, int _index){
      this.clientName = _clientName;
      this.index = _index;   
   } //End of method
   
   /**
   * getName getter method
   *
   * @return String of the clientname 
   */ 
   public String getName(){
      return clientName;
   }
   
   /**
   * getIndex getter method
   *
   * @return integer of the player index
   */ 
   public int getIndex(){
      return index;
   }
   
} //End of class playerInfo 