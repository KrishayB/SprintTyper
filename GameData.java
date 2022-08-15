// Krishay
// 5/16/22
// GameData.java
// This class contains the game's data, and it allows for other clases to read
// and write information to each other.

public class GameData
{
    private boolean hasSpoiler; // Determines if the car has a spoiler
    private boolean hasExhaust; // Determines if the car has an exhaust
    private boolean hasMirrors; // Determines if the car has mirrors
    private boolean hasRoof; // Determines if the car has a roof
    private String carColor; // Determines the car color
    private int carSpeed; // Determines if the computer cars' speeds

    private String[] texts;  // A String[] containing the texts to type

    private SprintTyperHolder sprintHolder; // An instance of the holder class
                                             // needed for accessing a method
    
    private int amountLines; // The amount of lines from the holder class

    public GameData(SprintTyperHolder sprintHolderIn, int amtLinesIn)
    {
        amountLines = amtLinesIn;
        sprintHolder = sprintHolderIn;
        hasSpoiler = true;
        hasExhaust = true;
        hasMirrors = true;
        hasRoof = true;
        carColor = "Red";
        carSpeed = 1;
        texts = new String[amountLines];
        fillTextsArray();
    }

    // This method fills the texts array using a for-loop and a random number that
    // acts as a random index to be generated.
    public void fillTextsArray()
    {
        int lineNumToRead = 0;
        boolean[] selectedQuestions = new boolean[amountLines];

        for (int i = 0; i < amountLines; i++)
        {
            lineNumToRead = (int)(Math.random() * amountLines) + 1;

            while (selectedQuestions[lineNumToRead - 1] == true)
                lineNumToRead = (int)(Math.random() * amountLines) + 1;

            if (selectedQuestions[lineNumToRead - 1] == false)
            {
                texts[i] = sprintHolder.getTypingText("texts.txt", lineNumToRead, i);
                selectedQuestions[lineNumToRead - 1] = true;
            }
        }
    }

    // Here, the car drawing class can request to get the info for a car part to
    // decide whether it needs to be drawn or not.
    public boolean getCarPartInfo(String carPart)
    {
        if (carPart.equalsIgnoreCase("Spoiler"))
            return hasSpoiler;
        else if (carPart.equalsIgnoreCase("Exhaust"))
            return hasExhaust;
        else if (carPart.equalsIgnoreCase("Mirrors"))
            return hasMirrors;
        else if (carPart.equalsIgnoreCase("Roof"))
            return hasRoof;
        else
            return false;
    }

    // The JCheckBoxes within the settings panel can use this method to set the
    // appropriate variable to the appropriate value. These variables determine
    // what is needed to be drawn with a boolean. If it is needed, then the value
    // is true, else, it is false.
    public void setCarPartInfo(String carPart, boolean val)
    {
        if (carPart.equalsIgnoreCase("Spoiler"))
            hasSpoiler = val;
        else if (carPart.equalsIgnoreCase("Exhaust"))
            hasExhaust = val;
        else if (carPart.equalsIgnoreCase("Mirrors"))
            hasMirrors = val;
        else if (carPart.equalsIgnoreCase("Roof"))
            hasRoof = val;
    }

    // Gets the color of the car. This is used in the car drawing class.
    public String getColor()
    {
        return carColor;
    }

    // Sets the color of the car. This is used in the settings panel.
    public void setColor(String color)
    {
        carColor = color;
    }

    // Gets the car speed for the computer cars. This is used in the game
    // panel.
    public int getCarSpeed()
    {
        return carSpeed;
    }

    // Sets the car speed for the computer cars. This is used in the settings
    // panel.
    public void setCarSpeed(int speed)
    {
        carSpeed = speed;
    }

    // Returns a String from the texts[] array at a given index. This is used
    // in the game panel.
    public String getTypingTextArray(int index)
    {
        return texts[index];
    }
}
