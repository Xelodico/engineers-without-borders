package GameSystem;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import BoardGame.Board;
import BoardGame.BoardGameUI;
import BoardGame.Direction;
import BoardGame.Objective;
import BoardGame.Player;
import BoardGame.Task;
import square.Square;

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

    private static ArrayList<Objective> objectives;
    private static ArrayList<Task> tasks;

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

            objectives = new ArrayList<Objective>();
            tasks = new ArrayList<Task>();

            // Setting up a default player array with at least one player to avoid errors
            turnOrder = new Player[] { new Player() };
            createData();

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
    public static ArrayList<Objective> getObjectives() {
        return objectives;
    }

    public static Board getBoard() {
        return gameBoard;
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
     * Displays a popup message to the player with customizable buttons.
     *
     * @param title     The title of the popup window.
     * @param desc      The description or message to display in the popup.
     * @param yesButton The text for the "Yes" button.
     * @param noButton  The text for the "No" button.
     */
    public static void showPopup(String title, String desc, String yesButton, String noButton, ActionListener yesAction,
            ActionListener noAction) {
        gameBoardUI.showPopup(title, desc, yesButton, noButton, yesAction, noAction);
    }

    /**
     * Hides the currently displayed popup window in the game UI.
     * This method ensures that any active popups are closed when they
     * are no longer needed, improving the user experience.
     */
    public static void hidePopup() {
        gameBoardUI.hidePopup();
    }

    /**
     * Toggles the visibility of the in-game journal.
     * The journal contains information about the players' objectives.
     */
    public static void toggleJournal() {
        gameBoardUI.toggleJournal();
    }

    /**
     * Toggles the in-game shop interface.
     * This allows players to access in-game purchases.
     */
    public static void toggleShop() {
        gameBoardUI.toggleShop();
    }

    /**
     * Starts or toggles the tutorial mode for the game.
     * This guides new players through key mechanics and gameplay features.
     */
    public static void playTutorial() {
        gameBoardUI.toggleTutorial();
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
     * Resets the game state for testing purposes.
     * This method clears all game data and resets key attributes,
     * ensuring a fresh start for unit tests or debugging sessions.
     * 
     * NOTE: This is a temporary method intended for development use only.
     * It should not be included in the final production version of the game.
     */
    public static void resetForTests() {
        gameActive = false; // Mark the game as inactive
        turnNumber = 0; // Reset turn tracking
        roundNumber = 0; // Reset round tracking

        // Reset lists and objects related to game objectives and tasks
        objectives = new ArrayList<>();
        tasks = new ArrayList<>();

        // Clear turn order and UI components
        turnOrder = null;
        gameBoard = null;
        gameBoardUI = null;
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
