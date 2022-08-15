// Krishay
// 5/16/22
// Settings.java
// In this panel, the user can view the settings for the game. When the
// settings button is pressed within the first page, the program switches to
// this panel. It has a GridLayout with 1 row and 2 columns, and each cell has
// a panel. The left panel contains the specific components that can change the
// car, and the right panel draws the car as the user interacts with the components.

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

// Class for the settings panel. This panel has a BorderLayout, and there is a
// GridLayout in the center. The GridLayout has a left and right panel; on the
// left, the user can interact with components to customize their car. On the
// right, their car will be drawn.
public class Settings extends JPanel
{
    private SprintTyperHolder sprintHolder; // The holder class
    private CardLayout cards; // The CardLayout of the holder class
    private GameData gameData; // The game data instance
    private Color salmon; // Color for the left side of the panel
    private Color darkGray; // Color at the bottom of the panel
    private Color lavender; // Color on the right of the panel

    private Car car; // The car instance created in the holder class to be drawn

    private JCheckBox spoiler; // Determines whether the car has a spoiler
    private JCheckBox exhaust; // Determines whether the car has an exhaust
    private JCheckBox mirrors; // Determines whether the car has mirrors
    private JCheckBox roof; // Determines whether the car has a roof

    private JSlider carSpeedSlider; // Determines the speed of the computer cars
    private JTextArea sliderLabel; // Shows the value of the slider
    
    public Settings(SprintTyperHolder sprintHolderIn, CardLayout cardsIn, GameData gameDataIn, Car carIn)
    {
        sprintHolder = sprintHolderIn;
        cards = cardsIn;
        gameData = gameDataIn;
        car = carIn;

        salmon = new Color(235, 147, 133);
        darkGray = new Color(68, 94, 102);
        lavender = new Color(147, 119, 130);
        
        setBackground(Color.ORANGE);
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = makeHomeButton();
        add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel mainGrid = new JPanel();
        mainGrid.setLayout(new GridLayout(1, 2));
        mainGrid.setBackground(Color.ORANGE);
        
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(salmon);
        leftPanel.setLayout(new BorderLayout());

        JPanel leftPanelWithin = new JPanel();
        leftPanelWithin.setLayout(new FlowLayout(FlowLayout.CENTER, 500, 30));
        leftPanelWithin.setBackground(salmon);
        
        JLabel title = sprintHolder.makeLabel("Customize the car!",
            new Font("Dialog", Font.BOLD, 30));
        leftPanelWithin.add(title);

        JPanel radioButtonPanel = makeCheckboxes();
        leftPanelWithin.add(radioButtonPanel);

        JPanel menuBarPanel = makeMenuBar();
        leftPanelWithin.add(menuBarPanel);

        JLabel title2 = sprintHolder.makeLabel("Game Settings:",
            new Font("Dialog", Font.BOLD, 30));
        leftPanelWithin.add(title2);

        JPanel sliderPanFull = new JPanel();
        sliderPanFull.setLayout(new FlowLayout(FlowLayout.CENTER));
        sliderPanFull.setBackground(salmon);

        carSpeedSlider = makeSlider();
        sliderPanFull.add(carSpeedSlider);

        JPanel flowLayoutForSliderLabel = new JPanel();
        flowLayoutForSliderLabel.setLayout(new FlowLayout(FlowLayout.CENTER));

        sliderLabel = makeTA();
        flowLayoutForSliderLabel.add(sliderLabel);

        sliderPanFull.add(flowLayoutForSliderLabel);

        leftPanelWithin.add(sliderPanFull);

        leftPanel.add(leftPanelWithin, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(lavender);
        rightPanel.setLayout(new BorderLayout());
        
        JLabel titleRight = sprintHolder.makeLabel("Your car:",
            new Font("Dialog", Font.BOLD, 30));
        rightPanel.add(titleRight, BorderLayout.NORTH);

        car.setBackground(lavender);
        rightPanel.add(car, BorderLayout.CENTER);
        
        mainGrid.add(leftPanel);
        mainGrid.add(rightPanel);
        
        add(mainGrid, BorderLayout.CENTER);
    }

    // Here, the home button that goes in the south of this panel is created.
    // The method returns a JPanel containing the JButton.
    public JPanel makeHomeButton()
    {
        JButton homeButton = new JButton("Home");
        HomeButtonListenerSettings homeButtonListener = new
            HomeButtonListenerSettings();
        homeButton.addActionListener(homeButtonListener);

        JPanel buttonP = new JPanel();
        buttonP.setLayout( new FlowLayout(FlowLayout.CENTER) );
        buttonP.setBackground(darkGray);
        buttonP.add(homeButton);

        return buttonP;
    }

    // Creates and adds JCheckboxes to a JPanel. These checkboxes help customize
    // the car. Each checkboxes can be selected or unselected to customize that
    // aspect of the car.
    public JPanel makeCheckboxes()
    {
        CheckBoxListener checkBoxL = new CheckBoxListener();

        spoiler = new JCheckBox("Spoiler");
        exhaust = new JCheckBox("Exhaust");
        mirrors = new JCheckBox("Mirrors");
        roof = new JCheckBox("Roof");
        spoiler.setBackground(salmon);
        exhaust.setBackground(salmon);
        mirrors.setBackground(salmon);
        roof.setBackground(salmon);
        spoiler.addActionListener(checkBoxL);
        exhaust.addActionListener(checkBoxL);
        mirrors.addActionListener(checkBoxL);
        roof.addActionListener(checkBoxL);

        spoiler.setSelected(true);
        exhaust.setSelected(true);
        mirrors.setSelected(true);
        roof.setSelected(true);
        
        JPanel checkBoxP = new JPanel();
        checkBoxP.setPreferredSize(new Dimension(80, 160));
        checkBoxP.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));
        checkBoxP.setBackground(salmon);
        checkBoxP.add(spoiler);
        checkBoxP.add(exhaust);
        checkBoxP.add(mirrors);
        checkBoxP.add(roof);

