// Krishay
// 5/16/22
// SprintTyper.java
// This file contains a JFrame and adds a JPanel that is the main holder of all
// the panels of the game.

import javax.swing.JFrame;

// Main class for the JFrame.
public class SprintTyper
{
    public SprintTyper()
    {
    }
    
    public static void main(String [] args)
    {
        SprintTyper sprintType = new SprintTyper();
        sprintType.run();
    }
    
    // When the program is ran, an instance of the holder class is created.
    public void run()
    {
        JFrame frame = new JFrame("Sprint Typer");
        frame.setSize(1028, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(50, 50);
        frame.setResizable(false);
        SprintTyperHolder sprintTypePan = new SprintTyperHolder();
        frame.getContentPane().add(sprintTypePan);
        frame.setVisible(true);
    }
}
