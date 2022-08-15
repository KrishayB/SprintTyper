// Krishay
// 5/16/22
// Quiz.java
// This group of panels is where the quiz runs. After the user finishes typing five
// texts, they will be redirected to this panel. That is the only way this panel can
// be accessed.

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;

class HomeButtonListenerQuiz implements ActionListener
{
    private SprintTyperHolder sprintHolder;
    private CardLayout cards;
    private QuizGameData quizData;

    public HomeButtonListenerQuiz(SprintTyperHolder sprintHolderIn, CardLayout cardsIn, QuizGameData quizDataIn)
    {
        sprintHolder = sprintHolderIn;
        cards = cardsIn;
        quizData = quizDataIn;
    }

    // When the home button is pressed, this takes it back the start screen.
    public void actionPerformed(ActionEvent evt)
    {
        quizData.saveScores();
        cards.show(sprintHolder, "Intro");
    }
}

class StartPanel extends JPanel implements ActionListener, KeyListener
{
    private QuizGameData quizData; // An instance containing the data for the quiz
    private CardLayout cards; // The main CardLayout
    private SprintTyperHolder sprintHolder; // An instance of the holder class
    private JTextField userNameTF; // A JTextArea for the user to type their name in
    private Color darkBlue; // The dark blue color used throughout this class
    private Color blue; // The blue color used throughout this class
    private Color lightBlue; // The light blue color used throughout this class
    private JButton next; // A JButton to go to the next panel
    
    public StartPanel(QuizGameData quizDataIn, CardLayout cardsIn, SprintTyperHolder sprintHolderIn)
    {
        quizData = quizDataIn;
        cards = cardsIn;
        sprintHolder = sprintHolderIn;
        darkBlue = new Color(4, 57, 108);
        blue = new Color(3, 91, 150);
        lightBlue = new Color(179, 205, 224);
        
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        JPanel centerGrid = new JPanel();
        centerGrid.setBackground(blue);
        centerGrid.setLayout(new GridLayout(2, 1));

        JPanel quizIntro = new JPanel();
        quizIntro.setBackground(blue);
        quizIntro.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel quizTitle = sprintHolder.makeLabel("Welcome to the quiz!", new Font("Serif", Font.BOLD, 40));
        quizTitle.setForeground(lightBlue);
        JTextArea quizInfo = new JTextArea("Quiz Information: \n\nAnswer a series" +
            " of 4 questions, and then, find out information about the races that you" +
            " typed just a few moments ago. You wll also see the highscores. \n\n" +
            "To start off, enter a username that you want to be identified by in the high" +
            " score list below. Click next once you're done.", 3, 40);
        quizInfo.setFont(new Font("Dialog", Font.PLAIN, 20));
        quizInfo.setBackground(blue);
        quizInfo.setEditable(false);
        quizInfo.setLineWrap(true);
        quizInfo.setWrapStyleWord(true);
        quizInfo.setForeground(lightBlue);

        quizIntro.add(quizTitle);
        quizIntro.add(quizInfo);
        centerGrid.add(quizIntro);
        
        JPanel bottomHalf = new JPanel();
        bottomHalf.setLayout(new BorderLayout());
        JPanel usernamePanel = new JPanel();
        usernamePanel.setBackground(blue);
        usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel usernameLabel = sprintHolder.makeLabel("Username: ",
            new Font("Serif", Font.BOLD, 30));
        usernameLabel.setForeground(lightBlue);
        usernamePanel.add(usernameLabel);
        userNameTF = new JTextField(16);
        userNameTF.addKeyListener(this);
        usernamePanel.add(userNameTF);
        bottomHalf.add(usernamePanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(blue);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        next = new JButton("Next");
        next.setEnabled(false);
        next.setPreferredSize(new Dimension(150, 30));
        next.addActionListener(this);
        buttonPanel.add(next);
        bottomHalf.add(buttonPanel, BorderLayout.CENTER);

        centerGrid.add(bottomHalf);

        add(centerGrid, BorderLayout.CENTER);

        JPanel homePanel = makeHomeButton();
        add(homePanel, BorderLayout.SOUTH);
    }

    // Additional key methods
    public void keyPressed(KeyEvent evt) {}
    public void keyTyped(KeyEvent evt) {}

    // This method limits the text field's characters. Whenever a key is released,
    // This method gets the amount of characters within the username text field
    // and determines whether to enable the "Next" JButton.
    public void keyReleased(KeyEvent evt)
    {
        int textLength = userNameTF.getText().length();

        if (textLength > 0 && textLength <= 20)
            next.setEnabled(true);
        else
            next.setEnabled(false);
    }

    // Here, the home button that goes in the south of this panel is created.
    // The method returns a JPanel containing the JButton.
    public JPanel makeHomeButton()
    {
        JButton homeButton = new JButton("Home");
        HomeButtonListenerQuiz homeButtonListener = new
            HomeButtonListenerQuiz(sprintHolder, cards, quizData);
        homeButton.addActionListener(homeButtonListener);

        JPanel buttonP = new JPanel();
        buttonP.setBackground(darkBlue);
        buttonP.setLayout( new FlowLayout(FlowLayout.CENTER) );
        buttonP.add(homeButton);

        return buttonP;
    }
    
    // Whenever the JButton calls this method, the program
    // stores the username within the quizData instance and switches the panel.
    public void actionPerformed(ActionEvent evt) 
    {
        String command = evt.getActionCommand();
        
        if (command.equals("Next"))
        {
            quizData.setName(userNameTF.getText());
            cards.next(sprintHolder);
        }
    }
}

class QuestionsPanel extends JPanel implements ActionListener
{
    private QuizGameData quizData; // An instance containing the data for the quiz
    private CardLayout cards; // The main CardLayout
    private SprintTyperHolder sprintHolder; // An instance of the holder class
    private ButtonGroup group; // A ButtonGroup for the answers
    private JLabel questionArea; // A JLabel for the 
    private JRadioButton[] answer; // An array that stores the answer JRadioButtons
    private JButton submit, nextQuestion, nextPanel; // Different JButtons to
                                                     // interact with the quiz
    private Color darkBlue; // The dark blue color used throughout this class
    private Color blue; // The blue color used throughout this class
    private Color lightBlue; // The light blue color used throughout this class
    private JFrame exFrame; // JFrame for the explanation
    
