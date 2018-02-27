import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;


public class Server extends JFrame{


   //SHOULDN'T THIS BE AN ARRAY? ADDTO
   //When we are refering to the player area to show changes how do we know which if we only have the associated string or indexes
   //We need some for of list to know which JTextarea to call 
   //-From Catie
	private JTextArea jtaP1;
	private JTextArea jtaP2;
	private JTextArea jtaP3;
	private JTextArea jtaP4;
	private Vector<JTextArea> jtaDisplay = new Vector<JTextArea>();

	//The JTextArea that holds the output of the chat program
	private JTextArea jtaChatLog;
	

   //Create vector of InnerClass for the players (clients)
   private Vector<PlayerThread> players = new Vector<PlayerThread>();
   
   public static void main(String [] args){
      new Server();
   } //End of main
   
   public Server(){
 	
		JPanel jpPlayers = new JPanel(new GridLayout(0,1));
		for(int i = 0; i < 4; ++i){
		   jtaDisplay.add(new JTextArea(5, 20));
		   jtaDisplay.get(i).setBackground(Color.BLACK);
		   jtaDisplay.get(i).setForeground(Color.GREEN);
         jtaDisplay.get(i).setEditable(false);
         jpPlayers.add(new JLabel("Player" + i));
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

	//jtaToString creates a string that will create the output for the server player JTAs
	public String jtaToString(String name, String address){
		String result = String.format("%s%n%s%n", name, address);

		return result;

	}

   
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
      	String name = "no name";

         try{
 
            // output to the client
            out = cs.getOutputStream(); 
            oos = new ObjectOutputStream(out);
            
            // open input from the client 
            in = cs.getInputStream(); 
            ois = new ObjectInputStream(in); 
           
            PlayersInfo playersInfo = new PlayersInfo();
				//ChatMessage cm = null;

            Object genObject = null; 
            while((genObject = (Object)ois.readObject()) != null){
               if (genObject instanceof PlayerName){
                  PlayerName pn = (PlayerName)genObject;
						/*if(p.getPlayerName().equals(null)){
							p.setPlayerName("NO NAME");
						}*/
						name = pn.getPlayerName();
						if(players.size() <= 1){
							//.setText(name + "\n" + cs);
						}
						else if(players.size() == 2){
							//jtaP2.setText(jtaToString(name, (cs+"")));

						}
						else if(players.size() == 3){
							//jtaP3.setText(jtaToString(name, (cs+"")));

						}
						else if(players.size() == 4){
							//jtaP4.setText(jtaToString(name, (cs+"")));

						}
                  //Must reject additional players here ADDTO
                  playersInfo.setName(pn.getPlayerName()); //adds the player to the playerInfo arraylist 
                  for(PlayerThread pt: players){
                     pt.sendInfo(playersInfo);
                  }
               }//End of if
               else if (genObject instanceof ChatMessage){
                  ChatMessage cm = (ChatMessage)genObject;
						cm.setName(name);
						jtaChatLog.append(cm.toString());
                  for(PlayerThread pt: players){
                     pt.sendInfo(cm);
                  }  
               }
               else if (genObject instanceof VertPlace){
                  VertPlace vp = (VertPlace)genObject;
                  
                  //Once jtextarea handled in list 
                  //.get(playersInfo.getIndex(vp.getName()).append("Placed a Vertical Wall");
                  //use the list to print results to proper person
                  
                  for(PlayerThread pt: players){
                     pt.sendInfo(vp);
                  }//send changes to each player
                  
                  //NEEDS TO GIVE TURN ORDER TO NEXT PLAYER HERE ADDTO
                  //-Catie             
               }
               else if(genObject instanceof HorzPlace){
                  HorzPlace hp = (HorzPlace)genObject;
                  
                  //Once jtextarea handled in list 
                  //.get(playersInfo.getIndex(vp.getName()).append("Placed a Horizontal Wall");
                  //use the list to print results to proper person
                  
                  for(PlayerThread pt: players){
                     pt.sendInfo(hp);
                  }//send changes to each player
                  
                  //NEEDS TO GIVE TURN ORDER TO NEXT PLAYER HERE ADDTO
                  //-Catie    
               }
               //else if(genObject instanceof TokenPlace){
               //   TokenPlace tp = (TokenPlace)genObject;
                  
               //}    
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
            //npe.printStackTrace();
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