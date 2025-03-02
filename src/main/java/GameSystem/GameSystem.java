package GameSystem;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import BoardGame.*;
import square.*;

/**
 * 
 * The GameSystem class manages game mechanics such as player movement, turn
 * progression,
 * rounds, objectives and tasks/subtasks. It ensures smooth gameplay by updating
 * the board,
 * checking win conditions, and managing UI interactions.
 * 
 * @author Issac Edmonds
 * @author Peter Robinson
 * @author Nathan Watkins (Supporting)
 * @author Curtis McCartney (Supporting)
 */
public abstract class GameSystem {
    // Game board where all gameplay interactions take place
    private static Board gameBoard;

    // Graphical User Interface (GUI) for the board game
    private static BoardGameUI gameBoardUI;

    // Array to maintain the order in which players take turns
    private static Player[] turnOrder;

    // Keeps track of the number of rounds played
    private static int roundNumber;

    // Keeps track of the current turn within a round
    private static int turnNumber;

    // private static ArrayList<JobRole> roles;
    private static ArrayList<Objective> objectives;
    private static ArrayList<Task> tasks;
    private static String[] subtasks;

    // Boolean flag to determine whether the game is active or not
    private static boolean gameActive = false;
    
    // Global variables for resource purchase price and scores for calculating the solution implementation percentage
    private final static int RESOURCE_PRICE = 100;
    private static int maxScore;
    private static int currentTotalAwardedScore;

    /**
     * Initialises the game by setting up essential components.
     * Ensures initialisation happens only once by checking `gameActive`.
     */
    public static void initialise() {
        if (!gameActive) {
            turnNumber = 0; // Reset turn number
            roundNumber = 0; // Reset round count

            // roles = new ArrayList<JobRole>();
            objectives = new ArrayList<Objective>();
            tasks = new ArrayList<Task>();

            // Setting up a default player array with at least one player to avoid errors
            turnOrder = new Player[] { new Player() };
            
            // Create data and update maxScore
            createData();
            maxScore = calculateMaxScore();
            
            
            // Create a new board and its associated GUI
            gameBoard = new Board();
            gameBoardUI = new BoardGameUI(gameBoard);
            gameBoardUI.setTitle("Pavers Valley"); // Set the title for the game window

            gameBoardUI.setVisible(true); // Make the game UI visible
            gameActive = true; // Mark the game as active
        }
    }

    /**
     * Starts the game by incrementing the round number and refreshing the UI.
     */
    public static void startGame() {
        roundNumber++; // Increment the round counter
        gameBoardUI.startGame(); // Start the game through the UI
        gameBoardUI.refresh(); // Refresh the UI to reflect updated game state
    }

    /**
     * Sets the order in which players take turns.
     * 
     * @param players An array of players representing the turn order.
     */
    public static void setTurnOrder(Player[] players) {
        turnOrder = players;
    }

    /**
     * Sets the current round number.
     * 
     * @param number The round number to be set.
     */
    public static void setRoundNumber(int number) {
        roundNumber = number;
    }

    /**
     * Sets the current turn number.
     * 
     * @param turnNum The turn number to be set.
     */
    public static void setTurnNumber(int turnNum) {
        turnNumber = turnNum;
    }

    /**
     * Retrieves the turn order of players.
     * 
     * @return An array of players representing the turn order.
     */
    public static Player[] getTurnOrder() {
        return turnOrder;
    }

    /**
     * Retrieves the player at the specified index in the turn order.
     * 
     * @param index The index of the player to retrieve.
     * @return The Player at the specified index.
     */
    public static Player getPlayerAt(int index) {
        return turnOrder[index];
    }

    /**
     * Retrieves the player whose turn it currently is.
     * 
     * @return The Player whose turn it is.
     */
    public static Player getPlayerAt() {
        return turnOrder[turnNumber];
    }

    /**
     * Retrieves the current round number.
     * 
     * @return The round number.
     */
    public static int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Retrieves the current turn number.
     * 
     * @return The turn number.
     */
    public static int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Retrieves the list of tasks available in the game.
     * 
     * @return An ArrayList of Task objects.
     */
    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static ArrayList<Objective> getObjectives() {
        return objectives;
    }

    /**
     * Moves the current player in the specified direction on the game board.
     * It updates the player's position, checks for movement restrictions, and
     * triggers any effects related to the occupied tile.
     *
     * @param direction The direction in which the player wishes to move.
     */
    public static void movePlayer(Direction direction) {
        // Retrieve the player whose turn it is
        Player currentPlayer = getPlayerAt();
        if (currentPlayer.getMovesLeft() > 0) {
            // Execute the move action within the board boundaries
            currentPlayer.moveAction(direction, gameBoard.boardSideLength);
        }

        // Update players on board and activate the tile they fell on
        gameBoard.renderPlayers(turnOrder);

        // Get the square the player has landed on, and activate it's effect (later
        // added using a button).
        Square sqrAtPosition = gameBoard.getSquareAt(currentPlayer.getCoord());
        sqrAtPosition.activateSquareEffect();
        
    }

    /**
     * Moves the turn to the next player.
     * If all players have taken their turn, the game progresses to the next round.
     */
    public static void nextTurn() {
        // If the last player in the turn order has finished their turn, reset to the
        // first player
        if (turnNumber >= turnOrder.length - 1) {
            turnNumber = 0; // Reset turn number to first player
            roundNumber++; // Start a new round
        } else {
            // Otherwise, move to the next player's turn
            turnNumber++;
        }
    }

