// Krishay
// 5/16/22
// Game.java
// This panel is where the typing game runs. When the play button is pressed
// within the first page, the program switches to this panel. To play the game
// the user must click the start button for the timer and then type the text that
// is displayed. In this panel, there are many field variables, and there are also
// nested classes. The panel's main content is in the center of a BorderLayout, and
// it contains a GridLayout that contains a panel for the upper half contents and a
// panel for the lower half contents.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import javax.swing.Timer;

import java.lang.StringIndexOutOfBoundsException;

public class Game extends JPanel implements KeyListener
{
    private SprintTyperHolder sprintHolder; // The holder class needed for accessing
                                             // methods
    private CardLayout cards; // The CardLayout of the holder class
    private GameData gameData; // The game data instance

    private Color green; // The green color used throughout this JPanel
    private Color darkGreen; // The dark green color used throughout this JPanel

    private Font labelFont; // The font for the top JLabels

    private MainTimer timerPanel; // The timer of the game used for animations
                                  // and calculations

    private RaceTrackDrawing raceTrackPanel; // The race track panel

    private int carXTop; // The change in x for the top computer car
    private int carXBottom; // The change in x for the bottom computer car

    private int totalCarXTop; // The x-position of the top computer car
    private int totalCarXBottom; // The x-position of the bottom computer car

    private Image topCar; // The image for the top computer car
    private Image bottomCar; // The image for the bottom computer car

    private JTextField typingField; // The text field to type in

    private String typeText; // The text that is to be typed
    private int phrasePosition; // The index of typeText
    private int playerCarX; // The x-position of the player's car

    private char letter; // The letter that is typed
    private int code; // The key code that of the letter that is typed
    private char previousLetter; // The previous letter than
    private String phrase; // The phrase that is typed

    private int counter; // The counter for how many texts the user has typed

    private JLabel textToType; // A JLabel for the text that is to be typed
    private JLabel amtTextsTyped; // A JLabel to inform the user how many texts they typed

    private boolean backSpacePressed; // Used to determine whether backspace was pressed
    private boolean shiftPressed; // Used to determine whether shift was pressed
    private boolean enterPressed; // Used to determine whether enter was pressed
    private boolean altPressed; // Used to determine whether alt was pressed
    private boolean capsPressed; // Used to determine whether caps lock was pressed
    private boolean escapePressed; // Used to determine whether escape was pressed
    private boolean insertPressed; // Used to determine whether insert was pressed
    private boolean ctrlPressed; // Used to determine whether control was pressed
    private boolean commandPressed; // Used to determine whether command was pressed

    private String secondsToDisplay; // The amount of seconds currently on the timer

    private int[] wpms; // Contains the WPM for each race

    private JLabel wpm; // Shows the last race's WPM

    private String[] racePositions; // Contains the car positions for the first race

    private boolean printedMessage;
    private boolean printedMessage1;
    