        return checkBoxP;
    }

    // Creates and adds the JMenuBar to a JPanel, which is returned. This menu
    // changes the color of the car.
    public JPanel makeMenuBar()
    {
        MenuListener menuL = new MenuListener();

        JMenuBar bar = new JMenuBar();
        JMenu carColor = new JMenu("Car Color");
        JMenuItem red = new JMenuItem("Red");
        JMenuItem green = new JMenuItem("Green");
        JMenuItem blue = new JMenuItem("Blue");
        JMenuItem yellow = new JMenuItem("Yellow");
        JMenuItem purple = new JMenuItem("Purple");
        carColor.add( red );
        carColor.add( green );
        carColor.add( blue );
        carColor.add( yellow );
        carColor.add( purple );
        bar.add( carColor );

        red.addActionListener(menuL);
        green.addActionListener(menuL);
        blue.addActionListener(menuL);
        yellow.addActionListener(menuL);
        purple.addActionListener(menuL);
        
        JPanel menuBarP = new JPanel();
        menuBarP.setBackground(salmon);
        menuBarP.setLayout(new FlowLayout(FlowLayout.CENTER));
        menuBarP.add(bar);

        return menuBarP;
    }

    // Creates the slider that changes the speed of the computer cars. Also, the
    // appropriate listener is added.
    public JSlider makeSlider()
    {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 15, 1);
        slider.setBackground(salmon);
        slider.setMajorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setLabelTable( slider.createStandardLabels(2) );
		slider.setPaintLabels(true);
        SliderListener carSpeSliderListener = new SliderListener();
        slider.addChangeListener(carSpeSliderListener);

        return slider;
    }

    // Makes the JTextArea that acts as a label for the car speed slider.
    public JTextArea makeTA()
    {
        JTextArea ta = new JTextArea("<- Car Speed of other cars: 1", 1, 1);
        ta.setEditable(false);

        return ta;
    }

    // When the state of the car speed slider is changed, the car speed variable is
    // changed within the game data instance. Also, the text of the JTextArea next
    // to the slider is changed.
    class SliderListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent evt)
        {
            int speed = carSpeedSlider.getValue();
            gameData.setCarSpeed(speed);
            sliderLabel.setText("<- Car Speed of other cars: " + speed);
        }
    }

    // When the checkboxes are selected, this will be called. The outside four
    // if-statements check for which button was clicked. Then, there are 2
    // if-statements inside each of those that check the state of the respective
    // button. The program makes the change in the gameData object.
    class CheckBoxListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            String checkBoxPressed = evt.getActionCommand();

            if (checkBoxPressed.equalsIgnoreCase("Spoiler"))
            {
                if (spoiler.isSelected())
                    gameData.setCarPartInfo("Spoiler", true);
                else if ( !(spoiler.isSelected()) )
                    gameData.setCarPartInfo("Spoiler", false);
            }

            if (checkBoxPressed.equalsIgnoreCase("Exhaust"))
            {
                if (exhaust.isSelected())
                    gameData.setCarPartInfo("Exhaust", true);
                else if ( !(exhaust.isSelected()) )
                    gameData.setCarPartInfo("Exhaust", false);
            }
            
            if (checkBoxPressed.equalsIgnoreCase("Mirrors"))
            {
                if (mirrors.isSelected())
                    gameData.setCarPartInfo("Mirrors", true);
                else if ( !(mirrors.isSelected()) )
                    gameData.setCarPartInfo("Mirrors", false);
            }

            if (checkBoxPressed.equalsIgnoreCase("Roof"))
            {
                if (roof.isSelected())
                    gameData.setCarPartInfo("Roof", true);
                else if ( !(roof.isSelected()) )
                    gameData.setCarPartInfo("Roof", false);
            }

            car.repaint();
        }
    }

    // When one of the menu items is used, this listener classes updates
    // the gameData object appropriately.
    class MenuListener implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            String menuItem = evt.getActionCommand();
            gameData.setColor(menuItem);

            car.repaint();
        }
    }
    
    // When the home button is pressed, the program switches back to the start
    // page.
    class HomeButtonListenerSettings implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            cards.show(sprintHolder, "Intro");
        }
    }
}
