import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList; // so this is going to store all the pipes in our game
import java.util.Random; // so this is going to be used for placing our pipes at random places
import javax.swing.*;

// FloppyBird class inherits JPanel
// inheritance: allows us to find a new class with all the functionalities of the JPanel class
// This way we can keep JPanel features and add variales and functions needed for our flappybird game
public class FlappyBird extends JPanel {
    int boardWidth = 360;
    int boardHeight = 640;
    // IMAGES : adding bg

    // The four following variables are going to store our image objects
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //BIRD : just like bg we need to give birb x,y position and width and height

    int birdX = boardWidth/8; //1/8 from the left side of the screen
    int birdY = boardHeight/2; // half of the height
    int birdWidth = 34;
    int birdHeight = 24;


    FlappyBird()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        //get class referrs to the flappybird class and get resource referrs to it's location get Image cuz the variable is image variable
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

    }

    //this is a function of the JPanel
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); //this is just going to invoke the function of the JPanel
        //super referrs to the parent class which is JPanel
        draw(g);
    }

    public void draw(Graphics g)
    {
        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
    }
    // we've now created a window and the bg img 
    // top left corner of the window is (0,0) and bottom right is (360,640)
    // by calling draw image we passed in the x,y and the width and height
    // when we're drawing we always start from the top right corner (b'_')b 


}
