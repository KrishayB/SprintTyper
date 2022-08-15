// Krishay
// 5/16/22
// Instructions.java
// This panel contains the instructions for the game. When the instructions
// button is pressed in the first page, the program switches to this panel.
// Class for the instructions panel. On this panel, there is a BorderLayout, and
// in the center, there is a GridLayout. The GridLayout, called centerGrid, contains
// the panel for the directions, which is on the left, and a panel for the screenshots,
//  which is on the right.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Instructions extends JPanel
{
    private SprintTyperHolder sprintHolder; // The holder class
    private CardLayout cards; // The CardLayout of the holder class
    private Color darkGreen; // Color at the bottom of the panel
    private Color middleGreen; // Color for the left of the panel
    private Color lightGreen; // Color for the right of the panel
    
    public Instructions(SprintTyperHolder sprintHolderIn, CardLayout cardsIn)
    {
        sprintHolder = sprintHolderIn;
        cards = cardsIn;

        darkGreen = new Color(105, 132, 116);
        middleGreen = new Color(137, 158, 129);
        lightGreen = new Color(186, 199, 167);
        
        setBackground(darkGreen);
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = makeHomeButton();
        add(buttonPanel, BorderLayout.SOUTH);

        JPanel centerGrid = new JPanel();
        centerGrid.setLayout(new GridLayout(1, 2));

        JPanel directionsPan = makeDirections();
        JPanel directionsPanel = new JPanel();
        directionsPanel.setLayout(new GridLayout(1, 2));
        directionsPanel.setBackground(middleGreen);
        directionsPanel.add(directionsPan);

        CarPanel carPanel = new CarPanel();

        JPanel leftGrid = new JPanel();
        leftGrid.setLayout( new GridLayout(2, 1) );
        leftGrid.add(directionsPanel);
        leftGrid.add(carPanel);

        centerGrid.add(leftGrid);

        ScreenshotsPanel screenshotsPanel = new ScreenshotsPanel();
        centerGrid.add(screenshotsPanel);

        add(centerGrid, BorderLayout.CENTER);
    }

    // Here, the home button that goes in the south of this panel is created.
    // The method returns a JPanel containing the JButton.
    public JPanel makeHomeButton()
    {
        JButton homeButton = new JButton("Home");
        HomeButtonListenerInstructions homeButtonListener = new
            HomeButtonListenerInstructions();
        homeButton.addActionListener(homeButtonListener);

        JPanel buttonP = new JPanel();
        buttonP.setLayout( new FlowLayout(FlowLayout.CENTER) );
        buttonP.setBackground(darkGreen);
        buttonP.add(homeButton);

        return buttonP;
    }

    // This method creates a JScrollPane containing the directions. It returns
    // a JPanel containing the JScrollPane. The
    // setBorder(BorderFactory.createEmptyBorder()) hides the border around the
    // JScrollPane.
    public JPanel makeDirections()
    {
        // Create the JTextArea
        JTextArea directions = new JTextArea("1. To play the game, hit the play " +
        "button and type the text. There will be 5 texts to type. If you type a wrong " +
        "character, then the text field will turn red. Click reset if you have trouble. (1st Img)" +
        "\n\n\n2. After, you  can answer a few short questions in a quiz. (2nd Img)" +
        "\n\n\n3. Customize your car and change game function in settings! (3rd Img)\n\n_");
        directions.setEditable(false);
        directions.setBackground(middleGreen);
        directions.setFont(new Font("Sans Serif", Font.BOLD, 20));
        directions.setLineWrap(true);
        directions.setWrapStyleWord(true);

        JScrollPane directionsS = new JScrollPane(directions);
        directionsS.setPreferredSize(new Dimension(300, 300));
        directionsS.setBorder(BorderFactory.createEmptyBorder());
        directionsS.setBackground(middleGreen);

        JPanel directionsP = new JPanel();
        directionsP.setLayout(new FlowLayout(FlowLayout.CENTER));
        directionsP.add(directionsS);
        directionsP.setBackground(middleGreen);

        return directionsP;
    }

    class ScreenshotsPanel extends JPanel
    {
        public ScreenshotsPanel()
        {
            setBackground(lightGreen);
        }

        // The method uses a method from the holder class to get an the screenshot
        // image. After, it draws the image at the appropriate location.
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Image picture = sprintHolder.getMyImage("Images/Screenshots/game.png");
            g.drawImage(picture, 120, 0, this);

            picture = sprintHolder.getMyImage("Images/Screenshots/quiz.png");
            g.drawImage(picture, 120, 178, this);

            picture = sprintHolder.getMyImage("Images/Screenshots/settings.png");
            g.drawImage(picture, 120, 356, this);
        }
    }

    class CarPanel extends JPanel
    {
        public CarPanel()
        {
            setBackground(middleGreen);
        }

        // The method fetches the image and then draws it at a certain position.
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Image picture = sprintHolder.getMyImage(
                "Images/Cars/springCarFullsize.png");
            g.drawImage(picture, 80, 60, this);
        }
    }
    
    class HomeButtonListenerInstructions implements ActionListener
    {
        // When the home button is pressed, this takes it back the start screen.
        public void actionPerformed(ActionEvent evt)
        {
            cards.show(sprintHolder, "Intro");
        }
    }
}
