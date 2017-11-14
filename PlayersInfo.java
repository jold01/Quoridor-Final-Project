import java.util.*;
import java.io.*;

public class PlayersInfo implements Serializable {

   ArrayList<String> players = new ArrayList<String>();
   
   
   public PlayersInfo(){

   } //End of constructor
   
   public void setName(String name){
      players.add(name);
   } //End of method
   
   public String getName(int index){
      return players.get(index);
   }
   
   public int getIndex (String playerName){
      return players.indexOf(playerName);
   }
   
} //End of class playerInfo 