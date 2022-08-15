// Krishay
// 5/16/22
// SprintTyperIntro.java
// This file contains the panel for the start page, which is also the first panel
// that shows up in the game. It has four buttons: play, instructions, settings,
// and quit. Each button does their respective task.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Class for the start panel. There are three buttons on this panel: play,
// instructions, and settings. Each button goes to their respective panel. The
// layout is a null layout, so setBounds() must be used for each component in
// order to properly position them.
public class SprintTyperIntro extends JPanel
{
    private SprintTyperHolder sprintHolder; // The holder instance
    private CardLayout cards; // The CardLayout of the holder instance

    private JTextField typingTF; // The text field that is typed in
    
    public SprintTyperIntro(SprintTyperHolder sprintHolderIn, CardLayout cardsIn, JTextField typingTFIn)
    {
        cards = cardsIn;
        sprintHolder = sprintHolderIn;
        typingTF = typingTFIn;
        
        setBackground( new Color(66, 154, 210) );
        setLayout(null);
        
        JLabel gameTitle = new JLabel("Sprint Typer");
        gameTitle.setForeground( new Color(172, 221, 250) );
        gameTitle.setFont( new Font("Serif", Font.BOLD, 70) );
        add(gameTitle);
        gameTitle.setBounds(315, 110, 500, 100);
        
        ButtonListener buttonL = new ButtonListener();
        
        JButton play = new JButton("Play");
        add(play);
        play.setBounds(430, 260, 150, 25);
        play.addActionListener(buttonL);
        
        JButton instructions = new JButton("Instructions");
        add(instructions);
        instructions.setBounds(430, 320, 150, 25);
        instructions.addActionListener(buttonL);
        
        JButton settings = new JButton("Settings");
        add(settings);
        settings.setBounds(430, 380, 150, 25);
        settings.addActionListener(buttonL);

        JButton highScores = new JButton("High Scores");
        add(highScores);
        highScores.setBounds(430, 440, 150, 25);
        highScores.addActionListener(buttonL);
        
        JButton exit = new JButton("Close Game");
        add(exit);
        exit.setBounds(430, 500, 150, 25);
        exit.addActionListener(buttonL);

        ImagesPanel imagePanel = new ImagesPanel();
        add(imagePanel);
        imagePanel.setBounds(0, 0, 1028, 600);
    }

    // This panel draws a camper van and a car as well as the triangles on the top
    // left and bottom right.
    class ImagesPanel extends JPanel
    {
        public ImagesPanel()
        {
            setBackground( new Color(66, 154, 210) );
        }

        // When this method is called, the appropriate shapes and images are drawn.
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.ORANGE);

            int[] triangleTopLeftX = {50, 150, 50};
            int[] triangleTopLeftY = {50, 50, 150};
            g.fillPolygon(triangleTopLeftX, triangleTopLeftY, 3);

            int[] triangleBottomRightX = {980, 880, 980};
            int[] triangleBottomRightY = {530, 530, 430};
            g.fillPolygon(triangleBottomRightX, triangleBottomRightY, 3);
            
            int[] triangleBottomLeftX = {50, 50, 150};
            int[] triangleBottomLeftY = {430, 530, 530};
            g.fillPolygon(triangleBottomLeftX, triangleBottomLeftY, 3);

            int[] triangleTopRightX = {880, 980, 980};
            int[] triangleTopRightY = {50, 50, 150};
            g.fillPolygon(triangleTopRightX, triangleTopRightY, 3);

            Image picture = sprintHolder.getMyImage("Images/Cars/camperVan.png");
            g.drawImage(picture, 200, 300, this);

            picture = sprintHolder.getMyImage("Images/Cars/springCar.png");
            g.drawImage(picture, 730, 300, this);

            picture = sprintHolder.getMyImage("Images/Cars/Racecars/corvette.png");
            g.drawImage(picture, 200, 380, this);

            picture = sprintHolder.getMyImage("Images/Cars/Racecars/formula1.png");
            g.drawImage(picture, 730, 380, this);

            picture = sprintHolder.getMyImage("Images/Cars/Racecars/carDrawing.png");
            g.drawImage(picture, 200, 460, this);

            picture = sprintHolder.getMyImage("Images/Cars/Racecars/porsche.png");
            g.drawImage(picture, 730, 460, this);
        }
    }
    
    // ActionListener for all the buttons in this class.
    class ButtonListener implements ActionListener
    {    
        public void actionPerformed(ActionEvent evt)
        {
            String cmd = evt.getActionCommand();
            
            if (cmd.equals("Play"))
            {
                cards.show(sprintHolder, "Game");
                typingTF.setBackground(Color.WHITE);
                typingTF.setEditable(false);
            }
            else if (cmd.equals("Instructions"))
                cards.show(sprintHolder, "Instructions");
            else if (cmd.equals("Settings"))
                cards.show(sprintHolder, "Settings");
            else if (cmd.equals("High Scores"))
                cards.show(sprintHolder, "HighScoresMain");
            else if (cmd.equals("Close Game"))
                System.exit(1);
        }
    }
}
