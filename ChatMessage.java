import java.util.*;
import java.io.*;

/**
 * ChatMessage is a serialized class that contains methods that are used to set a player's message, 
 * and access them. <p> 
 *
 * Group# 06 <p>
 * ISTE 121
 * 
 * @author Catherine Poggioli 
 * @author John Hill
 * @author Jack Old
 * @author David Luong
 *
 *
 * @version 2017-11-30
 */

public class ChatMessage implements Serializable {

   private String name;
   private String message;

   /**
   * Parameterized constructor accepts a player message, and saves it as an attribute.
   * @param message_ player message that's sent
   */   
   public ChatMessage(String message_){
      message = message_;
      
   } //End of parameterized constructor
   
   /**
   * getMessage() method - retrieves individual player message 
   * @return String value of the message text
   */   
   public String getMessage(){
      return message;
   }
   
   /**
   * setName() method - sets the player name
   * @param name_ player's name
   */   
  	public void setName(String name_){
		name = name_;
	}
   
   /**
   * toString() method - inherited from Java's Object class, this method will return the name of 
   *                         the player who's sending the message, and his/her messages. <p>
   *
   * @return String of the name and the message
   */   
	public String toString(){
		String result = String.format("%s: %s%n", name, getMessage());
   	return result;
	}

} //End of class chatMessage