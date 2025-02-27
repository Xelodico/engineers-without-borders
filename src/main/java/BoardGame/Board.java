package BoardGame;

import javax.swing.*;

import GameSystem.GameSystem;
import square.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Board extends JPanel {

    /**
     * The side length of the grid.
     */
    public final int boardSideLength = 12;

    /**
     * The distribution ratio of the square types on the board.
     * The ratio is Task : Resource : Normal.
     */
    private final static int[] squareTypeRatios = { 2, 2, 6 };

    /**
     * The List of all the Players in the game.
     */
    private Player[] players;

    /**
     * The list of squares on the board.
     */
    protected List<Square> squareArray;

    private List<JPanel> squarePanels;

    /**
     * Constructs a new Board object and initializes the board with the default
     * settings.
     */
    public Board(ArrayList<Task> tasks) {
        this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        this.setLayout(new java.awt.GridLayout(boardSideLength, boardSideLength));

        squareArray = generateBoardSquares(tasks);
        for (int i = 0; i < 12; i++) {
            generateNewSquares(1, new TaskSquare(tasks.get(i)));
        }
        renderBoard(squareArray);
    }

    /**
     * Distributes the types of squares on the board based on a given ratio.
     * The types of squares include Task, Resource, and Normal.
     * Additionally, specific locations can be designated as Spawn points.
     * 
     * @return A list of strings representing the type of each square on the board.
     */
    private List<Square> generateBoardSquares(ArrayList<Task> tasks) {
        int totalSquares = boardSideLength * boardSideLength;
        final List<Integer> spawnLocations = Arrays.asList(65, 66, 77, 78);
        List<Square> squareArray = new ArrayList<>();

        int totalWeight = Arrays.stream(squareTypeRatios).sum();
        int numTask = totalSquares / totalWeight * squareTypeRatios[0];
        int numNormal = totalSquares;

        squareArray.addAll(Collections.nCopies(numNormal, new Square()));

        // for (int i = 0; i < numTask; i++) {
        //     // squareArray.add(new TaskSquare(new Task()));
        //     if(i < tasks.size()) {
        //         squareArray.add(new TaskSquare(tasks.get(i)));
        //     } else {
        //         System.out.println("Too many TaskSquares are being created! Some TaskSquares will be empty.");
        //         squareArray.add(new TaskSquare(new Task()));
        //     }
        // }

        Collections.shuffle(squareArray);

        for (int location : spawnLocations) {
            squareArray.set(location, new ShopSquare());
        }

        return squareArray;
    }

    /**
     * Renders the board based on the types of squares and player positions.
     * 
     * @param squareArray A list of strings representing the type of each square on
     *                    the board.
     */
    public void renderBoard(List<Square> squareArray) {
        int totalSquares = boardSideLength * boardSideLength;
        if (squarePanels != null) {
            squarePanels.clear();
        }
        squarePanels = new ArrayList<JPanel>();

        for (int i = 0; i < totalSquares; i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
            panel.setBackground(squareArray.get(i).getColor());

            squarePanels.add(panel);
            this.add(panel);
        }
        if (players != null) {
            renderPlayers(players);
        }
    }

    /**
     * Refreshes the board to reflect the current state of the game.
     */
    public void refresh() {
        players = GameSystem.getTurnOrder();
        this.removeAll();
        renderBoard(squareArray);
        this.revalidate();
        this.repaint();
    }

    /**
     * Gets the list of square types on the board.
     * 
     * @return The list of square types on the board.
     */
    public List<Square> getSquareArray() {
        return squareArray;
    }

    /**
     * Gets the square at a given index on the board.
     * 
     * @param index The index of the square to get.
     * @return The type of square at the given index.
     */
    public Square getSquareAt(int index) {
        if (index < 0 || index >= squareArray.size()) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        }
        return squareArray.get(index);
    }

    /**
     * Sets the square at a given index on the board.
     * 
     * @param index      The index of the square to set.
     * @param square The square to set at the given index.
     */
    public void setSquareAt(int index, Square square) {
        if (index < 0 || index >= squareArray.size()) {
            throw new IllegalArgumentException("Index out of bounds: " + index);
        } else if (square == null) {
            throw new IllegalArgumentException("Square type cannot be null.");
        }

        squareArray.set(index, square);
        refresh();
    }

    /**
     * Generates a given number of squares of a given type on the board.
     * 
     * @param amount     The number of squares to generate.
     * @param squareType The type of square to generate.
     * @throws IllegalArgumentException if the amount of squares exceeds the number
     *                                  of
     *                                  squares on the board or if the amount is
     *                                  negative.
     */
    public void generateNewSquares(int amount, Square squareType) {
        if (amount > squareArray.size()) {
            throw new IllegalArgumentException("Amount of potholes cannot exceed the number of squares on the board.");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("Amount of potholes cannot be negative.");
        }

        long normalSquareCount = squareArray.stream()
                .filter(square -> square.getSquareType() == SquareType.SQUARE)
                .count();

        if (amount > normalSquareCount) {
            throw new IllegalArgumentException("Not enough normal squares to generate " + amount + " pothole(s).");
        }

        for (int i = 0; i < amount; i++) {
            boolean valid = false;
            while (!valid) {
                int randomIndex = (int) (Math.random() * squareArray.size());
                if (squareArray.get(randomIndex).getSquareType() == SquareType.SQUARE) {
                    squareArray.set(randomIndex, squareType);
                    valid = true;
                }
            }
        }

        refresh();
    }

    /**
     * Renders the players on the board.
     * 
     * @param players The list of players to render on the board.
     */
    public void renderPlayers(Player[] players) {
        this.players = players;
        for (int i = 0; i < squarePanels.size(); i++) {
            JPanel panel = squarePanels.get(i);
            panel.removeAll();
            final int squareIndex = i;

            // Count the number of players on this square
            long playersOnSquare = Arrays.stream(players).filter(player -> player.getCoord() == squareIndex).count();

            ImageIcon imageIcon = null;
            switch ((int) playersOnSquare) {
                case 1:
                    imageIcon = new ImageIcon("src/main/resources/images/players/playerIcon.png");
                    break;
                case 2:
                    imageIcon = new ImageIcon("src/main/resources/images/players/twoPlayerIcon.png");
                    break;
                case 3:
                    imageIcon = new ImageIcon("src/main/resources/images/players/threePlayerIcon.png");
                    break;
                case 4:
                    imageIcon = new ImageIcon("src/main/resources/images/players/fourPlayerIcon.png");
                    break;
                default:
                    break;
            }

            if (imageIcon != null) {
                ImageIcon resizedImage = new ImageIcon(
                        imageIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
                JLabel playerIcon = new JLabel(resizedImage);
                panel.add(playerIcon, BorderLayout.CENTER);
            }

            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 650);
        frame.add(new Board(new ArrayList<Task>()));
        frame.setVisible(true);
    }
}