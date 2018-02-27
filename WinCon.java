import java.util.*;
import java.io.*;
/**
 * WinCon is a serialized class that contains methods for retrieving the game winner by his or 
 * her horizontal and vertical location on the board, by the winner's index, and by the winner's 
 * name. <p> 
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
public class WinCon implements Serializable {

   String name;
   int index;
   int x;
   int y;
   

   /**
    * Parameterized constructor accepts the winner's name, index, and its horizontal and vertical
    * positions, and saves them as attributes. <p>
    *
    * @param _name - winner's name
    * @param _index - winner's index
    * @param _x - winner's horizontal position
    * @param _y - winner's vertical position
    */
   public WinCon(String _name,int _index, int _x, int _y){
      
      this.name = _name;
      this.index = _index;
      this.x = _x;
      this.y = _y;
      
   } //End of constructor
   
   /**
    * getIndex() method - retrieves the winner's index
    * @return index of the player
    */ 
   public int getIndex(){
      return index;
   }
   /**
    * getName() method - retrieves the winner's name
    * @return string name of the winner
    */ 
   public String getName(){
      return name;
   }
   /**
    * getX() method - retrieves the winner's horizontal position 
    * @return x position of the winner 
    */
   public int getX(){
      return x;
   }
   
   /**
    * getY() - retrieves the winner's vertical position 
    * @return y position of the winner 
    */ 
   public int getY(){
      return y;
   }

} //End of class PlayerTurn