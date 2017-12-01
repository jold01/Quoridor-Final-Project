import java.util.*;
import java.io.*;

public class PlayerName implements Serializable {
   
   String name;
   int index;
   
   public PlayerName(String name_){
      name = name_;
   } //End of constructor
   
   public String getPlayerName(){
      return name;
   }
   public void setIndex(int _index){
      this.index = _index;
   }
   public int getIndex(){
      return index;
   }

} //End of class playerInfo 