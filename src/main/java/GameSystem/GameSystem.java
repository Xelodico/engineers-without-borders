package GameSystem;

import java.util.ArrayList;

import BoardGame.*;
import square.*;

public abstract class GameSystem {
    // Attributes
    private static Board gameBoard;
    private static BoardGameUI gameBoardUI;
    private static Player[] turnOrder;
    private static int roundNumber;
    private static int turnNumber;
    private static int currentStage;

    private static ArrayList<JobRole> roles;
    private static ArrayList<Task> tasks;

    private static boolean gameActive = false;

    // Methods
    public static void initialise() {
        if (!gameActive) {
            currentStage = 0;
            turnNumber = 0;
            roundNumber = 0;

            roles = new ArrayList<JobRole>();
            tasks = new ArrayList<Task>();

            turnOrder = new Player[]{new Player()}; // Need to initialise with at least one player to start with
            
            gameBoard = new Board();
            gameBoardUI = new BoardGameUI(gameBoard);
            gameBoardUI.setTitle("Pavers Valley");
            
            gameBoardUI.setVisible(true);
            gameActive = true;
        }
    }

    public static void startGame() {
        roundNumber++;
        gameBoardUI.startGame();
        gameBoardUI.refresh();
    }

    public static int getCurrentStage() {
        return currentStage;
    }

    public static void setTurnOrder(Player[] players) {
        turnOrder = players;
    }

    public static void setRoundNumber(int number) {
        roundNumber = number;
    }

    public static void setTurnNumber(int turnNum) {
        turnNumber = turnNum;
    }

    public static Player[] getTurnOrder() {
        return turnOrder;
    }

    public static Player getPlayerAt(int index) {
        return turnOrder[index];
    }

    public static Player getPlayerAt() {
        return turnOrder[turnNumber];
    }

    public static int getRoundNumber() {
        return roundNumber;
    }

    public static int getTurnNumber() {
        return turnNumber;
    }

    public static ArrayList<Task> getTasks() {
        return tasks;
    }

    public static ArrayList<JobRole> getRoles() {
        return roles;
    }

    public static void movePlayer(Direction direction) {
        // Tell player to move (update coordinates)
        Player currentPlayer = getPlayerAt();
        Square previousSquare = gameBoard.getSquareAt(currentPlayer.getCoord());
        if (currentPlayer.getMovesLeft() > 0) {
            currentPlayer.moveAction(direction, gameBoard.boardSideLength);
        }

        // Once the Player has moved off the square, remove them as primary occupier of the square.
        previousSquare.setPrimaryOccupier(null);

        // Update players on board and activate the tile they fell on
        gameBoard.renderPlayers(turnOrder);

        // Get the square the player has landed on, and activate it's effect (later added using a button).
        Square sqrAtPosition = gameBoard.getSquareAt(currentPlayer.getCoord());
        sqrAtPosition.activateSquareEffect();
    }

    public static void nextTurn() {
        // Change turn & round number
        if (turnNumber >= turnOrder.length-1) {
            turnNumber = 0;
            nextRound();
        } else {
            turnNumber++;
        }
    }

    private static void nextRound() {
        // Update the round number
        roundNumber++;

        // If all players have completed their tasks, move to the next stage
        if (checkWinCondition()) {
            nextStage();
        }

    }

    private static boolean checkWinCondition() {
        // for (Task task : tasks) {
        //     // If the task isn't completed
        //     // and the task belongs to the current stage, return false
        //     if (!task.isCompleted() && task.getAvailableStage() == currentStage)
        //         return false;

        // }

        return true;
    }

    private static void nextStage() {

    }

    public static void endGame() {
    }

    public static void showPopup(String title, String desc, String yesButton, String noButton) {
        gameBoardUI.showPopup(title, desc, yesButton, noButton);
    }

    public static void main(String[] args) {
        initialise();
    }

}
