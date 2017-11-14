import java.net.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


public class Server extends JFrame{

   //Create vector of InnerClass for the players (clients)
   private Vector<PlayerThread> players = new Vector<PlayerThread>();
   
   public static void main(String [] args){
      new Server();
   } //End of main
   
   public Server(){
   
      try{
      
         //set up serversocket, specifying the same port# as the client 
         ServerSocket ss = new ServerSocket(16789);
         
         // get a client connection, and a socket
         Socket cs = null; //cs means client socket
      
         // prints out the localhost
         System.out.println(InetAddress.getLocalHost());
   
         while(true){
            
            // Wait for a client to connect 
            System.out.println("Waiting for a client to connect.... ");
            cs = ss.accept(); // accept a client 
            System.out.println("Have a client connection: " + cs);
     
            //after accepting the client, start the thread
            PlayerThread pt = new PlayerThread(cs);
            pt.start();
            
            //add player to player vector
            players.add(pt);
               
         } // end of while loop
         
      } //end try 

      catch (SocketException se){
         System.out.println("A client has disconnected");
         //se.printStackTrace();
      } //end of catch
         
      catch (IOException ioe){
         ioe.printStackTrace();
      } //end of catch       
   
   } //End of server constructor 
   
   
   class PlayerThread extends Thread{
   
      private Socket cs = null;

      OutputStream out = null;
      ObjectOutputStream oos = null;
      
      // open input from the client 
      InputStream in = null;
      ObjectInputStream ois = null;
      
      public PlayerThread(Socket cs_){
         cs = cs_;
      }
   
      //start the work of the thread 
      public void run(){
      
         try{
 
            // output to the client
            out = cs.getOutputStream(); 
            oos = new ObjectOutputStream(out);
            
            // open input from the client 
            in = cs.getInputStream(); 
            ois = new ObjectInputStream(in); 
           
            PlayersInfo playersInfo = new PlayersInfo();
           

            Object genObject = null; 
            while((genObject = (Object)ois.readObject()) != null){
               if (genObject instanceof PlayerName){
                  PlayerName p = (PlayerName)genObject;
                  playersInfo.setName(p.getPlayerName()); //adds the player to the playerInfo arraylist 
                  for(PlayerThread pt: players){
                     pt.sendInfo(playersInfo);
                  }
               }//End of if
            }//End of while 
             
               
            // close everything
            oos.close();
            ois.close();
            cs.close(); 

         } // end of try 
         
         catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
         }
         
         catch (SocketException se){
            System.out.println("A player has disconnected");
         }
         
         catch(NullPointerException npe){
            npe.printStackTrace();
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         } 
         
      } //End of run method 
      
      
      public void sendInfo(Object p){
      
         try{
            //send object back to client
            oos.writeObject(p);
            oos.flush();
         }
         
         catch (IOException io){
            io.printStackTrace();
         }

      } //End of sendMessage method 
   
   
   } // End of InnerClass PlayerThread
   

} //End of Server Class