    public Game(SprintTyperHolder sprintHolderIn, CardLayout cardsIn, GameData gameDataIn)
    {
        sprintHolder = sprintHolderIn;
        cards = cardsIn;
        gameData = gameDataIn;
        green = new Color(98, 189, 105);
        darkGreen = new Color(48, 105, 75);
        labelFont = new Font("Dialog", Font.PLAIN, 25);
        carXTop = 10;
        carXBottom = 10;
        totalCarXTop = 0;
        totalCarXBottom = 0;
        counter = 0;
        wpms = new int[5];
        racePositions = new String[3];
        printedMessage = false;
        printedMessage1 = false;

        int index = (int)(Math.random() * 3 + 1);
        int topCarIndex;
        if (index != 4)
            topCarIndex = index + 1;
        else
            topCarIndex = index - 1;

        raceTrackPanel = new RaceTrackDrawing();
        topCar = raceTrackPanel.generateRandomCar(index);
        bottomCar = raceTrackPanel.generateRandomCar(topCarIndex);
        
        setBackground(green);
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = makeHomeButton();
        add(buttonPanel, BorderLayout.SOUTH);

        JPanel centerGrid = new JPanel();
        centerGrid.setBackground(green);
        centerGrid.setLayout(new GridLayout(2, 1));

        JPanel upperHalfPanel = new JPanel();
        upperHalfPanel.setBackground(green);
        upperHalfPanel.setLayout(new BorderLayout());

        JPanel gridLayoutTop = new JPanel();
        gridLayoutTop.setBackground(green);
        gridLayoutTop.setLayout(new GridLayout(1, 3));

        JLabel directions = sprintHolder.makeLabel("Type the text!", labelFont);
        gridLayoutTop.add(directions);

        JLabel title = sprintHolder.makeLabel("Sprint Typer", labelFont);
        gridLayoutTop.add(title);

        timerPanel = new MainTimer();
        timerPanel.setPreferredSize(new Dimension(200, 100));
        timerPanel.setBackground(green);
        gridLayoutTop.add(timerPanel);

        JPanel centerFlowLayout = new JPanel();
        centerFlowLayout.setBackground(green);
        centerFlowLayout.setLayout(new FlowLayout(FlowLayout.CENTER));

        typeText = gameData.getTypingTextArray(counter);
        textToType = sprintHolder.makeLabel(typeText, new Font("Dialog", Font.BOLD, 15));
        centerFlowLayout.add(textToType);

        typingField = makeTextField();
        centerFlowLayout.add(typingField);

        JPanel labelflowPanel = new JPanel();
        labelflowPanel.setPreferredSize(new Dimension(1028, 60));
        amtTextsTyped = sprintHolder.makeLabel("You have to type " +
            (5 - counter) + " more text(s)!",
            new Font("Dialog", Font.PLAIN, 13));
        labelflowPanel.add(amtTextsTyped);
        labelflowPanel.setBackground(green);
        centerFlowLayout.add(labelflowPanel);

        wpm = sprintHolder.makeLabel("WPM for last race: 0",
            new Font("Dialog", Font.BOLD, 15));
        centerFlowLayout.add(wpm);

        upperHalfPanel.add(centerFlowLayout, BorderLayout.CENTER);
        upperHalfPanel.add(gridLayoutTop, BorderLayout.NORTH);

        JPanel lowerHalfPanel = new JPanel();
        lowerHalfPanel.setBackground( new Color(46, 46, 46) );
        lowerHalfPanel.add(raceTrackPanel);

        centerGrid.add(upperHalfPanel);
        centerGrid.add(lowerHalfPanel);
        add(centerGrid, BorderLayout.CENTER);
    }

    // Whenever this is called, the counter for the number of texts will reset,
    // and the racetrack and timer will update accordingly. The racetrack will
    // reset the positions of the cars, and the timer will reset to 1:00.
    public void paintComponent(Graphics g)
    {
        counter = 0;
        amtTextsTyped.setText("You have to type " + (5 - counter) +
            " more text(s)!");
        raceTrackPanel.repaint();
        timerPanel.repaint();
        timerPanel.starter.setText("Start Race");
        super.paintComponent(g);
    }

    // When a key is pressed, if the key is a back space, then the position at
    // which the phrase is checked is moved back, and the respective boolean is
    // changed. Otherwise, if the shift key or the enter key was not pressed,
    // we know a letter or number key was pressed, so we increment the position
    // at which the phrase is checked.
    public void keyPressed(KeyEvent evt)
    {
        letter = evt.getKeyChar();
        code = evt.getKeyCode();

        if (code == KeyEvent.VK_BACK_SPACE)
        {
            if (!((typingField.getText()).equals("")))
                phrasePosition--;

            backSpacePressed = true;
        }
        else if (code != KeyEvent.VK_SHIFT &&
                 code != KeyEvent.VK_ENTER &&
                 code != KeyEvent.VK_ALT &&
                 code != KeyEvent.VK_CAPS_LOCK &&
                 code != KeyEvent.VK_ESCAPE &&
                 code != KeyEvent.VK_INSERT &&
                 code != KeyEvent.VK_CONTROL &&
                 code != KeyEvent.VK_META
        )
        {
                phrasePosition++;
                backSpacePressed = false;
                shiftPressed = false;
                enterPressed = false;
                altPressed = false;
                capsPressed = false;
                escapePressed = false;
                insertPressed = false;
                ctrlPressed = false;
                commandPressed = false;
        }
        else if (code == KeyEvent.VK_SHIFT)
            shiftPressed = true;
        else if (code == KeyEvent.VK_ENTER)
            enterPressed = true;
        else if (code == KeyEvent.VK_ALT)
            altPressed = true;
        else if (code == KeyEvent.VK_CAPS_LOCK)
            capsPressed = true;
        else if (code == KeyEvent.VK_ESCAPE)
            escapePressed = true;
        else if (code == KeyEvent.VK_INSERT)
            insertPressed = true;
        else if (code == KeyEvent.VK_CONTROL)
            ctrlPressed = true;
        else if (code == KeyEvent.VK_META)
            commandPressed = true;
    }

    // Additional key methods
    public void keyReleased(KeyEvent evt) {}
    public void keyTyped(KeyEvent evt) {}

