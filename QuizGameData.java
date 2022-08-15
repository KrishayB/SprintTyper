// Krishay
// 5/16/22
// QuizGameData.java
// This class is where the data for the quiz is located, and it is also where
// the FileIO occurs during the quiz.

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class QuizGameData
{
    private String username; // A String for the user's name
    private String question; // A String for the question
    private String[] answerSet; // A String[] array containing the answers
    private String explanation; // A String for the explanation for each question
    private int correctAnswer; // The index of the correct answer in the
                               // answerSet[] array
    private boolean[] chosenQuestions; // A boolean[] that determines which questions
                                       // were chosen from the 29 available
    private int questionCount; // The amount of questions that the user answers
    private int correctCount; // An int showing the amount of questions the user
                              // got correct
    private int lastGameCorrectCount; // The amount of questions that the user got
                                      // correct the last time
    private int numQuestions; // The number of total available questions
    private int avgwpm; // The average WPM of the user
    
    public QuizGameData()
    {
        numQuestions = 29;
        username = "";
        correctCount = 0;
        avgwpm = 0;
        resetAll();
    }
    
    // This method resets the values of the field variables.
    public void resetAll()
    {
        lastGameCorrectCount = correctCount;
        answerSet = new String[4];
        question = "";
        correctAnswer = -1;
        chosenQuestions = new boolean[numQuestions];
        questionCount = correctCount = 0;

        for(int i = 0; i < answerSet.length; i++)
            answerSet[i] = "";
    }

    // This is one place where the program is reading using FileIO from the file.
    // First, a Scanner instance is declared and initialized within the try-catch
    // block. If the file cannot be found, then the program informs the programmer
    // of the issue. Then, a random index is chosen for choosing a random question.
    // The while loop makes it so that if the question at the questionNumber index
    // has already been chosen, then the program shouldn't ask the same question
    // again. The program then sets the boolean at that index to be true since we
    // have used the question. questionCount increments so that we can keep track
    // of the amount of questions that we have asked. The next while loop goes to
    // the question in the file, and 6 is used because there are 6 lines for each
    // question in the file. After that, we want to save the question and reset the
    // counter since it willbe resused. After that, we read in the 4 answers using
    // this while-loop, and we find the index of the correct answer using the
    // nextInt() method.
    public void grabQuestionFromFile()
    {
        Scanner inFile = null;
        String fileName = "computerQuestions.txt";
        File inputFile = new File(fileName);

        try
        {
            inFile = new Scanner(inputFile);
        }
        catch (FileNotFoundException err)
        {
            System.err.printf("ERROR: Cannot open %s\n", fileName);
            System.out.println(err);
            System.exit(1);
        }

        int questionNumber = (int)(Math.random()*numQuestions);
        while (chosenQuestions[questionNumber] == true)
            questionNumber = (int)(Math.random()*numQuestions);

        chosenQuestions[questionNumber] = true;
        questionCount++;
        int counter = 0;

        // Because 7 lines for each question
        while (inFile.hasNext() && counter < 7 * questionNumber)
        {
            inFile.nextLine();
            counter++;
        }

        question = inFile.nextLine();
        counter = 0;

        while (inFile.hasNext() && counter < 4)
        {
            answerSet[counter] = inFile.nextLine();
            counter++;
        }

        if (inFile.hasNext())
        {
            correctAnswer = inFile.nextInt();
            inFile.nextLine();
            explanation = inFile.nextLine();
        }
        
        inFile.close();
    }
    
    // This method sets the username field variable.
    public void setName(String name)
    {
        username = name;
    }

    // This method sets the WPM field variable.
    public void setWPM(int wpm)
    {
        avgwpm = wpm;
    }
    
    // This method returns the question as well as the question number.
    public String getQuestion()
    {
        return "" + questionCount + ". " + question;
    }

    public String getExplanation()
    {
        return "" + questionCount + ". " + explanation;
    }
    
    // This method gets the answer for a specific question.
    public String getAnswer(int index)
    {
        return answerSet[index];
    }
    
    // This method gets the correct answer index.
    public int getCorrectAnswer()
    {
        return correctAnswer;
    }
    
    // This method gets the amount of questions that the user answers.
    public int getQuestionCount()
    {
        return questionCount;
    }
    
    // This method gets the amount of questions that the user answered correctly.
    public int getCorrectCount()
    {
        return lastGameCorrectCount;
    }
    
    // This method increments the user's score.
    public void incrementUserScore()
    {
        correctCount++;
    }
    
    // This method returns a String containing the response to the user's
    // gameplay.
    public String toString()
    {
        if (lastGameCorrectCount > 2)
        {
            return "Good job, " + username + "! You got " + lastGameCorrectCount +
                "/4! Your name will be added to the high scores, on the right. " +
                "Nice job!";
        }
        return "Nice try " + username + "! You got " + lastGameCorrectCount +
            "/4. Keep studying, and maybe next time you will be added to the " +
            "high scores, on the right.";
    }
    
    // This method reads the high scores from the highScores.txt file. It stores
    // the entire file into one String variable and returns that variable.
    public String getHighScores()
    {
        String result = "";
        String fileName = "highScores.txt";
        Scanner inFile = null;
        File inputFile = new File(fileName);
        try 
        {
            inFile = new Scanner(inputFile);
        } 
        catch (FileNotFoundException e) 
        {
            System.err.printf("ERROR: Cannot open %s\n", fileName);
            System.out.println(e);
            System.exit(1);
        }
        while(inFile.hasNext()) 
        {
            String line = inFile.nextLine();
            result += line + "\n";
        }
        return result;
    }
    
    // This method writes to the highScores.txt file. It starts out with an
    // if-statement that checks whether the user's score was greater than 3.
    // If the score is not greater than 3, then the program won't save the
    // score. It firsts creates a String that will keep track of what is written
    // to the text file, and it then creates the Scanner instance to read to. It
    // then has a while loop that goes to the next line until it finds the line
    // that it needs to print to. Inside the while loop, we first find the amount
    // of correct answers of the the other users in the file line by line. If the
    // score hasn't been added yet and if that score of the other user is less than
    // the user's score, then we save the user's high score at that point in the file.
    public void saveScores()
    {
        if (lastGameCorrectCount >= 3)
        {
            String result = "";
            boolean scoreAdded = false;
            String fileName = "highScores.txt";
            Scanner inFile = null;
            File inputFile = new File(fileName);
            try 
            {
                inFile = new Scanner(inputFile);
            } 
            catch (FileNotFoundException e) 
            {
                System.err.printf("ERROR: Cannot open %s\n", fileName);
                System.out.println(e);
                System.exit(1);
            }

            while(inFile.hasNext())
            {
                String line = inFile.nextLine();

                while (line.indexOf("/") == -1 && inFile.hasNext())
                {
                    result += line + "\n";

                    if (inFile.hasNext())
                        line = inFile.nextLine();
                }

                int amtCorrect = 0;
                
                if (line.indexOf("/") != -1)
                    amtCorrect = Integer.parseInt(
                        "" + line.charAt(line.indexOf("/") - 1)
                    );

                if (!scoreAdded && amtCorrect <= lastGameCorrectCount)
                {
                    result += username + " - " + lastGameCorrectCount +
                        "/4\nAVG WPM: " + avgwpm + "\n\n";
                    scoreAdded = true;
                }

                result += line + "\n";
            }

            if (!scoreAdded)
            {
                result += username + " - " + lastGameCorrectCount +
                    "/4\nAVG WPM: " + avgwpm + "\n\n";
            }

            inFile.close();

            File ioFile = new File("highScores.txt");
            PrintWriter outFile = null;
            try
            {
                outFile = new PrintWriter(ioFile);
            }
            catch (IOException err)
            {
                err.printStackTrace();
                System.exit(1);
            }

            outFile.print(result);
            outFile.close();
        }
    }
}