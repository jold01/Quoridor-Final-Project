import java.util.*;
import java.io.*;

/**
 * HorzPlace is a serialized class that contains methods for retrieving the location of a 
 * horizontal piece by its horizontal and vertical positions, and its position on the game board 
 * by index. <p> 
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

public class HorzPlace implements Serializable {

   int x;
   int y;
   int index; //an alternative to name might be to send in the index  
   
   /**
    * Parameterized constructor accepts the piece's horizontal and vertical positions, and 
    * the piece's index, and saves them as attributes. <p>
    *
    * @param _x - the piece's horizontal position
    * @param _y - the piece's vertical position
    * @param _index - the piece's index
    */
    public HorzPlace(int _x, int _y, int _index){
        this.index = _index;
        this.x = _x;
        this.y = _y;
    } 
   
    /**
    * getIndex() method - retrieves name for the selected horizontal piece
    * @return integer of the player index
    */      
    public int getIndex(){
      return index;
    }
   
   /**
   * getX() method- retrieves the horizontal position of the horizontal piece
   * @return x-coordinate of horizontal piece   
   */      
   public int getX(){
      return x;
   }
   
   /**
   * getY() method- retrieves the vertical position of the horizontal piece
   * @return y-coordinate
   */         
   public int getY(){
      return y;
   }
   
} //End of class 