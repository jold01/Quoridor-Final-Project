import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.util.*;
import java.net.*;

public class Player extends JFrame{

   // send output to server
   OutputStream out;
   ObjectOutputStream oos;
   
   JButton jbSend;
   
   // open input from the server
   InputStream in;
   ObjectInputStream ois;


   int[][] playerSpace = new int[9][9];
                                    
   int[][] rightWall = new int[9][9];

   int[][] bottomWall = new int[9][9];

   int[][] centerWall = new int[9][9];
   
   int[][] solution = new int[10][10];
   
   int[][] tLocation = new int[][]{
                                 {0,0}, //change 2nd number to 4 when using this for real
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
   
   
   //CHAT VAR
   private JTextArea jtaMessage; 
   private JTextField jtfMessage; 
   
   //Player Names temporary only
   ArrayList<String> pNames = new ArrayList<String>();
   
   //Label for ScoreBoard 
   ArrayList<JLabel> jlScore = new ArrayList<JLabel>();
   
   ArrayList<Integer> pScore = new ArrayList<Integer>();
   
   //Record wins if players are playing multiple games
   ArrayList<Integer> wallCount = new ArrayList<Integer>();
   
   
   //Label for ScoreBoard 
   private JLabel jlScoreTitle;
   private JLabel jlWallTitle;
   
   //Label of Walls for wallTracker
   ArrayList<JLabel> jlWallCount = new ArrayList<JLabel>();
 
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
   
     new Player();
     
   } //End of main
 
 
   
   public Player(){
      
      for(int i = 0; i < pAmount; i++){
         pNames.add("Player " + i);
         wallCount.add(7);
         pScore.add(0);
         jlWallCount.add(new JLabel());
         jlScore.add(new JLabel());
         
      }
      add(new GridBag(), BorderLayout.CENTER);
      
      add(new ChatDisplay(), BorderLayout.EAST); 
      
      add(new Tracker(), BorderLayout.SOUTH);             
         //Windows and visibility 
      setTitle("Quoridor Final Project");
      setSize(1000,700);
      setLocationRelativeTo(this);
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      setVisible(true); 
      
      new SocketSetup();
      
   }

   
   class ChatDisplay extends JPanel implements ActionListener{
   
      public ChatDisplay(){
      	
        	//int columnHeight = getHeight() - 10;
         
         jtaMessage = new JTextArea(30, 30);
         jtaMessage.setEditable(false);
         JScrollPane scroll = new JScrollPane(jtaMessage);
         
         jtfMessage = new JTextField(24);
         jbSend = new JButton("Send");
         
      
         JPanel jpChatOutput = new JPanel();
      
         jpChatOutput.add(scroll);
      
         JPanel jpChatInput = new JPanel();
      
         jpChatInput.add(jtfMessage);
         jpChatInput.add(jbSend);
      	
         JPanel jpChatMain = new JPanel(new GridLayout(0,1));
      
         jpChatMain.add(jpChatOutput);
         jpChatMain.add(jpChatInput);
         
         add(jpChatMain, BorderLayout.CENTER);

         //System.out.println(""+ this.getHeight());
         setBackground(Color.LIGHT_GRAY);  
      
      } //End of chat display method
      
      
      /* For actionListener interface */
      @Override
      public void actionPerformed (ActionEvent ae){
          
      }
      
      
      
   }
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
   class GridBag extends JPanel implements ActionListener{
   
      JButton[][] centerWall;
      JButton[][] playerSpace;
      JButton[][] rightWall;
      JButton[][] bottomWall;
      JButton button;
      
