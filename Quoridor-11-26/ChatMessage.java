import java.util.*;
import java.io.*;

public class ChatMessage implements Serializable {
	private String name;
   private String message;
   
   public ChatMessage(String message_){
      message = message_;
   } //End of constructor
   
   public String getMessage(){
      return message;
   }
  	public void setName(String name_){

		name = name_;
	}
	public String toString(){
		String result = String.format("%s: %s%n", name, getMessage());

		return result;

	}

} //End of class chatMessage