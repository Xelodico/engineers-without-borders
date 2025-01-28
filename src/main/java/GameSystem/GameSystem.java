package GameSystem;

import java.util.InputMismatchException;
import java.util.ArrayList;

import BoardGame.*;

import java.util.Scanner;

public abstract class GameSystem {
    // Attributes
    private static Scanner input;

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
            input = new Scanner(System.in);
            currentStage = 0;
            turnNumber = 0;
            roundNumber = 0;

            roles = new ArrayList<JobRole>();
            tasks = new ArrayList<Task>();

            // TODO: Worry about this later
            enterPlayers();
            startGame();

            gameBoard = new Board(turnOrder);
            gameBoardUI = new BoardGameUI(gameBoard, turnOrder);

            gameBoardUI.setVisible(true);
            gameActive = true;
        }
    }

    private static void startGame() {
        // TODO: Integrate into UI
        System.out.println("Press Enter to start new game");
        input.nextLine();

        roundNumber++;
        nextTurn();

    }

    private static void enterPlayers() {
        // TODO: Integrate into UI
        boolean isValid = false;
        int playerNum = 0;
        // Check if the number entered is a valid number (No characters; Between 1-4)
        while (!isValid){
            System.out.print("Please enter the number of players (1-4): ");
            try {
                playerNum = input.nextInt();
                input.nextLine();
                
                if (playerNum <= 0 || playerNum > 4){
                    System.err.print("ERROR: Outside the valid range (1-4). Try again.");
                    continue;
                }

                isValid = true;
            } catch (InputMismatchException e) {
                System.err.print("ERROR: Invalid number. Please try again.");
            }
        }

        turnOrder = new Player[playerNum];

        for (int i = 0; i < turnOrder.length; i++) {
            isValid = false;
            // Check if the player name is valid, otherwise catch an InputMismatchException
            while (!isValid){
                System.out.print("Please enter the name of Player " + (i + 1) + ": ");
                try {
                    // TODO: Check if name is not empty
                    String name = input.nextLine();
                    turnOrder[i].setName(name);
                    isValid = true;
                } catch (InputMismatchException e) {
                    System.err.print("ERROR: Invalid name. Please try again.");
                }
            }
            
        }
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

    public static ArrayList<JobRoles> getRoles() {
        return roles;
    }

    public static void movePlayer(Direction direction) {
        // Tell player to move (update coordinates)
        Player currentPlayer = getPlayerAt();
        currentPlayer.rollDie();
        if (currentPlayer.getMovesLeft() > 0) {
            currentPlayer.moveAction(direction);
        }

        // Update players on board and activate the tile they fell on
        gameBoard.refresh();

        Square sqrAtPosition = gameBoard.getSquareAt(currentPlayer.getCoord());
        if (sqrAtPosition.getPrimaryOccupier != currentPlayer) { // If another player is already on the square
            sqrAtPosition.alreadyOccupiedEffect(currentPlayer); // Activate the specific effect for multiple players
        } else {
            sqrAtPosition.activateSquareEffect();
        }

        // TODO: Check if the player's role matches the tasks square

    }

    private static void nextTurn() {
        // Change turn & round number
        if (turnNumber >= turnOrder.length) {
            turnNumber = 0;
            nextRound();
        } else {
            turnNumber++;
        }
    }

    private static void nextRound() {
        // TODO: Update the board

        // Update the round number
        roundNumber++;

        // If all players have completed their tasks, move to the next stage
        if (checkWinCondition()) {
            nextStage();
        }

    }

    private static boolean checkWinCondition() {
        for (Task task : tasks) {
            // If the task isn't completed
            // and the task belongs to the current stage, return false
            if (!task.isCompleted() && task.getAvailableStage() == currentStage)
                return false;

        }

        return true;
    }

    private static void nextStage() {

    }

    public static void updatePlayerDisplay() {
        // TODO: Check if method is needed
    }

    public static void endGame() {
    }

}
