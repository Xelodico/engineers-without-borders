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
    private int id;

    static int nextID;
    
    // Default Constructor for Player Object
    public Player() {
        this.id = nextID;
        this.name = "";
        this.coord = 0;
        this.resources = 0;
        this.movesLeft = 0;
    }

    /**
     * Constructor for Player Object with just their name, coords and default values.
     * @param newName
     */
    public Player(String name, int coords) {
        this.id = nextID;
        this.name = name;
        this.coord = coords;
        this.resources = 0;
        this.movesLeft = 0;
        nextID++;
    }
    
    // Parameterized Constructor for the Player Class
    public Player(String name, int coord, int resources, int movesLeft) {
        this.id = nextID;
        this.name = name;
        this.coord = coord;
        this.resources = resources;
        this.movesLeft = movesLeft;
        nextID++;
    }
    
    public int getID() {
        return this.id;
    }
    
    // Getter and Setter methods for the Player attributes
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoord() {
        return this.coord;
    }

    public void setCoord(int coord) {
        this.coord = coord;
    }

    public int getResources() {
        return this.resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getMovesLeft() {
        return this.movesLeft;
    }

    public void setMovesLeft(int movesLeft) {
        this.movesLeft = movesLeft;
    }

    // Roll a die for movement or other game mechanics
    public int rollDie() {
        Random randomGen = new Random();
        int randomNumber = randomGen.nextInt(6) + 1;
        System.out.println(randomNumber);
        movesLeft = randomNumber;
        return randomNumber;
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