    class RaceTrackDrawing extends JPanel
    {
        private Car car; // The instance used to draw the car

        public RaceTrackDrawing()
        {
            setLayout(null);
            car = new Car(gameData, 5);
            car.setPreferredSize(new Dimension(105, 80));
            car.setOpaque(false);

            add(car);
            car.setBounds(playerCarX, 0, 105, 80);

            setBackground( new Color(46, 46, 46) );
            setPreferredSize(new Dimension(1028, 300));
        }

        // If the text that the user typed is correct, then the x-position of the
        // player car will change, and the color of the text field will be white.
        // Otherwise, it will be changed to red. After that, the racetrack is drawn,
        // and then the other cars are drawn after.
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            if (typeText != null && phrasePosition != 0)
            {
                char let = '\u0000';
                try
                {
                    let = typeText.charAt(phrasePosition - 1);
                }
                catch (StringIndexOutOfBoundsException e)
                {
                    if (!printedMessage)
                    {
                        System.out.println("The program tried to check an index out of" +
                            " bounds while changing the car's position. You can continue" +
                            " playing the game, this is just a message for the try-catch" +
                            " block.\n\n");
                        printedMessage = true;
                    }
                }

                if (letter == let
                    && letter != previousLetter
                )
                {
                    previousLetter = letter;
                    playerCarX += (int)((1.0/(typeText.length()))*1028);
                    typingField.setBackground(Color.WHITE);
                }
                else if ((letter != let ||
                    letter != previousLetter) &&
                    !backSpacePressed &&
                    !shiftPressed &&
                    !enterPressed &&
                    !altPressed &&
                    !capsPressed &&
                    !escapePressed &&
                    !insertPressed &&
                    !ctrlPressed &&
                    !commandPressed
                )
                {
                    typingField.setBackground(Color.RED);
                }
            }

            car.setBounds(playerCarX, 0, 105, 80);

