package BoardGame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The board class represents the game board.
 * It extends JPanel and is responsible for initializing the board and randomly distributing the sqaure types.
 * The board is a grid with a changeable, fixed number of columns and rows.
 * 
 * @author Nathan Watkins
 */
public class Board extends JPanel {
    
    /**
     * The number of columns in the grid.
     */
    final int GRID_COLUMNS = 10;

    /**
     * The number of rows in the grid.
     */
    final int GRID_ROWS = GRID_COLUMNS; // Currently set equal to GRID_COLUMNS to make it an even square

    /**
     * Distributes the types of squares on the board based on a given ratio.
     * The types of squares include Knowledge, Pothole, Resource, and Normal.
     * Additionally, specific locations can be designated as Spawn points.
     * 
     * @param totalSquares The total number of squares on the board.
     * @param spawnLocations A list of indices where spawn points should be placed.
     * @return A list of strings representing the type of each square on the board.
     */
    public static List<String> gridDistribution(int totalSquares, List<Integer> spawnLocations) {
        int[] distributionRatio = {1, 2, 2, 3}; //Knowledge : Pothole : Resource : Normal ratio

        int totalWeight = Arrays.stream(distributionRatio).sum();
        int numKnowledge = totalSquares / totalWeight * distributionRatio[0];
        int numPothole = totalSquares / totalWeight * distributionRatio[1];
        int numResource = totalSquares / totalWeight * distributionRatio[2];
        int numNormal = totalSquares / totalWeight * distributionRatio[3];

        List<String> squareTypes = new ArrayList<>();
        for (int i = 0; i < numNormal; i++) {
            squareTypes.add("Normal");
        }
        for (int i = 0; i < numPothole; i++) {
            squareTypes.add("Pothole");
        }
        for (int i = 0; i < numResource; i++) {
            squareTypes.add("Resource");
        }
        for (int i = 0; i < numKnowledge; i++) {
            squareTypes.add("Knowledge");
        }

        Collections.shuffle(squareTypes);

        for (int location : spawnLocations) {
            squareTypes.add(location, "Spawn");
        }

        return squareTypes;
    }
    
    /**
     * Constructs a new Board object and initializes the board with the default settings.
     * The board is initialized with a grid of squares and specific spawn locations.
     */
    public Board() {
        int totalSquares = GRID_COLUMNS*GRID_ROWS;
        final List<Integer> spawnLocations = Arrays.asList(0, GRID_COLUMNS-1, totalSquares-GRID_COLUMNS, totalSquares-1);
        List<String> squareTypes = gridDistribution(totalSquares, spawnLocations);

        for (int i = 0; i < (totalSquares); i++) {
            JPanel panel = new JPanel();
            panel.setPreferredSize(new Dimension(40, 40));
            panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

            String squareType = squareTypes.get(i);
            switch (squareType) {
                case "Normal":
                panel.setBackground(Color.WHITE);
                break;
                case "Pothole":
                panel.setBackground(Color.RED);
                break;
                case "Knowledge":
                panel.setBackground(Color.BLUE);
                break;
                case "Resource":
                panel.setBackground(Color.ORANGE);
                break;
                case "Spawn":
                panel.setBackground(Color.GREEN);
                break;
            }

            this.add(panel);
        }
        this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        this.setLayout(new java.awt.GridLayout(GRID_ROWS, GRID_COLUMNS));
    }


    /**
     * Main method for running the board by itself.
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Board Game");
        Board board = new Board();

        frame.add(board);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
