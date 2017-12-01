import java.util.*;
import java.io.*;

public class VertPlace implements Serializable {

   int x;
   int y;
   int index;
   //an alternative to name might be to send in the index
   
   public VertPlace(int _x, int _y, int _index){
      this.index = _index;
      this.x = _x;
      this.y = _y;
   } //End of method
   
   public int getIndex(){
      return index;
   }
   public int getX(){
      return x;
   }
   public int getY(){
      return y;
   }
   
} //End of class playerInfo 