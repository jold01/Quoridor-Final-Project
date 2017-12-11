import java.util.*;
import java.io.*;

/**
 * VertPlace is a serialized class that contains methods for retrieving the location of a vertical 
 * piece by its horizontal and vertical positions, and its position on the game board by index. <p> 
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

public class VertPlace implements Serializable {


   int x;
   int y;
   int index;
   //an alternative to name might be to send in the index
   /**
    * Parameterized constructor accepts the piece's horizontal and vertical positions, and 
    * the piece's index, and saves them as attributes. <p>
    *
    * @param _x - the piece's horizontal position
    * @param _y - the piece's vertical position
    * @param _index - the piece's index
    */     
   public VertPlace(int _x, int _y, int _index){
      this.index = _index;
      this.x = _x;
      this.y = _y;
   } 
   
   /**
    * getIndex() method- retrieves name for the selected vertical piece
    * @return integer index
    */    
   public int getIndex(){
      return index;
   }
   
   /**
    * getX() method - retrieves the horizontal position of the vertical piece
    * @return integer x-coordinate   
    */ 
   public int getX(){
      return x;
   }
   /**
    * getY() method - retrieves the vertical position of the vertical piece
    * @return y-coordinate   
    */   
   public int getY(){
      return y;
   }
   
} //End of class playerInfo 