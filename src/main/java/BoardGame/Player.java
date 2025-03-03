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
    private int score;
    private int money;
    private int asphalt;
    private int volunteers;
    private int influence;
    private int knowledge;
    private int movesLeft;
    private int moneySpent;
    private int score;
    private int movesTravelled;
    private int timesHelped;
    private ArrayList<Task> tasks;

    /**
     * Default constructor for a blank player object.
     * Initializes a player with default values for name, coordinates,
     * resources, and moves left. The player's ID is set using a unique counter.
     * private ArrayList<Task> tasks;
     * 
     * /**
     * Constructor for a player object with a name, coordinates, resources,
     * and available moves.
     * 
     * @param name      The name of the player
     * @param coord     The initial coordinates of the player on the board
     * @param resources The initial resources the player has
     * @param movesLeft The number of moves available to the player
     */
    public Player(String name, int coord, int resources, int movesLeft) {
        this.name = name;
        this.coord = coord;
        this.score = 0;
        this.money = 0;
        this.asphalt = 0;
        this.volunteers = 0;
        this.influence = 0;
        this.knowledge = 0;
        this.movesLeft = movesLeft;
        this.timesHelped = timesHelped;
        this.tasks = new ArrayList<Task>();
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
        this.name = name;
        this.coord = coords;
        this.score = 0;
        this.money = 0;
        this.asphalt = 0;
        this.volunteers = 0;
        this.influence = 0;
        this.knowledge = 0;
        this.movesLeft = 0;
        this.timesHelped = 0;
        this.tasks = new ArrayList<Task>();
    }

    /**
     * Default constructor for a blank player object.
     * Initializes a player with default values for name, coordinates,
     * resources, and moves left. The player's ID is set using a unique counter.
     */
    public Player() {
        this.name = "";
        this.coord = 0;
        this.score = 0;
        this.money = 0;
        this.asphalt = 0;
        this.volunteers = 0;
        this.influence = 0;
        this.knowledge = 0;
        this.movesLeft = 0;
        this.timesHelped = 0;
        this.tasks = new ArrayList<Task>();
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
        } else if (coord > 143) {
            throw new IllegalArgumentException("Coordinate cannot be greater than 143");
        }
        this.coord = coord;
    }
    
    /**
     * Gets the score of the player
     * 
     * @return the score of the player
     */
    public int getScore() {
        return this.score;
    }
    
    /** 
     * Changes the score of the player by the given amount. Score cannot go below 0
     * 
     * @param changeAmount - the amount to change the score by
     */
    public void changeScoreBy(int changeAmount) {
        if(this.score + changeAmount < 0) {
        	this.score = 0;
        } else {
        	this.score += changeAmount;
        }
    }

    /**
     * Retrieves the money value of the player
     * 
     * @return returns current money amount of the player
     */

    public int getMoney() {
        return this.money;
    }

    /**
     * Sets the money value of the player.
     * 
     * @param money The new money value to assign to the player
     */

    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * changes existing money value by a certain amount
     * 
     * @param money change in money value
     */

    public void changeMoney(int money) {
        this.money = this.money + money;
    }

    /**
     * Retrieves the total amount of money the player has spent.
     *
     * @return The total money spent by the player.
     */
    public int getMoneySpent() {
        return this.moneySpent;
    }

    /**
     * Increases the amount of money the player has spent.
     *
     * @param money The amount to add to the player's total money spent.
     */
    public void increaseMoneySpent(int money) {
        this.moneySpent += money;
    }

    /**
     * Retrieves the player's current score.
     *
     * @return The player's score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Updates the player's score.
     *
     * @param score The new score to set for the player.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Retrieves the total number of moves the player has traveled.
     *
     * @return The number of moves the player has made.
     */
    public int getMovesTravelled() {
        return this.movesTravelled;
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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    
    /**
     * Gets the amount of the resource referenced by the inputed ResourceType
     * 
     * @param resourceType - The type of resource amount to return
     * @return the amount of the resource referenced by resourceType
     */
    public int getResource(ResourceType resourceType) {
        switch(resourceType) {
        case ASPHALT: 
        	return asphalt;
        case VOLUNTEERS: 
        	return volunteers;
        case INFLUENCE:
        	return influence;
        case KNOWLEDGE:
        	return knowledge;
        default: 
        	return 0;
        }
    }

    /**
     * Sets the amount of the resource referenced by the inputed ResourceType
     * 
     * @param resource - The new amount of the resource
     * @param resourceType - The type of resource to set
     */
    public void setResource(int resource, ResourceType resourceType) {
    	switch(resourceType) {
        case ASPHALT: 
        	this.asphalt = resource;
        case VOLUNTEERS: 
        	this.volunteers = resource;
        case INFLUENCE:
        	this.influence = resource;
        case KNOWLEDGE:
        	this.knowledge = resource;
        }
    }
    
    /**
     * changes the amount of the resource referenced by the inputed ResourceType by the amount inputed
     * 
     * @param changeAmount - The amount to change resource by (can be negative)
     * @param resourceType - The type of resource to set
     */
    public void changeResource(int changeAmount, ResourceType resourceType) {
    	switch(resourceType) {
        case ASPHALT: 
        	if(this.asphalt + changeAmount < 0) {
            	this.asphalt = 0;
            } else {
            	this.asphalt += changeAmount;
            } 
        case VOLUNTEERS: 
        	if(this.volunteers + changeAmount < 0) {
            	this.volunteers = 0;
            } else {
            	this.volunteers += changeAmount;
            } 
        case INFLUENCE:
        	if(this.influence + changeAmount < 0) {
            	this.influence = 0;
            } else {
            	this.influence += changeAmount;
            } 
        case KNOWLEDGE:
        	if(this.knowledge + changeAmount < 0) {
            	this.knowledge = 0;
            } else {
            	this.knowledge += changeAmount;
            }
        }
    }

    public int getTimesHelped() {
        return timesHelped;
    }

    public void changeTimesHelped(int timesHelped) {
        this.timesHelped += timesHelped;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

    public Task findTask(Task task) {
        for (Task t : tasks) {
            if (t.equals(task)) {
                return t;
            }
        }
        return null;
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
     * @param player    The player to move.
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
                this.movesTravelled++;
                System.out.println(this.getName() + " moved to position " + newCoord);
                System.out.println("Moves travelled: " + this.movesTravelled);
            }
        } else {
            System.out.println(this.getName() + " has no moves left this turn.");
        }
    }
}