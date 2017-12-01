import java.util.*;
import java.io.*;
/**
 * WinCon is a serialized class that contains methods for retrieving the game winner by his or 
 * her horizontal and vertical location on the board, by the winner's index, and by the winner's 
 * name. <p> 
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
public class WinCon implements Serializable {

   /**
    * @attributes: winner's name, index, horizontal position, and vertical position 
    */

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
    * @method getIndex() - retrieves the winner's index
    * @type accessor
    */ 
   public int getIndex(){
      return index;
   }
   /**
    * @method getName() - retrieves the winner's name
    * @type accessor
    */ 
   public String getName(){
      return name;
   }
   /**
    * @method getX() - retrieves the winner's horizontal position 
    * @type accessor   
    */
   public int getX(){
      return x;
   }
   /**
    * @method getX() - retrieves the winner's vertical position 
    * @type accessor   
    */ 
   public int getY(){
      return y;
   }

} //End of class PlayerTurn