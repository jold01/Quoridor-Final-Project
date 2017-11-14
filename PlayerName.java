import java.util.*;
import java.io.*;

public class PlayerName implements Serializable {
   
   String name;
   
   public PlayerName(String name_){
      name = name_;
   } //End of constructor
   
   public String getPlayerName(){
      return name;
   }

} //End of class playerInfo 