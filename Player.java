import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.util.*;
import java.net.*;

/**
 * Player is a serialized class, and the main GUI for the Quoridor game, which contains the 
 * board, message area, score board, and their functionalities. <p>
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

public class Player extends JFrame{

   // send output to server
   OutputStream out; 
   ObjectOutputStream oos;
   
   //ArrayList<Vector> playerNames = new ArrayList<Vector>();
   
   boolean turn;
   
   JButton jbSend;

   // open input from the server
   InputStream in;
   ObjectInputStream ois;
   
   ImageIcon emptySpace = new ImageIcon("emptyspace.jpg");

   int[][] playerArray = new int[9][9]; //stores the player token info
                                    
   int[][] rightArray = new int[9][9]; //stores the right wall info

   int[][] bottomArray = new int[9][9]; //stores the bottom wall info

   int[][] centerArray = new int[9][9]; //stores the center wall info

   
   //Pathing solution
   int[][] solution = new int[9][9];
   
   JButton[][] centerWall;
   JButton[][] playerSpace;
   JButton[][] rightWall;
   JButton[][] bottomWall;
   JButton button;
   
   //column then row 
   int[][] tLocation = new int[][]{
                                 {0,4}, //change 2nd number to 4 when using this for real
                                 {8,4},
                                 {4,0},
                                 {4,8}
                                       };
   int[][] resetLocation = new int[][]{
                                 {0,4}, //change 2nd number to 4 when using this for real
                                 {8,4},
                                 {4,0},
                                 {4,8}
                                       };
                                       
   int[] winCon = new int[]{8 , 0, 8, 0}; //p1-4 col or row needed to win
   
   int[][] pathOrder = new int[][]{
                                    { 1, 0, 0, 1},
                                    {-1, 0, 0,-1},
                                    { 0, 1, 1, 0},
                                    { 0,-1,-1, 0},
                                                   };
                                                   
   int pAmount = 4; //# of players  
   
   
   //Initial placeholder value for name of player
   String clientName = "Player";
   int clientNum;
   
   //CHAT VAR
   private JTextArea jtaMessage; 
   private JTextField jtfMessage; 
   
  	//NEW STUFF FROM JACK
	private JMenuBar jmb;
	private JMenu jm;
	private JMenuItem jmiAbout, jmiCredits, jmiExit, jmiConnect, jmiNewGame;

	//Connect Window Info NEW STUFF FROM JACK
	private String ip_address;
   
   //Player Names when game is initially started
   ArrayList<String> pNames = new ArrayList<String>();
   
   //Label for ScoreBoard 
   ArrayList<JLabel> jlScore = new ArrayList<JLabel>();
   
   ArrayList<Integer> pScore = new ArrayList<Integer>();
   
   //Record wins if players are playing multiple games
   ArrayList<Integer> wallCount = new ArrayList<Integer>();
   
   //stores the images of icons
   static final   ImageIcon[] imageList = {new ImageIcon("blue.jpg"), new ImageIcon("pink.jpg"),new ImageIcon("green.jpg"),new ImageIcon("yellow.jpg")};
   
   Color[] colors = { Color.blue, Color.red, Color.green, Color.yellow};
   
   //Label for ScoreBoard 
   private JLabel jlScoreTitle;
   private JLabel jlWallTitle;
   
   //Label of Walls for wallTracker
   ArrayList<JLabel> jlWallCount = new ArrayList<JLabel>();
   
   /**
   * Main method calls the default constructor of this class, and displays the game
   *
   * @param args - argument string(s) to run during compilation 
   */    
   public static void main(String [] args){
     
      try {
           // Set cross-platform Java L&F (also called "Metal")
         UIManager.setLookAndFeel(
         UIManager.getCrossPlatformLookAndFeelClassName());
      } 
      catch (UnsupportedLookAndFeelException e) {
      // handle exception
      }
      catch (ClassNotFoundException e) {
      // handle exception
      }
      catch (InstantiationException e) {
      // handle exception
      }
      catch (IllegalAccessException e) {
      // handle exception
      }
      
      //creates player object
      new Player();
     
   } //End of main
 
   
   /**
   * Default constructor for the player class
   *
   */   
 
   public Player(){
      
      //add inital wall count of 5 for each player
      for(int i = 0; i < pAmount; i++){
         pNames.add("Player " + (i + 1));
         wallCount.add(5);
         pScore.add(0);
         jlWallCount.add(new JLabel());
         jlScore.add(new JLabel());
         
      }
	
      //initialize GUI components and add to frame
		add(new PlayerMenu(), BorderLayout.NORTH);

      add(new GridBag(), BorderLayout.CENTER);
      
      add(new ChatDisplay(), BorderLayout.EAST); 
      
      add(new Tracker(), BorderLayout.SOUTH);             
      
      //Windows and visibility 
      setTitle("Quoridor Final Project");
      setSize(1050,800);
      setLocationRelativeTo(this);
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      setVisible(true); 
      
      
      //NEW STUFF FROM JACK
      clientName = JOptionPane.showInputDialog("Enter a player name: ");
 
      //Rules
		String intro = "Welcome! \n\nThis is the intro guide on how to navigate"+
		" our Quoridor game.\nAll of the options listed below can be accessed"+
		" through the 'Settings'\ntab at the top left of the window.\n\n" +
		"Connect to a Game - To connect to an online game, click on the\n"+
		"'Connect...' menu item. A window will pop up asking for an ip address.\n"+
		"If the server is located on your computer, just click the 'Connect'\n"+
		"button. If not, type in the IP address of the computer that has the\n"+
		"server on it.\n\n"+
		"View the Rules of Quoridor - If you are not sure how to play Quoridor,\n"+
		"simply click on the 'Game Rules' menu item.\n\n"+
		"See Who Made this Program - If you would like to see who developed this\n"+
		"program, click on the 'Credits' menu item.\n\n"+
		"Exit the Game - To exit the game, click on the 'Exit' menu item.";

		JOptionPane.showMessageDialog(this, intro);
      
      /**
      * WindowListener
      */  
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){

				new PlayerDisconnect();
			}

		});
      
   }
   
	/**
   * PlayerMenu class, Sets up the JMenu for the player GUI
   */  
	class PlayerMenu extends JPanel {
		
      
      /**
      * PlayerMenu default constructor, creates player menu GUI components 
      */  
		public PlayerMenu(){
			
			setLayout(new FlowLayout(FlowLayout.LEFT));
			
			
			jmb = new JMenuBar();
			add(jmb);

			jm = new JMenu("Settings");
			jmb.add(jm);

			jmiConnect = new JMenuItem("Connect...");
			jmiAbout = new JMenuItem("Game Rules");
			jmiCredits = new JMenuItem("Credits");
			jmiExit = new JMenuItem("Exit");
         jmiNewGame = new JMenuItem("New Game");

			jm.add(jmiConnect);
         jm.add(jmiNewGame);
			jm.add(jmiAbout);
			jm.add(jmiCredits);
			jm.add(jmiExit);
         jmiNewGame.setEnabled(false);


         /**
         * Anon inner class, adds action listener for connect button, which allows the player to connect to the server and start playing
         */  
			jmiConnect.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					new ConnectWindow();
				}
			});
         
         
         /**
         * Anon inner class, adds action listener for new game button, which clears the board and creates a new game
         */           
			jmiNewGame.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					NewGame ng = new NewGame(clientName, clientNum);
               try{
                  oos.writeObject(ng); //send to serverJPanel jpPlayers = new JPanel(new GridLayout(0,1));JPanel jpPlayers = new JPanel(new GridLayout(0,1));
                  oos.flush();
               }
               catch (IOException io){}

				}
			});
         
         
         /**
         * Anon inner class, adds action listener for about button, which shows the rules of the game 
         */ 
			jmiAbout.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					String ruleString = "Each player's pawn start in the center of the board. \n" +
                      "The goal of the game is to reach the other side. \n" +
							                       "A player may choose to either move their pawn or place a wall on their turn. \n" +
														                        "Player pieces may move up, down, left, or right. Two pieces may not occupy the same spot. \n" +
																						                      "Walls are placed horizontally or vertically and take up two connecting spots. \n" +
																													                       "Walls can't be moved and can never completely wall in the opponent. There must always be a path. \n";

					JOptionPane.showMessageDialog(jmiAbout, ruleString);

				}

			});
         
         /**
         * Anon inner class, adds action listener for credits button, which shows the credits
         */ 
			jmiCredits.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){

					String credits = "The contributors to the Quoridor Program: \n\n"+
					"- Catherine Poggioli\n- John Hill\n- Jack Old\n- David Luong";

					JOptionPane.showMessageDialog(jmiCredits, credits);

				}

			});
         
         /**
         * Anon inner class, adds action listener for exit button, which allows the player to disconnect
         */ 
			jmiExit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					//System.exit(0);
					new PlayerDisconnect();
				}
			});
		}


	}//end of PlayerMenu Class

   /**
   * ConnectWindow class, when a player connects, it initializes their GUI
   */  
	class ConnectWindow extends JFrame{

   	private JPanel jpStartPane;
   	private JPanel jpNamePane;
   	private JPanel jpIpPane;
   
   
   	private JLabel jlAddress;
   	private JTextField jtfAddress;
   
   	private JButton jbConnect;
   
      /**
      * ConnectWindow default constructor
      */  
   	public ConnectWindow(){
   
      	//General Frame Information
      	setTitle("Client Start-Up");
      	setDefaultCloseOperation(ConnectWindow.DISPOSE_ON_CLOSE);
      	setSize(400,150);
      	setLocationRelativeTo(null);
      
      	//Create Panels
      	jpStartPane = new JPanel();
      	jpNamePane = new JPanel(new FlowLayout());
      	jpIpPane = new JPanel(new FlowLayout());
      
      
      	//create Label and field for ip Address
      	jlAddress = new JLabel("Server IP: ");
      	jpIpPane.add(jlAddress);
      
      	jtfAddress = new JTextField(20);
         jtfAddress.setText("localhost");
      	jpIpPane.add(jtfAddress);
      
 			//Add Panels to JFrame
      	jpStartPane.add(jpNamePane, BorderLayout.NORTH);
      	jpStartPane.add(jpIpPane, BorderLayout.CENTER);
      
      	jbConnect = new JButton("Start Client");
      	jpStartPane.add(jbConnect, BorderLayout.SOUTH);
      	add(jpStartPane);

      
         /**
         * Anon inner class for connect button, sets up the player socket to the server 
         */  
			jbConnect.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					ip_address = jtfAddress.getText();
					new SocketSetup();
					dispose();

				}

			});
         setResizable(false);
			setVisible(true);
		}

	}

   /**
   * Player Disconnect, inner class that sends info if a player disconnects
   */  
	class PlayerDisconnect{
   
   
      /**
      * Player Disconnect default constructor 
      */ 
		public PlayerDisconnect(){
			
         PlayerExit pe = new PlayerExit();
         
         if(turn == true){
			   pe.setTurn(true, clientNum);
            
         }
         else{
            pe.setTurn(false, clientNum);
         }
         
			try{
				oos.writeObject(pe);
				oos.flush();

			}
			catch(IOException ioe){
				System.out.println("window adapter exception");
			}
			catch(NullPointerException npe){
				System.out.println("PlayerDisconnect NPE");
			}
			finally{
				System.exit(0);
			}
	
		}
		

  	}//end of PlayerDisconnect 
		
   
   /**
   * ChatDisplay class, Inner Class for displaying chat messages 
   */   
   class ChatDisplay extends JPanel implements ActionListener, KeyListener{
   
      public ChatDisplay(){
      	
        	//int columnHeight = getHeight() - 10;
         
         jtaMessage = new JTextArea(30, 30);
         jtaMessage.setEditable(false);
         JScrollPane scroll = new JScrollPane(jtaMessage);
         
         jtfMessage = new JTextField(24);
         jbSend = new JButton("Send");

       
         /**
         * Anon inner class, Add action listeners for the send button
         */
         jbSend.addActionListener(new ActionListener(){
            public void actionPerformed (ActionEvent ae){
               String message = null;
               message = jtfMessage.getText(); //get text from text field
               ChatMessage cm = new ChatMessage(message);
               try{
                  oos.writeObject(cm); //send to serverJPanel jpPlayers = new JPanel(new GridLayout(0,1));JPanel jpPlayers = new JPanel(new GridLayout(0,1));


                  oos.flush();
               }
               catch (IOException io){}
               jtfMessage.setText(""); 
            } 
         });
         
         
         /**
         * Anon inner class, Adds a keylistener/adapter for the enter key to be able to send messages
         */
         jtfMessage.addKeyListener(
            new KeyAdapter(){
               @Override 
                  public void keyPressed( KeyEvent e )	
            		{
            			int keyCode = e.getKeyCode();   // what letter was pressed              				
                     if(keyCode == KeyEvent.VK_ENTER){
                        String messageTwo = null;
                        messageTwo = jtfMessage.getText(); //get text from text field
                        ChatMessage cm = new ChatMessage(messageTwo);
                        try{
                           oos.writeObject(cm); //send to server
                           oos.flush();
                        }
                        catch (IOException io){}
                        jtfMessage.setText(""); 
                     }   
                   
                  } // end of keypressed class 
         });
         

         JPanel jpChatOutput = new JPanel();
      
         jpChatOutput.add(scroll);
      
         JPanel jpChatInput = new JPanel();
      
         jpChatInput.add(jtfMessage);
         jpChatInput.add(jbSend);
      	
         JPanel jpChatMain = new JPanel(new GridLayout(0,1));
      
         jpChatMain.add(jpChatOutput);
         jpChatMain.add(jpChatInput);
         
         add(jpChatMain, BorderLayout.CENTER);

         setBackground(Color.LIGHT_GRAY);  
      
      } //End of chat display constructor
      
      
      /* For actionListener interface */
      @Override
      public void actionPerformed (ActionEvent ae){
          
      }
      
      /* For KeyListener interface */
   
      @Override
      public void keyTyped (KeyEvent e){
          
      } 
       
      @Override
      public void keyPressed (KeyEvent e){
          
      }
      
      @Override   
      public void keyReleased (KeyEvent e){
          
      }  
      
      
      
   }//End of chat display class
   
   
   /**
   * Tracker Class, Inner Class for updating scores on the score board.
   */   
   class Tracker extends JPanel{
   
      public Tracker(){
         
         //createEmptyBorder(0,10,10,10);
         
         setLayout(new GridLayout(0,4));
         
         JPanel leftPanel = new JPanel(new GridLayout(0,1));
         jlScoreTitle = new JLabel("Player Scores : ");
         jlScoreTitle.setFont(new Font("Arial", Font.BOLD, 18));
         jlScoreTitle.setHorizontalAlignment(JLabel.RIGHT);
         leftPanel.add(jlScoreTitle);
         leftPanel.setBackground(Color.LIGHT_GRAY);
         leftPanel.setBorder(new EmptyBorder(new Insets(0,0,0,15)));
         
         JPanel rightPanel = new JPanel(new GridLayout(0,1));
         jlWallTitle = new JLabel("Walls Available : ");
         jlWallTitle.setFont(new Font("Arial", Font.BOLD, 18));
         jlWallTitle.setHorizontalAlignment(JLabel.RIGHT);
         rightPanel.add(jlWallTitle);
         rightPanel.setBackground(Color.LIGHT_GRAY);
         rightPanel.setBorder(new EmptyBorder(new Insets(0,15,0,0)));
         
         
         for(int i = 0; i < pAmount; i++){
            jlWallCount.get(i).setText(pNames.get(i) + "'s Walls: " + wallCount.get(i)); 
            jlWallCount.get(i).setFont(new Font("Arial", Font.PLAIN, 18));
            jlWallCount.get(i).setHorizontalAlignment(JLabel.RIGHT);
            rightPanel.add(jlWallCount.get(i));
            
            jlScore.get(i).setText(pNames.get(i) + " : " + pScore.get(i));
            jlScore.get(i).setFont(new Font("Arial", Font.PLAIN, 18));
            jlScore.get(i).setHorizontalAlignment(JLabel.RIGHT);
            leftPanel.add(jlScore.get(i));
         }
         add(leftPanel);
         add(rightPanel);
         setBackground(Color.LIGHT_GRAY);  
         //setPreferredSize(new Dimension(200, 300));
         //setBorder( new EmptyBorder( 10, 30, 15, 15 ) );
      }
   } 
   
   
   /**
    * GridBag Class, sets the dimensions of the game board
   */   
   class GridBag extends JPanel implements ActionListener{
      
      
      /**
      * GridBag default constructor
      */       
      public GridBag(){
   
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         int h = screenSize.height;
         int w = screenSize.width;
         
         JPanel jpBoard;
         jpBoard = new JPanel(new GridLayout(9,9));
         jpBoard.setPreferredSize(new Dimension(650, 650));
         playerSpace = new JButton[10][10];
         rightWall = new JButton[10][10];
         bottomWall = new JButton[10][10];
         centerWall = new JButton[10][10];
   
         //Outer for loop
         for (int i = 0; i < 9; i++){
         
            //Inner for loop
            for (int j = 0; j < 9; j++){
               
               JPanel jpSquare = new JPanel(); //one square is 4 players spaces
         
               //JButton button;
               GridBagLayout gridbag = new GridBagLayout();
               jpSquare.setLayout(gridbag);
               GridBagConstraints c = new GridBagConstraints(); 
                   
               //player space   
               button = new JButton("");
               button.setFocusPainted(false);
               button.setBackground(Color.LIGHT_GRAY);
               c.ipadx = 0;//(int)(24); //x spacing
               c.ipady = 0;//(int)(24); //y spacing
               c.gridx = 0; //0 x location
               c.gridy = 0; //0 y location
               gridbag.setConstraints(button, c);
               jpSquare.add(button);
               playerSpace[i][j] = button;
               playerSpace[i][j].setActionCommand("player" + "-" + i + "-" + j);
               playerSpace[i][j].addActionListener(this);
               playerSpace[i][j].setEnabled(false);
               playerSpace[i][j].setIcon(emptySpace);
               //playerSpace[i][j].setDisabledIcon(emptySpace);
               
               //right wall creation
               button = new JButton(""); 
               button.setBackground(Color.DARK_GRAY);
               c.ipadx = 1;//(int)(40);
               c.ipady = 40;//(int)(40);
               c.gridx = 1;
               c.gridy = 0;
               gridbag.setConstraints(button, c);
               rightWall[i][j] = button;
               rightWall[i][j].setActionCommand("right" + "-" + i + "-" + j);
               rightWall[i][j].addActionListener(this);
               rightWall[i][j].setEnabled(false);
   
               //get rid of right wall for the last column
               if(j != 8){
                  jpSquare.add(button);
               } 
               else{
                  button.setOpaque(false);
                  button.setContentAreaFilled(false);
                  button.setBorderPainted(false);
                  jpSquare.add(button);
               }  
   
               //creation of bottom wall
               button = new JButton("");
               button.setBackground(Color.DARK_GRAY);
               c.gridx = 0;
               c.gridy = 1;
               c.ipadx = 40;//(int)(60);
               c.ipady = 15;//(int)(20);
               gridbag.setConstraints(button, c);            
               bottomWall[i][j] = button;
               bottomWall[i][j].setActionCommand("bottom" + "-" + i + "-" + j);
               bottomWall[i][j].addActionListener(this);
               bottomWall[i][j].setEnabled(false);
               
               
               //for bottom row get rid of bottom wall
               if (! (i == 8)){             
                  jpSquare.add(button);
               }
               else{
                  button.setOpaque(false);
                  button.setContentAreaFilled(false);
                  button.setBorderPainted(false);
                  jpSquare.add(button);
               } 
               
               //creation of center wall
               button = new JButton("");
               button.setBackground(Color.DARK_GRAY);
               c.gridx = 1;
               c.gridy = 1;
               c.ipadx = 1;//(int)(40);
               c.ipady = 15;//(int)(20);
               gridbag.setConstraints(button, c);
               centerWall[i][j] = button;
               centerWall[i][j].setActionCommand("center" + "-" + i + "-" + j);
               centerWall[i][j].addActionListener(this); 
               centerWall[i][j].setEnabled(false);
                           
               //if not bottom row, add normal
               if ((!(i == 8) && (j != 8))){             
                  jpSquare.add(button);
               }
               //if bottom row, make invisible
               else{
                  button.setOpaque(false);
                  button.setContentAreaFilled(false);
                  button.setBorderPainted(false);
                  jpSquare.add(button);
               } 
   
               jpBoard.add(jpSquare); //add the current square to the board
            
            }//end of inner for loop 
           
         } //end of outer for loop 
              
         add(jpBoard);
          
      }//End of constructor 
      
      
      /**
      * Handles the event of a button being clicked on the player GUI
      */      
      public void	actionPerformed(ActionEvent ae){
         
         //Get location of button clicked
         String command = ae.getActionCommand();
         String[] split = command.split("-");
         
         String name = split[0];
         int x = Integer.parseInt(split[1]);
         int y = Integer.parseInt(split[2]);
         int choice  = 0;
         
         //If middle wall clicked, need to decide if they want to place a horizontal or vertical wall
         if (name.equals("player")){
            //System.out.println(x + " " + y);
        
            if(!((x == tLocation[clientNum][0]) && (y == tLocation[clientNum][1]))){
               enableDisableWall(false);
               enableDisableSpace(false);
               turn = false;
               
               //Check if win condition is met
               if((clientNum == 0 && x == 8) || (clientNum == 1 && x == 0) || (clientNum == 2 && y == 8) || (clientNum == 3 && y == 0)){//location matches win Con #
               
                  WinCon wc = new WinCon(clientName,clientNum,x,y);
                  try{
                     oos.writeObject(wc); //send to server
                     oos.flush();
                  }
                  catch (IOException io){}                  
               
               }//end if
               else{
               //send normal moves over
                  
                  TokenPlace place = new TokenPlace(x,y, clientNum);
                  try{
                     oos.writeObject(place); //send to server
                     oos.flush();
                  }
                  catch (IOException io){}   
                  
               }//emd else
            } 
         }
         
         //If a center wall is clicked
         if (name.equals("center")){
                     
            Object[] options = {"Horizontal","Vertical"};
   
            choice = JOptionPane.showOptionDialog(centerWall[x][y],
                    "Do you want to place a horizontal or vertical wall?",
                    "Wall Placement",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, //Icon
                    options,
                    null); //Initial value
                               
            //Horizontal wall was clicked 
            if (choice == JOptionPane.YES_OPTION){
               //Temp horz wall in Array to check if path avaliable
               centerArray[x][y] = 1;            
               bottomArray[x][y]= 1;
               bottomArray[x][y+1] = 1;
               if(pathTrial() == true){
               //path found
                  turn = false;
                  centerArray[x][y] = 0;
                  bottomArray[x][y] = 0;
                  bottomArray[x][y+1] = 0;
                  //sets the arrays back ... they will be changed during the reading phase
                  enableDisableWall(false);
                  enableDisableSpace(false);
                  HorzPlace hp = new HorzPlace(x,y, clientNum);
                  try{
                     oos.writeObject(hp); //send to server
                     oos.flush();
                  }
                  catch (IOException io){}
               
                  //NEEDS TO DISABLE CLICKING / TURN ADDTO      
                  }
               else{
                  //GIVE PROMPT TELL THEM OPTION IS NOT ALLOWED/ THIS BLOCKS PATH
                  centerArray[x][y] = 0;
                  bottomArray[x][y] = 0;
                  bottomArray[x][y+1] = 0;
                  JOptionPane.showMessageDialog(centerWall[x][y], "This is not a valid move. Placing this wall will block other player's paths. Please choose again.");
               }             
            }
            //Vertical wall was clicked
            else if(choice == JOptionPane.NO_OPTION){
               //Temp horz wall in Array to check if path avaliable
            
               centerArray[x][y] = 1;            
               rightArray[x][y]= 1;
               rightArray[x+1][y] = 1;
               if(pathTrial() == true){
               //path found
                  turn = false;
                  centerArray[x][y] = 0;
                  rightArray[x][y] = 0;
                  rightArray[x+1][y] = 0;
                  //sets the arrays back ... they will be changed during the reading phase
                  enableDisableWall(false);
                  enableDisableSpace(false);
                  VertPlace vp = new VertPlace(x,y, clientNum);
                  try{
                     oos.writeObject(vp); //send to server
                     oos.flush();
                  }
                  catch (IOException io){}
               
                  //NEEDS TO DISABLE CLICKING / TURN  ADDTO        
                  }
               else{
                  //GIVE PROMPT TELL THEM OPTION IS NOT ALLOWED/ THIS BLOCKS PATH
                  centerArray[x][y] = 0;
                  rightArray[x][y] = 0;
                  rightArray[x+1][y] = 0;
                  JOptionPane.showMessageDialog(centerWall[x][y], "This is not a valid move. Placing this wall will block other player's paths. Please choose again.");
               }
            }
             
         } //End of center if
            
      } //End of actionPerformed
   
   
   } //End of class GridBag
   
   
   /**
   * SocketSetup inner class, initializes the player socket to the server, so they can communicate with one another
   */ 
   
   class SocketSetup{
   
      
      /**
      * SocketSetup default constructor
      */ 
   
      public SocketSetup(){
      
         try{
            Socket s = new Socket(ip_address, 16789);
                     
            // output to the server
            out = s.getOutputStream(); 
            oos = new ObjectOutputStream(out);
            
            // open input from the server
            in = s.getInputStream(); 
            ois = new ObjectInputStream(in);
            
            ThreadReader tr = new ThreadReader(); 
            tr.start(); //starts the reader thread 
            
         } //End of try
         catch (UnknownHostException uhe){
					System.out.println("WRONG IP BOI");
					//JOptionPane.showMessageDialog(null, "No server with that IP.");


			}
         
         catch (ConnectException ce){
            System.out.println("Sorry no server available at the time");
         }
   
         catch (IOException io){
            //io.printStackTrace(); //SocketException is thrown
         }  
      
      }//End of constructor
      
   } //End of class SocketSetup
   
   
   
   /**
   * ThreadReader, Inner Class, creates a thread for reading messages/info
   */   
   class ThreadReader extends Thread implements ActionListener{
      
      
      /**
      * ThreadReader default constructor
      */     
      public ThreadReader(){

      } 
      
      //starts the work of the thread
      public void run(){
      
         try{
            //clientName = JOptionPane.showInputDialog("Enter a player name: ");
            PlayerName pn = new PlayerName(clientName);
            oos.writeObject(pn); //send player name to server
            oos.flush();
            
            Object genObject = null; 
            while((genObject = (Object)ois.readObject()) != null){
               //used to get the player name and their index at the very start of the game
               if (genObject instanceof PlayerName){
                  //casts gen Object as player Info
                  pn = (PlayerName)genObject;
                  clientNum = pn.getIndex();
                  
               }//End of if
              
               //used to set up a new game with current total players
               else if (genObject instanceof InitialGame){
                  jmiNewGame.setEnabled(true);
                  InitialGame ig = (InitialGame)genObject;
                  pAmount = ig.getPlayerAmount();
                  pNames = ig.getArray();
                  
                  for(int i = 0; i < pAmount; i++){
                     playerArray[resetLocation[i][0]][resetLocation[i][1]] = 1;
                     playerSpace[resetLocation[i][0]][resetLocation[i][1]].setIcon(imageList[i]);  
                     jlWallCount.get(i).setText(pNames.get(i) + "'s Walls: " + wallCount.get(i));
                     jlScore.get(i).setText(pNames.get(i) + ": " + pScore.get(i));                    
                  }                 
               }
               
               else if (genObject instanceof PlayerTurn){
                  //Dialog pop up
                  JOptionPane.showMessageDialog( jmiConnect, "It is now your turn");
                  turn = true; //set boolean as true
                  //run enable functions here
                  if(wallCount.get(clientNum) > 0){
                     enableDisableWall(true);
                     enableDisableSpace(true);
                  }
                  else{
                     enableDisableSpace(true);
                  }            
                  
                  //Possible timer?
               }
               
               else if (genObject instanceof WinCon){
               
                  WinCon wc = (WinCon)genObject;
                  int x = wc.getX();
                  int y = wc.getY();
                  int index = wc.getIndex();
                 
                  int prevx = tLocation[index][0];
                  int prevy = tLocation[index][1];
                  
                  //Here change the locations
                  tLocation[index][0] = x;
                  tLocation[index][1] = y;
                  
                  //Change the values in the grid to show the new locations as filled or empty
                  playerArray[prevx][prevy] = 0;
                  playerArray[x][y] = 1;
                  
                  playerSpace[prevx][prevy].setIcon(emptySpace); //Uses empty space graphic to show spot is empty
                  playerSpace[x][y].setIcon(imageList[index]); //use player info to get the proper Icon Image
                  
                  pScore.set(index, pScore.get(index) +1);
                  jlScore.get(index).setText(pNames.get(index) + ": " + pScore.get(index));
                  
                  if(clientNum == index){
                     JOptionPane.showMessageDialog(jmiConnect, "YOU WIN!");
   
                  }
                  else{
                     JOptionPane.showMessageDialog(jmiConnect, wc.getName() + " has reached the opposite side and wins!");

                  }
               }
               else if(genObject instanceof NewGame){
                  NewGame ng = (NewGame)genObject;
                  enableDisableSpace(false);
                  enableDisableWall(false);
                  
                  JOptionPane.showMessageDialog(jmiConnect, ng.getName() + " has requested to start a new game!");
                  
                  for(int i = 0; i < 9; i++){
                     for(int j = 0; j <9; j++){
                     
                        centerWall[i][j].setBackground(Color.DARK_GRAY);
                        rightWall[i][j].setBackground(Color.DARK_GRAY);
                        bottomWall[i][j].setBackground(Color.DARK_GRAY);
                        
                        centerArray[i][j] = 0;
                        rightArray[i][j] = 0;
                        bottomArray[i][j] = 0;
                  
                     }//end of inner for loop
                  }//end of outer for loop
                  
                  for(int i = 0; i < 4; i++){
                     playerSpace[tLocation[i][0]][tLocation[i][1]].setIcon(emptySpace);
                     //playerSpace[tLocation[i][0]][tLocation[i][1]] = 0;
                     playerArray[tLocation[i][0]][tLocation[i][1]] = 0;
                     playerArray[resetLocation[i][0]][resetLocation[i][1]] = 0;
                     playerSpace[resetLocation[i][0]][resetLocation[i][1]].setIcon(imageList[i]); 
                     wallCount.set(i, 5);
                     jlWallCount.get(i).setText(pNames.get(i) + "'s Walls: " + wallCount.get(i));                  
                  }//end of for
                  tLocation = resetLocation;
               
               }
               else if (genObject instanceof ChatMessage){
                  ChatMessage cm = (ChatMessage)genObject;
                  jtaMessage.append(cm.toString());  
               }
               
               else if (genObject instanceof HorzPlace){
                  HorzPlace hp = (HorzPlace)genObject;
                  int x = hp.getX();
                  int y = hp.getY();
                  int index = hp.getIndex();
                  
                  centerArray[x][y] = 1;
                  bottomArray[x][y] = 1;
                  bottomArray[x][y+1] = 1;
                  
                  centerWall[x][y].setBackground(Color.RED);
                  bottomWall[x][y].setBackground(Color.RED);
                  bottomWall[x][y+1].setBackground(Color.RED);
                  
                  wallCount.set(index, wallCount.get(index) -1);
                  jlWallCount.get(index).setText(pNames.get(index) + "'s Walls: " + wallCount.get(index)); 
               }
               
               else if (genObject instanceof VertPlace){
                  VertPlace vp = (VertPlace)genObject;
                  int x = vp.getX();
                  int y = vp.getY();
                  int index = vp.getIndex();

                  centerWall[x][y].setBackground(Color.RED);
                  rightWall[x][y].setBackground(Color.RED);
                  rightWall[x+1][y].setBackground(Color.RED);     
                  
                  centerArray[x][y] = 1;
                  rightArray[x][y] = 1;
                  rightArray[x+1][y ] = 1;
                  
                  wallCount.set(index, wallCount.get(index) -1);
                  jlWallCount.get(index).setText(pNames.get(index) + "'s Walls: " + wallCount.get(index));           
               }
               else if (genObject instanceof TokenPlace){
                  TokenPlace tp = (TokenPlace)genObject;
                  int x = tp.getX();
                  int y = tp.getY();
                  int index = tp.getIndex();
                 
                  int prevx = tLocation[index][0];
                  int prevy = tLocation[index][1];
                  
                  //Here change the locations
                  tLocation[index][0] = x;
                  tLocation[index][1] = y;
                  
                  //Change the values in the grid to show the new locations as filled or empty
                  playerArray[prevx][prevy] = 0;
                  playerArray[x][y] = 1;
                  
                  playerSpace[prevx][prevy].setIcon(emptySpace); //Uses empty space graphic to show spot is empty
                  playerSpace[x][y].setIcon(imageList[index]); //use player info to get the proper Icon Image
                  
               }
               
               
					else if(genObject instanceof PlayerExit){
						PlayerExit pl = (PlayerExit)genObject;
						
						try{
							ois.close();
							oos.close();
						}
						catch(IOException ioe){
						}
						finally{
							System.exit(0);
						}
					}
               
               
            }//End of while 
         
          } //end of try

         
          catch (IOException io){
            //io.printStackTrace();
          }
          catch (ClassNotFoundException cnf){
            //cnf.printStackTrace();
          }
      
      
      }//end of run method  
      
      @Override
      public void actionPerformed (ActionEvent ae){
          
      }
   } //End of inner class ThreadConnection
   
    
   /**
   * enableDisableWall method, enables or disables the center walls
   *
   * @param clickable a boolean value, false when not this players turn, true if it is their turn
   */ 
   
   public void enableDisableWall(boolean clickable){
      for(int x = 0; x < 9; x++){
         for(int y = 0; y < 9; y++){
            if(centerArray[x][y] == 0){
               centerWall[x][y].setEnabled(clickable);
            }
         }
      }
   }
   
   /**
   * enableDisableSpace method, enables or disables the player spaces
   *
   * @param clickable a boolean value, false when not this players turn, true if it is their turn
   */ 
   
   public void enableDisableSpace(boolean clickable){
      int x = tLocation[clientNum][0];
      int y = tLocation[clientNum][1];
      
      //action listner prevents this from having an event
      playerSpace[x][y].setEnabled(clickable);
       
      try{
         if(playerArray[x+1][y] == 0 && bottomArray[x][y] == 0){
            playerSpace[x+1][y].setEnabled(clickable);
         }  
      }
      catch(ArrayIndexOutOfBoundsException ae){}
      try{
         if(playerArray[x-1][y] == 0 && bottomArray[x-1][y] == 0){
            playerSpace[x-1][y].setEnabled(clickable);
         }     
      }
      catch(ArrayIndexOutOfBoundsException ae){}   
      try{
         if(playerArray[x][y+1] == 0 && rightArray[x][y] == 0){
            playerSpace[x][y+1].setEnabled(clickable);
         }            
      }
      catch(ArrayIndexOutOfBoundsException ae){}   
      try{
         if(playerArray[x][y-1] == 0 && rightArray[x][y-1] == 0){
            playerSpace[x][y-1].setEnabled(clickable);
         }            
      }
      catch(ArrayIndexOutOfBoundsException ae){}      
   }
   
   
   /**
   * pathTrial method, checks that the current move should be allowed
   *
   * @return a boolean value, true if the move is valid, false if it is not
   * @return boolean value of whether or not the move is valid
   */ 
   
   public boolean pathTrial(){
      boolean pathAllow = true;
      for(int pNum = 0; pNum < pAmount; pNum++){//change pAmount for less players or have a list of only active players (Option)
         if(pathFound(pNum) == false){
            clearArray(solution);
            pathAllow = false;
            break;
         }
         clearArray(solution);
         if(pNum == pAmount -1){
            pathAllow = true;
         }
      }
      return pathAllow;
   }//end PathTrail
   
   
   /**
   * pathFound method
   *
   * @param playerNum integer value of the player index
   * @return boolean value of whether or not the move is valid
   */ 
   public boolean pathFound(int playerNum){
   
      //passes in player token locations and player number
      if (pathRec(tLocation[playerNum][0], tLocation[playerNum][1], playerNum) == false){
      
         //System.out.println("No path found");
         return false;
      }
      
      //System.out.println("Path found");     
      return true;
   }//end pathFound
   
   
   /**
   * isSafe, returns false if the cordinates are outside of the array or player space is filled
   *
   * @param x  x coordinate
   * @param y  y coordinate
   * @param array 2D array
   * @return boolean value of whether or not the move is valid
   */ 
   public boolean isSafe(int x, int y, int array[][]){
      return( x >= 0 && x < 9 && y >= 0 && y < 9 && array[x][y] == 0); //replace 9 with a variable for Array lengths
   }
   
   /**
   * clearArray, reset the solution to empty
   *
   * @param array 2D array
   */    
   public void clearArray(int array[][]){
      for(int i = 0; i < 9; i++){
         for(int j = 0; j < 9; j++){
            array[i][j] = 0;
         }//end of inner loop
      }//end of outer loop
   }//end of Clear Solution
   
   /**
   * pathRec method
   *
   * @param x x-coordinate
   * @param y y-coordinate
   * @param num player number
   * @return boolean value of whether or not the move is valid
   */     
   public boolean pathRec(int x, int y, int num){
   
      //Check for path avaliable allows exit of recursion
      if((num == 0 || num == 1) && x == winCon[num]){
         //System.out.println("in win codition "+ x + " "+ winCon[num]);
         solution[x][y] = 1;
         //printSolution(solution, bottomArray, rightArray, centerArray); //Prints array of solution path
         return true; 
      }//end of if , check for player 1 and 2 have a path
      if((num == 2 || num == 3) && y == winCon[num]){
         solution[x][y] = 1;
         //printSolution(solution, bottomArray, rightArray, centerArray); //Prints array of solution path
         return true;
      }// end of if, check for player 3 and 4 have a path
      
      if(isSafe(x, y, solution) == true){ //runs if player space is safe
         
         //mark solution path taken
         solution[x][y] = 1;
         
         //check for movement depending on player direction needed to win
         if(num == 0){
            //try south
            if(isSafe(x,y,bottomArray) == true){ //check if wall in array and empty 
               if(pathRec(x + 1, y, num)){
                  return true;
               }
            }
            //try east
            if(isSafe(x,y,rightArray) == true){ //check if wall in array and empty
               if(pathRec(x, y + 1, num)){
                  return true;
               }
            }
            //try west
            if(isSafe(x,y-1,rightArray) == true){//check if wall in array and empty
               if(pathRec(x, y - 1, num)){
                  return true;
               }
            }
            //backtrack
            solution[x][y] = 0;
            return false;          
         }
         if(num == 1){
            //try north
            if(isSafe(x - 1,y,bottomArray) == true){ //check if wall in array and empty 
               if(pathRec(x - 1, y, num)){
                  return true;
               }
            }
            //try west
            if(isSafe(x,y - 1,rightArray) == true){//check if wall in array and empty
               if(pathRec(x, y - 1, num)){
                  return true;
               }
            }
            //try east
            if(isSafe(x,y,rightArray) == true){ //check if wall in array and empty
               if(pathRec(x, y + 1, num)){
                  return true;
               }
            }
            //backtrack
            solution[x][y] = 0;
            return false;  
         }
         if(num == 2){
            //try east
            if(isSafe(x,y,rightArray) == true){ //check if wall in array and empty
               if(pathRec(x, y + 1, num)){
                  return true;
               }
            }
            //try south
            if(isSafe(x,y,bottomArray) == true){ //check if wall in array and empty 
               if(pathRec(x + 1, y, num)){
                  return true;
               }
            }
            //try north
            if(isSafe(x - 1,y,bottomArray) == true){ //check if wall in array and empty 
               if(pathRec(x - 1, y, num)){
                  return true;
               }
            }
            //backtrack
            solution[x][y] = 0;
            return false;    
         }
         if(num == 3){
            //try west
            if(isSafe(x,y - 1,rightArray) == true){//check if wall in array and empty
               if(pathRec(x, y - 1, num)){
                  return true;
               }
            }
            //try north
            if(isSafe(x - 1,y,bottomArray) == true){ //check if wall in array and empty 
               if(pathRec(x - 1, y, num)){
                  return true;
               }
            }
            //try south
            if(isSafe(x,y,bottomArray) == true){ //check if wall in array and empty 
               if(pathRec(x + 1, y, num)){
                  return true;
               }
            }
            //backtrack
            solution[x][y] = 0;
            return false;    
         }
      }//is playerspace safe end 
      
      return false; //if player space is unsafe           
   }//end of path Recursion     
   
   
   
   
}//end of Outer