    public QuestionsPanel(QuizGameData quizDataIn, CardLayout cardsIn, SprintTyperHolder sprintHolderIn)
    {
        quizData = quizDataIn;
        cards = cardsIn;
        sprintHolder = sprintHolderIn;
        darkBlue = new Color(4, 57, 108);
        blue = new Color(3, 91, 150);
        lightBlue = new Color(179, 205, 224);
        
        setBackground(blue);
        setLayout(new BorderLayout());

        JPanel centerBorder = new JPanel();
        centerBorder.setLayout(new BorderLayout());
        
        answer = new JRadioButton[4];
        
        JPanel question = new JPanel();
        question.setBackground(blue);
        question.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        questionArea = sprintHolder.makeLabel(quizData.getQuestion(),
            new Font("Serif", Font.BOLD, 40));
        new JTextArea(quizData.getQuestion(), 3, 30);
        questionArea.setOpaque(false);
        questionArea.setForeground(lightBlue);
        question.add(questionArea);
        centerBorder.add(question, BorderLayout.NORTH);
        
        JPanel answers = new JPanel();
        answers.setBackground(blue);
        answers.setLayout(new GridLayout(4, 1, 20, 20));
        centerBorder.add(answers, BorderLayout.CENTER);

        group = new ButtonGroup();

        for (int i = 0; i < answer.length; i++)
        {
            answer[i] = new JRadioButton("" + (char)(65 + i) + ". " +
                quizData.getAnswer(i));
            group.add(answer[i]);
            answer[i].setFont(new Font("Serif", Font.BOLD, 30));
            answer[i].setOpaque(true);
            answer[i].setBackground(lightBlue);
            answer[i].addActionListener(this);
            answers.add(answer[i]);
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(blue);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 30));
        centerBorder.add(buttonPanel, BorderLayout.SOUTH);
        
        submit = new JButton("Submit");
        submit.addActionListener(this);
        submit.setEnabled(false);
        buttonPanel.add(submit);

        nextQuestion = new JButton("Next Question");
        nextQuestion.addActionListener(this);
        nextQuestion.setEnabled(false);
        buttonPanel.add(nextQuestion);

        nextPanel = new JButton("Next");
        nextPanel.addActionListener(this);
        nextPanel.setEnabled(false);
        buttonPanel.add(nextPanel);

        add(centerBorder, BorderLayout.CENTER);

        JPanel homePanel = makeHomeButton();
        add(homePanel, BorderLayout.SOUTH);
    }

