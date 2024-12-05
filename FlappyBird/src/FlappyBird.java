import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList; // this is going to store all the pipes in our game
import java.util.Random; // this is going to be used for placing our pipes at random places
import javax.swing.*;

// FloppyBird class inherits JPanel
// inheritance: allows us to find a new class with all the functionalities of the JPanel class
// This way we can keep JPanel features and add variales and functions needed for our flappybird game

public class FlappyBird extends JPanel implements ActionListener, KeyListener{

    int boardWidth = 360;
    int boardHeight = 640;
    
    // IMAGES
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
        }
    }

    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    class Pipe 
    {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; // to see whether flappybird passed the pipes

        Pipe(Image img)
        {
            this.img = img;
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

    int velocitx = -4; // for the pipes

    ArrayList<Pipe> pipes;
    Random random = new Random();

    Timer gameLoop; //the timer class lets me run a piece of code repeatedly at certain intervals
    Timer placePipesTimer;
    
    boolean gameOver = false;

    double score = 0;

    FlappyBird()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);

        setFocusable(true); //enables the component to recieve focus. responds to keyboard typing
        addKeyListener(this); //ensures that we check the 3 keyPressed, Typed, Released

        //load images
        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>(); //now we have an arraylist of pipes and now we add a new pipe every 1.5s

        //place pipes timer : calls "placePipes" every 1500 milisecons
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                placePipes();
            }
        });
        placePipesTimer.start();

        //game timer
        gameLoop = new Timer(1000/60, this);
        gameLoop.start(); // starts the timer

    }

    public void placePipes()
    {
        //(0-1) * (pipeHeight/2) -> (0,256)
        //128
        //0-128-(0,256) --> pipeHeight/4 -> 3/4 of piprHeight
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
        
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    //get class referrs to the flappybird class and get resource referrs to it's location get Image cuz the variable is image variable

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

        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for (int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver)
        {
            g.drawString("Game Over: " + String.valueOf((int)score), 10, 35);
        }
        else
        {
            g.drawString(String.valueOf((int) score), 10, 35);
        }

    }
    
    // we've now created a window and the bg img 
        // top left corner of the window is (0,0) and bottom right is (360,640)
        // by calling draw image we passed in the x,y and the width and height
        // when we're drawing we always start from the top right corner (b'_')b

    // we've now added the bird but we need to make it move for which we gotta make a game loop
        // the paint component function only calls the draw method once
        // to make a loop we've gotta make a function that does it over and over again (per frame) (60fps)


    public void move()
    {
        //bird                                                                        
        velocityY += gravity;     //update the velocity with the gravity// updates all the x and y position of the object
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        //pipes
        for (int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            pipe.x += velocitx; // every frame pipes will move -4 to the left

            if (!pipe.passed && bird.x > pipe.x + pipe.width)
            {
                pipe.passed = true;
                score += 0.5;
            }

            if (collision(bird, pipe))
            {
                gameOver = true;
            };
        }

        if (bird.y > boardHeight)
        {
            gameOver = true;
        }
    }

    public boolean collision(Bird a, Pipe b)
    {
        return a.x < b.x + b.width &&
        a.x + a.width > b.x &&
        a.y < b.y + b.height &&
        a.y + a.height >b.y;
    }

    @Override //this is going to be the action performed 60 times every second
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); //this will call paintcomponent

        if (gameOver)
        {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
    //key prssed-> for all keys
    //if the key pressed is the space bar key its gonna do a velocity check and go up (cuz v is -9 here)
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            velocityY = -9;

            if (gameOver)
            {
                //restart the game by resetting the conditions
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                score = 0;
                gameOver = false;
                gameLoop.start();
                placePipesTimer.start();
            }
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
