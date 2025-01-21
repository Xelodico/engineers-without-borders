package BoardGame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Board extends JPanel {

    /**
     * The side length of the grid.
     */
    private final static int boardSideLength = 10;

    /**
     * The distribution ratio of the square types on the board.
     * The ratio is Knowledge : Pothole : Resource : Normal.
     */
    private final static int[] squareTypeRatios = { 1, 2, 2, 3 };

    /**
     * The List of all the Players in the game.
     */
    private List<Player> players;
    
    /**
     * The list of squares on the board.
     */
    protected List<String> squareArray;

    /**
     * Constructs a new Board object and initializes the board with the default
     * settings.
     */
    public Board() {
        this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        this.setLayout(new java.awt.GridLayout(boardSideLength, boardSideLength));

        squareArray = generateBoardSquares();
        players = new ArrayList<>();
        
        players.add(new Player("Placeholder Name 1", 0));
        players.add(new Player("Placeholder Name 2", 9));
        players.add(new Player("Placeholder Name 3", 90));
        players.add(new Player("Placeholder Name 4", 99));
        renderBoard(squareArray);
    }

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
        final List<Integer> spawnLocations = Arrays.asList(0, boardSideLength - 1, totalSquares - boardSideLength, totalSquares - 1);
        List<String> squareArray = new ArrayList<>();

        int totalWeight = Arrays.stream(squareTypeRatios).sum();
        int numKnowledge = totalSquares / totalWeight * squareTypeRatios[0];
        int numPothole = totalSquares / totalWeight * squareTypeRatios[1];
        int numResource = totalSquares / totalWeight * squareTypeRatios[2];
        int numNormal = totalSquares - (numKnowledge + numPothole + numResource);

        squareArray.addAll(Collections.nCopies(numNormal, "Normal"));
        squareArray.addAll(Collections.nCopies(numPothole, "Pothole"));
        squareArray.addAll(Collections.nCopies(numResource, "Resource"));
        squareArray.addAll(Collections.nCopies(numKnowledge, "Knowledge"));

        Collections.shuffle(squareArray);

        for (int location : spawnLocations) {
            squareArray.set(location, "Spawn");
        }

        return squareArray;
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

        for (int i = 0; i < totalSquares; i++) {
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

            for (Player player : players) {
                if (player.getCoord() == i) {
                    ImageIcon imageIcon = new ImageIcon("src/main/resources/images/playerIcon.png");
                    ImageIcon resizedImage = new ImageIcon(imageIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
                    JLabel playerIcon = new JLabel(resizedImage);
                    panel.add(playerIcon, BorderLayout.CENTER);
                }
            }

            this.add(panel);
        }

        this.revalidate();
        this.repaint();
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