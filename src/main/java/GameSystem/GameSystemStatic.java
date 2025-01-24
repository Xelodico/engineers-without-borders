package GameSystem;

import BoardGame.*;

import java.util.Scanner;


public abstract class GameSystemStatic {
    // Attributes
	private static Scanner input;
	
    private static Board gameBoard;
    private static BoardGameUI gameBoardUI;
    private static Player[] turnOrder;
    private static int roundNumber;
    private static int turnNumber;
    
    private static boolean gameActive = false;

    // Methods

    public static void init() {
    	if(!gameActive) {
    		input = new Scanner(System.in);
    	
    		gameBoard = new Board(turnOrder);
    		gameBoardUI = new BoardGameUI(gameBoard, turnOrder);
    		gameBoardUI.setVisible(true);
    	
        startGame();
        gameActive = true;
    	}
    }

    private static void startGame() {
    	enterPlayers();
    	
    	System.out.println("Press Enter to start new game");
    	input.nextLine();
    	
    	initialise();
    }
    
    private static void enterPlayers() {
    	System.out.print("Please enter the number of players: ");
    	int playerNum = input.nextInt();
    	input.nextLine();
    	turnOrder = new Player[playerNum]; 
    	
    	for(int i = 0; i < this.turnOrder.length; i++) {
    		System.out.print("Please enter the name of Player " + (i+1) + ": ");
    		String name = input.nextLine();
    		turnOrder[i].setName(name);
    	}
    }
    
    private static void initialise(){
    	
    }

    public static void setTurnOrder(Player[] players) {
    	turnOrder = players;
    }

    public static void setRoundNumber(int number) {
        roundNumber = number;
    }

    public static void setTurnNumber(int turnNumber) {
        turnNumber = turnNumber;
    }

    public static Player[] getTurnOrder(){
        return turnOrder;
    }

    public static Player getPlayerAt(int index){
        return turnOrder[index];
    }
    
    public static Player getPlayerAt() {
    	return turnOrder[turnNumber];
    }

    public static int getRoundNumber(){
        return roundNumber;
    }

    public static int getTurnNumber(){
        return turnNumber;
    }

    public static void movePlayer(Direction direction) {
    	// Tell player to move (update coordinates)
    	Player currentPlayer = getPlayerAt();
    	currentPlayer.rollDie();
    	if (currentPlayer.getMovesLeft() > 0) {
    		currentPlayer.moveAction(direction);
    	}
    	
    	// Update players on board and activate the tile they fell on
    	gameBoard.updatePlayerPositions();
    	
    	Square sqrAtPosition = gameBoard.getSquareAt(currentPlayer.getCoord());
    	if(sqrAtPosition.getPrimaryOccupier != currentPlayer) { // If another player is already on the square
    		sqrAtPosition.alreadyOccupiedEffect(currentPlayer); // Activate the specific effect for multiple players
    	} else {
    		sqrAtPosition.activateSquareEffect();
    	}
    	
    }
    
    private static void nextTurn(){
    	// Change turn & round number
    	if(turnNumber >= turnOrder.length) {
    		turnNumber = 0;
    		roundNumber++;
    	} else {
    		turnNumber++;
    	}
    }

    private static void nextRound(){

    }

    public static void updatePlayerDisplay(){

    }

}
