package BoardGame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The board class represents the game board.
 * It extends JPanel and is responsible for initializing the board and randomly
 * distributing the square types.
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
     * The number of rows in the grid (square board).
     */
    final int GRID_ROWS = GRID_COLUMNS;

    private List<Integer> playerPositions;

    protected List<String> squareTypes;

    /**
     * Distributes the types of squares on the board based on a given ratio.
     * The types of squares include Knowledge, Pothole, Resource, and Normal.
     * Additionally, specific locations can be designated as Spawn points.
     * 
     * @param totalSquares   The total number of squares on the board.
     * @param spawnLocations A list of indices where spawn points should be placed.
     * @return A list of strings representing the type of each square on the board.
     * @throws IllegalArgumentException if a spawn location is out of bounds.
     */
    public static List<String> gridDistribution(int totalSquares, List<Integer> spawnLocations) {
        int[] distributionRatio = { 1, 2, 2, 3 }; // Knowledge : Pothole : Resource : Normal ratio

        int totalWeight = Arrays.stream(distributionRatio).sum();
        int numKnowledge = totalSquares / totalWeight * distributionRatio[0];
        int numPothole = totalSquares / totalWeight * distributionRatio[1];
        int numResource = totalSquares / totalWeight * distributionRatio[2];
        int numNormal = totalSquares - (numKnowledge + numPothole + numResource);

        List<String> squareTypes = new ArrayList<>();

        squareTypes.addAll(Collections.nCopies(numNormal, "Normal"));
        squareTypes.addAll(Collections.nCopies(numPothole, "Pothole"));
        squareTypes.addAll(Collections.nCopies(numResource, "Resource"));
        squareTypes.addAll(Collections.nCopies(numKnowledge, "Knowledge"));

        Collections.shuffle(squareTypes);

        for (int location : spawnLocations) {
            if (location < 0 || location >= totalSquares) {
                throw new IllegalArgumentException("Spawn location is out of bounds: " + location);
            }
            squareTypes.set(location, "Spawn");
        }

        return squareTypes;
    }

    /**
     * Constructs a new Board object and initializes the board with the default
     * settings.
     */
    public Board() {
        int totalSquares = GRID_COLUMNS * GRID_ROWS;
        final List<Integer> spawnLocations = Arrays.asList(0, GRID_COLUMNS - 1, totalSquares - GRID_COLUMNS,
                totalSquares - 1);
        squareTypes = gridDistribution(totalSquares, spawnLocations);

        playerPositions = new ArrayList<>(spawnLocations);

        this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        this.setLayout(new java.awt.GridLayout(GRID_ROWS, GRID_COLUMNS));

        renderBoard(squareTypes);
    }

    /**
     * Renders the board based on the types of squares and player positions.
     * 
     * @param squareTypes A list of strings representing the type of each square on
     *                    the board.
     */
    private void renderBoard(List<String> squareTypes) {
        this.removeAll();

        int totalSquares = GRID_COLUMNS * GRID_ROWS;

        for (int i = 0; i < (totalSquares); i++) {
            JPanel panel = new JPanel();
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

            if (playerPositions.contains(i)) {
                ImageIcon imageIcon = new ImageIcon("src/main/resources/images/playerIcon.png");
                ImageIcon resizedImage = new ImageIcon(
                        imageIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
                JLabel playerIcon = new JLabel(resizedImage);
                panel.add(playerIcon, BorderLayout.CENTER);
            }

            this.add(panel);
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Moves a player in a given direction on the board.
     * 
     * @param playerIndex The index of the player to move.
     * @param direction   The direction to move the player. This comes from the
     *                    Direction enum.
     */
    public void movePlayer(int playerIndex, Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }
        int currentPosition = playerPositions.get(playerIndex);
        int newPosition = currentPosition;

        // TODO: Check if the player has enough moves before moving them

        switch (direction) {
            case UP:
                if (currentPosition >= GRID_COLUMNS) {
                    newPosition = currentPosition - GRID_COLUMNS;
                }
                break;
            case DOWN:
                if (currentPosition < (GRID_ROWS - 1) * GRID_COLUMNS) {
                    newPosition = currentPosition + GRID_COLUMNS;
                }
                break;
            case LEFT:
                if (currentPosition % GRID_COLUMNS != 0) {
                    newPosition = currentPosition - 1;
                }
                break;
            case RIGHT:
                if (currentPosition % GRID_COLUMNS != GRID_COLUMNS - 1) {
                    newPosition = currentPosition + 1;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }

        if (newPosition == currentPosition) {
            System.out.println("Player " + playerIndex + " cannot move in direction " + direction);
            return;
        }

        playerPositions.set(playerIndex, newPosition);
        renderBoard(squareTypes);
        System.out.println("Player " + playerIndex + " moved to position " + newPosition);
    }

    /**
     * Gets the list of player positions on the board.
     * 
     * @return The list of player positions on the board.
     */
    public List<Integer> getPlayerPositions() {
        return playerPositions;
    }

    /**
     * Gets the list of square types on the board.
     * 
     * @return The list of square types on the board.
     */
    public List<String> getSquareTypes() {
        return squareTypes;
    }

    /**
     * The main method creates a JFrame and adds a new Board object to it.
     * Can be used to run the game board as a standalone application.
     * 
     * @param args Command line arguments (not used).
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
