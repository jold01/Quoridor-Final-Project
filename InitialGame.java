import java.util.*;
import java.io.*;

public class InitialGame implements Serializable {

   int playerAmount;
   ArrayList<String> pNames = new ArrayList<String>();
   
   public InitialGame(){
      
   } //End of constructor
   public void setArray(ArrayList<String> _pNames){
      this.pNames = _pNames;
      this.playerAmount = _pNames.size();
   }
   public ArrayList<String> getArray(){
      return pNames;
   }
   public int getPlayerAmount(){
      return playerAmount;
   }

} //End of class PlayerTurn