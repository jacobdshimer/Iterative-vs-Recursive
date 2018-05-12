/*
Filename: Gui.java
Author: Shimer, Jacob D.

This is the main file for Project 3.  It contains the classes GuiFrame, Panel, WindowHandler, Application and main.
GuiFrame extends JFrame and sets the size of the frame, the title of the frame and sets it to visible.  Panel builds
out the panel and creates an action listener for the button compute.  WindowHandler creates the file once the frame
is closed.

*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

public class Gui {
    class GuiFrame extends JFrame {
        GuiFrame(String title, int width, int height) {
            super(title);
            setFrame(width, height);
        }

        void display() {
            setVisible(true);
        }

        void setFrame(int width, int height) {
            setSize(width, height);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }

    private class Panel extends JPanel {
        //All of the parts for the gui
        private JRadioButton iterative = new JRadioButton("Iterative");
        private JRadioButton recursive = new JRadioButton("Recursive");
        private JLabel userInputLabel = new JLabel("Enter N");
        private JTextField userInput = new JTextField();
        private JButton compute = new JButton("Compute");
        private JLabel resultsLabel = new JLabel("Results");
        private JTextField results = new JTextField();
        private JLabel efficiencyLabel = new JLabel("Efficiency");
        private JTextField efficiency = new JTextField();

        private Panel() {
            ButtonGroup group = new ButtonGroup();
            group.add(iterative);
            group.add(recursive);
            //Sets iterative to be selected when you load the program
            iterative.setSelected(true);

            //Makes these text fields not editable because they are meant for displaying information
            efficiency.setEditable(false);
            results.setEditable(false);

            //Creates the first panel for the radio buttons
            JPanel radio = new JPanel();
            radio.setLayout(new GridLayout(1,2,1,2));
            radio.add(iterative);
            radio.add(recursive);

            //Creates the second panel which contains the user's input
            JPanel userInputPanel = new JPanel();
            userInputPanel.setLayout(new GridLayout(2,2,1,2));
            userInputPanel.add(userInputLabel);
            userInputPanel.add(userInput);
            userInputPanel.add(compute);

            //Creates a third panel for displaying the information to the user
            JPanel data = new JPanel();
            data.setLayout(new GridLayout(2,2,1,2));
            data.add(resultsLabel);
            data.add(results);
            data.add(efficiencyLabel);
            data.add(efficiency);

            //Creates a fourth panel for putting everything together
            JPanel allTogether = new JPanel();
            allTogether.setLayout(new GridLayout(3,1,1,4));
            allTogether.add(radio);
            allTogether.add(userInputPanel);
            allTogether.add(data);
            add(allTogether);

            compute.addActionListener(e -> actionPerformed());
        }

        private void actionPerformed() {
            //Checks which button is selected iterative or recursive
            if (iterative.isSelected()) {
                //Checks if the user's input is numeric and the it is greater then or equal to zero.
                if (StringUtils.isNumeric(userInput.getText()) && (Integer.parseInt(userInput.getText()) >= 0)) {
                    //This gets the text from the userInput field, then turns it into an integer to pass to computeIterative,
                    //then changes it back to a string, and then finally sets the results field with what computeIterative returns
                    results.setText(String.valueOf(Sequence.computeIterative(Integer.parseInt(userInput.getText()))));
                    efficiency.setText(String.valueOf(Sequence.getEfficiency()));
                } else {
                    JOptionPane.showMessageDialog(null, "Error, your input must by numeric and greater then 0.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (recursive.isSelected()) {
                if (StringUtils.isNumeric(userInput.getText()) && (Integer.parseInt(userInput.getText()) >= 0)) {
                    //This gets the text from the userInput field, then turns it into an integer to pass to computeRecursive,
                    //then changes it back to a string, and then finally sets the results field with what computeRecursive returns
                    results.setText(String.valueOf(Sequence.computeRecursive(Integer.parseInt(userInput.getText()))));
                    efficiency.setText(String.valueOf(Sequence.getEfficiency()));
                } else {
                    JOptionPane.showMessageDialog(null, "Error, your input must by numeric and greater then 0.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    //This class extends WindowAdapter and creates the file once the user closes the program
    static class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);

            //try-catch for creating the file
            try {
                File file = new File(".\\results.csv");
                //If the file doesn't exists, it creates the file
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                //For loop to pass 0 through 10 to computeRecursive and computeIterative
                try {
                    for (int x=0; x <=10; x++) {
                        Sequence.computeRecursive(x);
                        String effRecurse = String.valueOf(Sequence.getEfficiency());
                        Sequence.computeIterative(x);
                        String effInverse = String.valueOf(Sequence.getEfficiency());
                        //Writes the value of x along with the results of running both of these methods
                        //then writes a newline after
                        bw.write(x + "," + effRecurse + "," + effInverse);
                        bw.newLine();
                    }
                //Closes the file once everything ran
                } finally {
                    bw.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    //Adds the Panel to the Frame
    class Application extends GuiFrame {
        Application(String title, int width, int height) {
            super(title, width, height);
            add(new Panel());
        }

    }

    //the start of the program.  Sets the name, width, and height of the frame before displaying the
    // frame and add the window listener
    public static void main(String[] args){
        Application app = new Gui().new Application("Project 3",250,250);
        app.display();
        app.addWindowListener(new WindowHandler());

    }
}
