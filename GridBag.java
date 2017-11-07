import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class GridBag extends JFrame implements ActionListener{

   JButton[][] centerWall;
   JButton[][] playerSpace;
   JButton[][] rightWall;
   JButton[][] bottomWall;
   JButton button;

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

      new GridBag();
   
   } //End of main
   
   public GridBag(){

      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      double width = screenSize.getWidth();
      double height = screenSize.getHeight();

      JPanel jpBoard;
      jpBoard = new JPanel(new GridLayout(9,9));
      
      playerSpace = new JButton[10][10];
      rightWall = new JButton[10][10];
      bottomWall = new JButton[10][10];
      centerWall = new JButton[10][10];

      for (int i = 1; i <= 9; i++){
      
         for (int j = 1; j <= 9; j++){
            
            JPanel jpSquare = new JPanel(); //one square is 4 players spaces
      
            //JButton button;
            GridBagLayout gridbag = new GridBagLayout();
            jpSquare.setLayout(gridbag);
            GridBagConstraints c = new GridBagConstraints(); 
                
            //player space   
            button = new JButton("            ");
            button.setFocusPainted(false);
            c.ipadx = (int)(width*0.025); //x spacing
            c.ipady = (int)(width*0.025); //y spacing
            c.gridx = 0; //0 x location
            c.gridy = 0; //0 y location
            gridbag.setConstraints(button, c);
            jpSquare.add(button);
            playerSpace[i][j] = button;
            playerSpace[i][j].setActionCommand("player" + "-" + i + "-" + j);
            playerSpace[i][j].addActionListener(this);
            
            //right wall creation
            button = new JButton(""); 
            button.setBackground(Color.BLUE);
            c.ipadx = 0;
            c.ipady = (int)(width*0.030);
            c.gridx = 1;
            c.gridy = 0;
            gridbag.setConstraints(button, c);
            rightWall[i][j] = button;
            rightWall[i][j].setActionCommand("right" + "-" + i + "-" + j);
            rightWall[i][j].addActionListener(this);

            //get rid of right wall for the last column
            if(j != 9){
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
            c.ipadx = (int)(width*0.035);
            c.ipady = (int)(height*0.015);
            gridbag.setConstraints(button, c);            
            bottomWall[i][j] = button;
            bottomWall[i][j].setActionCommand("bottom" + "-" + i + "-" + j);
            bottomWall[i][j].addActionListener(this);
            
            
            //for bottom row get rid of bottom wall
            if (! (i == 9)){             
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
            c.ipadx = 0;
            c.ipady = (int)(height*0.015);
            gridbag.setConstraints(button, c);
            centerWall[i][j] = button;
            centerWall[i][j].setActionCommand("center" + "-" + i + "-" + j);
            centerWall[i][j].addActionListener(this); 
                        
            //if not bottom row, add normal
            if ((!(i == 9) && (j != 9))){             
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
            
      //Windows and visibility 
      setTitle("Quoridor Mini Project");
      pack();
      setLocationRelativeTo(this);
      setDefaultCloseOperation( EXIT_ON_CLOSE );
      setVisible(true); 
   
   
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