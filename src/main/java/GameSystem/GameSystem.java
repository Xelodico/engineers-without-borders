package GameSystem;

import java.awt.Color;
import java.awt.event.ActionListener;
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
import BoardGame.ResourceType;
import BoardGame.SubTask;
import BoardGame.Task;
import Popup.EndGame.Ending;
import square.MoneySquare;
import square.ShopSquare;
import square.Square;
import square.TaskSquare;

/**
 * 
 * The GameSystem class manages game mechanics such as player movement, turn
 * progression,
 * rounds, objectives and tasks/subtasks. It ensures smooth gameplay by updating
 * the board,
 * checking win conditions, and managing UI interactions.
 * 
 * @author Isaac Edmonds
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

    // Lists to store objectives and tasks for the game
    private static ArrayList<Objective> objectives;
    private static ArrayList<Task> tasks;

    // Boolean flag to determine whether the game is active or not
    private static boolean gameActive = false;

    // Global variables for resource purchase price and scores for calculating the
    // solution implementation percentage
    private static final int RESOURCE_PRICE = 20;
    private static final int RESOURCE_REWARD_AMOUNT = 30;
    private static final int MAINTENANCE_COST_EACH_ROUND = 5;
    private static int maxScore;
    private static int currentTotalAwardedScore;

    /**
     * Initialises the game by setting up essential components.
     * Ensures initialisation happens only once by checking {@code gameActive}
     */
    public static void initialise() {
        if (!gameActive) {
            turnNumber = 0; // Reset turn number
            roundNumber = 0; // Reset round count

            // Create new lists to store objectives and tasks
            objectives = new ArrayList<>();
            tasks = new ArrayList<>();

            // Setting up a default player array with at least one player to avoid errors
            turnOrder = new Player[] { new Player() };

            // Create data and update maxScore
            createData();
            maxScore = calculateMaxScore();

            // Create a new board and its associated GUI
            gameBoard = new Board(tasks);
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
        toggleTutorial(); // Start the tutorial
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
     * Sets the maximum possible score that can be achieved in the game.
     * 
     * @param score The maximum score limit to set.
     */
    public static void setMaxScore(int score) {
        maxScore = score;
    }

    /**
     * Updates the total awarded score currently accumulated by players.
     * This method is used to keep track of the total points earned
     * throughout the game.
     * 
     * @param score The current total awarded score to be updated.
     */
    public static void setCurrentTotalAwardedScore(int score) {
        currentTotalAwardedScore = score;
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

    /**
     * Retrieves the current game board instance for testing purposes.
     * This method provides access to the main board object, allowing
     * other components to reference and manipulate the game state.
     * 
     * @return The current Board object representing the game board.
     */
    public static Board getBoard() {
        return gameBoard;
    }

    /**
     * Moves the current player in the specified direction on the game board.
     * 
     * This method updates the player's position if they have remaining moves,
     * checks if they land on a special square (e.g., a shop), updates the game
     * board, and triggers the effect of the tile they land on.
     *
     * @param direction The direction in which the player wishes to move.
     */
    public static void movePlayer(Direction direction) {
        // Retrieve the player whose turn it is
        Player currentPlayer = getPlayerAt();

        // Ensure the player has remaining moves before proceeding
        if (currentPlayer.getMovesLeft() > 0) {
            // Execute the move action, ensuring it remains within board boundaries
            currentPlayer.moveAction(direction, gameBoard.boardSideLength);
        }

        // Check if the player has landed on a shop square and toggle shop button
        // visibility
        if (gameBoard.getSquareAt(currentPlayer.getCoord()) instanceof ShopSquare) {
            gameBoardUI.setShopButtonVisible(true); // Enable shop access
        } else {
            gameBoardUI.setShopButtonVisible(false); // Hide shop button
        }

        // Update the game board to reflect the new player position
        gameBoard.renderPlayers(turnOrder);

        // Retrieve the square the player has landed on
        Square sqrAtPosition = gameBoard.getSquareAt(currentPlayer.getCoord());

        // Activate the effect of the landed square
        sqrAtPosition.activateSquareEffect();
    }

    /**
     * ActionListener that handles the event when the player runs out of money.
     * This listener will hide the popup and end the game.
     */
    static ActionListener ranOutOfMoney = e -> {
        GameSystem.hidePopup();
        toggleEndGame(Ending.BAD);
    };

    /**
     * Moves the turn to the next player.
     * 
     * If all players have taken their turn, the game progresses to the next round.
     * At the start of a new round, each player pays a maintenance cost, and if a
     * player runs out of money, the game ends for all of them. The method also
     * checks if the next player lands on a shop square and updates the shop button
     * visibility accordingly.
     * 
     */
    public static void nextTurn() {
        // If the last player in the turn order has finished their turn, reset to the
        // first player
        if (turnNumber >= turnOrder.length - 1) {
            turnNumber = 0; // Reset turn number to first player
            roundNumber++; // Start a new round

            // Deduct maintenance cost from each player at the end of the round
            for (Player player : turnOrder) {
                player.changeMoney(-MAINTENANCE_COST_EACH_ROUND);

                // If a player runs out of money, trigger the game-ending popup
                if (player.getMoney() <= 0) {
                    showPopup("Game Finished!", player.getName() + " ran out of Money!", "End Game", null,
                            ranOutOfMoney, null);
                }
            }

            // Refresh game resources at the beginning of a new round
            refreshResources();
        } else {
            // Otherwise, move to the next player's turn
            turnNumber++;
        }

        // Check if the next player's current square is a shop and update shop button
        // visibility
        if (gameBoard.getSquareAt(getPlayerAt().getCoord()) instanceof ShopSquare) {
            gameBoardUI.setShopButtonVisible(true); // Enable shop access
        } else {
            gameBoardUI.setShopButtonVisible(false); // Hide shop button
        }
    }

    /**
     * Retrieves the designated spawn locations on the board.
     * 
     * These locations are used to determine where players or specific game
     * elements should be placed at the start of the game.
     * 
     * @return An array of integers representing spawn locations.
     */
    public static int[] getSpawnLocations() {
        return new int[] { 40 }; // Predefined spawn location on the board
    }

    /**
     * Progresses the selected task by completing one step and awarding points to
     * the current player.
     * 
     * This method checks if the player has enough resources to complete the current
     * step.
     * If the step is completed successfully, it deducts resources, updates the
     * player's score, and moves the task to the next subtask. If the task is fully
     * completed, additional completion points are awarded, and the corresponding
     * board square is updated.
     *
     * @param selectedTask The task to progress.
     * @return {@code true} if the task progresses successfully, {@code false} if
     *         resources are insufficient.
     */
    public static boolean progressTask(Task selectedTask) {
        // Get the current player whose turn it is
        Player currentPlayer = getPlayerAt();

        // Retrieve the required resource type and current step of the task
        ResourceType resourceType = selectedTask.getResourceType();
        SubTask currentStep = selectedTask.getCurrentSubTask();

        // Check if the player has enough resources to complete the step
        boolean resourceCheck = currentPlayer.getResource(resourceType) >= currentStep.getResourceCost();
        if (!resourceCheck) {
            return false; // Task progression fails due to insufficient resources
        }

        // Ensure the task is not already completed
        if (!selectedTask.isCompleted()) {
            // Deduct the required resources from the player's inventory
            int newResourceAmount = currentPlayer.getResource(resourceType) - currentStep.getResourceCost();
            currentPlayer.setResource(newResourceAmount, resourceType);

            // Award points to the player for completing the step
            int scoreIncrease = currentStep.getCompletionScore();
            currentPlayer.changeScoreBy(scoreIncrease);
            currentTotalAwardedScore += scoreIncrease;

            // Move the task to the next subtask
            selectedTask.completeStep();

            // If the task is now fully complete, award additional completion points
            if (selectedTask.isCompleted()) {
                currentPlayer.changeScoreBy(selectedTask.getCompletionScore());
                currentTotalAwardedScore += selectedTask.getCompletionScore();

                // Find the square containing the completed task and replace it with a normal
                // square
                final int[] squarePosition = new int[1];
                gameBoard.getSquareArray().stream()
                        .filter(square -> square instanceof TaskSquare
                                && ((TaskSquare) square).getTask() == selectedTask)
                        .forEach(square -> squarePosition[0] = gameBoard.getSquareArray().indexOf(square));

                // Update the board, removing the completed task
                gameBoard.setSquareAt(squarePosition[0], new Square());
            }
        }
        return true; // Task successfully progressed
    }

    /**
     * Attempts to purchase a specified resource for the current player.
     * 
     * This method deducts a fixed amount of money ({@code RESOURCE_PRICE})
     * from the player's funds and grants them a predefined amount of the
     * requested resource ({@code RESOURCE_REWARD_AMOUNT}).
     * If the player does not have enough funds, the purchase fails.
     *
     * @param resourceType The type of resource being purchased.
     * @return {@code true} if the purchase was successful, {@code false} if the
     *         player lacks funds.
     */
    public static boolean purchaseResource(ResourceType resourceType) {
        // Retrieve the player whose turn it is
        Player currentPlayer = getPlayerAt();

        // Ensure the player has enough funds to purchase the resource
        if (currentPlayer.getMoney() < RESOURCE_PRICE) {
            return false; // Purchase fails due to insufficient funds
        }

        // Deduct the resource cost from the player's balance
        currentPlayer.changeMoney(-RESOURCE_PRICE);

        // Grant the player the purchased resource
        currentPlayer.changeResource(RESOURCE_REWARD_AMOUNT, resourceType);

        // Track the total amount of money the player has spent
        currentPlayer.increaseMoneySpent(RESOURCE_PRICE);

        return true; // Purchase was successful
    }

    /**
     * Attempts to purchase a task using the specified resource type.
     * 
     * This method checks if the player has enough resources to acquire the task.
     * If successful, the required amount of resources is deducted from the player's
     * inventory, and ownership of the task is assigned to the player.
     *
     * @param player       The player attempting to purchase the task.
     * @param resourceType The type of resource being used for the purchase.
     * @param task         The task being acquired by the player.
     * @return {@code true} if the purchase was successful, {@code false} if the
     *         player lacks sufficient resources.
     */
    public static boolean purchaseTask(Player player, ResourceType resourceType, Task task) {
        // Retrieve the current player making the purchase
        Player currentPlayer = player;

        // Ensure the player has enough resources to afford the task
        if (currentPlayer.getResource(resourceType) < task.getResourceCost()) {
            return false; // Purchase fails due to insufficient resources
        }

        // Deduct the resource cost from the player's inventory
        currentPlayer.changeResource(-task.getResourceCost(), resourceType);

        // Assign ownership of the task to the player
        task.setOwnedBy(player);

        return true; // Purchase was successful
    }

    /**
     * Retrieves the fixed price required to purchase a resource.
     * 
     * @return The cost of a resource in game currency.
     */
    public static int getResourcePrice() {
        return RESOURCE_PRICE;
    }

    /**
     * Retrieves the amount of resources awarded when a player purchases them.
     * 
     * @return The number of resource units given per purchase.
     */
    public static int getResourceAwardedAmount() {
        return RESOURCE_REWARD_AMOUNT;
    }

    /**
     * Allows a player to assist another player by discounting the cost of a
     * subtask.
     * 
     * This method enables cooperative gameplay by letting players use their
     * resources to partially fund a subtask for another player. The assisting
     * player spends half the original resource cost, gains half of the completion
     * score as a reward, and increases their "times helped" counter.
     * 
     * If the subtask is valid and the assisting player has sufficient resources,
     * the subtask is marked as discounted.
     * </p>
     * 
     * @param taskToDiscount The task whose current subtask is being discounted.
     * @return {@code true} if the discount was successfully applied, {@code false}
     *         if the discount was not possible.
     */
    public static boolean discountSubTask(Task taskToDiscount) {
        // Retrieve the current subtask of the specified task
        SubTask currentSubTask = taskToDiscount.getCurrentSubTask();

        // Check if the subtask is valid for a discount (must have a title)
        if (currentSubTask.getTitle().equals("")) {
            return false; // Subtask is invalid for discounting
        }

        // Get the assisting player
        Player currentPlayer = getPlayerAt();

        // Retrieve the player's available resource amount for the required type
        int playerResourceAmount = currentPlayer.getResource(currentSubTask.getResourceType());

        // Ensure the player has enough resources to apply the discount
        if (playerResourceAmount < currentSubTask.getResourceCost()) {
            return false; // Not enough resources to assist with the task
        } else {
            // Apply the discount by reducing the player's resource count by half the
            // original cost
            currentPlayer.changeResource(-currentSubTask.getResourceCost() / 2, currentSubTask.getResourceType());

            // Award the assisting player half of the original completion score
            int scoreIncrease = currentSubTask.getCompletionScore() / 2;
            currentPlayer.changeScoreBy(scoreIncrease);
            currentTotalAwardedScore += scoreIncrease;

            // Increment the assisting player's "times helped" counter
            currentPlayer.changeTimesHelped(1);

            // Mark the subtask as discounted
            currentSubTask.discountSubTask();
        }

        return true; // Discount successfully applied
    }

    /**
     * Calculates the implementation progress of the game and gives it as a fraction
     * rounded to 3 decimal points.
     * (e.g. 0.762 = 76.2% completed)
     * 
     * @return a fraction rounded to 3dp showing the current completion progress of
     *         the game (e.g. 0.762 = 76.2% completed)
     */
    public static double getImplementationPercent() {
        double percentUnrounded = (double) currentTotalAwardedScore / maxScore;
        return Math.round(percentUnrounded * 1000.0) / 1000.0;
    }

    /**
     * Evaluates whether all objectives have been completed, determining if the game
     * can progress.
     * 
     * @return true if all objectives have been completed; false otherwise.
     * 
     */
    public static boolean checkWinCondition() {
        for (Objective objective : objectives) {
            // If any task within the objectives is incomplete, return false
            if (!objective.isCompleted()) {
                return false;
            }
        }

        // If all objectives are completed, return true
        return true;
    }

    /**
     * Displays a customisable popup message to the player.
     * 
     * This method triggers a popup window with a title, description, and optional
     * "Yes" and "No" buttons. The buttons can have custom text and associated
     * actions, allowing for interactive choices within the game.
     *
     * @param title     The title of the popup window.
     * @param desc      The message or description displayed in the popup.
     * @param yesButton The text for the "Yes" button (can be {@code null} if not
     *                  needed).
     * @param noButton  The text for the "No" button (can be {@code null} if not
     *                  needed).
     * @param yesAction The action triggered when the "Yes" button is clicked (can
     *                  be {@code null}).
     * @param noAction  The action triggered when the "No" button is clicked (can be
     *                  {@code null}).
     */
    public static void showPopup(String title, String desc, String yesButton, String noButton, ActionListener yesAction,
            ActionListener noAction) {
        // Delegate the popup creation to the game UI
        gameBoardUI.showPopup(title, desc, yesButton, noButton, yesAction, noAction);
    }

    /**
     * Hides the currently displayed popup window in the game UI.
     * 
     * This method ensures that any active popups are closed when they
     * are no longer needed, improving the user experience.
     */
    public static void hidePopup() {
        gameBoardUI.hidePopup();
    }

    /**
     * Displays a cost-related popup message to the player.
     * 
     * This method triggers a popup that presents a cost decision to the player,
     * displaying a title, description, required resource type, and cost amount.
     * The popup includes optional "Yes" and "No" buttons with customisable actions.
     *
     * @param title     The title of the popup window.
     * @param desc      The message or description displayed in the popup.
     * @param currency  The type of resource being used as currency for the cost.
     * @param cost      The amount of the resource required.
     * @param yesAction The action triggered when the "Yes" button is clicked (can
     *                  be {@code null}).
     * @param noAction  The action triggered when the "No" button is clicked (can be
     *                  {@code null}).
     */
    public static void showCostPopup(String title, String desc, ResourceType currency, int cost,
            ActionListener yesAction, ActionListener noAction) {
        // Delegate the popup creation to the game UI
        gameBoardUI.showCostPopup(title, desc, currency, cost, yesAction, noAction);
    }

    /**
     * Hides the currently displayed cost popup.
     * This method closes the cost-related popup if it is currently visible.
     */
    public static void hideCostPopup() {
        // Delegate the hide action to the game UI
        gameBoardUI.hideCostPopup();
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
    public static void toggleTutorial() {
        gameBoardUI.toggleTutorial();
    }

    /**
     * Toggles the transfer interface for the given task.
     * This method interacts with the game UI to enable or disable the
     * task transfer feature, allowing players to manage their task assignments.
     * 
     * @param task The task object that is being transferred.
     */
    public static void toggleTransfer(Task task) {
        gameBoardUI.toggleTransfer(task);
    }

    /**
     * Refreshes the in-game journal UI.
     * 
     * This method updates the journal interface, ensuring that the latest
     * information is displayed to the player.
     */
    public static void refreshJournal() {
        gameBoardUI.refreshJournal();
    }

    /**
     * Triggers the end-game sequence based on the game's outcome.
     * 
     * This method determines whether the game ends with a success or failure
     * and updates the UI accordingly.
     * 
     * @param ending The final game outcome (e.g., GOOD or BAD ending).
     */
    public static void toggleEndGame(Ending ending) {
        gameBoardUI.toggleEndGame(ending);
    }

    /**
     * Loads and generates Objectives, Tasks, and Subtasks from a JSON file.
     * 
     * This method reads task-related data from a JSON file and dynamically
     * creates Objective, Task, and SubTask objects. Each task is assigned a
     * resource type based on its corresponding objective.
     */
    private static void createData() {
        String file = "src/main/resources/tasks.json";
        try {
            // Read JSON file contents into a string
            String contents = new String((Files.readAllBytes(Paths.get(file))));
            JSONArray o = new JSONArray(contents);

            // Iterate through each objective in the JSON array
            o.forEach((objective) -> {
                JSONObject obj = (JSONObject) objective;
                Objective o1 = new Objective(obj.getString("objective"));
                objectives.add(o1);

                // Iterate through tasks within the objective
                obj.getJSONArray("tasks").forEach((t) -> {
                    JSONObject tObj = (JSONObject) t;
                    Task task = new Task();
                    task.setTitle(tObj.getString("task"));
                    tasks.add(task);

                    // Assign subtasks to the task
                    JSONArray subtasksArr = tObj.getJSONArray("subtasks");
                    for (int i = 0; i < subtasksArr.length(); i++) {
                        SubTask subtask = new SubTask();
                        subtask.setTitle(subtasksArr.getString(i));
                        task.setBelongsTo(o1);

                        // Assign resource type based on objective title
                        switch (o1.getTitle()) {
                            case "Repair Potholes (Cold Asphalt)":
                                task.setResourceType(ResourceType.ASPHALT);
                                subtask.setResourceType(ResourceType.ASPHALT);
                                break;
                            case "Secure Grant (Influence)":
                                task.setResourceType(ResourceType.INFLUENCE);
                                subtask.setResourceType(ResourceType.INFLUENCE);
                                break;
                            case "Train Volunteers (Teaching Material)":
                                task.setResourceType(ResourceType.KNOWLEDGE);
                                subtask.setResourceType(ResourceType.KNOWLEDGE);
                                break;
                            case "Secure Longevity (Volunteers)":
                                task.setResourceType(ResourceType.VOLUNTEERS);
                                subtask.setResourceType(ResourceType.VOLUNTEERS);
                                break;
                        }

                        task.addStep(subtask);
                    }
                    o1.addTask(task);
                });
            });

        } catch (IOException | NullPointerException e) {
            // Handle file read errors and unexpected null references
            System.err.println("Error reading file: " + file);
            System.exit(1);
        }

        // Assign UI colors to the first four objectives to enable colour-coded board
        // squares
        objectives.get(0).setUiColour(Color.BLUE);
        objectives.get(1).setUiColour(Color.RED);
        objectives.get(2).setUiColour(Color.MAGENTA);
        objectives.get(3).setUiColour(Color.CYAN);
    }

    /**
     * Calculates the total maximum score achievable in the game.
     * 
     * This method iterates through all tasks and their corresponding subtasks,
     * summing up their completion scores to determine the highest possible score.
     * It should be called after all game data has been initialised to ensure
     * accurate calculations.
     * 
     * @return The total maximum score of all tasks and subtasks in the game.
     */
    private static int calculateMaxScore() {
        int scoreCalculation = 0;

        // Iterate through all tasks and accumulate their completion scores
        for (Task currentTask : tasks) {
            scoreCalculation += currentTask.getCompletionScore();

            // Include the completion scores of all subtasks within the task
            for (SubTask currentSub : currentTask.getSteps()) {
                scoreCalculation += currentSub.getCompletionScore();
            }
        }
        return scoreCalculation;
    }

    /**
     * Handles the transition after a player lands on a MoneySquare and claims its
     * reward.
     * 
     * This method generates a new MoneySquare at a random location on the board
     * while converting the current MoneySquare (where the player landed) into a
     * generic square after the money has been claimed. This ensures dynamic
     * gameplay by keeping MoneySquares available in different locations.
     */
    public static void replaceMoneySquare() {
        // Generate one new MoneySquare at a random location on the board
        gameBoard.generateNewSquares(1, new MoneySquare());

        // Replace the MoneySquare the player landed on with a standard generic square
        gameBoard.setSquareAt(getPlayerAt().getCoord(), new Square());
    }

    /**
     * Resets the game state, preparing it for a fresh start.
     * 
     * This method is used both for restarting the game and during testing.
     * It clears all game data, resets key attributes, and disposes of
     * the current UI to allow for a complete reset.
     * 
     * This ensures that the game can be restarted properly without
     * retaining previous session data or inconsistencies.
     */
    public static void reset() {
        gameActive = false; // Mark the game as inactive
        turnNumber = 0; // Reset turn tracking
        roundNumber = 0; // Reset round tracking
        currentTotalAwardedScore = 0; // Reset total awarded score

        // Reset lists and objects related to game objectives and tasks
        objectives = new ArrayList<>();
        tasks = new ArrayList<>();

        // Dispose of the current game UI to ensure a clean restart
        if (gameBoardUI != null) {
            gameBoardUI.dispose();
        }

        // Clear turn order and UI components
        turnOrder = null;
        gameBoard = null;
        gameBoardUI = null;
    }

    /**
     * Updates the resource values displayed in the game UI.
     * 
     * This method ensures that the player's resource values are updated
     * dynamically in the UI to reflect any changes in resource amounts.
     * It should be called whenever resources are modified.
     * 
     */
    public static void refreshResources() {
        // Update the displayed resource values in the game UI
        gameBoardUI.setResourceValues();
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
