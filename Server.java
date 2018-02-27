import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.lang.Object.*;
import java.lang.StringBuilder;
/**
 * Game Server accepts player connections, and enables player communication.
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
 
public class Server extends JFrame{


   //SHOULDN'T THIS BE AN ARRAY? ADDTO
   //When we are refering to the player area to show changes how do we know which if we only have the associated string or indexes
   //We need some for of list to know which JTextarea to call 
   //-From Catie
	//private JTextArea jtaP1;
	//private JTextArea jtaP2;
	//private JTextArea jtaP3;
	//private JTextArea jtaP4;
	private Vector<JTextArea> jtaDisplay = new Vector<JTextArea>();

	//The JTextArea that holds the output of the chat program
	private JTextArea jtaChatLog;
	
   ArrayList<String> nameList = new ArrayList<String>();
   //Is the Player Connected?
   ArrayList<String> connectionList = new ArrayList<String>();

   //Create vector of InnerClass for the players (clients)
   private Vector<PlayerThread> players = new Vector<PlayerThread>();

   /**
    * Main method calls the default constructor, which starts the Server
    *
    * @param args -  argument string(s) to run during compilation 
    */   
   public static void main(String [] args){
      new Server();
   } //End of main
   
   /**
   * Server default constructor
   */   
   public Server(){
 	
		JPanel jpPlayers = new JPanel(new GridLayout(0,1));
		for(int i = 0; i < 4; ++i){
		   jtaDisplay.add(new JTextArea(5, 20));
		   jtaDisplay.get(i).setBackground(Color.BLACK);
		   jtaDisplay.get(i).setForeground(Color.GREEN);
         jtaDisplay.get(i).setEditable(false);
         jpPlayers.add(new JLabel("Player" + (i +1)));
         jpPlayers.add(jtaDisplay.get(i));
		   //jtaP3.setText("TESTING");
		}
		jtaChatLog = new JTextArea(5,50);
		
		JScrollPane scroll = new JScrollPane(jtaChatLog);
		
		add(scroll, BorderLayout.WEST);
		add(jpPlayers, BorderLayout.CENTER);
		
		jtaChatLog.setEditable(false);
		
		//jtaChatLog.setText("TEsting");
		
		jtaChatLog.setForeground(Color.GREEN);
		
		jtaChatLog.setBackground(Color.BLACK);
		
		jpPlayers.setBackground(Color.GRAY);
		
		
		setTitle("Server-side Gui");
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	  

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
				//jtaP1.setText(""+cs);
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
         //ioe.printStackTrace();
      } //end of catch       
   
   } //End of server constructor 


   /**
   * jtaToString creates a string to show a new player connected on the Server GUI
   *
   * @param name String name of the player
   * @param address IP address of the player
   * @return String combination of name and IP address
   */   
	public String jtaConnectToString(String name, String address){
		String result = String.format("%s%n%s%nConnected%n", name, address);
		return result;
	}
   
   /**
   * jtaToString creates a string to show a player disconnected on the Server GUI
   * @param name String name of the player
   * @param address IP address of the player
   * @return String combination of name and IP address
   */
	public String jtaDisconnectToString(String name, String address){
		String result = String.format("%s%n%s%nDisconnected%n", name, address);
		return result;
	}
   
   /**
   * PlayerThread class, creates a thread for each player that connects
   */   
   class PlayerThread extends Thread{
   
      private Socket cs = null;

      OutputStream out = null;
      ObjectOutputStream oos = null;
      
      // open input from the client 
      InputStream in = null;
      ObjectInputStream ois = null;
      
      /**
      * PlayerThread default constructor
      *
      * @param cs_ client socket object
      */
      public PlayerThread(Socket cs_){
         cs = cs_;
      }
   
      /**
      * run method, starts the work of the thread 
      */
      public void run(){
      	String name = "no name";
			int index = 0;

         try{
 
            // output to the client
            out = cs.getOutputStream(); 
            oos = new ObjectOutputStream(out);
            
            // open input from the client 
            in = cs.getInputStream(); 
            ois = new ObjectInputStream(in); 
           
				//ChatMessage cm = null;
            
            Object genObject = null; 
            while((genObject = (Object)ois.readObject()) != null){
               if (genObject instanceof PlayerName){
                  PlayerName pn = (PlayerName)genObject;
						/*if(p.getPlayerName().equals(null)){
							p.setPlayerName("NO NAME");
						}*/
                  
                  name = pn.getPlayerName();
                  nameList.add(name); //adds the player to the playerName arraylist
                  index = nameList.size() -1;
                  //
                  connectionList.add("C");
						jtaDisplay.get(index).setText(jtaConnectToString(name, (""+cs)));
                  pn.setIndex(index);
                  //Must reject additional players here ADDTO
                   
                  players.get(index).sendInfo(pn);
                  
                  if(players.size() == 4){ // change to 4 for normal play
                     startGame();
                  }
                  
               }//End of if
               else if (genObject instanceof WinCon){//Checks for win condition msg
                  WinCon wc = (WinCon)genObject;
                  for(int i = 0; i < players.size(); i++){
                     if(connectionList.get(i) == "C"){
                        players.get(i).sendInfo(wc);
                     }
                  }
               }//end if
               else if (genObject instanceof ChatMessage){
                  ChatMessage cm = (ChatMessage)genObject;
						cm.setName(name);
                  
                  String msgRead = cm.getMessage();
                  if(msgRead.charAt(0) == '@'){//for private messaging
                     String[] brokenMsg = msgRead.split(":");
                     
                     StringBuilder nameMsg = new StringBuilder(brokenMsg[0]);
                     nameMsg.deleteCharAt(0);
                     msgRead = nameMsg.toString();
                     if(nameList.indexOf(msgRead) != -1){
                        cm = new ChatMessage(brokenMsg[1]);
                        cm.setName(name);
                        players.get(nameList.indexOf(name)).sendInfo(cm);
                        players.get(nameList.indexOf(msgRead)).sendInfo(cm);   
                     }
                     else{
                        cm = new ChatMessage("Error. Not a player name. Please try again.");
                        cm.setName("Server");
                        if(connectionList.get(nameList.indexOf(name)) == "C"){
                           players.get(nameList.indexOf(name)).sendInfo(cm);
                        }
                     }
                  }
                  else{//if msg is for all players
						   jtaChatLog.append(cm.toString());
                     for(int i = 0; i < players.size(); i++){
                        if(connectionList.get(i) == "C"){
                           players.get(i).sendInfo(cm);
                        }
                     } 
                  }
               }
               else if (genObject instanceof VertPlace){
                  VertPlace vp = (VertPlace)genObject;
                  
                  //Once jtextarea handled in list 
                  jtaDisplay.get(vp.getIndex()).append("Placed a Vertical Wall");
                  //use the list to print results to proper person
                  
                  for(int i = 0; i < players.size(); i++){
                     if(connectionList.get(i) == "C"){
                        players.get(i).sendInfo(vp);
                     }
                  }
                  
                  //NEEDS TO GIVE TURN ORDER TO NEXT PLAYER HERE 
                  passTurn(vp.getIndex(), 0, vp.getIndex());
                  //-Catie             
               }
               else if(genObject instanceof HorzPlace){
                  HorzPlace hp = (HorzPlace)genObject;
                  
                  //Once jtextarea handled in list 
                  jtaDisplay.get(hp.getIndex()).append("Placed a Horizontal Wall");
                  //use the list to print results to proper person
                  
                  for(int i = 0; i < players.size(); i++){
                     if(connectionList.get(i) == "C"){
                        players.get(i).sendInfo(hp);
                     }
                  }
                  
                  passTurn(hp.getIndex(), 0, hp.getIndex());
               }
               else if(genObject instanceof TokenPlace){
                  TokenPlace place = (TokenPlace)genObject;
                  
                  jtaDisplay.get(place.getIndex()).append("Moved their Token");
                  
                  for(int i = 0; i < players.size(); i++){
                     if(connectionList.get(i) == "C"){
                        players.get(i).sendInfo(place);
                     }
                  }
                  passTurn(place.getIndex(), 0, place.getIndex());   
               }
               //USE IT
               else if(genObject instanceof NewGame){
                  NewGame ng = (NewGame)genObject;
                  
                  jtaDisplay.get(ng.getIndex()).append("Requested to start a new game.");
                  
                  for(int i = 0; i < players.size(); i++){
                     if(connectionList.get(i) == "C"){
                        players.get(i).sendInfo(ng);
                     }
                  }
                  startGame(); //send start game once changes are sent 
               }
               //MY CHANGES
               else if(genObject instanceof PlayerExit){
						PlayerExit pl = (PlayerExit)genObject;
                  ChatMessage cm = new ChatMessage(String.format("***%s HAS DISCONNECTED***%n", name));
						cm.setName("System Console");
						System.out.println("Player Exited BOIII");
						

						connectionList.set(index, "D");

						for(int i=0;i<players.size(); i++){
							players.get(i).sendInfo(cm);

						}

						jtaDisplay.get(index).setText(jtaDisconnectToString(name, (""+cs)));
                  if(pl.getTurn() == true){
                     passTurn(pl.getIndex(), 0, pl.getIndex());
                  } 
						try{
						oos.writeObject(pl);
						oos.flush();

					   oos.close();
					   ois.close();
						cs.close();
						}
						catch(IOException ioe){
							System.out.println("Confirmed Player left Boi");
						}
					
						
					}
               
               
                   
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
            jtaChatLog.append("**A PLAYER HAS DISCONNECTED** \n");
         }
         
         catch(NullPointerException npe){
            //npe.printStackTrace();
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         } 
         
      } //End of run method 
      
      /**
      * sendInfo method, sends information to the players
      *
      * @param p general object that gets sent to the players
      */
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
      
      
      /**
      * passTurn method, called each time a players turn is complete
      *
      * @param curNum current number 
      * @param trys trys
      * @param starting starting    
      */
      
      public void passTurn(int curNum, int trys, int starting){
         if(trys > nameList.size()){//checks if win by default since all other players disconnected
            System.out.println((starting +1) + "Wins by default");
            //ADD TO PASS WIN OBJECT
         }//end of if
         else{
            
            //Make changes to curNum
            if(curNum == nameList.size()-1){ //if player is the last to have joined game
               trys += 1; 
               curNum = 0; //will try to pass turn to 1st player
            }
            else{ //all other players
               trys += 1;
               curNum += 1; //will pass turn to next player
            }   
            
            //Check for connection to pass turn  
            if(connectionList.get(curNum) == "C"){//if client is connected
               PlayerTurn turn = new PlayerTurn();
               players.get(curNum).sendInfo(turn); // send Player Turn to the player
            }
            //If not connected, try the next player in list
            else{
               passTurn(curNum, trys, starting);
            }    
         }//end of else      
      }//end of passTurn 
      
      
      /**
      * startGame method, called when a new game needs to be started
      *  
      */
      public void startGame(){
      //START GAME 
                     
      //generate inital tokens command
         InitialGame ig = new InitialGame();
         ig.setArray(nameList);
         for(int i = 0; i < players.size(); i++){
            if(connectionList.get(i) == "C"){
               players.get(i).sendInfo(ig);
            }
         }
                     
         //Now give turn order to first player       
         passTurn(-1,0,-1);            
      }  
   
   } // End of InnerClass PlayerThread
   

} //End of Server Class
