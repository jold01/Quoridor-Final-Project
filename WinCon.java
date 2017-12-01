import java.util.*;
import java.io.*;

public class WinCon implements Serializable {

   String name;
   int index;
   int x;
   int y;
   public WinCon(String _name,int _index, int _x, int _y){
      
      this.name = _name;
      this.index = _index;
      this.x = _x;
      this.y = _y;
      
   } //End of constructor
   public int getIndex(){
      return index;
   }
   public String getName(){
      return name;
   }
   public int getX(){
      return x;
   }
   public int getY(){
      return y;
   }

} //End of class PlayerTurn