    /**
     * Completes one step of the inputed Task object and awards the current player points before moving to
     * the next step
     * 
     * @param selectedTask - The task to progress
     */
    public static void progressTask(Task selectedTask) {
    	// Get current player
    	Player currentPlayer = getPlayerAt();
    	
    	// Check that the next step is able to be completed and task is not completed already
    	ResourceType resourceType = selectedTask.getResourceType();
    	SubTask currentStep = selectedTask.getCurrentSubTask();
    	boolean resourceCheck = currentPlayer.getResource(resourceType) >= currentStep.getResourceCost();
    	
    	if (!selectedTask.isCompleted() && resourceCheck) {
    		// Subtract from current player's resources
    		int newResourceAmount = currentPlayer.getResource(resourceType) - currentStep.getResourceCost();
    		currentPlayer.setResource(newResourceAmount, resourceType);
    		
    		// Update player score and global score
    		int scoreIncrease = currentStep.getCompletionScore();
    		currentPlayer.changeScoreBy(scoreIncrease);
    		currentTotalAwardedScore += scoreIncrease;
    	
    		// Progress the task onto the next subtask
    		selectedTask.completeStep();
    		
    		// If the task is now fully complete, award the task's completion score to the player
    		if(selectedTask.isCompleted()) {
    			currentPlayer.changeScoreBy(selectedTask.getCompletionScore());
    		}
    	}  	
    }
    
    
    /**
     * Subtracts funds equal to RESOURCE_PRICE from the current player's funds and gives them a set amount of 
     * resources of the type inputed in return.
     * Cannot purchase if the player doesn't have enough funds
     * 
     * @param resourceType - The type of resource being purchased
     * @return - True if there was enough money to purchase the resources, false if not
     */
    public static boolean purchaseResource(ResourceType resourceType) {
    	// Get current player
    	Player currentPlayer = getPlayerAt();
    	
    	// Check that player has enough funds to buy some resources (from RESOURCE_PRICE constant)
    	if (currentPlayer.getResource(resourceType) < RESOURCE_PRICE) {
    		return false;
    	}
    	
    	// Subtract funds from player and add resources
    	currentPlayer.changeMoney(-RESOURCE_PRICE);
    	currentPlayer.changeResource(25, resourceType);
    	
		return true;
	}
    
    /**
     * Calculates the implementation progress of the game and gives it as a fraction rounded to 3 decimal points.
     * (e.g. 0.762 = 76.2% completed)
     * 
     * @return a fraction rounded to 3dp showing the current completion progress of the game (e.g. 0.762 = 76.2% completed)
     */
    public static double getImplementationPercent() {
    	double percentUnrounded = currentTotalAwardedScore / maxScore;
    	return Math.round(percentUnrounded * 1000.0) / 1000.0;
    }
    
    /**
     * Evaluates whether all objectives have been completed, determining if the game
     * can progress.
     * 
     * @return true if all objectives have been completed; false otherwise.
     *         //
     */
    // private static boolean checkWinCondition() {
    // for (Objective objective : objectives) {
    // // If any task within the objectives is incomplete, return false
    // if (!objective.isCompleted()) {
    // return false;
    // }
    // }

    // // If all objectives are completed, return true
    // return true;
    // }

    /**
     * Ends the game and handles any necessary cleanup or victory conditions.
     * (Currently, no implementation provided.)
     */
    // public static void endGame() {
    // boolean win = checkWinCondition();

    private static boolean checkWinCondition() {
        for (Task task : tasks) {
            // If the task isn't completed return false
            if (!task.isCompleted())
                return false;
        }
        return true;
    }

    private static void nextStage() {

    }

    public static void endGame() {
    }

    public static void showPopup(String title, String desc, String yesButton, String noButton, ActionListener yesAction,
            ActionListener noAction) {
        gameBoardUI.showPopup(title, desc, yesButton, noButton, yesAction, noAction);
    }

    public static void hidePopup() {
        gameBoardUI.hidePopup();
    }

    public static void toggleJournal() {
        gameBoardUI.toggleJournal();
    }

    public static void toggleShop() {
        gameBoardUI.toggleShop();
    }

    /**
     * Temporarily generates Objectives, Tasks, and Subtasks.
     * This method is a placeholder until file reading from the Data Access Layer is
     * implemented.
     */
    private static void createData() {
        String file = "src/main/resources/tasks.json";
        try {
            String contents = new String((Files.readAllBytes(Paths.get(file))));
            JSONArray o = new JSONArray(contents);
            o.forEach((objective) -> {
                JSONObject obj = (JSONObject) objective;
                Objective o1 = new Objective(obj.getString("objective"));
                objectives.add(o1);

                obj.getJSONArray("tasks").forEach((t) -> {
                    JSONObject tObj = (JSONObject) t;
                    Task task = new Task();
                    task.setTitle(tObj.getString("task"));
                    tasks.add(task);
                    o1.addTask(task);
                });
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Calculates the total score of all tasks and subtasks in the game
     * Should be called ideally after data has already been initialised
     * 
     * @return The total score of all tasks and subtasks in the game
     */
    private static int calculateMaxScore() {
    	int scoreCalculation = 0;
    	
    	// Loop through all tasks and add their completion score to the calculation (including step scores)
    	for(Task currentTask : tasks) {
    		scoreCalculation += currentTask.getCompletionScore();
    		
    		// Loop through all of this task's steps and add their score to the calculation as well
    		for(SubTask currentSub : currentTask.getSteps()) {
    			scoreCalculation += currentSub.getCompletionScore();
    		}
    	}
    	
    	return scoreCalculation;
    	
    }

    /**
     * Entry point for the application. Initialises the game system.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        // Initialize the game system and set up necessary components
        initialise();
    }
}
