import java.util.*;
import java.io.*;

public class ChatMessage implements Serializable {

   String message;
   
   public ChatMessage(String message_){
      message = message_;
   } //End of constructor
   
   public String getMessage(){
      return message;
   }
   

} //End of class chatMessage