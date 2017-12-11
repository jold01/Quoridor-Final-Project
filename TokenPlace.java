import java.util.*;
import java.io.*;

/**
 * TokensPlace is a serialized class for retrieving the location of a token by its horizontal 
 * and vertical positions, and its position on the game board by index. <p>
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

public class TokenPlace implements Serializable {

   int x;
   int y;
   int index;
   
   //an alternative to name might be to send in the index  
   /**
    * Parameterized constructor accepts the token's horizontal and vertical positions, and 
    * the token's index, and saves them as attributes. <p>
    *
    * @param _x - the token's horizontal position
    * @param _y - the token's vertical position
    * @param _index - the token's index
    */     
   public TokenPlace(int _x, int _y, int _index){
      this.index = _index;
      this.x = _x;
      this.y = _y;
   } 
   
   /**
    * getIndex() method- retrieves the token's index
    * @return integer index
    */     
   public int getIndex(){
      return index;
   }
   
   /**
    * getX() method - retrieves the horizontal position of the token
    * @return integer x-coordinate  
    */ 
   public int getX(){
      return x;
   }
   /**
    * getY() method - retrieves the vertical position of the token
    * @return integer y-coordinate   
    */ 
   public int getY(){
      return y;
   }
   
} //End of class playerInfo 