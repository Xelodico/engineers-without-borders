package BoardGame;

import java.util.Random;

/**
 * This class holds all of the data for each "Player" in the game.
 * @author Curtis
 */
public class Player {
    private String name;
    private int coord;
    private int resources;
    private int movesLeft;
    
    // Default Constructor for Player Object
    public Player() {
        name = "";
        coord = 0;
        resources = 0;
        movesLeft = 1;
    }

    /**
     * Constructor for Player Object with just their name, coords and default values.
     * @param newName
     */
    public Player(String newName, int newCoords) {
        name = newName;
        coord = newCoords;
        resources = 0;
        movesLeft = 1;
    }
    
    // Parameterized Constructor for the Player Class
    public Player(String newName, int newCoord, int newResources, int newMovesLeft) {
        name = newName;
        coord = newCoord;
        resources = newResources;
        movesLeft = newMovesLeft;
    }

    // Getter and Setter methods for the Player attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoord() {
        return coord;
    }

    public void setCoord(int coord) {
        this.coord = coord;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getMovesLeft() {
        return movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    // Roll a die for movement or other game mechanics
    private void rollDie() {
        Random randomGen = new Random();
        int randomNumber = randomGen.nextInt(6) + 1;
        System.out.println(randomNumber);
    }
    
    /**
     * Move the player in the specified direction on the board.
     * Updates the player's position and reduces the number of moves left.
     * 
     * @param boardSideLength The side length of the game board (used to calculate movement).
     * @param direction The direction in which the player wants to move.
     */
    public void movePlayer(int boardSideLength, Direction direction) {
        if (movesLeft > 0) {
            int currentCoord = getCoord();
            int newCoord = currentCoord;

            // Determine the new coordinate based on the direction
            switch (direction) {
                case UP:
                    if (currentCoord >= boardSideLength) {
                        newCoord = currentCoord - boardSideLength;
                    }
                    break;
                case DOWN:
                    if (currentCoord < (boardSideLength - 1) * boardSideLength) {
                        newCoord = currentCoord + boardSideLength;
                    }
                    break;
                case LEFT:
                    if (currentCoord % boardSideLength != 0) {
                        newCoord = currentCoord - 1;
                    }
                    break;
                case RIGHT:
                    if (currentCoord % boardSideLength != boardSideLength - 1) {
                        newCoord = currentCoord + 1;
                    }
                    break;
            }

            // Only move if the new coordinate is valid
            if (newCoord != currentCoord) {
                setCoord(newCoord);
                setMovesLeft(getMovesLeft() - 1);  // Decrease moves after moving
                System.out.println(getName() + " moved to position " + newCoord);
            }
        } else {
            System.out.println(getName() + " has no moves left this turn.");
        }
    }
}