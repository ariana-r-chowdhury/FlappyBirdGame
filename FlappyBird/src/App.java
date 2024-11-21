import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        
        //creating our game window
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");

        frame.setSize(boardWidth, boardHeight); //to set the size

        frame.setLocationRelativeTo(null); //places the window at the center of the screen
        frame.setResizable(false); // so that players cannot resize the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // when player clicks x button on the window it will terminate the program

        //adding our JPanel onto our frame
        FlappyBird flappyBird = new FlappyBird(); //adding an instance of flappybird
        frame.add(flappyBird); //adds flappy bird to frame
        frame.pack(); // so that the bar on top does not count when measuring the size
        flappyBird.requestFocus(); //flappybird receives inputs from user
        frame.setVisible(true); // generally you'd want your window to be visible after you've added all the settings

    }
}

// next we're gonna make a J Panel which we will use for our canvas
// With a J Panel we can draw our game

//JFrame is a top-level window container that provides the basic functionality 
        //for creating a GUI
        //JFrames are used to create windows that can hold various components like 
        //buttons, labels, text fields, and more. They provide the basic structure and 
        //functionality for a window, including title bars, borders, and resizing capabilities.
        //Flappy Bird is the title of the window