    // Here, the home button that goes in the south of this panel is created.
    // The method returns a JPanel containing the JButton.
    public JPanel makeHomeButton()
    {
        JButton homeButton = new JButton("Home");
        HomeButtonListenerQuiz homeButtonListener = new
            HomeButtonListenerQuiz(sprintHolder, cards, quizData);
        homeButton.addActionListener(homeButtonListener);

        JPanel buttonP = new JPanel();
        buttonP.setBackground(darkBlue);
        buttonP.setLayout( new FlowLayout(FlowLayout.CENTER) );
        buttonP.add(homeButton);

        return buttonP;
    }
    
    // Whenever one of the buttons on the panel is pressed, this method will be called.
    // First, if the button group has JRadioButtons, then the submit button is enabled.
    // Second, if the submit button is clicked, then the correct answer is set to green,
    // and a for-loop is written to check the other JRadioButtons. If the correct
    // answer was not selected, then it highlights the user's answer in red. Otherwise,
    // we know that the user answered correctly and so we increment their score by 1.
    // Then the question gets reset. The other two if statements check whether the
    // Next Question button was selected or the Next button was selected. If the Next
    // Question button was selected, then the program switches to the next question, but
    // if the Next button was selected, then the program switches the panel.
    public void actionPerformed(ActionEvent evt) 
    {
        String command = evt.getActionCommand();
        
        if (group.getSelection() != null)
        {
            submit.setEnabled(true);
        }
        
        if (command.equals("Submit"))
        {
            answer[quizData.getCorrectAnswer()].setBackground(new Color(66, 168, 64));
            for (int i = 0; i < answer.length; i++)
            {
                if (answer[i].isSelected())
                {
                    if (i != quizData.getCorrectAnswer())
                    {
                        answer[i].setBackground(new Color(227, 73, 73));
                        createExplanationFrame();
                    }
                    else
                    {
                        quizData.incrementUserScore();
                    }
                }
            }

            group.clearSelection();

            for (int i = 0; i < answer.length; i++)
                answer[i].setEnabled(false);

            submit.setEnabled(false);

            if (quizData.getQuestionCount() == 4)
                nextPanel.setEnabled(true);
            else
                nextQuestion.setEnabled(true);
        }
        else if (command.equals("Next Question"))
        {
            resetQuestion();
            nextQuestion.setEnabled(false);
        }
        else if (command.equals("Next"))
        {
            quizData.resetAll();
            resetQuestion();
            nextPanel.setEnabled(false);
            cards.next(sprintHolder);
        }
    }

    // This method simply creates the small explanation JFrame that pops up when
    // a user answers a question incorrectly. It contains a JScrollPane.
    public void createExplanationFrame()
    {
        exFrame = new JFrame("Explanation");
        exFrame.setSize(400, 200);
        exFrame.setLocation(800, 70);
        exFrame.setResizable(false);
        JPanel explanationPan = new JPanel();
        explanationPan.setBackground(darkBlue);
        JTextArea explan = new JTextArea(quizData.getExplanation() + "\n");
        explan.setEditable(false);
        explan.setBackground(darkBlue);
        explan.setFont(new Font("Sans Serif", Font.BOLD, 20));
        explan.setForeground(lightBlue);
        explan.setLineWrap(true);
        explan.setWrapStyleWord(true);

        JScrollPane explanS = new JScrollPane(explan);
        explanS.setPreferredSize(new Dimension(300, 180));
        explanS.setBackground(blue);
        explanS.setBorder(BorderFactory.createEmptyBorder());

        explanationPan.add(explanS);
        exFrame.getContentPane().add(explanationPan);
        exFrame.setVisible(true);
    }
    
    // This method is called whenever the user has finished answering one of the
    // questions and wants to move onto the next question. The JRadioButtons as well
    // as the question JTextArea is reset.
    public void resetQuestion()
    {
        quizData.grabQuestionFromFile();
        questionArea.setText(quizData.getQuestion());
        answer[0].setText("A. " + quizData.getAnswer(0));
        answer[1].setText("B. " + quizData.getAnswer(1));
        answer[2].setText("C. " + quizData.getAnswer(2));
        answer[3].setText("D. " + quizData.getAnswer(3));
        for(int i = 0; i < answer.length; i++)
        {
            answer[i].setEnabled(true);
            answer[i].setBackground(new Color(230, 230, 230));
        }
    }
}

