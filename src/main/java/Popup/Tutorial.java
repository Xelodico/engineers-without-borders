package Popup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JPanel;

public class Tutorial extends JPanel {
    public Tutorial() {
    }

    /**
     * Temporary method for reading tutorial data from a text file.
     * This method will be integrated into the GUI by a teammate.
     *
     * Reads the contents of "tutorial.txt" line by line and prints them to the
     * console.
     * Titles (lines starting with '#') are formatted with a newline for emphasis.
     *
     * TODO: Replace console output with GUI text display.
     */
    private void readData() {
        // Define the path to the tutorial data file
        String filePath = "src/main/resources/data/tutorial.txt";
        File tutorialFile = new File(filePath);
        Scanner fileReader;

        try {
            // Initialize the Scanner to read the file
            fileReader = new Scanner(tutorialFile);

            // Read the file line by line
            while (fileReader.hasNext()) {
                String line = fileReader.nextLine();

                // Skip empty lines to maintain readability
                if (line.isEmpty()) {
                    continue;
                }

                // Check if the line represents a title (lines starting with '#')
                boolean isTitle = line.startsWith("#");

                if (isTitle) {
                    // Remove '#' and print with a newline for separation
                    System.out.println("\n" + line.substring(1));
                } else {
                    // Print regular content lines
                    System.out.println(line);
                }
            }

            // Close the Scanner to release resources
            fileReader.close();

        } catch (FileNotFoundException e) {
            // Handle the case where the tutorial file is missing
            System.err.println("Tutorial file not found.");
            e.printStackTrace();
        }
    }

}
