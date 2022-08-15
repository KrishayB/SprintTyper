// Krishay
// 5/16/22
// Highscores.java
// This panel is where the high scores for the game show up. It simply contains a
// JTextArea and 3 buttons: one for playing the game, one for exiting, and one for
// going back to the home screen.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;

class HighScores extends JPanel
{
    private QuizGameData quizData; // An instance containing the data for the quiz
    private CardLayout cards; // The main CardLayout
    private SprintTyperHolder sprintHolder; // An instance of the holder class
    private JTextArea highScoresArea; // A JTextArea to write the high scores in
    private Color darkBlue; // The dark blue color used throughout this class
    private Color blue; // The blue color used throughout this class
    private Color lightBlue; // The light blue color used throughout this class
    
    public HighScores(QuizGameData quizDataIn, CardLayout cardsIn, SprintTyperHolder sprintHolderIn)
    {
        quizData = quizDataIn;
        cards = cardsIn;
        sprintHolder = sprintHolderIn;
        darkBlue = new Color(4, 57, 108);
        blue = new Color(3, 91, 150);
        lightBlue = new Color(179, 205, 224);
        
        setLayout(new BorderLayout());

        JPanel centerBorder = new JPanel();
        centerBorder.setLayout(new BorderLayout());
        setBackground(blue);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(1, 2));
        centerPanel.setBackground(blue);
        centerBorder.add(centerPanel, BorderLayout.CENTER);
        
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        rightSidePanel.setBackground(blue);
        centerPanel.add(rightSidePanel, BorderLayout.CENTER);
        
        highScoresArea = new JTextArea("" + quizData.getHighScores(), 13, 20);
        highScoresArea.setLineWrap(true);
        highScoresArea.setWrapStyleWord(true);
        highScoresArea.setEditable(false);
        highScoresArea.setBackground(blue);
        highScoresArea.setForeground(lightBlue);
        highScoresArea.setFont(new Font("Dialog", Font.BOLD, 30));
        JScrollPane scroller = new JScrollPane(highScoresArea);
        scroller.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        scroller.setBackground(blue);
        rightSidePanel.add(scroller);

        add(centerBorder, BorderLayout.CENTER);

        JPanel homePanel = makeHomeButton();
        add(homePanel, BorderLayout.SOUTH);
    }

    // Here, the home button that goes in the south of this panel is created.
    // The method returns a JPanel containing the JButton.
    public JPanel makeHomeButton()
    {
        JButton homeButton = new JButton("Home");
        HomeButtonListenerHighScores homeButtonListener = new
            HomeButtonListenerHighScores(sprintHolder, cards);
        homeButton.addActionListener(homeButtonListener);

        JPanel buttonP = new JPanel();
        buttonP.setBackground(darkBlue);
        buttonP.setLayout( new FlowLayout(FlowLayout.CENTER) );
        buttonP.add(homeButton);

        return buttonP;
    }

    class HomeButtonListenerHighScores implements ActionListener
    {
        SprintTyperHolder sprintHolder;
        CardLayout cards;

        public HomeButtonListenerHighScores(SprintTyperHolder sprintHolderIn, CardLayout cardsIn)
        {
            sprintHolder = sprintHolderIn;
            cards = cardsIn;
        }

        // When the home button is pressed, this takes it back the start screen.
        public void actionPerformed(ActionEvent evt)
        {
            cards.show(sprintHolder, "Intro");
        }
    }
    
    // Every time that this is called, the program refreshes the high scores, and the
    // setCaretPosition() method sets the position of where the text will be inserted.
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        highScoresArea.setText("" + quizData.getHighScores());
        highScoresArea.setCaretPosition(0);
    }
}