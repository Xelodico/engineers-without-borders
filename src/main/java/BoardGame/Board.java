package BoardGame;

import javax.swing.*;
import javax.swing.border.BevelBorder;

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
    public final int boardSideLength = 9;

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

        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(30, 0, 0, 0),
                BorderFactory.createBevelBorder(BevelBorder.LOWERED)
        ));
        this.setLayout(new GridLayout(boardSideLength, boardSideLength));

        squareArray = generateBoardSquares();
        for (int i = 0; i < 12; i++) {
            generateNewSquares(1, new TaskSquare(tasks.get(i)));
        }
        generateNewSquares(2, new MoneySquare());
        renderBoard(squareArray);
    }

    /**
     * Distributes the types of squares on the board based on a given ratio.
     * The types of squares include Task, Resource, and Normal.
     * Additionally, specific locations can be designated as Spawn points.
     * 
     * @return A list of strings representing the type of each square on the board.
     */
    private List<Square> generateBoardSquares() {
        int totalSquares = boardSideLength * boardSideLength;
        final int[] spawnLocations = GameSystem.getSpawnLocations();
        List<Square> squareArray = new ArrayList<>();

        squareArray.addAll(Collections.nCopies(totalSquares, new Square()));
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
            throw new IllegalArgumentException("Amount of squares cannot exceed the number of squares on the board.");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("Amount of squares cannot be negative.");
        }

        // Collect all empty squares
        List<Integer> availablePositions = new ArrayList<>();
        Map<SquareType, Set<Integer>> existingSquares = new HashMap<>();

        for (SquareType type : SquareType.values()) {
            existingSquares.put(type, new HashSet<>());
        }

        for (int i = 0; i < squareArray.size(); i++) {
            SquareType type = squareArray.get(i).getSquareType();
            existingSquares.get(type).add(i); // Track all existing square types

            if (type == SquareType.SQUARE) {
                availablePositions.add(i); // Collect blank squares
            }
        }

        if (amount > availablePositions.size()) {
            throw new IllegalArgumentException("Not enough normal squares to generate " + amount + " squares.");
        }

        Collections.shuffle(availablePositions); // Shuffle once for random placement

        int placed = 0;
        Iterator<Integer> iterator = availablePositions.iterator();

        while (iterator.hasNext() && placed < amount) {
            int index = iterator.next();

            if (isWithinRadius(index, null, 1, existingSquares.get(SquareType.TASKSQUARE))) {
                continue;
            }

            if (squareType instanceof TaskSquare taskSquare) {
                if (isWithinRadius(index, taskSquare.getTask().getBelongsTo(), 3, existingSquares.get(SquareType.TASKSQUARE))) {
                    continue;
                }
            } else if (squareType instanceof MoneySquare) {
                if (isWithinRadius(index, null, 3, existingSquares.get(SquareType.MONEYSQUARE))) {
                    continue;
                }
            }

            squareArray.set(index, squareType);
            existingSquares.get(squareType.getSquareType()).add(index); // Track the new placement
            placed++;
        }

        refresh();
    }

    private boolean isWithinRadius(int index, Objective objective, int radius, Set<Integer> existingPositions) {
        int row = index / boardSideLength;
        int col = index % boardSideLength;

        for (int i = -radius; i <= radius; i++) {
            for (int j = -radius; j <= radius; j++) {
                int newRow = row + i;
                int newCol = col + j;
                int newIndex = newRow * boardSideLength + newCol;

                if (newRow >= 0 && newRow < boardSideLength && newCol >= 0 && newCol < boardSideLength) {
                    if (existingPositions.contains(newIndex)) {
                        if (objective == null || ((TaskSquare) squareArray.get(newIndex)).getTask().getBelongsTo() == objective) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
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
                        imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
                JLabel playerIcon = new JLabel(resizedImage);
                panel.add(playerIcon, BorderLayout.CENTER);
            }

            revalidate();
            repaint();
        }
    }
}