            g.setColor(Color.ORANGE);
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 34; j++)
                    g.fillRect(30*j+10, 70*i+75, 20, 5);
            }
            
            totalCarXTop += carXTop;
            g.drawImage(topCar, totalCarXTop, 120, this);

            totalCarXBottom += carXBottom;
            g.drawImage(bottomCar, totalCarXBottom, 190, this);
        }

        // This method returns an image from the Images/Cars/Racecars directory
        // randomly. It uses the getMyImage() method contained in the holder class.
        public Image generateRandomCar(int index)
        {
            Image car = null;
            if (index == 1)
                car = sprintHolder.getMyImage("Images/Cars/Racecars/carDrawing.png");
            else if (index == 2)
                car = sprintHolder.getMyImage("Images/Cars/Racecars/corvette.png");
            else if (index == 3)
                car = sprintHolder.getMyImage("Images/Cars/Racecars/formula1.png");
            else if (index == 4)
                car = sprintHolder.getMyImage("Images/Cars/Racecars/porsche.png");

            return car;
        }
    }
    
    class MainTimer extends JPanel implements ActionListener
    {
        private Timer countDowntimer; // The timer
        private JButton starter; // JButtons to start and reset the game
        private Font font; // The font of the timer text
        private int time; // The time
        private int tenthSec; // The tenth second
        private int elapsedMinutes; // The number of minutes that has gone by
        private double secondsDecimal, secondsDisplay; // The amount of seconds
        private boolean running; // Determines whether the timer is running or not
        
        public MainTimer()
        {        
            initialValues();
            countDowntimer = new Timer(10, this);
            
            font = new Font("Dialog", Font.PLAIN, 25);
        
            starter = new JButton("Start Race");
            starter.addActionListener(this);
            this.add(starter);
        }

        // This resets the timer and the components within the game panel. The
        // \u0000 sets the value of the character to nothing. This is needed because
        // if it is not used, then the program will think that a wrong character
        // was typed in the text field.
        public void initialValues()
        {
            time = 60;
            tenthSec = elapsedMinutes = 0;
            secondsDecimal = 0.0;
            running = false;
            carXTop = 10;
            carXBottom = 10;
            totalCarXTop = 0;
            totalCarXBottom = 0;
            playerCarX = 0;
            letter = '\u0000';
            code = 0;
            previousLetter = '\u0000';
            phrasePosition = 0;

            if (typingField != null)
                typingField.setText("");
        }

        // When this is called, the timer will update its values as well as the
        // text that is drawn on the 
        public void paintComponent(Graphics g)   
        {
            super.paintComponent(g);
            g.setFont(font);
            
            secondsDecimal = time - tenthSec / 100.0;
            secondsDisplay = secondsDecimal % 60;
            elapsedMinutes = (int)secondsDecimal / 60;
            
            secondsToDisplay = String.format("%.0f", secondsDisplay);
            
            if (secondsToDisplay.length() < 2)
                secondsToDisplay = "0" + String.format("%.0f", secondsDisplay);
                        
            g.drawString(elapsedMinutes + ":" + secondsToDisplay, 143, 60);
            g.setColor(Color.BLUE);
        }

        // Whenever the timer calls this method every 10 ms, it first gets the
        // String of the component that called it. If the String is "Start Race" or
        // "Reset", then a JButton called this method. Otherwise, if the String
        // is null, then it skips the if-statement and checks whether the timer
        // is equal to 0:00. If it is, then the timer stops. If the timer is still
        // running, then it continues counting down. After that, the computer cars
        // move a set distance, which is randomly generated. Additionally, the
        // phrase that is typed is received from the text field, and then the
        // length of the phrase is checked. If that length is equal to the length
        // of the text from the texts.txt file, then the program moves onto the
        // next text. This happens until 5 texts are typed, and at that point, the
        // program switches to the quiz panel.
        public void actionPerformed(ActionEvent evt)
        {
            String command = evt.getActionCommand();
            if (command != null)
            {
                if (command.equals("Start Race"))
                {
                    starter.setText("Reset");
                    phrasePosition = 0;

                    if (typingField != null)
                        typingField.setText("");

                    typingField.setEditable(true);
                    typingField.setBackground(Color.WHITE);
                    running = true;
                    countDowntimer.start();
                    typingField.requestFocusInWindow();
                }
                else if (command.equals("Reset") )
                {
                    typingField.setEditable(false);
                    starter.setText("Start Race");
                    
                    initialValues();
                    typingField.setBackground(Color.WHITE);
                    countDowntimer.stop();
                    running = false;
                }
            }

            checkPositions();

            if (secondsDisplay == 0 && elapsedMinutes == 0)
            {
                countDowntimer.stop();
                running = false;
            }

            if (running)
                tenthSec++;
            
            int speed = gameData.getCarSpeed();
            
            carXTop = (int)(Math.random()*2 + speed/14.0);
            carXBottom = (int)(Math.random()*2 + speed/14.0);
            phrase = typingField.getText();

            String correctlyTyped = "";
            try
            {
                correctlyTyped = typeText.substring(0, phrasePosition);
            }
            catch (Exception e)
            {
                if (!printedMessage1)
                {
                    System.err.println("\n\nThe program tried to check an index out of" +
                        " bounds while checking the text. You can continue playing the" +
                        " game; this is just a message for the try-catch block.\n\n");
                    printedMessage1 = true;
                }
            }

            if (!phrase.equals(correctlyTyped))
                typingField.setBackground(Color.RED);
            else
                typingField.setBackground(Color.WHITE);

            if (phrase.length() == typeText.length() && phrase.equals(correctlyTyped))
            {
                int[] amtCharsLocal = sprintHolder.getAmtCharsArray();
                int amtWordsText = amtCharsLocal[counter]/5;
                double minTyped = 1 - (Double.parseDouble(secondsToDisplay) / 60);

                if (racePositions[1] == null && racePositions[2] == null &&
                    racePositions[0] != null
                )
                {
                    if (racePositions[0].equals("you"))
                    {
                        if (totalCarXBottom > totalCarXTop)
                        {
                            racePositions[1] = "the bottom car";
                            racePositions[2] = "the middle car";
                        }
                        else
                        {
                            racePositions[1] = "the middle car";
                            racePositions[2] = "the bottom car";
                        }
                    }
                }
                else if (racePositions[1] != null && racePositions[2] == null && racePositions[0] != null)
                {
                    if ((racePositions[0].equals("you") &&
                        racePositions[1].equals("the middle car")
                        ) ||
                        (racePositions[0].equals("the middle car") &&
                        racePositions[1].equals("you")
                        )
                    )
                        racePositions[2] = "the bottom car";

                    if ((racePositions[0].equals("the bottom car") &&
                        racePositions[1].equals("the middle car")
                        ) ||
                        (racePositions[0].equals("the middle car") &&
                        racePositions[1].equals("the bottom car")
                        )
                    )
                        racePositions[2] = "you";

                    if ((racePositions[0].equals("you") &&
                        racePositions[1].equals("the bottom car")
                        ) ||
                        (racePositions[0].equals("the bottom car") &&
                        racePositions[1].equals("you")
                        )
                    )
                        racePositions[2] = "the middle car";
                }

                wpms[counter] = (int)(amtWordsText/minTyped);
                wpm.setText("WPM for last race: " + wpms[counter]);

                starter.setText("Start Race");
                counter++;
                typingField.setEditable(false);
                typingField.setBackground(Color.WHITE);
                initialValues();
                countDowntimer.stop();
                running = false;
                if (counter != 5)
                    typeText = gameData.getTypingTextArray(counter);
                textToType.setText(typeText);
                amtTextsTyped.setText("You have to type " + (5 - counter) +
                    " more text(s)!");

                if (counter == 5)
                {
                    cards.show(sprintHolder, "QuizBegin");
                }
            }
            
            this.repaint();
            raceTrackPanel.repaint();
        }
    }

    // This method checks the positions of the racecars and stores them in an
    // array.
    public void checkPositions()
    {
        if (racePositions[0] == null && racePositions[1] == null &&
            racePositions[2] == null
        )
        {
            if (playerCarX > totalCarXTop && playerCarX > totalCarXBottom &&
                playerCarX > 700
            )
                racePositions[0] = "you";

            if (totalCarXTop > playerCarX && totalCarXTop > totalCarXBottom &&
                totalCarXTop > 700
            )
                racePositions[0] = "the middle car";

            if (totalCarXBottom > playerCarX && totalCarXBottom > totalCarXTop &&
                totalCarXBottom > 700
            )
                racePositions[0] = "the bottom car";
        }

        if (racePositions[0] != null && racePositions[1] == null &&
            racePositions[2] == null
        )
        {
            if ((playerCarX > totalCarXTop || playerCarX > totalCarXBottom) &&
                playerCarX > 700 && !racePositions[0].equals("you")
            )
                racePositions[1] = "you";

            if ((totalCarXTop > playerCarX || totalCarXTop > totalCarXBottom) &&
                totalCarXTop > 700 && !racePositions[0].equals("the middle car")
            )
                racePositions[1] = "the middle car";

            if ((totalCarXBottom > playerCarX || totalCarXBottom > totalCarXTop) &&
                totalCarXBottom > 700 && !racePositions[0].equals("the bottom car")
            )
                racePositions[1] = "the bottom car";
        }

        if (racePositions[0] != null && racePositions[1] != null &&
            racePositions[2] == null
        )
        {
            if ((racePositions[0].equals("you") &&
                racePositions[1].equals("the middle car")
                ) ||
                (racePositions[0].equals("the middle car") &&
                racePositions[1].equals("you")
                )
            )
                racePositions[2] = "the bottom car";

            if ((racePositions[0].equals("the bottom car") &&
                racePositions[1].equals("the middle car")
                ) ||
                (racePositions[0].equals("the middle car") &&
                racePositions[1].equals("the bottom car")
                )
            )
                racePositions[2] = "you";

            if ((racePositions[0].equals("you") &&
                racePositions[1].equals("the bottom car")
                ) ||
                (racePositions[0].equals("the bottom car") &&
                racePositions[1].equals("you")
                )
            )
                racePositions[2] = "the middle car";
        }
    }

    // Creates the text field that is typed in. The setTransferHandler() method
    // prevents copy/pasting.
    public JTextField makeTextField()
    {
        JTextField tf = new JTextField(50);
        tf.setTransferHandler(null);
        tf.addKeyListener(this);

        return tf;
    }

    // Here, the home button that goes in the south of this panel is created.
    // The method returns a JPanel containing the JButton.
    public JPanel makeHomeButton()
    {
        JButton homeButton = new JButton("Home");
        HomeButtonListenerGame homeButtonListener = new HomeButtonListenerGame();
        homeButton.addActionListener(homeButtonListener);

        JPanel buttonP = new JPanel();
        buttonP.setLayout( new FlowLayout(FlowLayout.CENTER) );
        buttonP.setBackground(darkGreen);
        buttonP.add(homeButton);

        return buttonP;
    }

    // Returns the typing field so that other classes can set its background to
    // white whenever the panel is clicked on.
    public JTextField getTF()
    {
        return typingField;
    }

    // Returns the int[] array containing the user's WPM calculations for each
    // of their races
    public int[] getWPMs()
    {
        return wpms;
    }

    // Returns the String[] array containing the positions for the first race
    public String[] getRacePositions()
    {
        return racePositions;
    }
    
    class HomeButtonListenerGame implements ActionListener
    {
        // When the home button is clicked, the program returns to the start screen.
        public void actionPerformed(ActionEvent evt)
        {
            cards.show(sprintHolder, "Intro");
            timerPanel.initialValues();
            timerPanel.countDowntimer.stop();
            timerPanel.running = false;
        }
    }
}
