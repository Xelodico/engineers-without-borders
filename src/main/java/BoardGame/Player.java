package BoardGame;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class holds all of the data for each "Player" in the game.
 * The Player object stores the player's name, coordinates, resources,
 * available moves, and a unique identifier. It also provides methods to
 * interact with and manipulate these attributes.
 * 
 * @author Curtis McCartney
 * @author Nathan Watkins
 */
public class Player {
    private String name;
    private int coord;
    private int resources;
    private int movesLeft;
    private int id;

    private int nextID = 0;
    private ArrayList<JobRole> roles;
    

    /**
     * Default constructor for a blank player object.
     * Initializes a player with default values for name, coordinates,
     * resources, and moves left. The player's ID is set using a unique counter.
     */
    public Player() {
        this.id = nextID;
        this.name = "";
        this.coord = 0;
        this.resources = 0;
        this.movesLeft = 0;
        nextID++;
    }

    /**
     * Constructor for a player object with a specified name and coordinates.
     * Initializes the player with the provided name and coordinates and
     * default values for resources and moves left. The player's ID is set
     * using a unique counter
     * 
     * @param name   The name of the player
     * @param coords The initial coordinates of the player on the board
     */
    public Player(String name, int coords) {
        this.id = nextID;
        this.name = name;
        this.coord = coords;
        this.resources = 0;
        this.movesLeft = 0;
        nextID++;
    }

    /**
     * Constructor for a player object with a name, coordinates, resources,
     * and available moves.
     * 
     * @param name      The name of the player
     * @param coord     The initial coordinates of the player on the board
     * @param resources The initial resources the player has
     * @param movesLeft The number of moves available to the player
     */
    public Player(String name, int coord, int resources, int movesLeft) {
        this.id = nextID;
        this.name = name;
        this.coord = coord;
        this.resources = resources;
        this.movesLeft = movesLeft;
        nextID++;
    }

    /**
     * Gets the ID of the player.
     * 
     * @return The unique ID of the player
     */
    public int getID() {
        return this.id;
    }

    /**
     * Gets the name of the player.
     * 
     * @return The name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the player.
     * 
     * @param name The new name to assign to the player
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    /**
     * Gets the coordinates of the player.
     * 
     * @return The current coordinates of the player
     */
    public int getCoord() {
        return this.coord;
    }

    /**
     * Sets the coordinates of the player.
     * 
     * @param coord The new coordinates to assign to the player
     */
    public void setCoord(int coord) {
        if (coord < 0) {
            throw new IllegalArgumentException("Coordinate cannot be negative");
        } else if (coord > 100) {
            throw new IllegalArgumentException("Coordinate cannot be greater than 100");
        }
        this.coord = coord;
    }

    /**
     * Gets the resources of the player.
     * 
     * @return The current resources of the player
     */
    public int getResources() {
        return this.resources;
    }

    /**
     * Sets the resources of the player.
     * 
     * @param resources The new resource value to assign to the player
     */
    public void setResources(int resources) {
        if (resources < 0) {
            throw new IllegalArgumentException("Resources cannot be negative");
        }
        this.resources = resources;
    }

    /**
     * Gets the moves left for the player.
     * 
     * @return The number of available moves left for the player
     */
    public int getMovesLeft() {
        return this.movesLeft;
    }

    /**
     * Adds a new role to the player
     * 
     * @param newRole The new role to add to this player's responsibilities
     * @return True if the role was added successfully, False if there was a problem
     */
    public boolean addRole(JobRole newRole) {
    	return roles.add(newRole);
    }
    
    /**
     * Removes a role from the player
     * 
     * @param roleToRemove The role to remove from this player's responsibilities
     * @return True if the role was removed successfully, False if there was a problem
     */
    public boolean removeRole(JobRole roleToRemove) {
    	return roles.remove(roleToRemove);
    }
    
    /**
     * Sets the moves left for the player.
     * 
     * @param movesLeft The new number of moves left to assign to the player
     */
    public void setMovesLeft(int movesLeft) {
        if (movesLeft < 0) {
            throw new IllegalArgumentException("Moves left cannot be negative");
        } else if (movesLeft > 6) {
            throw new IllegalArgumentException("Moves left cannot be greater than 6");
        }
        this.movesLeft = movesLeft;
    }

    /**
     * Rolls a die to determine movement or other game mechanics.
     * A random number between 1 and 6 is generated, and the number of moves left
     * is updated based on the roll.
     * 
     * @return The random number generated by the die roll
     */
    public int rollDie() {
        Random randomGen = new Random();
        int randomNumber = randomGen.nextInt(6) + 1;
        System.out.println(randomNumber);
        this.movesLeft = randomNumber;
        return randomNumber;
    }

    /**
     * Moves the player in the specified direction on the game board.
     * 
     * @param player The player to move.
     * @param direction The direction to move the player.
     */
    public void moveAction(Direction direction, int boardSideLength) {
        int currentCoord = this.getCoord();
        int totalSquares = boardSideLength * boardSideLength;

        if (currentCoord < 0 || currentCoord >= totalSquares) {
            throw new IllegalArgumentException("Player position is out of bounds");
        }

        if (direction == null) {
            throw new IllegalArgumentException("Direction cannot be null");
        }

        if (this.getMovesLeft() > 0) {
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
                this.setCoord(newCoord);
                this.setMovesLeft(this.getMovesLeft() - 1); // Decrease moves after moving
                System.out.println(this.getName() + " moved to position " + newCoord);
            }
        } else {
            System.out.println(this.getName() + " has no moves left this turn.");
        }
    }
}