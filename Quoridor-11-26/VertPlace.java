import java.util.*;
import java.io.*;

public class VertPlace implements Serializable {

   int x;
   int y;
   String name;
   //an alternative to name might be to send in the index
   
   public VertPlace(int _x, int _y, String _name){
      this.name = _name;
      this.x = _x;
      this.y = _y;
   } //End of method
   
   public String getName(){
      return name;
   }
   public int getX(){
      return x;
   }
   public int getY(){
      return y;
   }
   
} //End of class playerInfo 