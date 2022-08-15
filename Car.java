// Krishay
// 5/16/22
// Car.java
// This panel draws the car based on information from the gameData instance that
// was created in the holder class. This class has the gameData instance to determine
// which car parts to draw based on JCheckBoxes in the settings panel. Additionally,
// all the coordinates are divided by a scale factor, mainly because this car is
// drawn in the game panel as well as the settings panel.

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

class Car extends JPanel
{
    private GameData gameData; // The game data instance created in the holder class
    private int scaleFactor; // A scale factor to scale the car

    public Car(GameData gameDataIn, int scaleFactorIn)
    {
        gameData = gameDataIn;
        scaleFactor = scaleFactorIn;
    }

    // This takes in a String that decides the color that the Graphics object should
    // be set to.
    public void chooseColor(Graphics g, String color)
    {
        Color colorToDecide = new Color(0, 0, 0);

        if (color.equals("BLACK"))
            colorToDecide = Color.BLACK;
        else if (color.equals("DARK GRAY"))
            colorToDecide = new Color(92, 92, 92);
        else if (color.equals("LIGHT GRAY"))
            colorToDecide = new Color(171, 171, 171);
        else if (color.equals("SKY BLUE"))
            colorToDecide = new Color(170, 197, 242);
        else if (color.equals("Red"))
            colorToDecide = new Color(227, 73, 73);
        else if (color.equals("Green"))
            colorToDecide = new Color(66, 168, 64);
        else if (color.equals("Blue"))
            colorToDecide = new Color(73, 138, 191);
        else if (color.equals("Yellow"))
            colorToDecide = new Color(242, 220, 73);
        else if (color.equals("Purple"))
            colorToDecide = new Color(170, 125, 212);

        g.setColor(colorToDecide);
    }

    // Here, all the methods used to draw the car are called.
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        drawRects(g);
        drawArcs(g);
        drawOvals(g);
        drawPolygon(g);
    }

    // Draws the exhaust in the program if the JCheckBox is selected in the settings
    // panel. It also draws the body of the car.
    public void drawRects(Graphics g)
    {
        if (gameData.getCarPartInfo("Exhaust"))
        {
            chooseColor(g, "DARK GRAY");
            g.fillRect(44/scaleFactor, 346/scaleFactor, 50/scaleFactor, 20/scaleFactor);
        }

        chooseColor(g, gameData.getColor());
        g.fillRect(87/scaleFactor, 256/scaleFactor, 270/scaleFactor, 110/scaleFactor);
    }

    // Draws the tires and the rims of the car.
    public void drawOvals(Graphics g)
    {
        chooseColor(g, "BLACK");
        g.fillOval(108/scaleFactor, 336/scaleFactor, 64/scaleFactor, 64/scaleFactor);
        g.fillOval(387/scaleFactor, 336/scaleFactor, 64/scaleFactor, 64/scaleFactor);

        chooseColor(g, "LIGHT GRAY");
        g.fillOval(116/scaleFactor, 344/scaleFactor, 48/scaleFactor, 48/scaleFactor);
        g.fillOval(395/scaleFactor, 344/scaleFactor, 48/scaleFactor, 48/scaleFactor);
    }

    // This draws the front part of the car's body.
    public void drawArcs(Graphics g)
    {
        chooseColor(g, gameData.getColor());
        g.fillArc(232/scaleFactor, 255/scaleFactor, 250/scaleFactor, 221/scaleFactor, 0, 90);
    }

    // The windshield gets drawn first. After that, if the appropriate JCheckBox
    // is selected, the roof is drawn. Then the side window is drawn using 2 triangles.
    // After that, if the JCheckBox is selected, the spoiler is drawn. Lastly, if the
    // user chooses to click the right JCheckBox, the mirrors are drawn.
    public void drawPolygon(Graphics g)
    {
        int[] xArrayTriangle = {303/scaleFactor, 303/scaleFactor, 360/scaleFactor};
		int[] yArrayTriagle = {205/scaleFactor, 257/scaleFactor, 257/scaleFactor};
        chooseColor(g, gameData.getColor());
		g.fillPolygon(xArrayTriangle, yArrayTriagle, 3);

        int[] xArrayRoof = {155/scaleFactor, 303/scaleFactor, 303/scaleFactor};
        int[] yArrayRoof = {257/scaleFactor, 205/scaleFactor, 257/scaleFactor};
        if (gameData.getCarPartInfo("Roof"))
            g.fillPolygon(xArrayRoof, yArrayRoof, 3);

        int[] xArraySideTriangle = {303/scaleFactor, 303/scaleFactor, 344/scaleFactor};
		int[] yArraySideTriagle = {212/scaleFactor, 255/scaleFactor, 255/scaleFactor};
        chooseColor(g, "SKY BLUE");
		g.fillPolygon(xArraySideTriangle, yArraySideTriagle, 3);

        int[] xArraySideTriangleLeft = {180/scaleFactor, 303/scaleFactor, 303/scaleFactor};
        int[] yArraySideTriangleLeft = {255/scaleFactor, 212/scaleFactor, 255/scaleFactor};
        g.fillPolygon(xArraySideTriangleLeft, yArraySideTriangleLeft, 3);

        if (gameData.getCarPartInfo("Spoiler"))
        {
            int[] xArraySpoilerBottom = {87/scaleFactor, 67/scaleFactor, 107/scaleFactor, 137/scaleFactor};
            int[] yArraySpoilerBottom = {256/scaleFactor, 236/scaleFactor, 236/scaleFactor, 256/scaleFactor};
            chooseColor(g, gameData.getColor());
            g.fillPolygon(xArraySpoilerBottom, yArraySpoilerBottom, 4);

            int[] xArraySpoilerTop = {47/scaleFactor, 47/scaleFactor, 107/scaleFactor};
            int[] yArraySpoilerTop = {236/scaleFactor, 216/scaleFactor, 236/scaleFactor};
            chooseColor(g, gameData.getColor());
            g.fillPolygon(xArraySpoilerTop, yArraySpoilerTop, 3);
        }

        chooseColor(g, "DARK GRAY");
        int[] xArrayMirrors = {319/scaleFactor, 335/scaleFactor, 344/scaleFactor, 344/scaleFactor, 340/scaleFactor};
        int[] yArrayMirros = {265/scaleFactor, 245/scaleFactor, 245/scaleFactor, 260/scaleFactor, 264/scaleFactor,};
        if (gameData.getCarPartInfo("Mirrors"))
        {
            chooseColor(g, "DARK");
            g.fillPolygon(xArrayMirrors, yArrayMirros, 5);
        }
    }
}
