package GameSystem;

import java.util.ArrayList;
import java.io.File; // File class for reading files
import java.util.Scanner; // Scanner for reading files
import java.io.FileNotFoundException; // Exception for file not found
import java.util.concurrent.TimeUnit; // TimeUnit for pausing epilogue output

import BoardGame.*;

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

    // List of objectives available in the game
    // private static ArrayList<Objective> objectives;

    // List of tasks and subtasks to be completed within the game, linked to
    // objectives
    private static ArrayList<Task> tasks;
    private static String[] subtasks;

    // Boolean flag to determine whether the game is active or not
    private static boolean gameActive = false;

    /**
     * Initialises the game by setting up essential components.
     * Ensures initialisation happens only once by checking `gameActive`.
     */
    public static void initialise() {
        if (!gameActive) {
            turnNumber = 0; // Reset turn number
            roundNumber = 0; // Reset round count

            // objectives = new ArrayList<Objective>(); // Initialize the list of job roles
            tasks = new ArrayList<Task>(); // Initialize the list of tasks

            // Setting up a default player array with at least one player to avoid errors
            turnOrder = new Player[] { new Player() };

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

    /**
     * Retrieves the list of objectives available in the game.
     * 
     * @return An ArrayList of Objective objects.
     */
    // public static ArrayList<Objective> getObjectives() {
    // return objectives;
    // }

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

        // Ensure the player has remaining moves before proceeding
        if (currentPlayer.getMovesLeft() > 0) {
            // Execute the move action within the board boundaries
            currentPlayer.moveAction(direction, gameBoard.boardSideLength);
        }

        // Update the game board to reflect the new player positions
        gameBoard.renderPlayers(turnOrder);

        // Handle interactions when a player lands on a new square
        // Square sqrAtPosition = gameBoard.getSquareAt(currentPlayer.getCoord());

        // Check if another player is already occupying the square
        // if (sqrAtPosition.getPrimaryOccupier() != currentPlayer) {
        // // Apply special effects for multiple players on the same square
        // sqrAtPosition.alreadyOccupiedEffect(currentPlayer);
        // } else {
        // // Activate the square’s default effect
        // sqrAtPosition.activateSquareEffect();
        // }

        // TODO: Implement logic to verify if the task on the square relates to the
        // player's objective.
    }

    /**
     * Moves the turn to the next player.
     * If all players have taken their turn, the game progresses to the next round.
     */
    public static void nextTurn() {
        // Check if the player has completed all objectives
        // if (checkWinCondition()) {
        // endGame();
        // return;
        // }

        // If the last player in the turn order has finished their turn, reset to the
        // first player
        if (turnNumber >= turnOrder.length - 1) {
            turnNumber = 0; // Reset turn number to first player
            roundNumber++; // Start a new round

            // TODO: Implement logic to handle end of round events
        } else {
            // Otherwise, move to the next player's turn
            turnNumber++;
        }
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
     * This method determines whether the player has won or lost, displays an
     * epilogue based on the outcome, and prompts the player to start a new game.
     */
    public static void endGame() {
        // TODO: Replace placeholder value with checkWinCondition() when ready
        boolean win = false;

        // Declare message and file path for epilogue event storage
        String winMessage;
        String filePath;

        // Determine the appropriate ending based on game outcome
        if (win) {
            winMessage = "Congratulations! You have completed all objectives!";
            System.out.println(winMessage);
            filePath = "src\\main\\resources\\data\\goodEndingEvents.txt";
        } else {
            winMessage = "You have failed to complete all objectives.";
            System.out.println(winMessage);
            filePath = "src\\main\\resources\\data\\badEndingEvents.txt";
        }

        // Display epilogue to the player
        System.out.println("\nThe path ahead...");
        File file = new File(filePath);
        int year = 2025; // Starting year for event projections

        try (Scanner fileReader = new Scanner(file)) {
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                if (data.isEmpty())
                    continue;
                System.out.println("By " + year + "...\n" + data);
                year++;

                // Introduce a brief pause for readability
                // This also fulfils the requirement for a pause between events
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    // Handle thread interruption exception
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException exception) {
            // Handle the case where the epilogue file is missing
            System.err.print("File not found.");
        }

        // TODO: Implement final game logic, such as displaying scores and resetting the
        // game state
        showPopup("Thanks for playing Pavers Valley!", "Start a new game?", "Yes",
        "No");

        // Highest score, spent most money, travelled the most


        // Terminate the game process
        System.exit(0);
    }

    /**
     * Displays a popup message to the player with customizable buttons.
     *
     * @param title     The title of the popup window.
     * @param desc      The description or message to display in the popup.
     * @param yesButton The text for the "Yes" button.
     * @param noButton  The text for the "No" button.
     */
    public static void showPopup(String title, String desc, String yesButton, String noButton) {
        // Delegate to the UI to display a popup window with the provided details
        gameBoardUI.showPopup(title, desc, yesButton, noButton);
    }

    /**
     * Temporarily generates Objectives, Tasks, and Subtasks.
     * This method is a placeholder until file reading from the Data Access Layer is
     * implemented.
     */
    private static void createData() {
        // TODO: Implement actual data creation logic via file reading
    }

    /**
     * Entry point for the application. Initialises the game system.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        // Initialize the game system and set up necessary components
        initialise();
        endGame();
    }
}
