import java.util.*;
import java.io.*;

/**
 * ChatMessage is a serialized class that contains methods that are used to set a player's message, 
 * and access them. <p> 
 *
 * @author Catherine Poggioli 
 * @author John Hill
 * @author Jack Old
 * @author David Luong
 *
 * @group# 06 
 * @course ISTE 121
 * @instructor Michael Floeser
 *
 * @version 2017-11-30
 */

public class ChatMessage implements Serializable {
   /**
    * @attributes: Player Name, and a Message string
    */
   private String name;
   private String message;

   /**
    * Parameterized constructor accepts a player message, and saves it as an attribute.
    * @param message - player message that's sent
    */   
   public ChatMessage(String message_){
      message = message_;
      
   } //End of parameterized constructor
   
   /**
    * @method getMessage() - retrieves individual player message 
    * @type accessor
    */   
   public String getMessage(){
      return message;
   }
   
   /**
    * @method setName() - sets the player name
    * @type mutator
    * @param name - player's name
    */   
  	public void setName(String name_){

		name = name_;
	}
   
   /**
    * @method: toString() - inherited from Java's Object class, this method will return the name of 
    *                         the player who's sending the message, and his/her messages. <p>
    */   
	public String toString(){
		String result = String.format("%s: %s%n", name, getMessage());

		return result;

	}

} //End of class chatMessage