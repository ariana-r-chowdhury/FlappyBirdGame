import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList; // so this is going to store all the pipes in our game
import java.util.Random; // so this is going to be used for placing our pipes at random places
import javax.swing.*;

// FloppyBird class inherits JPanel
// inheritance: allows us to find a new class with all the functionalities of the JPanel class
// This way we can keep JPanel features and add variales and functions needed for our flappybird game

public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    //ActionListener for repeating smth multiple times
    //KeyListener so that prssing space can make bird jump


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

    class Bird // this class is made to make it easier to make it easier to access the values with simplified name
    {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img; //an image field

        Bird(Image img) // define the constructor for the image
        {
            this.img = img;
            //used to differentiate between the instance variable and other variables with the same name
        }
    }
    //game logic

    //here we are using the Bird class as a blueprint for "bird"
    Bird bird;
    //in order to make the bird move we gotta give it a velocity
    int velocityY = 0; 
    // the bird only needs to move in the y direction cuz the pipes will move in the x direction
    // also -y is upward because (0,0) is at the top
    int gravity = 1;
    //gravity is added so that the bird doesn't keep going upward forever
    //every frame the bird is going to slow down 1 pixel

    Timer gameLoop; //the timer class lets me run a piece of code repeatedly at certain intervals

    FlappyBird()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);

        setFocusable(true); //enables the component to recieve focus. responds to keyboard typing
        addKeyListener(this); //ensures that we check the 3 keyPressed, Typed, Released

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        //get class referrs to the flappybird class and get resource referrs to it's location get Image cuz the variable is image variable
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdImg);

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start(); // starts the timer

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
        // we've now created a window and the bg img 
        // top left corner of the window is (0,0) and bottom right is (360,640)
        // by calling draw image we passed in the x,y and the width and height
        // when we're drawing we always start from the top right corner (b'_')b


        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null); 
        // we've now added the bird but we need to make it move for which we gotta make a game loop
        // the paint component function only calls the draw method once
        // to make a loop we've gotta make a function that does it over and over again (per frame) (60fps)

    }

    public void move()
    {
        // updates all the x and y position of the object
        velocityY += gravity; //update the velocity with the gravity
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);
    }

    @Override //this is going to be the action performed 60 times every second
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); //this will call paintcomponent
    }

    @Override

    //key prssed-> for all keys
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            velocityY = -9;
            //if the key pressed is the space bar key its gonna do a velocity check and go up (cuz v is -9 here)
        }
    }

    // we wont be using keyTypped or Released
    @Override
    //keyReleased-> when you let go off a key and the key goes back up
    public void keyReleased(KeyEvent e) {} 
    @Override
    //keyTyped-> for letter keys
    public void keyTyped(KeyEvent e) {}

}
