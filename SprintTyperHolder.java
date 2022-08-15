// Krishay
// 5/16/22
// SprintTyperHolder.java
// This file contains the holder for all the panels. It has the home page, the
// game panel, the instructions panel, and the settings panel. All the instances of
// these classes are contained within a CardLayout.

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;

// This class holds the first page panel as well as the other panels for the game
// within a CardLayout.
class SprintTyperHolder extends JPanel
{
    private int amtLines; // The amount of lines in the texts.txt file
    private int[] amtChars; // The amount of words for each text

    public SprintTyperHolder()
    {
        amtLines = 29;
        amtChars = new int[amtLines];
        CardLayout cards = new CardLayout();
        setLayout(cards);

        GameData gameData = new GameData(this, amtLines);
        QuizGameData quizData = new QuizGameData();
        quizData.grabQuestionFromFile();
        Car car = new Car(gameData, 1);
        
        Game game = new Game(this, cards, gameData);
        JTextField typingTF = game.getTF();
        SprintTyperIntro introPage = new SprintTyperIntro(this, cards, typingTF);

        StartPanel quizbegin = new StartPanel(quizData, cards, this);
        QuestionsPanel questions = new QuestionsPanel(quizData, cards, this);
        HighScoresQuizPanel highscores = new HighScoresQuizPanel(quizData, game,
            cards, this);

        HighScores highScoresMain = new HighScores(quizData, cards, this);

        Instructions instructions = new Instructions(this, cards);
        Settings settings = new Settings(this, cards, gameData, car);
        
        add(introPage, "Intro");
        add(game, "Game");
        add(quizbegin, "QuizBegin");
        add(questions, "Questions");
        add(highscores, "HighScores");
        add(highScoresMain, "HighScoresMain");
        add(instructions, "Instructions");
        add(settings, "Settings");
    }

    // Gets and returns any image within this directory given a String parameter.
    // If the image was not found, then null is returned.
    public Image getMyImage(String picturePath)
    {
        File imageFile = new File(picturePath);
        Image picture;
        
        try
        {
            picture = ImageIO.read(imageFile);
            return picture;
        }
        catch (IOException e)
        {
            System.err.println("\n\n " + picturePath + " can't be found.\n\n");
            e.printStackTrace();
            return null;
        }
    }

    // This method gets and returns a String that contains a line from a file to
    // read. The file that the line is read from as well as the line number that
    // is read can be customized.
    public String getTypingText(String fileToRead, int lineToRead, int iIn)
    {
        String textToType = "";
        File textsFile = new File(fileToRead);
        Scanner input = null;
        lineToRead--;

        try
        {
            input = new Scanner(textsFile);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("\n\nCannot find file " + fileToRead + ".\n\n");
            System.exit(1);
        }

        for (int i = 0; i < lineToRead; i++)
            input.nextLine();

        textToType = input.nextLine();
        amtChars[iIn] = Integer.parseInt(textToType.substring(
                textToType.indexOf("00"), textToType.length()
        ));
        textToType = textToType.substring(0,
            textToType.indexOf("00") - 1);

        return textToType;
    }

    // Creates a standard center-aligned JLabel with certain parameters. text is
    // the content of the JLabel and font is the font.
    public JLabel makeLabel(String text, Font font)
    {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setHorizontalAlignment(JLabel.CENTER);

        return label;
    }

    // Returns the array containing the amount of words for each text
    public int[] getAmtCharsArray()
    {
        return amtChars;
    }
}
