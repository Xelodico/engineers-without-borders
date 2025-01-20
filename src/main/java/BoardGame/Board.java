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
     * The side length of the grid.
     */
    private final static int boardSideLength = 10;

    /**
     * The distribution ratio of the square types on the board.
     * The ratio is Knowledge : Pothole : Resource : Normal.
     */
    private final static int[] sqaureTypeRatios = { 1, 2, 2, 3 };

    /**
     * The list of player positions on the board.
     */
    private List<Integer> playerPositions;

    /**
     * The list of squares on the board.
     */
    protected List<String> squareArray;

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
    private List<String> generateBoardSquares() {

        int totalSquares = boardSideLength * boardSideLength;
        final List<Integer> spawnLocations = Arrays.asList(0, boardSideLength - 1, totalSquares - boardSideLength,
                totalSquares - 1);

        playerPositions = new ArrayList<>(spawnLocations);

        int totalWeight = Arrays.stream(sqaureTypeRatios).sum();
        int numKnowledge = totalSquares / totalWeight * sqaureTypeRatios[0];
        int numPothole = totalSquares / totalWeight * sqaureTypeRatios[1];
        int numResource = totalSquares / totalWeight * sqaureTypeRatios[2];
        int numNormal = totalSquares - (numKnowledge + numPothole + numResource);

        List<String> squareArray = new ArrayList<>();

        squareArray.addAll(Collections.nCopies(numNormal, "Normal"));
        squareArray.addAll(Collections.nCopies(numPothole, "Pothole"));
        squareArray.addAll(Collections.nCopies(numResource, "Resource"));
        squareArray.addAll(Collections.nCopies(numKnowledge, "Knowledge"));

        Collections.shuffle(squareArray);

        for (int location : spawnLocations) {
            if (location < 0 || location >= totalSquares) {
                throw new IllegalArgumentException("Spawn location is out of bounds: " + location);
            }
            squareArray.set(location, "Spawn");
        }

        return squareArray;
    }

    /**
     * Constructs a new Board object and initializes the board with the default
     * settings.
     */
    public Board() {
        this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        this.setLayout(new java.awt.GridLayout(boardSideLength, boardSideLength));

        squareArray = generateBoardSquares();
        renderBoard(squareArray);
    }

    /**
     * Renders the board based on the types of squares and player positions.
     * 
     * @param squareArray A list of strings representing the type of each square on
     *                    the board.
     */
    private void renderBoard(List<String> squareArray) {
        this.removeAll();

        int totalSquares = boardSideLength * boardSideLength;

        for (int i = 0; i < (totalSquares); i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

            String squareType = squareArray.get(i);
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
                if (currentPosition >= boardSideLength) {
                    newPosition = currentPosition - boardSideLength;
                }
                break;
            case DOWN:
                if (currentPosition < (boardSideLength - 1) * boardSideLength) {
                    newPosition = currentPosition + boardSideLength;
                }
                break;
            case LEFT:
                if (currentPosition % boardSideLength != 0) {
                    newPosition = currentPosition - 1;
                }
                break;
            case RIGHT:
                if (currentPosition % boardSideLength != boardSideLength - 1) {
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
        renderBoard(squareArray);
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
    public List<String> getsquareArray() {
        return squareArray;
    }

    /**
     * Gets the square at a given index on the board.
     * 
     * @param index The index of the square to get.
     * @return The type of square at the given index.
     */
    public String getSquareAt(int index) {
        if (index < 0 || index >= squareArray.size()) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        return squareArray.get(index);
    }

    /**
     * Sets the square at a given index on the board.
     * 
     * @param index      The index of the square to set.
     * @param squareType The type of square to set at the given index.
     */
    public void setSquareAt(int index, String squareType) {
        if (index < 0 || index >= squareArray.size()) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        squareArray.set(index, squareType);
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