class HighScoresQuizPanel extends JPanel implements ActionListener
{
    private QuizGameData quizData; // An instance containing the data for the quiz
    private CardLayout cards; // The main CardLayout
    private Game game; // The Game instance
    private SprintTyperHolder sprintHolder; // An instance of the holder class
    private JTextArea scoreInfo; // A JTextArea to tell the user their score's info
    private JTextArea highScoresArea; // A JTextArea to show the high scores
    private Color darkBlue; // The dark blue color used throughout this class
    private Color blue; // The blue color used throughout this class
    private Color lightBlue; // The light blue color used throughout this class
    
    public HighScoresQuizPanel(QuizGameData quizDataIn, Game gameIn, CardLayout cardsIn, SprintTyperHolder sprintHolderIn)
    {
        quizData = quizDataIn;
        game = gameIn;
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
        
        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setLayout(new BorderLayout());
        leftSidePanel.setBackground(blue);
        centerPanel.add(leftSidePanel, BorderLayout.CENTER);
        
        scoreInfo = new JTextArea("" + quizData.getCorrectCount(), 10, 20);
        scoreInfo.setLineWrap(true);
        scoreInfo.setWrapStyleWord(true);
        scoreInfo.setEditable(false);
        scoreInfo.setBackground(blue);
        scoreInfo.setForeground(lightBlue);
        scoreInfo.setFont(new Font("Serif", Font.BOLD, 20));
        scoreInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftSidePanel.add(scoreInfo);
        
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BorderLayout());
        rightSidePanel.setBackground(blue);
        centerPanel.add(rightSidePanel, BorderLayout.CENTER);
        
        highScoresArea = new JTextArea("" + quizData.getHighScores(), 10, 20);
        highScoresArea.setLineWrap(true);
        highScoresArea.setWrapStyleWord(true);
        highScoresArea.setEditable(false);
        highScoresArea.setBackground(blue);
        highScoresArea.setForeground(lightBlue);
        highScoresArea.setFont(new Font("Dialog", Font.BOLD, 30));
        JScrollPane scroller = new JScrollPane(highScoresArea);
        scroller.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scroller.setBackground(blue);
        rightSidePanel.add(scroller);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(blue);

        JButton playAgain = new JButton("Play Again");
        playAgain.addActionListener(this);
        buttonPanel.add(playAgain);
        
        JButton exit = new JButton("Close Game");
        exit.addActionListener(this);
        buttonPanel.add(exit);
        centerBorder.add(buttonPanel, BorderLayout.SOUTH);

        add(centerBorder, BorderLayout.CENTER);

        JPanel homePanel = makeHomeButton();
        add(homePanel, BorderLayout.SOUTH);
    }

    // Here, the home button that goes in the south of this panel is created.
    // The method returns a JPanel containing the JButton.
    public JPanel makeHomeButton()
    {
        JButton homeButton = new JButton("Home");
        HomeButtonListenerQuiz homeButtonListener = new
            HomeButtonListenerQuiz(sprintHolder, cards, quizData);
        homeButton.addActionListener(homeButtonListener);

        JPanel buttonP = new JPanel();
        buttonP.setBackground(darkBlue);
        buttonP.setLayout( new FlowLayout(FlowLayout.CENTER) );
        buttonP.add(homeButton);

        return buttonP;
    }
    
    // Whenever this method is called, the JTextAreas update. The setCaretPosition()
    // method sets the position of where the text will be inserted.
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int[] wpmsLocal = game.getWPMs();
        int sum = 0;

        for (int i = 0; i < 5; i ++)
            sum += wpmsLocal[i];
        
        double average = sum/5.0;
        quizData.setWPM((int)average);

        String[] positions = game.getRacePositions();

        scoreInfo.setText(quizData.toString() +
            " Your average WPM for the 5 races was " + average + ".\n\n" +
            "In your first race, " +
            positions[0] + " got first place, " + positions[1] + " got " +
            "second place, and " + positions[2] + " got third place.");
        highScoresArea.setText("" + quizData.getHighScores());
        highScoresArea.setCaretPosition(0);
    }
    
    // This method is called whenever one of the buttons is pressed. If the user
    // wants to play again, then they can hit the Play Again JButton, and the program
    // will take them to the game panel after saving the high scores. If the user
    // wants to close the game, then they can hit the Close Game button, and the program
    // will quit running.
    public void actionPerformed(ActionEvent evt) 
    {
        String command = evt.getActionCommand();
        
        if (command.equals("Play Again"))
        {
            quizData.saveScores();
            cards.show(sprintHolder, "Game");
        }
        else if (command.equals("Close Game"))
        {
            quizData.saveScores();
            System.exit(0);
        }
    }
}