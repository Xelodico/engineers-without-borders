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

    private static ArrayList<Objective> objectives;
    private static ArrayList<Task> tasks;

    // Boolean flag to determine whether the game is active or not
    private static boolean gameActive = false;

    // Global variables for resource purchase price and scores for calculating the
    // solution implementation percentage
    private final static int RESOURCE_PRICE = 20;
    private final static int resourceRewardAmount = 30;
    private final static int maintenanceCostEachRound = 5;
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
        toggleTutorial();
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

        if (gameBoard.getSquareAt(currentPlayer.getCoord()) instanceof ShopSquare) {
            gameBoardUI.setShopButtonVisible(true);
        } else {
            gameBoardUI.setShopButtonVisible(false);
        }

        // Update players on board and activate the tile they fell on
        gameBoard.renderPlayers(turnOrder);

        // Get the square the player has landed on, and activate it's effect (later
        // added using a button).
        Square sqrAtPosition = gameBoard.getSquareAt(currentPlayer.getCoord());
        sqrAtPosition.activateSquareEffect();

    }

    /**
     * ActionListener that handles the event when the player runs out of money.
     * This listener will hide the popup and end the game.
     */
    static ActionListener ranOutOfMoney = new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GameSystem.hidePopup();
                endGame();
            }
    };
    
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
    
                // Deduct money from each player at the end of the round
                for(Player player : turnOrder){
                    player.changeMoney(-maintenanceCostEachRound);
                    if(player.getMoney() <= 0){
                        showPopup("Game Finished!", getPlayerAt().getName() + " ran out of Money!", "End Game", null, ranOutOfMoney, null);
                }
            }
            refreshResources();
        } else {
            // Otherwise, move to the next player's turn
            turnNumber++;
        }

        if (gameBoard.getSquareAt(getPlayerAt().getCoord()) instanceof ShopSquare) {
            gameBoardUI.setShopButtonVisible(true);
        } else {
            gameBoardUI.setShopButtonVisible(false);
        }

    }

    public static int[] getSpawnLocations() {
        return new int[] { 40 };
    }

    /**
     * Completes one step of the selected Task object and awards the current player
     * points before moving to
     * the next step
     * 
     * @param selectedTask - The task to progress
     */
    public static boolean progressTask(Task selectedTask) {
        // Get current player
        Player currentPlayer = getPlayerAt();

        // Check that the next step is able to be completed and task is not completed
        // already
        ResourceType resourceType = selectedTask.getResourceType();
        SubTask currentStep = selectedTask.getCurrentSubTask();
        boolean resourceCheck = currentPlayer.getResource(resourceType) >= currentStep.getResourceCost();

        if(!resourceCheck) {
            return false;
        }

        if (!selectedTask.isCompleted()) {
            // Subtract from current player's resources
            int newResourceAmount = currentPlayer.getResource(resourceType) - currentStep.getResourceCost();
            currentPlayer.setResource(newResourceAmount, resourceType);

            // Update player score and global score
            int scoreIncrease = currentStep.getCompletionScore();
            currentPlayer.changeScoreBy(scoreIncrease);
            currentTotalAwardedScore += scoreIncrease;

            // Progress the task onto the next subtask
            selectedTask.completeStep();
            

            // If the task is now fully complete, award the task's completion score to the
            // player
            if (selectedTask.isCompleted()) {
                currentPlayer.changeScoreBy(selectedTask.getCompletionScore());
                currentTotalAwardedScore += selectedTask.getCompletionScore();

                // Update the UI to reflect the task's progress
                final int[] squarePosition = new int[1];
                gameBoard.getSquareArray().stream().filter(square -> square instanceof TaskSquare && ((TaskSquare) square).getTask() == selectedTask)
                        .forEach(square -> squarePosition[0] = gameBoard.getSquareArray().indexOf(square));
                gameBoard.setSquareAt(squarePosition[0], new Square());
            }
        }
        return true;
    }

    /**
     * Subtracts funds equal to RESOURCE_PRICE from the current player's funds and
     * gives them a set amount of
     * resources of the type inputed in return.
     * Cannot purchase if the player doesn't have enough funds
     * 
     * @param resourceType - The type of resource being purchased
     * @return - True if there was enough money to purchase the resources, false if
     *         not
     */
    public static boolean purchaseResource(ResourceType resourceType) {
        // Get current player
        Player currentPlayer = getPlayerAt();

        // Check that player has enough funds to buy some resources (from RESOURCE_PRICE
        // constant)
        if (currentPlayer.getMoney() < RESOURCE_PRICE) {
            return false;
        }

        // Subtract funds from player and add resources
        currentPlayer.changeMoney(-RESOURCE_PRICE);
        currentPlayer.changeResource(resourceRewardAmount, resourceType);

        return true;
    }

    public static boolean purchaseTask(Player player, ResourceType resourceType, Task task) {
        // Get current player
        Player currentPlayer = player;

        // Check that player has enough resources to buy the task
        if (currentPlayer.getResource(resourceType) < task.getResourceCost()) {
            return false;
        }

        // Subtract resources from player and add task
        currentPlayer.changeResource(-task.getResourceCost(), resourceType);
        task.setOwnedBy(player);

        return true;
    }

    public static int getResourcePrice() {
        return RESOURCE_PRICE;
    }

    public static int getResourceAwardedAmount() {
        return resourceRewardAmount;
    }

    public static boolean discountSubTask(Task taskToDiscount){
        SubTask currentSubTask = taskToDiscount.getCurrentSubTask();
        System.out.println(currentSubTask.getResourceCost());
        if (currentSubTask.getTitle().equals("")){
            return false;
        }
    
        Player currentPlayer = getPlayerAt();
        int playerResourceAmount = currentPlayer.getResource(currentSubTask.getResourceType());
        if (playerResourceAmount < currentSubTask.getResourceCost()){
            // You do not have enough money to discount this task
            return false;
        } else {
            // Continue with discounting the task
            currentPlayer.changeResource(currentSubTask.getResourceCost() / 2, currentSubTask.getResourceType());
            currentPlayer.changeScoreBy(currentSubTask.getCompletionScore() / 2);
            
            if(!currentSubTask.discountSubTask()){
                return false;
            }
        }

        System.out.println(currentSubTask.getResourceCost());
        return true;
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
        System.out.println("Current Awarded Score total is " + currentTotalAwardedScore);
        System.out.println();
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
     * TODO: Remove method once achievements are implemented.
     */
    public static void endGame() {
        // TODO: Remove placeholder players
        Player player1 = new Player("Kal", 0, 100, 4);
        player1.changeMoney(5000);
        player1.increaseMoneySpent(1000);
        player1.setScore(100);
        Player player2 = new Player("Nathan", 0, 120, 2);
        player2.changeMoney(1000);
        player2.increaseMoneySpent(500);
        player2.setScore(500);
        turnOrder = new Player[] { player1, player2 };

        // Display player scores
        System.out.println("Final Scores:");

        // Stores the indices of players who achieved notable milestones:
        // - Highest Scorer: The player with the most points.
        // - Lowest Spender: The player who spent the least amount of money.
        // - Traveller: The player who moved the most across the board.
        // - Resource Hogger: The player who collected the most resources.
        // TODO: Consider adding a "Team Player" achievement for the most contributive
        // player.
        int highestScorer = 0, lowestSpender = 0, traveller = 0, resourceHogger = 0;
        int maxScore = 0, minSpent = Integer.MAX_VALUE, maxResources = 0, maxTravelled = 0;

        // Iterate through each player to determine the top achievers
        // TODO: Update to reflect changes to resources in Player class
        for (int i = 0; i < turnOrder.length; i++) {
            // Determine the player with the highest score
            if (turnOrder[i].getScore() > maxScore) {
                highestScorer = i;
                maxScore = turnOrder[i].getScore();
            }

            // Determine the player who spent the least money
            if (turnOrder[i].getMoneySpent() < minSpent) {
                lowestSpender = i;
                minSpent = turnOrder[i].getMoneySpent();
            }

            // Determine the player who accumulated the most resources
            // if (turnOrder[i].getResources() > maxResources) {
            // resourceHogger = i;
            // maxResources = turnOrder[i].getResources();
            // }

            // Determine the player who travelled the most
            if (turnOrder[i].getMovesTravelled() > maxTravelled) {
                traveller = i;
                maxTravelled = turnOrder[i].getMovesTravelled();
            }

            // Print player stats
            System.out.println(turnOrder[i].getName() + ":\n" +
                    "Score: " + turnOrder[i].getScore() + "\n" +
                    // "Resources: " + turnOrder[i].getResources() + "\n" +
                    "Money: " + turnOrder[i].getMoney() + "\n");
        }

        // Display Player Achievements
        System.out.println("Special Achievements!");
        System.out.println("Highest Scorer: " + getPlayerAt(highestScorer).getName() +
                " (" + getPlayerAt(highestScorer).getScore() + " points)");
        System.out.println("The Cheapskate: " + getPlayerAt(lowestSpender).getName() +
                " (Spent " + getPlayerAt(lowestSpender).getMoneySpent() + " rand)");
        // System.out.println("The Resource Hogger: " +
        // getPlayerAt(resourceHogger).getName() +
        // " (Kept " + getPlayerAt(resourceHogger).getResources() + " bags of cold
        // asphalt)");
        System.out.println("The Traveller: " + getPlayerAt(traveller).getName() +
                " (Travelled " + getPlayerAt(traveller).getMovesTravelled() + " squares)");

        // showPopup("Thanks for playing Pavers Valley!", "Start a new game?", "Yes",
        // "No");

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

    public static void showCostPopup(String title, String desc, String currency, int cost, ActionListener yesAction,
            ActionListener noAction) {
        gameBoardUI.showCostPopup(title, desc, currency, cost, yesAction, noAction);
    }

    public static void hideCostPopup() {
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

    public static void refreshJournal() {
        gameBoardUI.refreshJournal();
    }

    public static void toggleEndGame(Ending ending) {
        gameBoardUI.toggleEndGame(ending);
    }

    /**
     * Generates Objectives, Tasks, and Subtasks.
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

                    JSONArray subtasksArr = tObj.getJSONArray("subtasks");
                    for (int i = 0; i < subtasksArr.length(); i++) {
                        SubTask subtask = new SubTask();
                        subtask.setTitle(subtasksArr.getString(i));
                        task.setBelongsTo(o1);
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
            System.err.println("Error reading file: " + file);
            System.exit(1);
        }

        objectives.get(0).setUiColour(Color.BLUE);
        objectives.get(1).setUiColour(Color.RED);
        objectives.get(2).setUiColour(Color.MAGENTA);
        objectives.get(3).setUiColour(Color.CYAN);

    }

    /**
     * Calculates the total score of all tasks and subtasks in the game
     * Should be called ideally after data has already been initialised
     * 
     * @return The total score of all tasks and subtasks in the game
     */
    private static int calculateMaxScore() {
        int scoreCalculation = 0;

        // Loop through all tasks and add their completion score to the calculation
        // (including step scores)
        for (Task currentTask : tasks) {
            scoreCalculation += currentTask.getCompletionScore();

            // Loop through all of this task's steps and add their score to the calculation
            // as well
            for (SubTask currentSub : currentTask.getSteps()) {
                scoreCalculation += currentSub.getCompletionScore();
            }
        }
        System.out.println(scoreCalculation);
        return scoreCalculation;

    }

    /**
     * Replaces the current square of the player with a new generic square and generates a new MoneySquare
     * at a random location on the game board.
     */
    public static void replaceMoneySquare() {
        gameBoard.generateNewSquares(1, new MoneySquare());
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

    public static void refreshResources() {
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