      GridBag(){
   
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         int h = screenSize.height;
         int w = screenSize.width;
         
         JPanel jpBoard;
         jpBoard = new JPanel(new GridLayout(9,9));
         jpBoard.setPreferredSize(new Dimension(550, 550));
         playerSpace = new JButton[10][10];
         rightWall = new JButton[10][10];
         bottomWall = new JButton[10][10];
         centerWall = new JButton[10][10];
   
         for (int i = 0; i < 9; i++){
         
            for (int j = 0; j < 9; j++){
               
               JPanel jpSquare = new JPanel(); //one square is 4 players spaces
         
               //JButton button;
               GridBagLayout gridbag = new GridBagLayout();
               jpSquare.setLayout(gridbag);
               GridBagConstraints c = new GridBagConstraints(); 
                   
               //player space   
               button = new JButton("            ");
               button.setFocusPainted(false);
               button.setBackground(Color.GRAY);
               c.ipadx = (int)(24); //x spacing
               c.ipady = (int)(24); //y spacing
               c.gridx = 0; //0 x location
               c.gridy = 0; //0 y location
               gridbag.setConstraints(button, c);
               jpSquare.add(button);
               playerSpace[i][j] = button;
               playerSpace[i][j].setActionCommand("player" + "-" + i + "-" + j);
               playerSpace[i][j].addActionListener(this);
               playerSpace[i][j].setEnabled(false);
               
               //right wall creation
               button = new JButton(""); 
               button.setBackground(Color.BLUE);
               c.ipadx = (int)(40);
               c.ipady = (int)(40);
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
               button.setBackground(Color.BLUE);
               c.gridx = 0;
               c.gridy = 1;
               c.ipadx = (int)(60);
               c.ipady = (int)(20);
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
               button.setBackground(Color.BLUE);
               c.gridx = 1;
               c.gridy = 1;
               c.ipadx = (int)(40);
               c.ipady = (int)(20);
               gridbag.setConstraints(button, c);
               centerWall[i][j] = button;
               centerWall[i][j].setActionCommand("center" + "-" + i + "-" + j);
               centerWall[i][j].addActionListener(this); 
                           
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
      
      public void	actionPerformed(ActionEvent ae){
         
         //Get location of button clicked
         String command = ae.getActionCommand();
         
         String[] split = command.split("-");
         
         String name = split[0];
         int x = Integer.parseInt(split[1]);
         int y = Integer.parseInt(split[2]);
         int choice  = 0;
         
         //If middle wall clicked, need to decide if they want to place a horizontal or vertical wall
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
               //System.out.println("Horizontal Wall");
               centerWall[x][y].setBackground(Color.RED);
               
               bottomWall[x][y].setBackground(Color.RED);
               bottomWall[x][y+1].setBackground(Color.RED);
               
            }
            //Vertical wall was clicked
            else if(choice == JOptionPane.NO_OPTION){
               //System.out.println("Vertical Wall");
               centerWall[x][y].setBackground(Color.RED);
               
               rightWall[x][y].setBackground(Color.RED);
               rightWall[x+1][y].setBackground(Color.RED);
            }
             
         } //End of center if
            
      } //End of actionPerformed
      
      public void placeWall(){
      
         System.out.println("asds");
      
      } //End of placeWall method 
   
   
   } //End of class GridBag
   
   class SocketSetup {
   
      public SocketSetup(){
      
         try{
            Socket s = new Socket("localhost", 16789);
                     
            // output to the server
            out = s.getOutputStream(); 
            oos = new ObjectOutputStream(out);
            
            // open input from the server
            in = s.getInputStream(); 
            ois = new ObjectInputStream(in);
            
            ThreadReader tr = new ThreadReader(); 
            tr.start(); //starts the reader thread 
            
            String playerName;
            playerName = JOptionPane.showInputDialog("Enter a player name: ");
            PlayerName player = new PlayerName(playerName);
            
            oos.writeObject(player); //send player name to server
            oos.flush();
            
         
         } //End of try
         
         catch (ConnectException ce){
            System.out.println("Sorry no server available at the time");
         }
   
         catch (IOException io){
            //io.printStackTrace();
         }  
      
      }//End of constructor
   
   
   } //End of class SocketSetup
   
   
   class ThreadReader extends Thread implements ActionListener{
   
      public ThreadReader(){
         
                
   
      }//End of constructor   
      
      //starts the work of the thread
      public void run(){
      
         try{
            PlayersInfo playersInfo;

            
            Object genObject = null; 
            while((genObject = (Object)ois.readObject()) != null){
               if (genObject instanceof PlayersInfo){
                  playersInfo = (PlayersInfo)genObject;
                  System.out.println(playersInfo.getName(0)); 
               }//End of if
            }//End of while 
         
          } //end of try

         
          catch (IOException io){
            io.printStackTrace();
          }
          catch (ClassNotFoundException cnf){
            cnf.printStackTrace();
          }
      
      
      }//end of run method  
      
      @Override
      public void actionPerformed (ActionEvent ae){
          
      }


} //End of inner class ThreadConnection
   
   
   
   
   
}//end of Outer